package chesterfield;

/**
 * The {@link chesterfield.CouchClient} is used for creating requests to the couch server
 */
interface CouchClient
{
    /**
     * Creates a request
     * @param url
     * @return request
     */
    CouchRequest createRequest(String url);

    /**
     * Creates a request with a single header value
     * @param url
     * @param headerKey
     * @param headerValue
     * @return request
     */
    CouchRequest createRequestWithHeader(String url, String headerKey, String headerValue);
}
