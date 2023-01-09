// generated with ast extension for cup
// version 0.8
// 9/0/2023 19:17:5


package rs.ac.bg.etf.pp1.ast;

public class DesignatorMultiAssign extends DesignatorStatement {

    private DesignatorMulti DesignatorMulti;
    private Designator Designator;

    public DesignatorMultiAssign (DesignatorMulti DesignatorMulti, Designator Designator) {
        this.DesignatorMulti=DesignatorMulti;
        if(DesignatorMulti!=null) DesignatorMulti.setParent(this);
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
    }

    public DesignatorMulti getDesignatorMulti() {
        return DesignatorMulti;
    }

    public void setDesignatorMulti(DesignatorMulti DesignatorMulti) {
        this.DesignatorMulti=DesignatorMulti;
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
        if(DesignatorMulti!=null) DesignatorMulti.accept(visitor);
        if(Designator!=null) Designator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorMulti!=null) DesignatorMulti.traverseTopDown(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorMulti!=null) DesignatorMulti.traverseBottomUp(visitor);
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorMultiAssign(\n");

        if(DesignatorMulti!=null)
            buffer.append(DesignatorMulti.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorMultiAssign]");
        return buffer.toString();
    }
}
