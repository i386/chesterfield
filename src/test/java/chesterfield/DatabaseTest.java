package chesterfield;

public class DatabaseTest extends ChesterfieldTestCase
{
    public void testGetDocumentCount() throws Exception
    {
        assertEquals(0, getDatabase().getDocumentCount());
    }
}
