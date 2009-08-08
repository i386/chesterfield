package chesterfield;

import com.google.gson.*;

import java.nio.charset.Charset;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

class DocumentOperationsImpl implements DocumentOperations
{
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private final CouchClient couchClient;
    private final Database database;
    private final Document document;
    private final Gson gson;

    DocumentOperationsImpl(CouchClient couchClient, Database database, Document document, Gson gson)
    {
        this.couchClient = couchClient;
        this.database = database;
        this.document = document;
        this.gson = gson;
    }

    public boolean delete()
    {
        final String url = getDocumentUrl() + "?rev=" + document.getRev();
        final CouchResult<JsonObject> result = couchClient.createRequest(url).execute(HttpMethod.DELETE);
        return result.getElement().get("ok") != null && result.isOK();
    }

    public boolean save()
    {
        final HttpMethod method = document.getId() == null ? HttpMethod.POST : HttpMethod.PUT;
        final String url = document.getId() == null ? database.getDbUrl() : getDocumentUrl();

        final JsonObject element = (JsonObject)gson.toJsonTree(document);
        DocumentUtils.changeIdAndRevFieldNamesForSerialization(element);
        final String body = gson.toJson(element);
        final ByteBuffer byteBuffer = CHARSET.encode(CharBuffer.wrap(body));
        final CouchResult<JsonObject> result = couchClient.createRequest(url).executeWithBody(method, byteBuffer);

        if (result.isOK())
        {
            document.setId(result.getElement().get("id").getAsString());
            document.setRev(result.getElement().get("rev").getAsString());
        }

        return result.isOK();
    }

    public boolean copy(String name)
    {
        return couchClient.createRequestWithHeader(getDocumentUrl(), "Destination", name).execute(HttpMethod.COPY).isOK();
    }

    /**
     * Get the document url
     * @return url
     */
    private String getDocumentUrl()
    {
        return database.getDbUrl() + URLUtils.urlEncode(document.getId());
    }
}