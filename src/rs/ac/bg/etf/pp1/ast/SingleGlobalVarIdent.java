// generated with ast extension for cup
// version 0.8
// 16/0/2023 15:0:37


package rs.ac.bg.etf.pp1.ast;

public class SingleGlobalVarIdent extends GlobalVarIdentList {

    private GlobalVarIdent GlobalVarIdent;

    public SingleGlobalVarIdent (GlobalVarIdent GlobalVarIdent) {
        this.GlobalVarIdent=GlobalVarIdent;
        if(GlobalVarIdent!=null) GlobalVarIdent.setParent(this);
    }

    public GlobalVarIdent getGlobalVarIdent() {
        return GlobalVarIdent;
    }

    public void setGlobalVarIdent(GlobalVarIdent GlobalVarIdent) {
        this.GlobalVarIdent=GlobalVarIdent;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(GlobalVarIdent!=null) GlobalVarIdent.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobalVarIdent!=null) GlobalVarIdent.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobalVarIdent!=null) GlobalVarIdent.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleGlobalVarIdent(\n");

        if(GlobalVarIdent!=null)
            buffer.append(GlobalVarIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleGlobalVarIdent]");
        return buffer.toString();
    }
}
