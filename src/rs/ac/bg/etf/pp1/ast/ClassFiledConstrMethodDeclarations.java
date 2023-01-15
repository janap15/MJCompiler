// generated with ast extension for cup
// version 0.8
// 14/0/2023 20:49:37


package rs.ac.bg.etf.pp1.ast;

public class ClassFiledConstrMethodDeclarations extends ClassFieldConstrMethodDeclList {

    private ClassFieldDeclList ClassFieldDeclList;
    private MethodOrConstrDeclList MethodOrConstrDeclList;

    public ClassFiledConstrMethodDeclarations (ClassFieldDeclList ClassFieldDeclList, MethodOrConstrDeclList MethodOrConstrDeclList) {
        this.ClassFieldDeclList=ClassFieldDeclList;
        if(ClassFieldDeclList!=null) ClassFieldDeclList.setParent(this);
        this.MethodOrConstrDeclList=MethodOrConstrDeclList;
        if(MethodOrConstrDeclList!=null) MethodOrConstrDeclList.setParent(this);
    }

    public ClassFieldDeclList getClassFieldDeclList() {
        return ClassFieldDeclList;
    }

    public void setClassFieldDeclList(ClassFieldDeclList ClassFieldDeclList) {
        this.ClassFieldDeclList=ClassFieldDeclList;
    }

    public MethodOrConstrDeclList getMethodOrConstrDeclList() {
        return MethodOrConstrDeclList;
    }

    public void setMethodOrConstrDeclList(MethodOrConstrDeclList MethodOrConstrDeclList) {
        this.MethodOrConstrDeclList=MethodOrConstrDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassFieldDeclList!=null) ClassFieldDeclList.accept(visitor);
        if(MethodOrConstrDeclList!=null) MethodOrConstrDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassFieldDeclList!=null) ClassFieldDeclList.traverseTopDown(visitor);
        if(MethodOrConstrDeclList!=null) MethodOrConstrDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassFieldDeclList!=null) ClassFieldDeclList.traverseBottomUp(visitor);
        if(MethodOrConstrDeclList!=null) MethodOrConstrDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassFiledConstrMethodDeclarations(\n");

        if(ClassFieldDeclList!=null)
            buffer.append(ClassFieldDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodOrConstrDeclList!=null)
            buffer.append(MethodOrConstrDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassFiledConstrMethodDeclarations]");
        return buffer.toString();
    }
}
