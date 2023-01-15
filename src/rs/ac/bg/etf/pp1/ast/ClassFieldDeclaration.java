// generated with ast extension for cup
// version 0.8
// 14/0/2023 20:49:37


package rs.ac.bg.etf.pp1.ast;

public class ClassFieldDeclaration extends ClassFieldDecl {

    private Type Type;
    private ClassFieldIdentList ClassFieldIdentList;

    public ClassFieldDeclaration (Type Type, ClassFieldIdentList ClassFieldIdentList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ClassFieldIdentList=ClassFieldIdentList;
        if(ClassFieldIdentList!=null) ClassFieldIdentList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ClassFieldIdentList getClassFieldIdentList() {
        return ClassFieldIdentList;
    }

    public void setClassFieldIdentList(ClassFieldIdentList ClassFieldIdentList) {
        this.ClassFieldIdentList=ClassFieldIdentList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ClassFieldIdentList!=null) ClassFieldIdentList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ClassFieldIdentList!=null) ClassFieldIdentList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ClassFieldIdentList!=null) ClassFieldIdentList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassFieldDeclaration(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassFieldIdentList!=null)
            buffer.append(ClassFieldIdentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassFieldDeclaration]");
        return buffer.toString();
    }
}
