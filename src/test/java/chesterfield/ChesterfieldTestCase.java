package chesterfield;

import junit.framework.TestCase;

public abstract class ChesterfieldTestCase extends TestCase
{
    protected static final String DATABASE_NAME = "chesterfield-test-db";
    
    private Session session;
    private Database database;

    protected Database getDatabase()
    {
        return database;
    }

    protected Session getSession()
    {
        return session;
    }

    @Override
    protected void setUp() throws Exception
    {
        session = new Session("localhost", 5984, false);
        session.deleteDatabase(DATABASE_NAME);
        database = session.createDatabase(DATABASE_NAME);
    }

    @Override
    protected void tearDown() throws Exception
    {
        session.deleteDatabase(DATABASE_NAME);
    }
}
