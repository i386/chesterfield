package chesterfield;

import java.util.List;

public class SessionTest extends ChesterfieldTestCase
{
    public void testGetDatabases() throws Exception
    {
        assertNotNull(getSession().createDatabase(ANOTHER_DATABASE_NAME));
        List<String> dbs = getSession().getDatabaseNames();
        assertTrue(dbs.contains(ANOTHER_DATABASE_NAME));
        assertTrue(dbs.contains(DATABASE_NAME));

        assertTrue(getSession().getDatabases().size() >= 1);

        for (Database database : getSession().getDatabases())
        {
            assertEquals(getSession(), database.getSession());
            assertNotNull(database.getName());
        }
    }

    public void testGetDatabase() throws Exception
    {
        assertNotNull(getSession().getDatabase(DATABASE_NAME));
        assertNull(getSession().getDatabase("does-not-exist"));
    }

    public void testGetDatabaseAndCreateIfDoesNotExist() throws Exception
    {
        assertNotNull(getSession().getDatabase(DATABASE_NAME, true));
        assertNotNull(getSession().getDatabase(ANOTHER_DATABASE_NAME, true));
    }

    public void testDelete() throws Exception
    {
        assertTrue(getSession().getDatabaseNames().contains(DATABASE_NAME));
        getSession().deleteDatabase(DATABASE_NAME);
        assertFalse(getSession().getDatabaseNames().contains(DATABASE_NAME));
    }


    public void testDeleteWithObj() throws Exception
    {
        assertTrue(getSession().getDatabaseNames().contains(DATABASE_NAME));
        getSession().deleteDatabase(getDatabase());
        assertFalse(getSession().getDatabaseNames().contains(DATABASE_NAME));
    }

    public void testDbWithSlashInName() throws Exception
    {
        Database database = getSession().createDatabase("chesterfield/testdb");
        assertNotNull(database);
        getSession().deleteDatabase(DATABASE_NAME);
    }
}
