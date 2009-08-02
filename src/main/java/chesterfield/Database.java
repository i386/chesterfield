package chesterfield;

import com.google.gson.JsonObject;

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
     * Get the Database Url
     * @return dbUrl
     */
    String getDbUrl()
    {
        try
        {
            return session.getBaseUrl() + URLEncoder.encode(name, "UTF-8") + "/";
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Get the total number of documents in the database
     * @return count
     */
    public int getDocumentCount()
    {
        final CouchResult<JsonObject> result = session.getClient().createRequest(getDbUrl()).execute(HttpMethod.GET);
        return result.getElement().get(DOC_COUNT).getAsInt();
    }
}
