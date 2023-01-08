// generated with ast extension for cup
// version 0.8
// 8/0/2023 18:25:9


package rs.ac.bg.etf.pp1.ast;

public class MethodCall implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private MethodCallBegin MethodCallBegin;
    private ActualPars ActualPars;

    public MethodCall (MethodCallBegin MethodCallBegin, ActualPars ActualPars) {
        this.MethodCallBegin=MethodCallBegin;
        if(MethodCallBegin!=null) MethodCallBegin.setParent(this);
        this.ActualPars=ActualPars;
        if(ActualPars!=null) ActualPars.setParent(this);
    }

    public MethodCallBegin getMethodCallBegin() {
        return MethodCallBegin;
    }

    public void setMethodCallBegin(MethodCallBegin MethodCallBegin) {
        this.MethodCallBegin=MethodCallBegin;
    }

    public ActualPars getActualPars() {
        return ActualPars;
    }

    public void setActualPars(ActualPars ActualPars) {
        this.ActualPars=ActualPars;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodCallBegin!=null) MethodCallBegin.accept(visitor);
        if(ActualPars!=null) ActualPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodCallBegin!=null) MethodCallBegin.traverseTopDown(visitor);
        if(ActualPars!=null) ActualPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodCallBegin!=null) MethodCallBegin.traverseBottomUp(visitor);
        if(ActualPars!=null) ActualPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodCall(\n");

        if(MethodCallBegin!=null)
            buffer.append(MethodCallBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActualPars!=null)
            buffer.append(ActualPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodCall]");
        return buffer.toString();
    }
}
