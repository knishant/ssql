package org.nkumar.ssql.translater;


import org.nkumar.ssql.model.ADColumn;
import org.nkumar.ssql.model.AddColumnStatement;
import org.nkumar.ssql.model.AddTableConstraintStatement;
import org.nkumar.ssql.model.AlterTableStatement;
import org.nkumar.ssql.model.Column;
import org.nkumar.ssql.model.Comment;
import org.nkumar.ssql.model.CreateEntityStatement;
import org.nkumar.ssql.model.CreateIndexStatement;
import org.nkumar.ssql.model.CreateSequenceStatement;
import org.nkumar.ssql.model.CreateTableStatement;
import org.nkumar.ssql.model.DropColumnStatement;
import org.nkumar.ssql.model.DropIndexStatement;
import org.nkumar.ssql.model.DropSequenceStatement;
import org.nkumar.ssql.model.DropTableConstraintStatement;
import org.nkumar.ssql.model.DropTableStatement;
import org.nkumar.ssql.model.ForeignKeyTableConstraint;
import org.nkumar.ssql.model.InsertStatement;
import org.nkumar.ssql.model.PlaceHolder;
import org.nkumar.ssql.model.PrecisionScaleBean;
import org.nkumar.ssql.model.PredefinedType;
import org.nkumar.ssql.model.References;
import org.nkumar.ssql.model.TableConstraint;
import org.nkumar.ssql.model.UniqueTableConstraint;
import org.nkumar.ssql.model.Value;
import org.nkumar.ssql.util.Util;

import java.sql.Types;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class GenericTranslatorSqlVisitor implements TranslatorSqlVisitor
{
    private static final String PADDEDCOLUMN_MODE = "paddedcolumn";

    private final StringBuilder buffer = new StringBuilder(8 * 1000);
    private final Mode mode = new Mode();

    private final String dbName;

    protected final TypeNames typeNames = new TypeNames();

    protected final Map<String, String> functions = new TreeMap<>();

    private final Set<String> keywords = new TreeSet<>();

    protected final CaseHandler caseHandler = CaseHandler.Factory.create(CaseHandler.Factory.UPPER);

    protected GenericTranslatorSqlVisitor(String dbName)
    {
        this.dbName = dbName;
        registerTypes();
        registerFunctions();
    }

    private void registerFunctions()
    {
        functions.put("current_date", "current_date");
        functions.put("current_time", "current_time");
        functions.put("current_timestamp", "current_timestamp");
    }

    private void registerTypes()
    {
        typeNames.put(Types.BIT, "bit");
        typeNames.put(Types.BOOLEAN, "boolean");
        typeNames.put(Types.TINYINT, "tinyint");
        typeNames.put(Types.SMALLINT, "smallint");
        typeNames.put(Types.INTEGER, "integer");
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
    }

    @Override
    public String getGeneratedSql()
    {
        return buffer.toString();
    }

    @Override
    public void clearBuffer()
    {
        this.buffer.setLength(0);
    }

    @Override
    public String getDbName()
    {
        return dbName;
    }

    @Override
    public final void visit(AddColumnStatement statement)
    {
        startAlterStatement(statement);
        buffer.append(caseHandler.transform(getAddColumnString())).append(" ");
        statement.getColumn().accept(this);
        terminateStatement();
    }

    protected String getAddColumnString()
    {
        return "add column";
    }

    protected void terminateStatement()
    {
        buffer.append(";\n\n");
    }

    @Override
    public final void visit(AddTableConstraintStatement statement)
    {
        startAlterStatement(statement);
        buffer.append(caseHandler.transform("add constraint "));
        statement.getTableConstraint().accept(this);
        terminateStatement();
    }

    @Override
    public final void visit(DropColumnStatement statement)
    {
        startAlterStatement(statement);
        buffer.append("drop column ").append(getQuotedIdentifier(statement.getColumnName()));
        terminateStatement();
    }

    @Override
    public final void visit(DropTableConstraintStatement statement)
    {
        startAlterStatement(statement);
        //foreign key constraint are handled differently in mysql
//        dialect.getDropForeignKeyString() +
        buffer.append(caseHandler.transform("drop constraint ")).append(
                getQuotedIdentifier(statement.getConstraintName()));
        terminateStatement();
    }

    private void startAlterStatement(AlterTableStatement statement)
    {
        appendComments(statement.getComments());
        buffer.append(caseHandler.transform("alter table "))
                .append(getQuotedIdentifier(statement.getTableName())).append(" ");
    }

    @Override
    public final void visit(UniqueTableConstraint constraint)
    {
        buffer.append(getQuotedIdentifier(constraint.getConstraintName())).append(" ");
        if (TableConstraint.PRIMARY_KEY.equals(constraint.getType()))
        {
            buffer.append(caseHandler.transform("primary key "));
        }
        if (TableConstraint.UNIQUE.equals(constraint.getType()))
        {
            buffer.append(caseHandler.transform("unique "));
        }
        buffer.append("(");
        appendQuotedIdentifiers(constraint.getColumnList());
        buffer.append(")");
    }

    @Override
    public final void visit(References references)
    {
        buffer.append(caseHandler.transform("references "))
                .append(getQuotedIdentifier(references.getTableName())).append(" (");
        appendQuotedIdentifiers(references.getColumnNames());
        buffer.append(")");
        if (references.isOnDeleteCascade())
        {
            if (supportsCascadeDelete())
            {
                buffer.append(caseHandler.transform(" on delete cascade"));
            }
            else
            {
                buffer.append(caseHandler.transform(" /*on delete cascade*/"));
            }
        }
    }

    protected boolean supportsCascadeDelete()
    {
        return true;
    }

    @Override
    public final void visit(ForeignKeyTableConstraint constraint)
    {
        buffer.append(getQuotedIdentifier(constraint.getConstraintName()))
                .append(caseHandler.transform(" foreign key ")).append("(");
        appendQuotedIdentifiers(constraint.getColumnList());
        buffer.append(") ");
        constraint.getReferences().accept(this);
    }

    @Override
    public final void visit(DropSequenceStatement statement)
    {
        if (supportsSequences())
        {
            appendComments(statement.getComments());
            String dropSequenceString = getDropSequenceString(statement.getName());
            if (dropSequenceString != null)
            {
                buffer.append(dropSequenceString);
                terminateStatement();
            }
        }
    }

    protected boolean supportsSequences()
    {
        return false;
    }

    protected String getDropSequenceString(String sequenceName)
    {
        assert supportsSequences();
        return caseHandler.transform("drop sequence ") + getQuotedIdentifier(sequenceName);
    }

    @Override
    public final void visit(DropTableStatement statement)
    {
        appendComments(statement.getComments());
        buffer.append(caseHandler.transform("drop table "));
        if (supportsIfExistsBeforeTableName())
        {
            buffer.append(caseHandler.transform("if exists "));
        }
        //TODO qualified name
        buffer.append(getQuotedIdentifier(statement.getName()));
        if (statement.isCascadeConstraint())
        {
            buffer.append(" ");
            buffer.append(caseHandler.transform(getCascadeConstraintsString()));
        }
        terminateStatement();
    }

    protected boolean supportsIfExistsBeforeTableName()
    {
        return false;
    }


    protected String getCascadeConstraintsString()
    {
        return "/*cascade constraints*/";
    }

    @Override
    public final void visit(DropIndexStatement statement)
    {
        appendComments(statement.getComments());
        buffer.append(caseHandler.transform("drop index "));
        if (supportsIfExistsBeforeTableName())
        {
            buffer.append(caseHandler.transform("if exists "));
        }
        buffer.append(getQuotedIdentifier(statement.getName()));
        terminateStatement();
    }

    @Override
    public final void visit(ADColumn column)
    {
        buffer.append(getQuotedIdentifier(column.getColumnName()));
        if (column.getAscDesc() != null)
        {
            buffer.append(" ").append(column.getAscDesc());
        }
    }

    private void startCreateStatement(CreateEntityStatement statement)
    {
        appendComments(statement.getComments());
        buffer.append(caseHandler.transform("create "));
        appendPlaceHolder(statement.getOpeningPlaceHolder());
    }

    private void endCreateStatement(CreateEntityStatement statement)
    {
        appendSpaceIfNecessary();
        appendPlaceHolder(statement.getClosingPlaceHolder());
        trimRight();
        terminateStatement();
    }

    private void appendPlaceHolder(PlaceHolder placeHolder)
    {
        if (placeHolder != null)
        {
            visit(placeHolder);
        }
    }

    @Override
    public final void visit(CreateIndexStatement statement)
    {
        startCreateStatement(statement);
        if (statement.isUnique())
        {
            buffer.append(caseHandler.transform("unique "));
        }
        //TODO qualified
        buffer.append(caseHandler.transform("index ")).append(getQuotedIdentifier(statement.getName()))
                .append(caseHandler.transform(" on ")).append(getQuotedIdentifier(statement.getTableName()));
        buffer.append(" (");
        for (Iterator<ADColumn> itr = statement.getColumns().iterator(); itr.hasNext(); )
        {
            ADColumn column = itr.next();
            column.accept(this);
            if (itr.hasNext())
            {
                buffer.append(", ");
            }
        }
        buffer.append(")");
        endCreateStatement(statement);
    }

    @Override
    public final void visit(InsertStatement statement)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void visit(CreateSequenceStatement statement)
    {
        startCreateStatement(statement);
        buffer.append(caseHandler.transform("sequence ")).append(getQuotedIdentifier(statement.getName()));
        if (statement.isStartWithSet())
        {
            buffer.append(caseHandler.transform(" start with ")).append(statement.getStartWith());
        }
        if (statement.isMinValueSet())
        {
            buffer.append(caseHandler.transform(" minvalue ")).append(statement.getMinValue());
        }
        if (statement.isMaxValueSet())
        {
            buffer.append(caseHandler.transform(" maxvalue "));
            //            if (context.isPostgres())
//            {
//                long number;
//                try
//                {
//                    number = Long.parseLong(maxValue);
//                }
//                catch (NumberFormatException ignore)
//                {
//                    number = Long.MAX_VALUE;
//                }
//                append(number);
//            }
//            else
//            {
            buffer.append(statement.getMaxValue());
            //            }
        }
//        if (noCycle)
//        {
//            if (context.isOracle())
//            {
//                append(" NOCYCLE");
//            }
//            else if (context.isPostgres())
//            {
//                append(" NO CYCLE");
//            }
//        }
//        if (noCache)
//        {
//            if (context.isOracle())
//            {
//                append(" NOCACHE");
//            }
//        }
//        if (noOrder)
//        {
//            if (context.isOracle())
//            {
//                append(" NOORDER");
//            }
//        }
        endCreateStatement(statement);
    }

    @Override
    public final void visit(PredefinedType type)
    {
        String datatype;
        int sqlCode = type.getType();
        if (type.isLengthSet() || type.isPrecisionScaleSet())
        {
            PrecisionScaleBean precisionScale = type.getPrecisionScale();
            datatype = typeNames.getTypeName(sqlCode, type.getLength(),
                    precisionScale.getPrecision(), precisionScale.getScale());
        }
        else
        {
            datatype = typeNames.getTypeName(sqlCode);
        }

        buffer.append(caseHandler.transform(datatype)).append(" ");
    }

    @Override
    public final void visit(Value value)
    {
        String dateValueFunction = value.getDateValueFunction();
        if (dateValueFunction != null)
        {
            String sqlFunction = functions.get(dateValueFunction);
            if (sqlFunction != null)
            {
                buffer.append(caseHandler.transform(sqlFunction));
            }
            else
            {
                //noinspection UseOfSystemOutOrSystemErr
                System.err.println(dateValueFunction + " is not supported in  " + dbName);
            }
        }
        else if (value.isNullValue())
        {
            buffer.append(caseHandler.transform("null"));
        }
        else if (value.getNumberValue() != null)
        {
            buffer.append(value.getNumberValue().toString());
        }
        else
        {
            buffer.append(value.getStringValue());
        }
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public final void visit(Column column)
    {
        //TODO identity column
        boolean paddingMode = mode.isModeSet(PADDEDCOLUMN_MODE);
        String paddingStr = paddingMode ? "    " : "";
        appendComments(column.getComments(), paddingStr);
        buffer.append(paddingStr);
        int padding = paddingMode ? 30 : 0;
        buffer.append(Util.padToSize(getQuotedIdentifier(column.getName()), padding)).append(" ");
        boolean typeSerialized;
        int bufferStartLength = buffer.length();
        appendPlaceHolder(column.getTypePlaceHolder());
        typeSerialized = buffer.length() > bufferStartLength;
        if (!typeSerialized)
        {
            column.getType().accept(this);
        }
        if (paddingMode)
        {
            buffer.append(Util.padToSize("", 25 - (buffer.length() - bufferStartLength)));
        }
        if (column.getDefaultValue() != null)
        {
            buffer.append(caseHandler.transform(" default "));
            column.getDefaultValue().accept(this);
        }
        if (column.isNotNull())
        {
            buffer.append(caseHandler.transform(" not null"));
        }
        trimRight();
    }

    private void appendComments(Comment[] comments)
    {
        appendComments(comments, null);
    }

    private void appendComments(Comment[] comments, String padding)
    {
        if (comments != null)
        {
            for (Comment comment : comments)
            {
                if (padding != null)
                {
                    buffer.append(padding);
                }
                String commentStr = comment.getComment();
                if (comment.getKind() == 1 && needsSpaceAfterDoubleDashComment())
                {
                    commentStr = commentStr.replaceFirst("--([^ ])", "-- $1");
                }
                buffer.append(commentStr).append("\n");
            }
        }
    }

    protected boolean needsSpaceAfterDoubleDashComment()
    {
        return false;
    }

    @Override
    public final void visit(CreateTableStatement statement)
    {
        startCreateStatement(statement);
        //TODO qualified name
        buffer.append(caseHandler.transform("table "))
                .append(getQuotedIdentifier(statement.getName())).append("\n(\n");
        mode.setMode(PADDEDCOLUMN_MODE, Boolean.TRUE);
        try
        {
            for (Iterator<Column> itr = statement.getColumns().iterator(); itr.hasNext(); )
            {
                Column column = itr.next();
                column.accept(this);
                if (itr.hasNext() || !statement.getTableConstraints().isEmpty())
                {
                    buffer.append(",");
                }
                buffer.append("\n");
            }
            for (Iterator<TableConstraint> iterator = statement.getTableConstraints().iterator(); iterator.hasNext(); )
            {
                TableConstraint tc = iterator.next();
                buffer.append("    ");
                tc.accept(this);
                if (iterator.hasNext())
                {
                    buffer.append(",");
                }
                buffer.append("\n");
            }
        }
        finally
        {
            mode.unsetMode(PADDEDCOLUMN_MODE);
        }
        buffer.append(")");
        endCreateStatement(statement);
    }


    @Override
    public final void visit(PlaceHolder placeHolder)
    {
        //currently handling on the literal type
        assert PlaceHolder.LITERAL_TYPE.equals(placeHolder.getType());
        String literal = placeHolder.getLiteral(dbName);
        if (literal != null)
        {
            buffer.append(literal).append(" ");
        }
    }

    private StringBuilder trimRight()
    {
        int i = buffer.length() - 1;
        for (; i >= 0; i--)
        {
            if (buffer.charAt(i) != ' ')
            {
                break;
            }
        }
        buffer.setLength(i + 1);
        return buffer;
    }

    private void appendSpaceIfNecessary()
    {
        if (buffer.length() == 0)
        {
            return;
        }
        char ch = buffer.charAt(buffer.length() - 1);
        if (!Character.isWhitespace(ch))
        {
            buffer.append(' ');
        }
    }

    protected char openQuote()
    {
        return '"';
    }

    protected char closeQuote()
    {
        return '"';
    }

    protected void addKeywords(String... strs)
    {
        for (String str : strs)
        {
            keywords.add(str.trim().toLowerCase());
        }
    }

    protected boolean isKeyword(String str)
    {
        return str != null && keywords.contains(str.toLowerCase());
    }

    protected String getQuotedIdentifier(String identifier)
    {
        identifier = caseHandler.transform(identifier);
        boolean keyword = isKeyword(identifier);
        return keyword ? openQuote() + identifier + closeQuote() : identifier;
    }

    private void appendQuotedIdentifiers(Iterable<String> identifiers)
    {
        if (identifiers != null)
        {
            for (Iterator<String> itr = identifiers.iterator(); itr.hasNext(); )
            {
                String identifier = itr.next();
                buffer.append(getQuotedIdentifier(identifier));
                if (itr.hasNext())
                {
                    buffer.append(", ");
                }
            }
        }
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
}
