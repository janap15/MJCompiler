// generated with ast extension for cup
// version 0.8
// 11/0/2023 23:45:0


package rs.ac.bg.etf.pp1.ast;

public class GlobalVarIdentifierList extends GlobalVarIdentList {

    private GlobalVarIdentList GlobalVarIdentList;
    private GlobalVarIdent GlobalVarIdent;

    public GlobalVarIdentifierList (GlobalVarIdentList GlobalVarIdentList, GlobalVarIdent GlobalVarIdent) {
        this.GlobalVarIdentList=GlobalVarIdentList;
        if(GlobalVarIdentList!=null) GlobalVarIdentList.setParent(this);
        this.GlobalVarIdent=GlobalVarIdent;
        if(GlobalVarIdent!=null) GlobalVarIdent.setParent(this);
    }

    public GlobalVarIdentList getGlobalVarIdentList() {
        return GlobalVarIdentList;
    }

    public void setGlobalVarIdentList(GlobalVarIdentList GlobalVarIdentList) {
        this.GlobalVarIdentList=GlobalVarIdentList;
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
        if(GlobalVarIdentList!=null) GlobalVarIdentList.accept(visitor);
        if(GlobalVarIdent!=null) GlobalVarIdent.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobalVarIdentList!=null) GlobalVarIdentList.traverseTopDown(visitor);
        if(GlobalVarIdent!=null) GlobalVarIdent.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobalVarIdentList!=null) GlobalVarIdentList.traverseBottomUp(visitor);
        if(GlobalVarIdent!=null) GlobalVarIdent.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalVarIdentifierList(\n");

        if(GlobalVarIdentList!=null)
            buffer.append(GlobalVarIdentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlobalVarIdent!=null)
            buffer.append(GlobalVarIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalVarIdentifierList]");
        return buffer.toString();
    }
}
