package org.nkumar.ssql.translater;

import org.nkumar.ssql.model.SqlVisitor;

public interface TranslatorSqlVisitor extends SqlVisitor
{
    public String getDbName();

    public String getGeneratedSql();

    public void clearBuffer();
}
