package org.nkumar.ssql.model;

public interface SqlVisitor
{
    void visit(DropSequenceStatement statement);

    void visit(DropTableStatement statement);

    void visit(CreateIndexStatement statement);

    void visit(InsertStatement statement);

    void visit(CreateSequenceStatement statement);

    void visit(CreateTableStatement statement);

    void visit(Column column);

    void visit(PredefinedType type);

    void visit(Value value);

    void visit(AddColumnStatement statement);

    void visit(AddTableConstraintStatement statement);

    void visit(DropColumnStatement statement);

    void visit(DropTableConstraintStatement statement);

    void visit(UniqueTableConstraint constraint);

    void visit(ForeignKeyTableConstraint constraint);

    void visit(References references);

    void visit(DropIndexStatement statement);

    void visit(ADColumn column);

    void visit(PlaceHolder placeHolder);
}
