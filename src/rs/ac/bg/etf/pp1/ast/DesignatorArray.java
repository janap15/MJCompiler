// generated with ast extension for cup
// version 0.8
// 16/0/2023 15:0:37


package rs.ac.bg.etf.pp1.ast;

public class DesignatorArray extends Designator {

    private DesignatorArrayLBracket DesignatorArrayLBracket;
    private Expr Expr;

    public DesignatorArray (DesignatorArrayLBracket DesignatorArrayLBracket, Expr Expr) {
        this.DesignatorArrayLBracket=DesignatorArrayLBracket;
        if(DesignatorArrayLBracket!=null) DesignatorArrayLBracket.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public DesignatorArrayLBracket getDesignatorArrayLBracket() {
        return DesignatorArrayLBracket;
    }

    public void setDesignatorArrayLBracket(DesignatorArrayLBracket DesignatorArrayLBracket) {
        this.DesignatorArrayLBracket=DesignatorArrayLBracket;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorArrayLBracket!=null) DesignatorArrayLBracket.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorArrayLBracket!=null) DesignatorArrayLBracket.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorArrayLBracket!=null) DesignatorArrayLBracket.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorArray(\n");

        if(DesignatorArrayLBracket!=null)
            buffer.append(DesignatorArrayLBracket.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorArray]");
        return buffer.toString();
    }
}
