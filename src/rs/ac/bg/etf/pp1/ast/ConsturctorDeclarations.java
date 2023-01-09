// generated with ast extension for cup
// version 0.8
// 9/0/2023 19:17:5


package rs.ac.bg.etf.pp1.ast;

public class ConsturctorDeclarations extends MethodOrConstrDeclList {

    private MethodOrConstrDeclList MethodOrConstrDeclList;
    private MethodOrConstrDecl MethodOrConstrDecl;

    public ConsturctorDeclarations (MethodOrConstrDeclList MethodOrConstrDeclList, MethodOrConstrDecl MethodOrConstrDecl) {
        this.MethodOrConstrDeclList=MethodOrConstrDeclList;
        if(MethodOrConstrDeclList!=null) MethodOrConstrDeclList.setParent(this);
        this.MethodOrConstrDecl=MethodOrConstrDecl;
        if(MethodOrConstrDecl!=null) MethodOrConstrDecl.setParent(this);
    }

    public MethodOrConstrDeclList getMethodOrConstrDeclList() {
        return MethodOrConstrDeclList;
    }

    public void setMethodOrConstrDeclList(MethodOrConstrDeclList MethodOrConstrDeclList) {
        this.MethodOrConstrDeclList=MethodOrConstrDeclList;
    }

    public MethodOrConstrDecl getMethodOrConstrDecl() {
        return MethodOrConstrDecl;
    }

    public void setMethodOrConstrDecl(MethodOrConstrDecl MethodOrConstrDecl) {
        this.MethodOrConstrDecl=MethodOrConstrDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodOrConstrDeclList!=null) MethodOrConstrDeclList.accept(visitor);
        if(MethodOrConstrDecl!=null) MethodOrConstrDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodOrConstrDeclList!=null) MethodOrConstrDeclList.traverseTopDown(visitor);
        if(MethodOrConstrDecl!=null) MethodOrConstrDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodOrConstrDeclList!=null) MethodOrConstrDeclList.traverseBottomUp(visitor);
        if(MethodOrConstrDecl!=null) MethodOrConstrDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConsturctorDeclarations(\n");

        if(MethodOrConstrDeclList!=null)
            buffer.append(MethodOrConstrDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodOrConstrDecl!=null)
            buffer.append(MethodOrConstrDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConsturctorDeclarations]");
        return buffer.toString();
    }
}
