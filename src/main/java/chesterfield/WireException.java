package chesterfield;

public class WireException extends RuntimeException
{
    public WireException()
    {
        super();
    }

    public WireException(String s)
    {
        super(s);
    }

    public WireException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public WireException(Throwable throwable)
    {
        super(throwable);
    }
}
