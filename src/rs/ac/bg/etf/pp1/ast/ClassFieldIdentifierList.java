// generated with ast extension for cup
// version 0.8
// 16/0/2023 15:0:37


package rs.ac.bg.etf.pp1.ast;

public class ClassFieldIdentifierList extends ClassFieldIdentList {

    private ClassFieldIdentList ClassFieldIdentList;
    private ClassFieldIdent ClassFieldIdent;

    public ClassFieldIdentifierList (ClassFieldIdentList ClassFieldIdentList, ClassFieldIdent ClassFieldIdent) {
        this.ClassFieldIdentList=ClassFieldIdentList;
        if(ClassFieldIdentList!=null) ClassFieldIdentList.setParent(this);
        this.ClassFieldIdent=ClassFieldIdent;
        if(ClassFieldIdent!=null) ClassFieldIdent.setParent(this);
    }

    public ClassFieldIdentList getClassFieldIdentList() {
        return ClassFieldIdentList;
    }

    public void setClassFieldIdentList(ClassFieldIdentList ClassFieldIdentList) {
        this.ClassFieldIdentList=ClassFieldIdentList;
    }

    public ClassFieldIdent getClassFieldIdent() {
        return ClassFieldIdent;
    }

    public void setClassFieldIdent(ClassFieldIdent ClassFieldIdent) {
        this.ClassFieldIdent=ClassFieldIdent;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassFieldIdentList!=null) ClassFieldIdentList.accept(visitor);
        if(ClassFieldIdent!=null) ClassFieldIdent.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassFieldIdentList!=null) ClassFieldIdentList.traverseTopDown(visitor);
        if(ClassFieldIdent!=null) ClassFieldIdent.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassFieldIdentList!=null) ClassFieldIdentList.traverseBottomUp(visitor);
        if(ClassFieldIdent!=null) ClassFieldIdent.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassFieldIdentifierList(\n");

        if(ClassFieldIdentList!=null)
            buffer.append(ClassFieldIdentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassFieldIdent!=null)
            buffer.append(ClassFieldIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassFieldIdentifierList]");
        return buffer.toString();
    }
}
