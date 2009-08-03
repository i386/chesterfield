package chesterfield;

import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

class URLUtils
{
    /**
     * URL Encode the passed string and wrap any exceptions in a {@link RuntimeException}
     *
     * @param s string to encode
     * @return encodedString
     */
    static String urlEncode(String s)
    {
        try
        {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Encodes Map as a form url
     * @param params
     * @return urlString
     */
    static String formUrlEncode(Map<String, String> params)
    {
        final List<Map.Entry> entries = new ArrayList(params.entrySet());
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < entries.size(); i++)
        {
            final Map.Entry<String, String> entry = entries.get(i);
            if (entry.getKey() != null)
            {
                if (i > 0)
                {
                    builder.append("&");
                }
                builder.append(urlEncode(entry.getKey()));
                builder.append("=");
                if (entry.getValue() != null)
                {
                    builder.append(urlEncode(entry.getValue()));
                }
            }
        }
        return builder.toString();
    }
}
