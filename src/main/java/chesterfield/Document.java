package chesterfield;

import com.google.gson.annotations.SerializedName;

/**
 * Minimum requred fields for document serialization and deserialization 
 */
public interface Document
{
    /**
     * Document Id
     * @return id
     */
    String getId();
    void setId(String id);

    /**
     * Revision id
     * @return rev
     */
    String getRev();
    void setRev(String rev);
}
