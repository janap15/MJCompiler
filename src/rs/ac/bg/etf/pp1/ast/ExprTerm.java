// generated with ast extension for cup
// version 0.8
// 8/0/2023 18:25:9


package rs.ac.bg.etf.pp1.ast;

public class ExprTerm extends Expr {

    private SingleTerm SingleTerm;

    public ExprTerm (SingleTerm SingleTerm) {
        this.SingleTerm=SingleTerm;
        if(SingleTerm!=null) SingleTerm.setParent(this);
    }

    public SingleTerm getSingleTerm() {
        return SingleTerm;
    }

    public void setSingleTerm(SingleTerm SingleTerm) {
        this.SingleTerm=SingleTerm;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SingleTerm!=null) SingleTerm.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SingleTerm!=null) SingleTerm.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SingleTerm!=null) SingleTerm.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprTerm(\n");

        if(SingleTerm!=null)
            buffer.append(SingleTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprTerm]");
        return buffer.toString();
    }
}
