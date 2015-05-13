package org.nkumar.ssql.translater;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class maps a type to names. Associations
 * may be marked with a capacity. Calling the get()
 * method with a type and actual size n will return
 * the associated name with smallest capacity >= n,
 * if available and an unmarked default type otherwise.
 * Eg, setting
 * <pre>
 * 	names.put(type,        "TEXT" );
 * 	names.put(type,   255, "VARCHAR($l)" );
 * 	names.put(type, 65534, "LONGVARCHAR($l)" );
 * </pre>
 * will give you back the following:
 * <pre>
 *  names.get(type)         // --> "TEXT" (default)
 *  names.get(type,    100) // --> "VARCHAR(100)" (100 is in [0:255])
 *  names.get(type,   1000) // --> "LONGVARCHAR(1000)" (1000 is in [256:65534])
 *  names.get(type, 100000) // --> "TEXT" (default)
 * </pre>
 * On the other hand, simply putting
 * <pre>
 * 	names.put(type, "VARCHAR($l)" );
 * </pre>
 * would result in
 * <pre>
 *  names.get(type)        // --> "VARCHAR($l)" (will cause trouble)
 *  names.get(type, 100)   // --> "VARCHAR(100)"
 *  names.get(type, 10000) // --> "VARCHAR(10000)"
 * </pre>
 */
//copied and modified from Hibernate project
public class TypeNames
{

    private final Map<Integer, Map<Long, String>> weighted = new HashMap<Integer, Map<Long, String>>();
    private final Map<Integer, String> defaults = new HashMap<Integer, String>();

    /**
     * get default type name for specified type
     * @param typecode the type key
     * @return the default type name associated with specified key
     */
    public String get(int typecode)
    {
        String result = defaults.get(typecode);
        if (result == null)
        {
            throw new RuntimeException("No Dialect mapping for JDBC type: " + typecode);
        }
        return result;
    }

    /**
     * get type name for specified type and size
     * @param typeCode the type key
     * @param size the SQL length
     * @param scale the SQL scale
     * @param precision the SQL precision
     * @return the associated name with smallest capacity >= size,
     *         if available and the default type name otherwise
     */
    public String get(int typeCode, long size, int precision, int scale)
    {
        Map<Long, String> map = weighted.get(typeCode);
        if (map != null && !map.isEmpty())
        {
            // iterate entries ordered by capacity to find first fit
            for (Map.Entry<Long, String> entry : map.entrySet())
            {
                if (size <= entry.getKey())
                {
                    return replace(entry.getValue(), size, precision, scale);
                }
            }
        }
        return replace(get(typeCode), size, precision, scale);
    }

    private static String replace(String type, long size, int precision, int scale)
    {
        type = replaceOnce(type, "$s", Integer.toString(scale));
        type = replaceOnce(type, "$l", Long.toString(size));
        return replaceOnce(type, "$p", Integer.toString(precision));
    }

    /**
     * set a type name for specified type key and capacity
     * @param typecode the type key
     */
    public void put(int typecode, long capacity, String value)
    {
        Map<Long, String> map = weighted.get(typecode);
        if (map == null)
        {// add new ordered map
            map = new TreeMap<Long, String>();
            weighted.put(typecode, map);
        }
        map.put(capacity, value);
    }

    /**
     * set a default type name for specified type key
     * @param typecode the type key
     */
    public void put(int typecode, String value)
    {
        defaults.put(typecode, value);
    }

    private static String replaceOnce(String template, String placeholder, String replacement)
    {
        if (template == null)
        {
            return template; // returnign null!
        }
        int loc = template.indexOf(placeholder);
        if (loc < 0)
        {
            return template;
        }
        else
        {
            return new StringBuilder(template.substring(0, loc))
                    .append(replacement)
                    .append(template.substring(loc + placeholder.length()))
                    .toString();
        }
    }

    /**
     * Get the name of the database type associated with the given
     * {@link java.sql.Types} typecode.
     * @param code The {@link java.sql.Types} typecode
     * @return the database type name
     * @throws org.hibernate.HibernateException If no mapping was specified for that type.
     */
    public String getTypeName(int code)
    {
        String result = get(code);
        if (result == null)
        {
            throw new RuntimeException("No default type mapping for (java.sql.Types) " + code);
        }
        return result;
    }

    /**
     * Get the name of the database type associated with the given
     * {@link java.sql.Types} typecode with the given storage specification
     * parameters.
     * @param code The {@link java.sql.Types} typecode
     * @param length The datatype length
     * @param precision The datatype precision
     * @param scale The datatype scale
     * @return the database type name
     * @throws HibernateException If no mapping was specified for that type.
     */
    public String getTypeName(int code, long length, int precision, int scale)
    {
        String result = get(code, length, precision, scale);
        if (result == null)
        {
            throw new RuntimeException(
                    String.format("No type mapping for java.sql.Types code: %s, length: %s", code, length));
        }
        return result;
    }

}






