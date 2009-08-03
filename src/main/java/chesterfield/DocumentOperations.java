package chesterfield;

/**
 * Operations that can be performed on a {@link chesterfield.Document}
 */
public interface DocumentOperations
{
    /**
     * Delete the document
     * @return success
     */
    boolean delete();

    /**
     * Save the document
     * @return success
     */
    boolean save();

    /**
     * Copy the document
     * @param newId id of the new 
     * @return success
     */
    boolean copy(String newId);
}