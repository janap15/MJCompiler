// generated with ast extension for cup
// version 0.8
// 16/0/2023 15:0:37


package rs.ac.bg.etf.pp1.ast;

public class ClassFieldDeclarationsList extends ClassFieldConstrMethodDeclList {

    private ClassFieldDeclList ClassFieldDeclList;

    public ClassFieldDeclarationsList (ClassFieldDeclList ClassFieldDeclList) {
        this.ClassFieldDeclList=ClassFieldDeclList;
        if(ClassFieldDeclList!=null) ClassFieldDeclList.setParent(this);
    }

    public ClassFieldDeclList getClassFieldDeclList() {
        return ClassFieldDeclList;
    }

    public void setClassFieldDeclList(ClassFieldDeclList ClassFieldDeclList) {
        this.ClassFieldDeclList=ClassFieldDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassFieldDeclList!=null) ClassFieldDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassFieldDeclList!=null) ClassFieldDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassFieldDeclList!=null) ClassFieldDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassFieldDeclarationsList(\n");

        if(ClassFieldDeclList!=null)
            buffer.append(ClassFieldDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassFieldDeclarationsList]");
        return buffer.toString();
    }
}
