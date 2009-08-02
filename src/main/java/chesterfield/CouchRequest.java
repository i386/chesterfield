package chesterfield;

import com.google.gson.JsonElement;

public interface CouchRequest<T extends JsonElement>
{
    CouchResult<T> execute(String method) throws WireException;

    CouchResult<T> executeWithBody(String method, String body) throws WireException;
}
