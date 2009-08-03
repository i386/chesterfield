package chesterfield;

import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

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
}
