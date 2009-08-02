package chesterfield;

public class SessionTest extends ChesterfieldTestCase
{
    public void testGetDatabaseNames() throws Exception
    {
        final String anotherDbName = "chester-field-another-test-db";
        assertNotNull(getSession().createDatabase(anotherDbName));
        
    }
}
