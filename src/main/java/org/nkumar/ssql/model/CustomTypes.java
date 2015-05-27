package org.nkumar.ssql.model;

import java.sql.Types;

public final class CustomTypes
{
    private CustomTypes()
    {
    }

    public static PredefinedType create(String alias)
    {
        alias = alias.toLowerCase();
        if ("money".equals(alias))
        {
            //NUMERIC(19,4)
            return createMoneyNumeric(alias, 19);
        }
        if ("smallmoney".equals(alias))
        {
            //NUMERIC(10,4)
            return createMoneyNumeric(alias, 10);
        }
        throw new AssertionError(alias + " is not supported");
    }

    private static PredefinedType createMoneyNumeric(String alias, int precision)
    {
        PredefinedType predefinedType = new PredefinedType(Types.NUMERIC);
        PrecisionScaleBean ps = new PrecisionScaleBean();
        ps.setPrecision(precision);
        ps.setScale(4);
        predefinedType.setPrecisionScale(ps);
        predefinedType.setAlias(alias);
        return predefinedType;
    }
}
