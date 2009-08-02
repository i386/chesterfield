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

    private final CouchClient couchClient;

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
            couchClient = new JettyCouchClient();
        }
        catch (Exception e) //Wow, jetty, that sort of sucks
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Lists all the databases on the couchdb server for this session
     * @return databases
     */
    public List<Database> getDatabases() 
    {
        final CouchResult<JsonArray> result = couchClient.createRequest("http://localhost:5984/_all_dbs").execute("GET");
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
    public String[] getDatabaseNames()
    {
        return null;
    }

    /**
     * Creates a database with the specified name. Returns null if database already exists.
     * @param databaseName
     * @return database
     */
    public Database createDatabase(String databaseName)
    {
        return null;
    }

    /**
     * Gets the database with the specified name and optionally create it if it does not exist
     * @param databaseName
     * @param createIfDoesNotExist
     * @return database
     */
    public Database getDatabase(String databaseName, boolean createIfDoesNotExist)
    {
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
        return false;
    }

    /**
     * Deletes the specified database
     * @param database
     * @return success
     */
    public boolean deleteDatabase(Database database)
    {
        return deleteDatabase(database.getName());
    }
}
