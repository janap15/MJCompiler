// generated with ast extension for cup
// version 0.8
// 9/0/2023 19:17:5


package rs.ac.bg.etf.pp1.ast;

public class LocalVarDeclaration extends LocalVarDecl {

    private Type Type;
    private LocalVarIdentList LocalVarIdentList;

    public LocalVarDeclaration (Type Type, LocalVarIdentList LocalVarIdentList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.LocalVarIdentList=LocalVarIdentList;
        if(LocalVarIdentList!=null) LocalVarIdentList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public LocalVarIdentList getLocalVarIdentList() {
        return LocalVarIdentList;
    }

    public void setLocalVarIdentList(LocalVarIdentList LocalVarIdentList) {
        this.LocalVarIdentList=LocalVarIdentList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(LocalVarIdentList!=null) LocalVarIdentList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(LocalVarIdentList!=null) LocalVarIdentList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(LocalVarIdentList!=null) LocalVarIdentList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LocalVarDeclaration(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(LocalVarIdentList!=null)
            buffer.append(LocalVarIdentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LocalVarDeclaration]");
        return buffer.toString();
    }
}
