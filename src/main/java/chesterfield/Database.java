package chesterfield;

import com.google.gson.*;

/**
 * Represents a Couchdb database which can be used to update documents
 */
public class Database
{
    private static final String DOC_COUNT = "doc_count";
    private final Gson gson;

    private final String name;
    private final Session session;

    public Database(String name, Session session)
    {
        this.name = name;
        this.session = session;
        this.gson = new Gson();
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
     * Returns a valid {@link chesterfield.DesignDocument} for the given {@link chesterfield.Document}
     * Typically this is used in a natural query
     * @param document
     * @return documentOperations
     */
    public DocumentOperations forDocument(Document document)
    {
        return new DocumentOperationsImpl(getClient(), this, document, gson);
    }

    /**
     * Get Document with the given id
     *
     * @param id  of the document
     * @param <T> type of the object to map to
     * @return document
     */
    public <T extends Document> T get(String id, Class<T> t)
    {
        final CouchResult<JsonObject> result = getClient().createRequest(getDocumentUrl(id)).execute(HttpMethod.GET);
        if (!result.isOK()) return null;
        return gson.fromJson(DocumentUtils.changeIdAndRevFieldNamesForDeserialization(result.getElement()), t);
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

    public View view(String designDocument, String viewName)
    {
        return view(designDocument, viewName, null);
    }

    public View view(String designDocument, String viewName, QueryBuilder queryBuilder)
    {
        return new View(this, queryBuilder, designDocument, viewName);
    }

    /**
     * Compact the database
     */
    public void compact()
    {
        getClient().createRequest(getDbUrl() + "/_compact").execute(HttpMethod.POST);
    }

    /**
     * Get the document url
     *
     * @param id
     * @return url
     */
    private String getDocumentUrl(String id)
    {
        return getDbUrl() + URLUtils.urlEncode(id);
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
        return session.getBaseUrl() + URLUtils.urlEncode(name) + "/";
    }
}
