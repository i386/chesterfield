package chesterfield;

/** Test document **/
public class MyDocument implements Document
{
    private String id;
    private String rev;
    private String helloWorld;
    private MyBean myBean = new MyBean();

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

    public MyBean getMyBean()
    {
        return myBean;
    }

    public void setMyBean(MyBean myBean)
    {
        this.myBean = myBean;
    }
}
