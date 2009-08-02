package chesterfield;

/**
 * Represents a Couchdb database which can be used to update documents
 */
public class Database
{
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
        return session.getBaseUrl() + name + "/";
    }
}
