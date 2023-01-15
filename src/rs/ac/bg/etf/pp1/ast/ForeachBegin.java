// generated with ast extension for cup
// version 0.8
// 14/0/2023 20:49:37


package rs.ac.bg.etf.pp1.ast;

public class ForeachBegin implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Designator Designator;
    private String foreachIdent;

    public ForeachBegin (Designator Designator, String foreachIdent) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.foreachIdent=foreachIdent;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public String getForeachIdent() {
        return foreachIdent;
    }

    public void setForeachIdent(String foreachIdent) {
        this.foreachIdent=foreachIdent;
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
        if(Designator!=null) Designator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForeachBegin(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+foreachIdent);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForeachBegin]");
        return buffer.toString();
    }
}
