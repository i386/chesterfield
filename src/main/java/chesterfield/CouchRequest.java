package chesterfield;

import com.google.gson.JsonElement;

public interface CouchRequest<T extends JsonElement>
{
    CouchResult<T> execute(HttpMethod method) throws WireException;

    CouchResult<T> executeWithBody(HttpMethod method, String body) throws WireException;
}
