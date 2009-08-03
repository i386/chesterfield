package chesterfield;

public class QueryBuilderTest extends ChesterfieldTestCase
{
    public void testBuildsValidQueryString() throws Exception
    {
        QueryBuilder builder = new QueryBuilder().startKey("start").endKey("end");
        assertEquals("start", builder.getParameters().get("startKey"));
        assertEquals("end", builder.getParameters().get("endKey"));
        assertEquals(2, builder.getParameters().size());
        assertEquals("startKey=start&endKey=end", builder.build());
    }

    public void testBuildQueryStringWithEncodedChars() throws Exception
    {
        assertEquals("key=i%5Clike%5Cslashes%5C", new QueryBuilder().key("i\\like\\slashes\\").build());
    }
}
