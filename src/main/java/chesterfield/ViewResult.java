package chesterfield;

import com.google.gson.JsonObject;
import com.google.gson.Gson;

public class ViewResult
{
    private final String key;
    private final JsonObject value;
    private final Gson gson;

    ViewResult(String key, JsonObject value, Gson gson)
    {
        this.key = key;
        this.value = DocumentUtils.changeIdAndRevFieldNamesForDeserialization(value);
        this.gson = gson;
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
        if (value == null) return null;
        return gson.fromJson(value, type);
    }
}
