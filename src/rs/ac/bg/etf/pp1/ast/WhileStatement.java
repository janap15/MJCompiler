// generated with ast extension for cup
// version 0.8
// 16/0/2023 15:0:37


package rs.ac.bg.etf.pp1.ast;

public class WhileStatement extends Statement {

    private WhileBegin WhileBegin;
    private WhileCondition WhileCondition;
    private Statement Statement;

    public WhileStatement (WhileBegin WhileBegin, WhileCondition WhileCondition, Statement Statement) {
        this.WhileBegin=WhileBegin;
        if(WhileBegin!=null) WhileBegin.setParent(this);
        this.WhileCondition=WhileCondition;
        if(WhileCondition!=null) WhileCondition.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public WhileBegin getWhileBegin() {
        return WhileBegin;
    }

    public void setWhileBegin(WhileBegin WhileBegin) {
        this.WhileBegin=WhileBegin;
    }

    public WhileCondition getWhileCondition() {
        return WhileCondition;
    }

    public void setWhileCondition(WhileCondition WhileCondition) {
        this.WhileCondition=WhileCondition;
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
        if(WhileBegin!=null) WhileBegin.accept(visitor);
        if(WhileCondition!=null) WhileCondition.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(WhileBegin!=null) WhileBegin.traverseTopDown(visitor);
        if(WhileCondition!=null) WhileCondition.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(WhileBegin!=null) WhileBegin.traverseBottomUp(visitor);
        if(WhileCondition!=null) WhileCondition.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("WhileStatement(\n");

        if(WhileBegin!=null)
            buffer.append(WhileBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(WhileCondition!=null)
            buffer.append(WhileCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [WhileStatement]");
        return buffer.toString();
    }
}
