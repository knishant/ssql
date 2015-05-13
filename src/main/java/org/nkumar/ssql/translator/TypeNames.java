package org.nkumar.ssql.translator;

import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    private final Map<Integer, Map<Long, String>> weighted = new HashMap<>();
    private final Map<Integer, String> defaults = new HashMap<>();

    /**
     * get default type name for specified type
     * @param typecode the type key
     * @return the default type name associated with specified key
     */
    public String getTypeName(int typecode)
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
     * if available and the default type name otherwise
     */
    private String get(int typeCode, long size, int precision, int scale)
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
        return replace(getTypeName(typeCode), size, precision, scale);
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
        validateTypecode(typecode);
        Map<Long, String> map = weighted.get(typecode);
        if (map == null)
        {// add new ordered map
            map = new TreeMap<>();
            weighted.put(typecode, map);
        }
        map.put(capacity, value.toLowerCase());
    }

    /**
     * set a default type name for specified type key
     * @param typecode the type key
     */
    public void put(int typecode, String value)
    {
        validateTypecode(typecode);
        defaults.put(typecode, value.toLowerCase());
    }

    private static void validateTypecode(int typecode)
    {
        if (!TYPE_MAP.containsKey(typecode))
        {
            throw new IllegalArgumentException(typecode + " not supported");
        }
    }

    private static String replaceOnce(String template, String placeholder, String replacement)
    {
        if (template == null)
        {
            return null;
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
     * {@link Types} typecode with the given storage specification
     * parameters.
     * @param code The {@link Types} typecode
     * @param length The datatype length
     * @param precision The datatype precision
     * @param scale The datatype scale
     * @return the database type name
     * @throws RuntimeException If no mapping was specified for that type.
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

    public String toXml()
    {
        StringBuilder builder = new StringBuilder(1000);
        builder.append("<types").append(">\n");
        for (Integer typeCode : TYPE_MAP.keySet())
        {
            appendTypeDetails(typeCode, builder);
        }
        builder.append("</types>\n");
        return builder.toString();
    }

    private void appendTypeDetails(int typeCode, StringBuilder builder)
    {
        String typeName = TYPE_MAP.get(typeCode);
        builder.append("<type name='");
        builder.append(typeName).append("'");
        String defaultTemplate = defaults.get(typeCode);
        if (defaultTemplate != null)
        {
            builder.append(" template='").append(defaultTemplate.toUpperCase()).append("'");
        }
        Map<Long, String> wMap = weighted.get(typeCode);
        if (wMap == null)
        {
            builder.append("/>\n");
        }
        else
        {
            builder.append(">\n");
            for (Map.Entry<Long, String> entry : wMap.entrySet())
            {
                builder.append("<weight size='").append(entry.getKey()).append("' template='")
                        .append(entry.getValue().toUpperCase()).append("'/>\n");
            }
            builder.append("</type>\n");
        }
    }

    private static final Map<Integer, String> TYPE_MAP;

    static
    {
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(Types.INTEGER, "INTEGER");
        map.put(Types.SMALLINT, "SMALLINT");
        map.put(Types.BIGINT, "BIGINT");
        map.put(Types.FLOAT, "FLOAT");
        map.put(Types.REAL, "REAL");
        map.put(Types.DOUBLE, "DOUBLE");
        map.put(Types.NUMERIC, "NUMERIC");
        map.put(Types.BIT, "BIT");
        map.put(Types.BOOLEAN, "BOOLEAN");
        map.put(Types.CHAR, "CHAR");
        map.put(Types.VARCHAR, "VARCHAR");
        map.put(Types.CLOB, "CLOB");
        map.put(Types.NCHAR, "NCHAR");
        map.put(Types.NVARCHAR, "NVARCHAR");
        map.put(Types.NCLOB, "NCLOB");
        map.put(Types.DATE, "DATE");
        map.put(Types.TIME, "TIME");
        map.put(Types.TIMESTAMP, "TIMESTAMP");
        map.put(Types.BINARY, "BINARY");
        map.put(Types.VARBINARY, "VARBINARY");
        map.put(Types.BLOB, "BLOB");
        TYPE_MAP = Collections.unmodifiableMap(map);
    }
}






