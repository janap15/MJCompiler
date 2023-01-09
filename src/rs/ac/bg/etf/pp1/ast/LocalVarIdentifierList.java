// generated with ast extension for cup
// version 0.8
// 9/0/2023 19:17:5


package rs.ac.bg.etf.pp1.ast;

public class LocalVarIdentifierList extends LocalVarIdentList {

    private LocalVarIdentList LocalVarIdentList;
    private LocalVarIdent LocalVarIdent;

    public LocalVarIdentifierList (LocalVarIdentList LocalVarIdentList, LocalVarIdent LocalVarIdent) {
        this.LocalVarIdentList=LocalVarIdentList;
        if(LocalVarIdentList!=null) LocalVarIdentList.setParent(this);
        this.LocalVarIdent=LocalVarIdent;
        if(LocalVarIdent!=null) LocalVarIdent.setParent(this);
    }

    public LocalVarIdentList getLocalVarIdentList() {
        return LocalVarIdentList;
    }

    public void setLocalVarIdentList(LocalVarIdentList LocalVarIdentList) {
        this.LocalVarIdentList=LocalVarIdentList;
    }

    public LocalVarIdent getLocalVarIdent() {
        return LocalVarIdent;
    }

    public void setLocalVarIdent(LocalVarIdent LocalVarIdent) {
        this.LocalVarIdent=LocalVarIdent;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LocalVarIdentList!=null) LocalVarIdentList.accept(visitor);
        if(LocalVarIdent!=null) LocalVarIdent.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LocalVarIdentList!=null) LocalVarIdentList.traverseTopDown(visitor);
        if(LocalVarIdent!=null) LocalVarIdent.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LocalVarIdentList!=null) LocalVarIdentList.traverseBottomUp(visitor);
        if(LocalVarIdent!=null) LocalVarIdent.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LocalVarIdentifierList(\n");

        if(LocalVarIdentList!=null)
            buffer.append(LocalVarIdentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(LocalVarIdent!=null)
            buffer.append(LocalVarIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LocalVarIdentifierList]");
        return buffer.toString();
    }
}
