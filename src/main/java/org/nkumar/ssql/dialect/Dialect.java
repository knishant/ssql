package org.nkumar.ssql.dialect;

import org.nkumar.ssql.translator.TypeNames;

import java.sql.Types;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Dialect
{
    private final String dbName;

    protected final TypeNames typeNames = new TypeNames();

    protected final Map<String, String> functions = new TreeMap<>();

    private final Set<String> keywords = new TreeSet<>();

    public Dialect(String dbName)
    {
        this.dbName = dbName;
        typeNames.put(Types.BIT, "bit");
        typeNames.put(Types.BOOLEAN, "boolean");
//        typeNames.put(Types.TINYINT, "tinyint");
        typeNames.put(Types.SMALLINT, "smallint");
        typeNames.put(Types.INTEGER, "int");
        typeNames.put(Types.BIGINT, "bigint");
        typeNames.put(Types.FLOAT, "float");
        typeNames.put(Types.DOUBLE, "double precision");
        typeNames.put(Types.NUMERIC, "numeric($p,$s)");
        typeNames.put(Types.REAL, "real");

        typeNames.put(Types.DATE, "date");
        typeNames.put(Types.TIME, "time");
        typeNames.put(Types.TIMESTAMP, "timestamp");

        typeNames.put(Types.VARBINARY, "bit varying($l)");
        typeNames.put(Types.BLOB, "blob");

        typeNames.put(Types.CHAR, "char($l)");
        typeNames.put(Types.VARCHAR, "varchar($l)");
        typeNames.put(Types.CLOB, "clob");

        typeNames.put(Types.NCHAR, "nchar($l)");
        typeNames.put(Types.NVARCHAR, "nvarchar($l)");
        typeNames.put(Types.NCLOB, "nclob");

        functions.put("current_date", "current_date");
        functions.put("current_time", "current_time");
        functions.put("current_timestamp", "current_timestamp");
    }

    public String getDbName()
    {
        return dbName;
    }

    public final void addKeywords(String... strs)
    {
        for (String str : strs)
        {
            keywords.add(str.trim().toLowerCase());
        }
    }

    public final boolean isKeyword(String str)
    {
        return str != null && keywords.contains(str.toLowerCase());
    }

    public String getAddColumnString()
    {
        return "add column";
    }

    public final String toXml()
    {
        StringBuilder builder = new StringBuilder(1000);
        builder.append("<mapping dbname='").append(dbName).append("'>\n");
        builder.append(typeNames.toXml());
        builder.append("</mapping>\n");
        if (!keywords.isEmpty())
        {
            builder.append("<keywords>\n");
            for (String keyword : keywords)
            {
                builder.append("<keyword value='").append(keyword.toUpperCase()).append("'/>\n");
            }
            builder.append("</keywords>\n");
        }
        if (!functions.isEmpty())
        {
            builder.append("<functions>\n");
            for (Map.Entry<String, String> entry : functions.entrySet())
            {
                builder.append("<function name='")
                        .append(entry.getKey().toUpperCase()).append("' mapping='")
                        .append(entry.getValue().toUpperCase()).append("'/>\n");
            }
            builder.append("</functions>\n");
        }
        return builder.toString();
    }

    public final String getTypeName(int typecode)
    {
        return typeNames.getTypeName(typecode);
    }

    public final String getTypeName(int code, long length, int precision, int scale)
    {
        return typeNames.getTypeName(code, length, precision, scale);
    }

    public final String getFunctionName(String func)
    {
        return functions.get(func);
    }

    public String getCascadeConstraintsString()
    {
        return "/*cascade constraints*/";
    }

    public boolean supportsIfExistsBeforeTableName()
    {
        return false;
    }

    public boolean supportsSequences()
    {
        return false;
    }

    public String getNoCycleSequenceString()
    {
        if (!supportsSequences())
        {
            throw new IllegalStateException("dialect does not support sequences");
        }
        return "no cycle";
    }

    public String getNoCacheSequenceString()
    {
        if (!supportsSequences())
        {
            throw new IllegalStateException("dialect does not support sequences");
        }
        return "no cache";
    }

    public char openQuote()
    {
        return '"';
    }

    public char closeQuote()
    {
        return '"';
    }

    public boolean needsSpaceAfterDoubleDashComment()
    {
        return false;
    }

    public String getTableTypeString()
    {
        return "";
    }

    public boolean supportsDefaultCurrentDate()
    {
        return true;
    }

    public boolean supportsDefaultCurrentTime()
    {
        return true;
    }

    public boolean supportsDefaultCurrentTimestamp()
    {
        return true;
    }

    public String getNullColumnString()
    {
        return "";
    }

    public boolean isIdentity()
    {
        return false;
    }
}
