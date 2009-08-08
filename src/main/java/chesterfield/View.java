package chesterfield;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

public class View
{
    private static final String IF_NONE_MATCH = "If-None-Match";
    private static final String ETAG = "ETag";

    private final Database database;
    private final String url;
    private final List<ViewResult> documents;
    private String lastKnownEtag = null;

    View(final Database database, final QueryBuilder queryBuilder, String designDocumentName, String viewName)
    {
        this.database = database;
        this.documents = new LinkedList<ViewResult>();
        
        StringBuilder sb = new StringBuilder();
        sb.append(database.getDbUrl());
        sb.append("_design/");
        sb.append(URLUtils.urlEncode(designDocumentName));
        sb.append("/_view/");
        sb.append(URLUtils.urlEncode(viewName));
        if (queryBuilder != null)
        {
            sb.append('?');
            sb.append(queryBuilder.build());
        }
        this.url = sb.toString();
    }

    /**
     * Number of documents in the {@link View}
     * @return size
     */
    public int size()
    {
        return documents.size();
    }

    public Iterable<ViewResult> getDocuments()
    {
        return new LinkedList<ViewResult>(documents);
    }

    /**
     * Updates the Documents inside of the View
     * @return rows updated. Value is -1 if the view did not update
     */
    public int update()
    {
        final CouchRequest request;
        final CouchResult<JsonObject> result;

        //If we have an etag use it to check for freshness
        if (lastKnownEtag != null)
        {
            request = database.getClient().createRequestWithHeader(url, IF_NONE_MATCH, lastKnownEtag);
        }
        else
        {
            request = database.getClient().createRequest(url);
        }

        result = request.execute(HttpMethod.GET);
        lastKnownEtag = result.getHeaders().get(ETAG);

        if (!result.notModified() || result.isOK())
        {
            documents.clear();
            
            final JsonArray jsonArray = result.getElement().get("rows").getAsJsonArray();
            final Iterator<JsonElement> iterator = jsonArray.iterator();
            while (iterator.hasNext())
            {
                JsonObject jsonObject = (JsonObject)iterator.next();
                documents.add(new ViewResult(jsonObject.get("key").getAsString(), jsonObject.get("value").getAsJsonObject()));
            }
            return result.getElement().get("total_rows").getAsInt();
        }
        return -1;
    }

    /**
     * Flushes the {@link View} of its state (eg any loaded documents, etags from previous requests, etc)
     */
    public void flush()
    {
        documents.clear();
        lastKnownEtag = null;
    }

    /**
     * Get the URL to the view
     * @return url
     */
    public String getUrl()
    {
        return url;
    }
}
