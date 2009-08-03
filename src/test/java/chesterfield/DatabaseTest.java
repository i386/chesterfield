package chesterfield;

public class DatabaseTest extends ChesterfieldTestCase
{
    public void testGetDocumentCount() throws Exception
    {
        assertEquals(0, getDatabase().getDocumentCount());

        MyDocument document = new MyDocument();
        document.setHelloWorld("Hello, world!");
        document.setId("123");

        MyDocument document2 = new MyDocument();
        document2.setHelloWorld("Hello, world!");
        document2.setId("456");

        getDatabase().forDocument(document).save();
        getDatabase().forDocument(document2).save();

        assertEquals(2, getDatabase().getDocumentCount());
    }

    public void testCreateSaveUpdateDelete() throws Exception
    {
        MyDocument document = new MyDocument();
        document.setHelloWorld("Hello, world!");
        document.setId("123");

        getDatabase().forDocument(document).save();

        MyDocument savedDocument = getDatabase().get("123", MyDocument.class);
        assertNotNull(savedDocument);

        assertEquals(document.getId(), savedDocument.getId());
        assertEquals(document.getRev(), savedDocument.getRev());
        assertEquals("Hello, world!", savedDocument.getHelloWorld());

        assertTrue(getDatabase().forDocument(document).delete());
        assertNull(getDatabase().get("123", MyDocument.class));
    }

    public void testCreateSaveUpdateDeleteWithServerAssignedId() throws Exception
    {
        MyDocument document = new MyDocument();
        document.setHelloWorld("Hello, world!");

        getDatabase().forDocument(document).save();

        MyDocument savedDocument = getDatabase().get(document.getId(), MyDocument.class);
        assertNotNull(savedDocument);

        assertEquals(document.getId(), savedDocument.getId());
        assertEquals(document.getRev(), savedDocument.getRev());
        assertEquals("Hello, world!", savedDocument.getHelloWorld());

        assertTrue(getDatabase().forDocument(document).delete());
        assertNull(getDatabase().get("123", MyDocument.class));
    }
}
