// generated with ast extension for cup
// version 0.8
// 8/0/2023 18:25:9


package rs.ac.bg.etf.pp1.ast;

public class DesignatorOtherStmt extends DesignatorStatement {

    private DesignatorOther DesignatorOther;

    public DesignatorOtherStmt (DesignatorOther DesignatorOther) {
        this.DesignatorOther=DesignatorOther;
        if(DesignatorOther!=null) DesignatorOther.setParent(this);
    }

    public DesignatorOther getDesignatorOther() {
        return DesignatorOther;
    }

    public void setDesignatorOther(DesignatorOther DesignatorOther) {
        this.DesignatorOther=DesignatorOther;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorOther!=null) DesignatorOther.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorOther!=null) DesignatorOther.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorOther!=null) DesignatorOther.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorOtherStmt(\n");

        if(DesignatorOther!=null)
            buffer.append(DesignatorOther.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorOtherStmt]");
        return buffer.toString();
    }
}
