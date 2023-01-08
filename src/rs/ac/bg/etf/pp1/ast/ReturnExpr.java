// generated with ast extension for cup
// version 0.8
// 8/0/2023 18:25:9


package rs.ac.bg.etf.pp1.ast;

public class ReturnExpr extends Statement {

    private ReturnBegin ReturnBegin;
    private ExprOptional ExprOptional;

    public ReturnExpr (ReturnBegin ReturnBegin, ExprOptional ExprOptional) {
        this.ReturnBegin=ReturnBegin;
        if(ReturnBegin!=null) ReturnBegin.setParent(this);
        this.ExprOptional=ExprOptional;
        if(ExprOptional!=null) ExprOptional.setParent(this);
    }

    public ReturnBegin getReturnBegin() {
        return ReturnBegin;
    }

    public void setReturnBegin(ReturnBegin ReturnBegin) {
        this.ReturnBegin=ReturnBegin;
    }

    public ExprOptional getExprOptional() {
        return ExprOptional;
    }

    public void setExprOptional(ExprOptional ExprOptional) {
        this.ExprOptional=ExprOptional;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ReturnBegin!=null) ReturnBegin.accept(visitor);
        if(ExprOptional!=null) ExprOptional.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ReturnBegin!=null) ReturnBegin.traverseTopDown(visitor);
        if(ExprOptional!=null) ExprOptional.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ReturnBegin!=null) ReturnBegin.traverseBottomUp(visitor);
        if(ExprOptional!=null) ExprOptional.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ReturnExpr(\n");

        if(ReturnBegin!=null)
            buffer.append(ReturnBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExprOptional!=null)
            buffer.append(ExprOptional.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ReturnExpr]");
        return buffer.toString();
    }
}
