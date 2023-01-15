// generated with ast extension for cup
// version 0.8
// 14/0/2023 20:49:37


package rs.ac.bg.etf.pp1.ast;

public class ClassDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ClassName ClassName;
    private ExtendsType ExtendsType;
    private ClassFieldConstrMethodDeclList ClassFieldConstrMethodDeclList;

    public ClassDecl (ClassName ClassName, ExtendsType ExtendsType, ClassFieldConstrMethodDeclList ClassFieldConstrMethodDeclList) {
        this.ClassName=ClassName;
        if(ClassName!=null) ClassName.setParent(this);
        this.ExtendsType=ExtendsType;
        if(ExtendsType!=null) ExtendsType.setParent(this);
        this.ClassFieldConstrMethodDeclList=ClassFieldConstrMethodDeclList;
        if(ClassFieldConstrMethodDeclList!=null) ClassFieldConstrMethodDeclList.setParent(this);
    }

    public ClassName getClassName() {
        return ClassName;
    }

    public void setClassName(ClassName ClassName) {
        this.ClassName=ClassName;
    }

    public ExtendsType getExtendsType() {
        return ExtendsType;
    }

    public void setExtendsType(ExtendsType ExtendsType) {
        this.ExtendsType=ExtendsType;
    }

    public ClassFieldConstrMethodDeclList getClassFieldConstrMethodDeclList() {
        return ClassFieldConstrMethodDeclList;
    }

    public void setClassFieldConstrMethodDeclList(ClassFieldConstrMethodDeclList ClassFieldConstrMethodDeclList) {
        this.ClassFieldConstrMethodDeclList=ClassFieldConstrMethodDeclList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassName!=null) ClassName.accept(visitor);
        if(ExtendsType!=null) ExtendsType.accept(visitor);
        if(ClassFieldConstrMethodDeclList!=null) ClassFieldConstrMethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassName!=null) ClassName.traverseTopDown(visitor);
        if(ExtendsType!=null) ExtendsType.traverseTopDown(visitor);
        if(ClassFieldConstrMethodDeclList!=null) ClassFieldConstrMethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassName!=null) ClassName.traverseBottomUp(visitor);
        if(ExtendsType!=null) ExtendsType.traverseBottomUp(visitor);
        if(ClassFieldConstrMethodDeclList!=null) ClassFieldConstrMethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDecl(\n");

        if(ClassName!=null)
            buffer.append(ClassName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExtendsType!=null)
            buffer.append(ExtendsType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassFieldConstrMethodDeclList!=null)
            buffer.append(ClassFieldConstrMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDecl]");
        return buffer.toString();
    }
}
