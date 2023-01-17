// generated with ast extension for cup
// version 0.8
// 16/0/2023 15:0:37


package rs.ac.bg.etf.pp1.ast;

public class DesignMulti extends DesignatorMulti {

    private DesignatorMultiBegin DesignatorMultiBegin;
    private DesignatorMultiList DesignatorMultiList;

    public DesignMulti (DesignatorMultiBegin DesignatorMultiBegin, DesignatorMultiList DesignatorMultiList) {
        this.DesignatorMultiBegin=DesignatorMultiBegin;
        if(DesignatorMultiBegin!=null) DesignatorMultiBegin.setParent(this);
        this.DesignatorMultiList=DesignatorMultiList;
        if(DesignatorMultiList!=null) DesignatorMultiList.setParent(this);
    }

    public DesignatorMultiBegin getDesignatorMultiBegin() {
        return DesignatorMultiBegin;
    }

    public void setDesignatorMultiBegin(DesignatorMultiBegin DesignatorMultiBegin) {
        this.DesignatorMultiBegin=DesignatorMultiBegin;
    }

    public DesignatorMultiList getDesignatorMultiList() {
        return DesignatorMultiList;
    }

    public void setDesignatorMultiList(DesignatorMultiList DesignatorMultiList) {
        this.DesignatorMultiList=DesignatorMultiList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorMultiBegin!=null) DesignatorMultiBegin.accept(visitor);
        if(DesignatorMultiList!=null) DesignatorMultiList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorMultiBegin!=null) DesignatorMultiBegin.traverseTopDown(visitor);
        if(DesignatorMultiList!=null) DesignatorMultiList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorMultiBegin!=null) DesignatorMultiBegin.traverseBottomUp(visitor);
        if(DesignatorMultiList!=null) DesignatorMultiList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignMulti(\n");

        if(DesignatorMultiBegin!=null)
            buffer.append(DesignatorMultiBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorMultiList!=null)
            buffer.append(DesignatorMultiList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignMulti]");
        return buffer.toString();
    }
}
