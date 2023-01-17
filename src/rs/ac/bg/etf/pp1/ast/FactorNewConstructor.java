// generated with ast extension for cup
// version 0.8
// 16/0/2023 15:0:37


package rs.ac.bg.etf.pp1.ast;

public class FactorNewConstructor extends Factor {

    private Type Type;
    private ConstructorCallBegin ConstructorCallBegin;
    private ActualPars ActualPars;

    public FactorNewConstructor (Type Type, ConstructorCallBegin ConstructorCallBegin, ActualPars ActualPars) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ConstructorCallBegin=ConstructorCallBegin;
        if(ConstructorCallBegin!=null) ConstructorCallBegin.setParent(this);
        this.ActualPars=ActualPars;
        if(ActualPars!=null) ActualPars.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ConstructorCallBegin getConstructorCallBegin() {
        return ConstructorCallBegin;
    }

    public void setConstructorCallBegin(ConstructorCallBegin ConstructorCallBegin) {
        this.ConstructorCallBegin=ConstructorCallBegin;
    }

    public ActualPars getActualPars() {
        return ActualPars;
    }

    public void setActualPars(ActualPars ActualPars) {
        this.ActualPars=ActualPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ConstructorCallBegin!=null) ConstructorCallBegin.accept(visitor);
        if(ActualPars!=null) ActualPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstructorCallBegin!=null) ConstructorCallBegin.traverseTopDown(visitor);
        if(ActualPars!=null) ActualPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstructorCallBegin!=null) ConstructorCallBegin.traverseBottomUp(visitor);
        if(ActualPars!=null) ActualPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorNewConstructor(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstructorCallBegin!=null)
            buffer.append(ConstructorCallBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActualPars!=null)
            buffer.append(ActualPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorNewConstructor]");
        return buffer.toString();
    }
}
