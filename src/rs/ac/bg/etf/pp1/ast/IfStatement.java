// generated with ast extension for cup
// version 0.8
// 8/0/2023 18:25:9


package rs.ac.bg.etf.pp1.ast;

public class IfStatement extends Statement {

    private IfBegin IfBegin;
    private IfCondition IfCondition;
    private Then Then;
    private Statement Statement;
    private ElseOptional ElseOptional;

    public IfStatement (IfBegin IfBegin, IfCondition IfCondition, Then Then, Statement Statement, ElseOptional ElseOptional) {
        this.IfBegin=IfBegin;
        if(IfBegin!=null) IfBegin.setParent(this);
        this.IfCondition=IfCondition;
        if(IfCondition!=null) IfCondition.setParent(this);
        this.Then=Then;
        if(Then!=null) Then.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.ElseOptional=ElseOptional;
        if(ElseOptional!=null) ElseOptional.setParent(this);
    }

    public IfBegin getIfBegin() {
        return IfBegin;
    }

    public void setIfBegin(IfBegin IfBegin) {
        this.IfBegin=IfBegin;
    }

    public IfCondition getIfCondition() {
        return IfCondition;
    }

    public void setIfCondition(IfCondition IfCondition) {
        this.IfCondition=IfCondition;
    }

    public Then getThen() {
        return Then;
    }

    public void setThen(Then Then) {
        this.Then=Then;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public ElseOptional getElseOptional() {
        return ElseOptional;
    }

    public void setElseOptional(ElseOptional ElseOptional) {
        this.ElseOptional=ElseOptional;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfBegin!=null) IfBegin.accept(visitor);
        if(IfCondition!=null) IfCondition.accept(visitor);
        if(Then!=null) Then.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(ElseOptional!=null) ElseOptional.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfBegin!=null) IfBegin.traverseTopDown(visitor);
        if(IfCondition!=null) IfCondition.traverseTopDown(visitor);
        if(Then!=null) Then.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(ElseOptional!=null) ElseOptional.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfBegin!=null) IfBegin.traverseBottomUp(visitor);
        if(IfCondition!=null) IfCondition.traverseBottomUp(visitor);
        if(Then!=null) Then.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(ElseOptional!=null) ElseOptional.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfStatement(\n");

        if(IfBegin!=null)
            buffer.append(IfBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IfCondition!=null)
            buffer.append(IfCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Then!=null)
            buffer.append(Then.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ElseOptional!=null)
            buffer.append(ElseOptional.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfStatement]");
        return buffer.toString();
    }
}
