package chesterfield;

import org.mortbay.jetty.client.HttpClient;
import org.mortbay.jetty.client.ContentExchange;
import org.mortbay.jetty.HttpFields;
import org.mortbay.io.nio.IndirectNIOBuffer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.nio.ByteBuffer;

/**
 * Implementation of {@link chesterfield.CouchClient} using the Jetty HTTP client
 */
class JettyCouchClient implements CouchClient
{
    private static final String APPLICATION_JSON = "application/json";
    private final HttpClient httpClient = new HttpClient();

    public JettyCouchClient() throws Exception
    {
        httpClient.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
        httpClient.start();
    }

    public CouchRequest createRequestWithHeader(String url, String headerKey, String headerValue)
    {
        return new JettyRequest(url, headerKey, headerValue);
    }

    public CouchRequest createRequest(final String url)
    {
        return new JettyRequest(url, null, null);
    }

    class JettyRequest implements CouchRequest
    {
        private final String url;
        private final String headerKey;
        private final String headerValue;

        JettyRequest(String url, String headerKey, String headerValue)
        {
            this.url = url;
            this.headerKey = headerKey;
            this.headerValue = headerValue;
        }

        public CouchResult execute(HttpMethod method) throws WireException
        {
            return executeWithBody(method, null);
        }

        public CouchResult executeWithBody(HttpMethod method, ByteBuffer body) throws WireException
        {
            final ContentExchange contentExchange = new ContentExchange(true);
            contentExchange.setMethod(method.name());
            contentExchange.setURL(url);
            contentExchange.setRequestContentType(APPLICATION_JSON);

            if (headerKey != null && headerValue != null) contentExchange.setRequestHeader(headerKey, headerValue);

            if (body != null)
            {
                contentExchange.setRequestContent(new IndirectNIOBuffer(body, true));
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
                final Map<String, String> headers = getHeaders(contentExchange.getResponseFields());
                return new CouchResult(contentExchange.getResponseStatus(), contentExchange.getResponseContent(), headers);
            }
            catch (UnsupportedEncodingException e)
            {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        private Map<String, String> getHeaders(HttpFields httpFields)
        {
            final Map<String, String> headers = new HashMap<String, String>();
            if (httpFields != null)
            {
                final Iterator<HttpFields.Field> iterator = httpFields.getFields();
                while (iterator.hasNext())
                {
                    final HttpFields.Field field = iterator.next();
                    headers.put(field.getName(), field.getValue());
                }
            }
            return headers;
        }
    }
}
