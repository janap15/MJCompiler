// generated with ast extension for cup
// version 0.8
// 16/0/2023 15:0:37


package rs.ac.bg.etf.pp1.ast;

public class DesignMultiList extends DesignatorMultiList {

    private DesignatorMultiList DesignatorMultiList;
    private DesignatorOrEpsilon DesignatorOrEpsilon;

    public DesignMultiList (DesignatorMultiList DesignatorMultiList, DesignatorOrEpsilon DesignatorOrEpsilon) {
        this.DesignatorMultiList=DesignatorMultiList;
        if(DesignatorMultiList!=null) DesignatorMultiList.setParent(this);
        this.DesignatorOrEpsilon=DesignatorOrEpsilon;
        if(DesignatorOrEpsilon!=null) DesignatorOrEpsilon.setParent(this);
    }

    public DesignatorMultiList getDesignatorMultiList() {
        return DesignatorMultiList;
    }

    public void setDesignatorMultiList(DesignatorMultiList DesignatorMultiList) {
        this.DesignatorMultiList=DesignatorMultiList;
    }

    public DesignatorOrEpsilon getDesignatorOrEpsilon() {
        return DesignatorOrEpsilon;
    }

    public void setDesignatorOrEpsilon(DesignatorOrEpsilon DesignatorOrEpsilon) {
        this.DesignatorOrEpsilon=DesignatorOrEpsilon;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorMultiList!=null) DesignatorMultiList.accept(visitor);
        if(DesignatorOrEpsilon!=null) DesignatorOrEpsilon.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorMultiList!=null) DesignatorMultiList.traverseTopDown(visitor);
        if(DesignatorOrEpsilon!=null) DesignatorOrEpsilon.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorMultiList!=null) DesignatorMultiList.traverseBottomUp(visitor);
        if(DesignatorOrEpsilon!=null) DesignatorOrEpsilon.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignMultiList(\n");

        if(DesignatorMultiList!=null)
            buffer.append(DesignatorMultiList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorOrEpsilon!=null)
            buffer.append(DesignatorOrEpsilon.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignMultiList]");
        return buffer.toString();
    }
}
