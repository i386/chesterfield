package chesterfield;

import com.google.gson.FieldNamingStrategy;

import java.lang.reflect.Field;

/**
 * Used to map the id and rev fields on the {@link chesterfield.Document} to their actual couchdb names (_id and _rev)
 */
class CouchFieldNamingStrategy implements FieldNamingStrategy
{
    private final String ID_FIELD = "id";
    private final String REV_FIELD = "rev";

    public String translateName(final Field f)
    {
        final boolean isCouchDocument = Document.class.isAssignableFrom(f.getDeclaringClass());
        if (isCouchDocument && f.getName().equals(ID_FIELD))
        {
            return "_id";
        }
        else if (isCouchDocument && f.getName().equals(REV_FIELD))
        {
            return "_rev";
        }
        return f.getName();
    }
}
