package chesterfield;

import chesterfield.jetty.JettyCouchClient;

import java.util.List;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * Used to get a connection to a Couchdb database
 */
public class Session
{
    private final String host;
    private final int port;
    private final boolean ssl;

    private final CouchClient client;
    private final String baseUrl;

    /**
     * Creates a new session
     * 
     * @param host
     * @param port
     * @param ssl
     */
    public Session(String host, int port, boolean ssl)
    {
        this.host = host;
        this.port = port;
        this.ssl = ssl;

        try
        {
            client = new JettyCouchClient();
        }
        catch (Exception e) //Wow, jetty, that sort of sucks
        {
            throw new RuntimeException(e.getMessage(), e);
        }

        baseUrl = (ssl ? "https" : "http") + "://" + host + ":" + port + "/";
    }

    /**
     * Get the {@link chesterfield.CouchClient}
     * @return client
     */
    CouchClient getClient()
    {
        return client;
    }

    /**
     * Get Base URL
     * @return url
     */
    String getBaseUrl()
    {
        return baseUrl;
    }

    /**
     * Lists all the databases on the couchdb server for this session
     * @return databases
     */
    public List<Database> getDatabases() 
    {
        final String listDatabasesUrl = baseUrl + "_all_dbs";
        final CouchResult<JsonArray> result = client.createRequest(listDatabasesUrl).execute(HttpMethod.GET);
        if (result.isOK())
        {
            final ArrayList<Database> databases = new ArrayList<Database>(result.getElement().size());
            for (JsonElement element : result.getElement())
            {
                databases.add(new Database(element.getAsString(), this));
            }
            return databases;
        }
        return null;
    }

    /**
     * Lists all the database names on the couchdb server for this session
     * @return databaseNames
     */
    public List<String> getDatabaseNames()
    {
        final String listDatabasesUrl = baseUrl + "_all_dbs";
        final CouchResult<JsonArray> result = client.createRequest(listDatabasesUrl).execute(HttpMethod.GET);
        if (result.isOK())
        {
            final ArrayList<String> databases = new ArrayList<String>(result.getElement().size());
            for (JsonElement element : result.getElement())
            {
                databases.add(element.getAsString());
            }
            return databases;
        }
        return null;
    }

    /**
     * Creates a database with the specified name. Returns null if database already exists.
     * @param databaseName
     * @return database
     */
    public Database createDatabase(String databaseName)
    {
        Database database = new Database(databaseName, this);
        CouchResult result = client.createRequest(database.getDbUrl()).execute(HttpMethod.PUT);
        if (!result.isOK()) return null;
        return database;
    }

    /**
     * Gets the database with the specified name and optionally create it if it does not exist
     * @param databaseName
     * @param createIfDoesNotExist
     * @return database
     */
    public Database getDatabase(String databaseName, boolean createIfDoesNotExist)
    {
        final boolean exists = getDatabaseNames().contains(databaseName);
        if (!exists && createIfDoesNotExist)
        {
            return createDatabase(databaseName);
        }
        else if (exists)
        {
            return new Database(databaseName, this);
        }
        return null;
    }

    /**
     * Gets the database with the specifed name
     * @param databaseName
     * @return database
     */
    public Database getDatabase(String databaseName)
    {
        return getDatabase(databaseName, false);
    }

    /**
     * Deletes the specified database
     * @param databaseName
     * @return success
     */
    public boolean deleteDatabase(String databaseName)
    {
        final Database database = new Database(databaseName, this);
        return client.createRequest(database.getDbUrl()).execute(HttpMethod.DELETE).isOK();
    }

    /**
     * Deletes the specified database
     * @param database
     * @return success
     */
    public boolean deleteDatabase(Database database)
    {
        if (database == null) return false;
        return deleteDatabase(database.getName());
    }
}
