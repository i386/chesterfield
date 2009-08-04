package chesterfield;

import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

class DocumentUtils
{
    static JsonObject changeIdAndRevFieldNamesForMapping(JsonObject jsonObject)
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
