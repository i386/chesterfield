package chesterfield.jetty;

import chesterfield.*;
import org.mortbay.jetty.client.HttpClient;
import org.mortbay.jetty.client.HttpExchange;
import org.mortbay.io.ByteArrayBuffer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Implementation of {@link chesterfield.CouchClient} using the Jetty HTTP client
 */
public class JettyCouchClient implements CouchClient
{
    private final HttpClient httpClient = new HttpClient();

    public JettyCouchClient() throws Exception
    {
        httpClient.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
        httpClient.start();
    }

    public CouchRequest createRequest(final String url)
    {
        return new CouchRequest()
        {
            public CouchResult execute(HttpMethod method) throws WireException
            {
                return executeWithBody(method, null);
            }

            public CouchResult executeWithBody(HttpMethod method, String body) throws WireException
            {
                HttpExchange.ContentExchange contentExchange = new HttpExchange.ContentExchange();
                contentExchange.setMethod(method.name());
                contentExchange.setURL(url);
                
                if (body != null)
                {
                    contentExchange.setRequestContent(new ByteArrayBuffer(body));
                }

                try
                {
                    httpClient.send(contentExchange);
                }
                catch (IOException e)
                {
                    throw new WireException(e.getMessage(), e);
                }
                
                try
                {
                    contentExchange.waitForDone();
                }
                catch (InterruptedException e)
                {
                    throw new RuntimeException(e.getMessage(), e);
                }

                try
                {
                    return new CouchResult(contentExchange.getResponseStatus(), contentExchange.getResponseContent());
                }
                catch (UnsupportedEncodingException e)
                {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        };
    }
}
