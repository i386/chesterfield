package chesterfield;

public interface DocumentOperations
{
    boolean delete();

    boolean save();

    boolean copy(String newId);
}