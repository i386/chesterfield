package chesterfield;

/**
 * Minimum requred fields for document serialization and deserialization
 *
 * Implement this interface on your own classes so that they can be saved to couch
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
