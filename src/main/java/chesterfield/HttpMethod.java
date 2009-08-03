package chesterfield;

/**
 * HTTP Methods
 */
enum HttpMethod
{
    GET,
    PUT,
    POST,
    DELETE,
    HEAD,
    
    /**
     * Non-standard HTTP method provided by couch for copying documents
     */
    COPY
}
