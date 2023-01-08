// generated with ast extension for cup
// version 0.8
// 8/0/2023 18:25:9


package rs.ac.bg.etf.pp1.ast;

public class ReadStatement extends Statement {

    private ReadBegin ReadBegin;
    private Designator Designator;

    public ReadStatement (ReadBegin ReadBegin, Designator Designator) {
        this.ReadBegin=ReadBegin;
        if(ReadBegin!=null) ReadBegin.setParent(this);
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
    }

    public ReadBegin getReadBegin() {
        return ReadBegin;
    }

    public void setReadBegin(ReadBegin ReadBegin) {
        this.ReadBegin=ReadBegin;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ReadBegin!=null) ReadBegin.accept(visitor);
        if(Designator!=null) Designator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ReadBegin!=null) ReadBegin.traverseTopDown(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ReadBegin!=null) ReadBegin.traverseBottomUp(visitor);
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ReadStatement(\n");

        if(ReadBegin!=null)
            buffer.append(ReadBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ReadStatement]");
        return buffer.toString();
    }
}
