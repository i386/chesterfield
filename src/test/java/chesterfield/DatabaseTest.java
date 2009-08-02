package chesterfield;

public class DatabaseTest extends ChesterfieldTestCase
{
    public void testGetDocumentCount() throws Exception
    {
        assertEquals(0, getDatabase().getDocumentCount());
    }

    public void testCreateSaveUpdateDelete() throws Exception
    {
        MyDocument document = new MyDocument();
        document.setHelloWorld("Hello, world!");
        document.setId("123");

        assertTrue(getDatabase().save(document));

        MyDocument savedDocument = getDatabase().getDocument("123", MyDocument.class);
        assertNotNull(savedDocument);

        assertEquals(document.getId(), savedDocument.getId());
        assertEquals(savedDocument.getRev(), savedDocument.getRev());
        assertEquals("Hello, world!", savedDocument.getHelloWorld());

        assertTrue(getDatabase().deleteDocument(savedDocument));
        assertNull(getDatabase().getDocument("123", MyDocument.class));
    }

    public void testCreateSaveUpdateDeleteWithServerAssignedId() throws Exception
    {
        MyDocument document = new MyDocument();
        document.setHelloWorld("Hello, world!");

        assertTrue(getDatabase().save(document));

        MyDocument savedDocument = getDatabase().getDocument(document.getId(), MyDocument.class);
        assertNotNull(savedDocument);

        assertEquals(document.getId(), savedDocument.getId());
        assertEquals(savedDocument.getRev(), savedDocument.getRev());
        assertEquals("Hello, world!", savedDocument.getHelloWorld());

        assertTrue(getDatabase().deleteDocument(savedDocument));
        assertNull(getDatabase().getDocument("123", MyDocument.class));
    }
}
