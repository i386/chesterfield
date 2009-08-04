package chesterfield;

import com.google.gson.JsonObject;
import com.google.gson.Gson;

public class ViewResult
{
    private final String key;
    private final JsonObject value;

    ViewResult(String key, JsonObject value)
    {
        this.key = key;
        this.value = value;
    }

    /**
     * Key of the View document
     * @return key
     */
    public String getKey()
    {
        return key;
    }

    /**
     * Value of the Key document
     * @param type
     * @param <T> type to map to
     * @return value
     */
    public <T> T getValue(Class<T> type)
    {
        final Gson gson = new Gson();
        return gson.fromJson(value, type);
    }
}
