package org.nkumar.ssql.model;

public interface SqlVisitable
{
    public void accept(SqlVisitor visitor);
}
