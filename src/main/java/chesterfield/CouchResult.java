package chesterfield;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.Map;

/**
 * Result from {@link chesterfield.CouchRequest}
 * @param <T> JsonElement impl to map back to
 */
class CouchResult<T extends JsonElement>
{
    private final int responseCode;
    private final T element;
    private final Map<String, String> headers;

    /**
     * Constructs a couch result
     * @param responseCode
     * @param body set to null if no JSON element is available eg no body for response
     * @param headers the response headers
     */
    public CouchResult(int responseCode, String body, Map<String, String> headers)
    {
        this.responseCode = responseCode;
        this.element = (body != null ? (T)new JsonParser().parse(body) : null);
        this.headers = headers;
    }

    /**
     * Get the response code
     * @return responseCode
     */
    public int getResponseCode()
    {
        return responseCode;
    }

    /**
     * Get the JSON element
     * @return element
     */
    public T getElement()
    {
        return element;
    }

    /**
     * Sent back a 2xx response code
     * @return ok
     */
    public boolean isOK()
    {
        return responseCode >= 200 && responseCode < 300;
    }

    /**
     * Response headers
     * @return headers
     */
    public Map<String, String> getHeaders()
    {
        return headers;
    }
}
