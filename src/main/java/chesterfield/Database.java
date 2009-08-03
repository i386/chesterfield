package chesterfield;

import com.google.gson.*;

/**
 * Represents a Couchdb database which can be used to update documents
 */
public class Database
{
    private static final String DOC_COUNT = "doc_count";
    private static final FieldNamingStrategy DEFAULT_FIELD_NAMING_STRATEGY = new CouchFieldNamingStrategy();
    private final Gson gson;

    private final String name;
    private final Session session;

    public Database(String name, Session session)
    {
        this.name = name;
        this.session = session;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingStrategy(DEFAULT_FIELD_NAMING_STRATEGY);
        this.gson = gsonBuilder.create();
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

    public DocumentOperations forDocument(Document document)
    {
        return new DocumentOperationsImpl(getClient(), this, document, gson);
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

    /**
     * Get Document with the given id
     *
     * @param id  of the document
     * @param <T> type of the object to map to
     * @return document
     */
    public <T extends Document> T get(String id, Class<T> t)
    {
        final CouchResult<JsonElement> result = getClient().createRequest(getDocumentUrl(id)).execute(HttpMethod.GET);
        if (!result.isOK() && result.getElement() == null) return null;
        return gson.fromJson(result.getElement(), t);
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

    /**
     * Get the document url
     *
     * @param id
     * @return url
     * @deprecated
     */
    private String getDocumentUrl(String id)
    {
        return getDbUrl() + URLUtils.urlEncode(id);
    }
}
