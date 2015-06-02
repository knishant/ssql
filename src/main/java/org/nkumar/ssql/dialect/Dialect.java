package org.nkumar.ssql.dialect;

import org.nkumar.ssql.translator.TypeNames;

import java.sql.Types;
import java.util.Collection;
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

        //reserved word sql 99
        addKeywords(("ABSOLUTE,ACTION,ADD,AFTER,ALL,ALLOCATE,ALTER,AND,ANY,ARE,ARRAY,AS,ASC,ASSERTION,AT," +
                "AUTHORIZATION,BEFORE,BEGIN,BETWEEN,BINARY,BIT,BLOB,BOOLEAN,BOTH,BREADTH,BY,CALL,CASCADE,CASCADED," +
                "CASE,CAST,CATALOG,CHAR,CHARACTER,CHECK,CLOB,CLOSE,COLLATE,COLLATION,COLUMN,COMMIT,CONDITION," +
                "CONNECT,CONNECTION,CONSTRAINT,CONSTRAINTS,CONSTRUCTOR,CONTINUE,CORRESPONDING,CREATE,CROSS,CUBE," +
                "CURRENT,CURRENT_DATE,CURRENT_DEFAULT_TRANSFORM_GROUP,CURRENT_TRANSFORM_GROUP_FOR_TYPE,CURRENT_PATH," +
                "CURRENT_ROLE,CURRENT_TIME,CURRENT_TIMESTAMP,CURRENT_USER,CURSOR,CYCLE,DATA,DATE,DAY,DEALLOCATE," +
                "DEC,DECIMAL,DECLARE,DEFAULT,DEFERRABLE,DEFERRED,DELETE,DEPTH,DEREF,DESC,DESCRIBE,DESCRIPTOR," +
                "DETERMINISTIC,DIAGNOSTICS,DISCONNECT,DISTINCT,DO,DOMAIN,DOUBLE,DROP,DYNAMIC,EACH,ELSE,ELSEIF,END," +
                "END-EXEC,EQUALS,ESCAPE,EXCEPT,EXCEPTION,EXEC,EXECUTE,EXISTS,EXIT,EXTERNAL,FALSE,FETCH,FIRST,FLOAT," +
                "FOR,FOREIGN,FOUND,FROM,FREE,FULL,FUNCTION,GENERAL,GET,GLOBAL,GO,GOTO,GRANT,GROUP,GROUPING,HANDLE," +
                "HAVING,HOLD,HOUR,IDENTITY,IF,IMMEDIATE,IN,INDICATOR,INITIALLY,INNER,INOUT,INPUT,INSERT,INT,INTEGER," +
                "INTERSECT,INTERVAL,INTO,IS,ISOLATION,JOIN,KEY,LANGUAGE,LARGE,LAST,LATERAL,LEADING,LEAVE,LEFT,LEVEL," +
                "LIKE,LOCAL,LOCALTIME,LOCALTIMESTAMP,LOCATOR,LOOP,MAP,MATCH,METHOD,MINUTE,MODIFIES,MODULE,MONTH," +
                "NAMES,NATIONAL,NATURAL,NCHAR,NCLOB,NESTING,NEW,NEXT,NO,NONE,NOT,NULL,NUMERIC,OBJECT,OF,OLD,ON,ONLY," +
                "OPEN,OPTION,OR,ORDER,ORDINALITY,OUT,OUTER,OUTPUT,OVERLAPS,PAD,PARAMETER,PARTIAL,PATH,PRECISION," +
                "PREPARE,PRESERVE,PRIMARY,PRIOR,PRIVILEGES,PROCEDURE,PUBLIC,READ,READS,REAL,RECURSIVE,REDO,REF," +
                "REFERENCES,REFERENCING,RELATIVE,RELEASE,REPEAT,RESIGNAL,RESTRICT,RESULT,RETURN,RETURNS,REVOKE,RIGHT," +
                "ROLE,ROLLBACK,ROLLUP,ROUTINE,ROW,ROWS,SAVEPOINT,SCHEMA,SCROLL,SEARCH,SECOND,SECTION,SELECT,SESSION," +
                "SESSION_USER,SET,SETS,SIGNAL,SIMILAR,SIZE,SMALLINT,SOME,SPACE,SPECIFIC,SPECIFICTYPE,SQL," +
                "SQLEXCEPTION,SQLSTATE,SQLWARNING,START,STATE,STATIC,SYSTEM_USER,TABLE,TEMPORARY,THEN,TIME,TIMESTAMP," +
                "TIMEZONE_HOUR,TIMEZONE_MINUTE,TO,TRAILING,TRANSACTION,TRANSLATION,TREAT,TRIGGER,TRUE,UNDER,UNDO," +
                "UNION,UNIQUE,UNKNOWN,UNNEST,UNTIL,UPDATE,USAGE,USER,USING,VALUE,VALUES,VARCHAR,VARYING,VIEW,WHEN," +
                "WHENEVER,WHERE,WHILE,WITH,WITHOUT,WORK,WRITE,YEAR,ZONE").toLowerCase().split(","));

        //non-reserved word sql 99
        addKeywords(("ABS,ADA,ADMIN,ASENSITIVE,ASSIGNMENT,ASYMMETRIC,ATOMIC,ATTRIBUTE,AVG,BIT_LENGTH,C,CALLED," +
                "CARDINALITY,CATALOG_NAME,CHAIN,CHAR_LENGTH,CHARACTERISTICS,CHARACTER_LENGTH,CHARACTER_SET_CATALOG," +
                "CHARACTER_SET_NAME,CHARACTER_SET_SCHEMA,CHECKED,CLASS_ORIGIN,COALESCE,COBOL,COLLATION_CATALOG," +
                "COLLATION_NAME,COLLATION_SCHEMA,COLUMN_NAME,COMMAND_FUNCTION,COMMAND_FUNCTION_CODE,COMMITTED," +
                "CONDITION_IDENTIFIER,CONDITION_NUMBER,CONNECTION_NAME,CONSTRAINT_CATALOG,CONSTRAINT_NAME," +
                "CONSTRAINT_SCHEMA,CONTAINS,CONVERT,COUNT,CURSOR_NAME,DATETIME_INTERVAL_CODE," +
                "DATETIME_INTERVAL_PRECISION,DEFINED,DEFINER,DEGREE,DERIVED,DISPATCH,EVERY,EXTRACT,FINAL,FORTRAN,G," +
                "GENERATED,GRANTED,HIERARCHY,IMPLEMENTATION,INSENSITIVE,INSTANCE,INSTANTIABLE,INVOKER,K,KEY_MEMBER," +
                "KEY_TYPE,LENGTH,LOWER,M,MAX,MIN,MESSAGE_LENGTH,MESSAGE_OCTET_LENGTH,MESSAGE_TEXT,MOD,MORE,MUMPS," +
                "NAME,NULLABLE,NUMBER,NULLIF,OCTET_LENGTH,ORDERING,OPTIONS,OVERLAY,OVERRIDING,PASCAL,PARAMETER_MODE," +
                "PARAMETER_NAME,PARAMETER_ORDINAL_POSITION,PARAMETER_SPECIFIC_CATALOG,PARAMETER_SPECIFIC_NAME," +
                "PARAMETER_SPECIFIC_SCHEMA,PLI,POSITION,REPEATABLE,RETURNED_CARDINALITY,RETURNED_LENGTH," +
                "RETURNED_OCTET_LENGTH,RETURNED_SQLSTATE,ROUTINE_CATALOG,ROUTINE_NAME,ROUTINE_SCHEMA,ROW_COUNT," +
                "SCALE,SCHEMA_NAME,SCOPE,SECURITY,SELF,SENSITIVE,SERIALIZABLE,SERVER_NAME,SIMPLE,SOURCE," +
                "SPECIFIC_NAME,STATEMENT,STRUCTURE,STYLE,SUBCLASS_ORIGIN,SUBSTRING,SUM,SYMMETRIC,SYSTEM,TABLE_NAME," +
                "TOP_LEVEL_COUNT,TRANSACTIONS_COMMITTED,TRANSACTIONS_ROLLED_BACK,TRANSACTION_ACTIVE,TRANSFORM," +
                "TRANSFORMS,TRANSLATE,TRIGGER_CATALOG,TRIGGER_SCHEMA,TRIGGER_NAME,TRIM,TYPE,UNCOMMITTED," +
                "UNNAMED,UPPER").toLowerCase().split(","));
    }

    public final String getDbName()
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
        Collection<String> kwds = new TreeSet<>(keywords);
        if (!isIdentity())
        {
            Dialect dialect = new Dialect("base");
            kwds.removeAll(dialect.keywords);
        }
        if (!kwds.isEmpty())
        {
            builder.append("<keywords>\n");
            for (String keyword : kwds)
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

    public boolean needsCheckConstraintForNumericBoolean()
    {
        return false;
    }
}
