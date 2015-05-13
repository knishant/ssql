package org.nkumar.ssql.translator;

public interface CaseHandler
{
    public String transform(String str);

    public static final class Factory
    {
        public static final char UPPER = 'U';
        public static final char LOWER = 'L';
        public static final char SAME = 'S';

        private Factory()
        {
        }

        public static CaseHandler create(char mode)
        {
            switch (mode)
            {
                case UPPER:
                    return new UpperCaseHandler();
                case LOWER:
                    return new LowerCaseHandler();
                case SAME:
                    return new SameCaseHandler();
                default:
                    throw new IllegalArgumentException(mode + " is not supported");
            }
        }
    }

    static final class UpperCaseHandler implements CaseHandler
    {
        @Override
        public String transform(String str)
        {
            return str == null ? null : str.toUpperCase();
        }
    }

    static final class LowerCaseHandler implements CaseHandler
    {
        @Override
        public String transform(String str)
        {
            return str == null ? null : str.toLowerCase();
        }
    }

    static final class SameCaseHandler implements CaseHandler
    {
        @Override
        public String transform(String str)
        {
            return str;
        }
    }

}
