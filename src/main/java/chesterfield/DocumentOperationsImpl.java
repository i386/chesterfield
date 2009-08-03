package chesterfield;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

class DocumentOperationsImpl implements DocumentOperations
{
    final CouchClient couchClient;
    final Database database;
    final Document document;
    final Gson gson;

    DocumentOperationsImpl(CouchClient couchClient, Database database, Document document, Gson gson)
    {
        this.couchClient = couchClient;
        this.database = database;
        this.document = document;
        this.gson = gson;
    }

    public boolean delete()
    {
        final HttpMethod method = document.getId() == null ? HttpMethod.POST : HttpMethod.PUT;
        final String url = document.getId() == null ? database.getDbUrl() : getDocumentUrl(document.getId());
        final CouchResult<JsonObject> result = couchClient.createRequest(url).executeWithBody(method, gson.toJson(document));

        if (result.isOK())
        {
            document.setId(result.getElement().get("id").getAsString());
            document.setRev(result.getElement().get("rev").getAsString());
        }

        return result.isOK();
    }

    public boolean save()
    {
        final HttpMethod method = document.getId() == null ? HttpMethod.POST : HttpMethod.PUT;
        final String url = document.getId() == null ? database.getDbUrl() : getDocumentUrl(document.getId());
        final CouchResult<JsonObject> result = couchClient.createRequest(url).executeWithBody(method, gson.toJson(document));

        if (result.isOK())
        {
            document.setId(result.getElement().get("id").getAsString());
            document.setRev(result.getElement().get("rev").getAsString());
        }

        return result.isOK();
    }

    public boolean copy(String name)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the document url
     *
     * @param id
     * @return url
     */
    private String getDocumentUrl(String id)
    {
        return database.getDbUrl() + URLUtils.urlEncode(id);
    }
}