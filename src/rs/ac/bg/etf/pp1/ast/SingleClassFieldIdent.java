// generated with ast extension for cup
// version 0.8
// 16/0/2023 15:0:37


package rs.ac.bg.etf.pp1.ast;

public class SingleClassFieldIdent extends ClassFieldIdentList {

    private ClassFieldIdent ClassFieldIdent;

    public SingleClassFieldIdent (ClassFieldIdent ClassFieldIdent) {
        this.ClassFieldIdent=ClassFieldIdent;
        if(ClassFieldIdent!=null) ClassFieldIdent.setParent(this);
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
        if(ClassFieldIdent!=null) ClassFieldIdent.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassFieldIdent!=null) ClassFieldIdent.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassFieldIdent!=null) ClassFieldIdent.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleClassFieldIdent(\n");

        if(ClassFieldIdent!=null)
            buffer.append(ClassFieldIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleClassFieldIdent]");
        return buffer.toString();
    }
}
