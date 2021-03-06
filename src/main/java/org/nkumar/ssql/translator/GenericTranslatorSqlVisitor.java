package org.nkumar.ssql.translator;


import org.nkumar.ssql.dialect.Dialect;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GenericTranslatorSqlVisitor implements TranslatorSqlVisitor
{
    private static final String PADDEDCOLUMN_MODE = "paddedcolumn";

    private final StringBuilder buffer = new StringBuilder(8 * 1000);
    private final Mode mode = new Mode();

    private final Dialect dialect;

    private final CaseHandler caseHandler = CaseHandler.Factory.create(CaseHandler.Factory.UPPER);

    public GenericTranslatorSqlVisitor(Dialect dialect)
    {
        this.dialect = dialect;
    }

    @Override
    public final String getGeneratedSql()
    {
        return buffer.toString();
    }

    @Override
    public final String getDbName()
    {
        return dialect.getDbName();
    }

    @Override
    public final void visit(AddColumnStatement statement)
    {
        startAlterStatement(statement);
        buffer.append(caseHandler.transform(dialect.getAddColumnString())).append(" ");
        statement.getColumn().accept(this);
        terminateStatement();
    }


    private void terminateStatement()
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
            buffer.append(caseHandler.transform(" on delete cascade"));
        }
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
        if (dialect.supportsSequences())
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


    private String getDropSequenceString(String sequenceName)
    {
        assert dialect.supportsSequences();
        return caseHandler.transform("drop sequence ") + getQuotedIdentifier(sequenceName);
    }

    @Override
    public final void visit(DropTableStatement statement)
    {
        appendComments(statement.getComments());
        buffer.append(caseHandler.transform("drop table "));
        if (dialect.supportsIfExistsBeforeTableName())
        {
            buffer.append(caseHandler.transform("if exists "));
        }
        //TODO qualified name
        buffer.append(getQuotedIdentifier(statement.getName()));
        if (statement.isCascadeConstraint())
        {
            buffer.append(" ");
            buffer.append(caseHandler.transform(dialect.getCascadeConstraintsString()));
        }
        terminateStatement();
    }


    @Override
    public final void visit(DropIndexStatement statement)
    {
        appendComments(statement.getComments());
        buffer.append(caseHandler.transform("drop index "));
        if (dialect.supportsIfExistsBeforeTableName())
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
        if (statement instanceof CreateTableStatement)
        {
            trimRight();
            buffer.append(dialect.getTableTypeString());
        }
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
        if (!dialect.supportsSequences())
        {
            return;
        }
        startCreateStatement(statement);
        buffer.append(caseHandler.transform("sequence ")).append(getQuotedIdentifier(statement.getName()));
        if (statement.isStartWithSet())
        {
            buffer.append(caseHandler.transform(" start with ")).append(statement.getStartWith());
        }
        if (statement.isIncrementBySet())
        {
            buffer.append(caseHandler.transform(" increment by ")).append(statement.getIncrementBy());
        }
        if (statement.isMinValueSet())
        {
            buffer.append(caseHandler.transform(" minvalue ")).append(statement.getMinValue());
        }
        if (statement.isMaxValueSet())
        {
            buffer.append(caseHandler.transform(" maxvalue ")).append(statement.getMaxValue());
        }
        if (statement.isCycleSet())
        {
            String option = statement.isCycle() ? "cycle" : dialect.getNoCycleSequenceString();
            buffer.append(" ").append(caseHandler.transform(option));
        }
        if (statement.isNoCacheSet())
        {
            String option = dialect.getNoCacheSequenceString();
            if (!option.isEmpty())
            {
                buffer.append(" ").append(caseHandler.transform(option));
            }
        }
        endCreateStatement(statement);
    }

    @Override
    public final void visit(PredefinedType type)
    {
        String datatype;
        int sqlCode = type.getType();
        if (dialect.isIdentity() && type.getAlias() != null)
        {
            datatype = type.getAlias();
        }
        else
        {
            if (type.isLengthSet() || type.isPrecisionScaleSet())
            {
                PrecisionScaleBean precisionScale = type.getPrecisionScale();
                datatype = dialect.getTypeName(sqlCode, type.getLength(),
                        precisionScale.getPrecision(), precisionScale.getScale());
            }
            else
            {
                datatype = dialect.getTypeName(sqlCode);
            }
        }
        buffer.append(caseHandler.transform(datatype)).append(" ");
    }

    @Override
    public final void visit(Value value)
    {
        String dateValueFunction = value.getDateValueFunction();
        if (dateValueFunction != null)
        {
            String sqlFunction = dialect.getFunctionName(dateValueFunction);
            if (sqlFunction != null)
            {
                buffer.append(caseHandler.transform(sqlFunction));
            }
            else
            {
                //noinspection UseOfSystemOutOrSystemErr
                System.err.println(dateValueFunction + " is not supported in  " + dialect.getDbName());
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
        String quotedColName = getQuotedIdentifier(column.getName());
        buffer.append(Util.padToSize(quotedColName, padding)).append(" ");
        boolean typeSerialized;
        int bufferStartLength = buffer.length();
        appendPlaceHolder(column.getTypePlaceHolder());
        typeSerialized = buffer.length() > bufferStartLength;
        if (!typeSerialized)
        {
            if (column.isIdentity() && dialect.supportsIdentityColumns() && !dialect.hasDataTypeInIdentityColumn())
            {
                //this is a hack, type is not serialized and does need to be serialized
                typeSerialized = true;
            }
        }
        if (!typeSerialized)
        {
            column.getType().accept(this);
        }
        if (paddingMode)
        {
            buffer.append(Util.padToSize("", 25 - (buffer.length() - bufferStartLength)));
        }
        if (column.isIdentity() && dialect.supportsIdentityColumns())
        {
            String identityColumnString = dialect.getIdentityColumnString(column.getType().getType());
            buffer.append(caseHandler.transform(identityColumnString)).append(' ');
        }
        Value defaultValue = column.getDefaultValue();
        if (defaultValue != null)
        {
            boolean defaultValueSupported = true;
            String dateValueFunction = defaultValue.getDateValueFunction();
            if (dateValueFunction != null)
            {
                switch (dateValueFunction)
                {
                    case "current_date":
                        defaultValueSupported = dialect.supportsDefaultCurrentDate();
                        break;
                    case "current_time":
                        defaultValueSupported = dialect.supportsDefaultCurrentTime();
                        break;
                    case "current_timestamp":
                        defaultValueSupported = dialect.supportsDefaultCurrentTimestamp();
                        break;
                    default:
                        throw new AssertionError(dateValueFunction + " is not supported");
                }
            }
            if (defaultValueSupported)
            {
                buffer.append(caseHandler.transform(" default "));
                defaultValue.accept(this);
            }
        }
        if (column.isNotNull())
        {
            buffer.append(caseHandler.transform(" not null"));
        }
        else
        {
            String nullColumnString = dialect.getNullColumnString();
            if (!nullColumnString.isEmpty())
            {
                buffer.append(" ").append(caseHandler.transform(nullColumnString));
            }
        }
        //CHECK (COL_NAME IN (1,0)), needed for oracle which does not support boolean
        if (column.getType().getType() == Types.BOOLEAN && dialect.needsCheckConstraintForNumericBoolean())
        {
            buffer.append(" ").append(caseHandler.transform("check"))
                    .append(" (").append(caseHandler.transform(quotedColName))
                    .append(" ").append(caseHandler.transform("in"))
                    .append(" (1,0))");
        }
        trimRight();
    }

    private void appendComments(Comment[] comments)
    {
        appendComments(comments, null);
    }

    private static final Pattern CUSTOM_COMMENT_PATTERN = Pattern.compile(
            "---START-CUSTOM[ ]+dialect=([a-zA-Z0-9_,]+)(.*)---END-CUSTOM", Pattern.DOTALL);

    private void appendComments(Comment[] comments, String padding)
    {
        if (comments == null)
        {
            return;
        }
        for (Comment comment : comments)
        {
            if (padding != null)
            {
                buffer.append(padding);
            }
            String commentStr = comment.getComment();
            if (comment.getKind() == 1 && dialect.needsSpaceAfterDoubleDashComment())
            {
                commentStr = commentStr.replaceFirst("--([^ ])", "-- $1");
            }
            //extract sql from the custom comment for the right dialect
            if (comment.getKind() == 3 && !dialect.isIdentity())
            {
                Matcher matcher = CUSTOM_COMMENT_PATTERN.matcher(commentStr);
                if (matcher.matches())
                {
                    String dialects = (matcher.group(1) + ",").toLowerCase();
                    String sql = matcher.group(2);
                    if (dialects.contains(dialect.getDbName().toLowerCase() + ","))
                    {
                        commentStr = sql;
                    }
                    else
                    {
                        commentStr = "";
                    }
                }
                else
                {
                    System.err.println("Following custom comment didnot match the expected pattern. Ignored.");
                    System.err.println(commentStr);
                    commentStr = "";
                }
            }
            if (!commentStr.isEmpty())
            {
                buffer.append(commentStr).append("\n");
            }
        }
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
                buffer.append(caseHandler.transform("constraint"));
                buffer.append(" ");
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
        String literal = placeHolder.getLiteral(dialect.getDbName());
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


    private String getQuotedIdentifier(String identifier)
    {
        identifier = caseHandler.transform(identifier);
        boolean keyword = dialect.isKeyword(identifier);
        return keyword ? dialect.openQuote() + identifier + dialect.closeQuote() : identifier;
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
}
