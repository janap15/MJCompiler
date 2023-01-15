// generated with ast extension for cup
// version 0.8
// 14/0/2023 20:49:37


package rs.ac.bg.etf.pp1.ast;

public class ElseStatement extends ElseOptional {

    private ElseBegin ElseBegin;
    private Statement Statement;

    public ElseStatement (ElseBegin ElseBegin, Statement Statement) {
        this.ElseBegin=ElseBegin;
        if(ElseBegin!=null) ElseBegin.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ElseBegin getElseBegin() {
        return ElseBegin;
    }

    public void setElseBegin(ElseBegin ElseBegin) {
        this.ElseBegin=ElseBegin;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ElseBegin!=null) ElseBegin.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ElseBegin!=null) ElseBegin.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ElseBegin!=null) ElseBegin.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ElseStatement(\n");

        if(ElseBegin!=null)
            buffer.append(ElseBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ElseStatement]");
        return buffer.toString();
    }
}
