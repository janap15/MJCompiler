// generated with ast extension for cup
// version 0.8
// 14/0/2023 20:49:37


package rs.ac.bg.etf.pp1.ast;

public class ConstructorDecl extends MethodOrConstrDecl {

    private ConstructorBegin ConstructorBegin;
    private ConstructorOrMethod ConstructorOrMethod;

    public ConstructorDecl (ConstructorBegin ConstructorBegin, ConstructorOrMethod ConstructorOrMethod) {
        this.ConstructorBegin=ConstructorBegin;
        if(ConstructorBegin!=null) ConstructorBegin.setParent(this);
        this.ConstructorOrMethod=ConstructorOrMethod;
        if(ConstructorOrMethod!=null) ConstructorOrMethod.setParent(this);
    }

    public ConstructorBegin getConstructorBegin() {
        return ConstructorBegin;
    }

    public void setConstructorBegin(ConstructorBegin ConstructorBegin) {
        this.ConstructorBegin=ConstructorBegin;
    }

    public ConstructorOrMethod getConstructorOrMethod() {
        return ConstructorOrMethod;
    }

    public void setConstructorOrMethod(ConstructorOrMethod ConstructorOrMethod) {
        this.ConstructorOrMethod=ConstructorOrMethod;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstructorBegin!=null) ConstructorBegin.accept(visitor);
        if(ConstructorOrMethod!=null) ConstructorOrMethod.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstructorBegin!=null) ConstructorBegin.traverseTopDown(visitor);
        if(ConstructorOrMethod!=null) ConstructorOrMethod.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstructorBegin!=null) ConstructorBegin.traverseBottomUp(visitor);
        if(ConstructorOrMethod!=null) ConstructorOrMethod.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstructorDecl(\n");

        if(ConstructorBegin!=null)
            buffer.append(ConstructorBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstructorOrMethod!=null)
            buffer.append(ConstructorOrMethod.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstructorDecl]");
        return buffer.toString();
    }
}
