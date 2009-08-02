package chesterfield;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

/**
 * Represents a Couchdb database which can be used to update documents
 */
public class Database
{
    private static final String DOC_COUNT = "doc_count";

    private final String name;
    private final Session session;

    public Database(String name, Session session)
    {
        this.name = name;
        this.session = session;
    }

    /**
     * Get the name of the database
     * @return database
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get the session used to create this {@link chesterfield.Database} instance
     * @return session
     */
    public Session getSession()
    {
        return session;
    }

    /**
     * Get the couch client
     * @return client
     */
    CouchClient getClient()
    {
        return getSession().getClient();
    }

    /**
     * Get the Database Url
     * @return dbUrl
     */
    String getDbUrl()
    {
        return session.getBaseUrl() + urlEncode(name) + "/";
    }

    /**
     * Get the document with the given id
     * @param id
     * @return element
     */
    public JsonObject getDocument(String id)
    {
        final JsonElement element = getDocumentAsJsonElement(id);
        return element == null ? null : element.getAsJsonObject();
    }

    private JsonElement getDocumentAsJsonElement(String id)
    {
        final CouchResult<JsonElement> result = getClient().createRequest(getDbUrl() + urlEncode(id)).execute(HttpMethod.GET);
        return result.isOK() ? result.getElement() : null;
    }

    /**
     * Get Document with the given id
     * @param id of the document
     * @param <T> type of the object to map to
     * @return document
     */
    public <T extends Document> T getDocument(String id, Class<T> t)
    {
        final JsonElement element = getDocumentAsJsonElement(id);
        if (element == null) return null;
        Gson gson = new Gson();
        return gson.fromJson(element, t);
    }

    /**
     * Deletes the document represented by the id
     * @param id
     * @return success
     */
    public boolean deleteDocumentById(String id)
    {
        return getClient().createRequest(getDbUrl() + id).execute(HttpMethod.DELETE).isOK();
    }

    /**
     * Deletes the document
     * @param document
     * @return success
     */
    public boolean deleteDocument(Document document)
    {
        return deleteDocumentById(document.getId());
    }

    /**
     * Get the total number of documents in the database
     * @return count
     */
    public int getDocumentCount()
    {
        final CouchResult<JsonObject> result = getClient().createRequest(getDbUrl()).execute(HttpMethod.GET);
        return result.getElement().get(DOC_COUNT).getAsInt();
    }

    private String urlEncode(String s)
    {
        try
        {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
