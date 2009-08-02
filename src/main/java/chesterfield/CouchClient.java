package chesterfield;

/**
 * The {@link chesterfield.CouchClient} is used for creating requests to the couch server
 */
public interface CouchClient
{
    CouchRequest createRequest(String url);
}
