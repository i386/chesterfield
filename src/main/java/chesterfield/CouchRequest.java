package chesterfield;

import com.google.gson.JsonElement;

/**
 * Represents a request that can be executed by a {@link chesterfield.CouchClient}
 * @param <T> JsonElement that is returned from the response body
 */
interface CouchRequest<T extends JsonElement>
{
    /**
     * Executes the request
     * @param method
     * @return result
     * @throws WireException
     */
    CouchResult<T> execute(HttpMethod method) throws WireException;

    /**
     * Executes the request with a request body
     * @param method
     * @param body
     * @return result
     * @throws WireException
     */
    CouchResult<T> executeWithBody(HttpMethod method, String body) throws WireException;
}
