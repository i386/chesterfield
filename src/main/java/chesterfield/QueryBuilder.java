package chesterfield;

import java.util.Map;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * Used for building query strings for document and view operations
 *
 * See http://wiki.apache.org/couchdb/HTTP_view_API for more details
 */
public class QueryBuilder
{
    private Map<String, String> params;

    /**
     * Creates a new {@link chesterfield.QueryBuilder}
     */
    public QueryBuilder()
    {
        this.params = new LinkedHashMap<String, String>();
    }

    /**
     * Creates a copy of the specified {@link chesterfield.QueryBuilder}
     * @param queryBuilder copy of the query builder
     */
    public QueryBuilder(QueryBuilder queryBuilder)
    {
        this.params = new LinkedHashMap<String, String>(queryBuilder.params);
    }

    public Map<String, String> getParameters()
    {
        return Collections.unmodifiableMap(params);
    }

    public QueryBuilder key(String key)
    {
        return add("key", key);
    }

    public QueryBuilder startKey(String key)
    {
        return add("startKey", key);
    }

    public QueryBuilder endKey(String key)
    {
        return add("endKey", key);
    }

    public QueryBuilder limitTo(int limit)
    {
        return add("limit", Integer.toString(limit));
    }

    public QueryBuilder useStale()
    {
        return add("stale", "true");
    }

    public QueryBuilder orderByDecending()
    {
        return add("descending", "true");
    }

    public QueryBuilder skip(int rowsToSkip)
    {
        return add("skip", Integer.toString(rowsToSkip));
    }

    public QueryBuilder groupResults()
    {
        return add("group", "true");
    }

    public QueryBuilder dontGroupResults()
    {
        return remove("group");
    }

    public QueryBuilder disableReduce()
    {
        return add("reduce", "false");
    }

    public QueryBuilder enableReduce()
    {
        return remove("reduce");
    }

    String build()
    {
        return URLUtils.formUrlEncode(params);
    }

    private QueryBuilder remove(String key)
    {
        params.remove(key);
        return this;
    }

    private QueryBuilder add(String key, String value)
    {
        params.put(key, value);
        return this;
    }

    @Override
    public String toString()
    {
        return build();
    }
}
