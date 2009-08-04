package chesterfield;

import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

class DocumentUtils
{
    static JsonObject changeIdAndRevFieldNamesForSerialization(JsonObject jsonObject)
    {
        JsonElement rev = jsonObject.get("rev");
        JsonElement id = jsonObject.get("id");
        if (rev != null)
        {
            jsonObject.remove("rev");
            jsonObject.add("_rev", rev);
        }

        if (id != null)
        {
            jsonObject.remove("id");
            jsonObject.add("_id", id);
        }
        return jsonObject;
    }

    static JsonObject changeIdAndRevFieldNamesForDeserialization(JsonObject jsonObject)
    {
        JsonElement rev = jsonObject.get("_rev");
        JsonElement id = jsonObject.get("_id");
        if (rev != null)
        {
            jsonObject.remove("_rev");
            jsonObject.add("rev", rev);
        }

        if (id != null)
        {
            jsonObject.remove("_id");
            jsonObject.add("id", id);
        }
        return jsonObject;
    }
}
