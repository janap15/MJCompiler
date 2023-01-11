// generated with ast extension for cup
// version 0.8
// 11/0/2023 23:45:0


package rs.ac.bg.etf.pp1.ast;

public class DesignatorForEach extends Statement {

    private ForeachBegin ForeachBegin;
    private Statement Statement;

    public DesignatorForEach (ForeachBegin ForeachBegin, Statement Statement) {
        this.ForeachBegin=ForeachBegin;
        if(ForeachBegin!=null) ForeachBegin.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForeachBegin getForeachBegin() {
        return ForeachBegin;
    }

    public void setForeachBegin(ForeachBegin ForeachBegin) {
        this.ForeachBegin=ForeachBegin;
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
        if(ForeachBegin!=null) ForeachBegin.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForeachBegin!=null) ForeachBegin.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForeachBegin!=null) ForeachBegin.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorForEach(\n");

        if(ForeachBegin!=null)
            buffer.append(ForeachBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorForEach]");
        return buffer.toString();
    }
}
