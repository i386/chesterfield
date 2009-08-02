package chesterfield;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Result from {@link chesterfield.CouchRequest}
 * @param <T> JsonElement impl to map back to
 */
public class CouchResult<T extends JsonElement>
{
    private final int responseCode;
    private final T element;

    public CouchResult(int responseCode, String body)
    {
        this.responseCode = responseCode;
        this.element = (T)new JsonParser().parse(body);
    }

    public int getResponseCode()
    {
        return responseCode;
    }

    public T getElement()
    {
        return element;
    }

    public boolean isOK()
    {
        return responseCode == 200;
    }
}
