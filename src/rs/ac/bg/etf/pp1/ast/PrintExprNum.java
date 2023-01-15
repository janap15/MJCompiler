// generated with ast extension for cup
// version 0.8
// 14/0/2023 20:49:37


package rs.ac.bg.etf.pp1.ast;

public class PrintExprNum extends Statement {

    private PrintBegin PrintBegin;
    private Expr Expr;
    private Integer printNum;

    public PrintExprNum (PrintBegin PrintBegin, Expr Expr, Integer printNum) {
        this.PrintBegin=PrintBegin;
        if(PrintBegin!=null) PrintBegin.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.printNum=printNum;
    }

    public PrintBegin getPrintBegin() {
        return PrintBegin;
    }

    public void setPrintBegin(PrintBegin PrintBegin) {
        this.PrintBegin=PrintBegin;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public Integer getPrintNum() {
        return printNum;
    }

    public void setPrintNum(Integer printNum) {
        this.printNum=printNum;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(PrintBegin!=null) PrintBegin.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(PrintBegin!=null) PrintBegin.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(PrintBegin!=null) PrintBegin.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PrintExprNum(\n");

        if(PrintBegin!=null)
            buffer.append(PrintBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+printNum);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PrintExprNum]");
        return buffer.toString();
    }
}
