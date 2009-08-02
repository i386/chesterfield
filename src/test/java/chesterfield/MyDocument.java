package chesterfield;

/** Test document **/
public class MyDocument implements Document
{
    private String id;
    private String rev;
    private String helloWorld;

    public MyDocument()
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId(final String id)
    {
        this.id = id;
    }

    public String getRev()
    {
        return rev;
    }

    public void setRev(final String rev)
    {
        this.rev = rev;
    }

    public String getHelloWorld()
    {
        return helloWorld;
    }

    public void setHelloWorld(final String helloWorld)
    {
        this.helloWorld = helloWorld;
    }
}
