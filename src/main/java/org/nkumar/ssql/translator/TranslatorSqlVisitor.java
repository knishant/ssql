package org.nkumar.ssql.translator;

import org.nkumar.ssql.model.SqlVisitor;

public interface TranslatorSqlVisitor extends SqlVisitor
{
    public String getDbName();

    public String getGeneratedSql();
}
