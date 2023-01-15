// generated with ast extension for cup
// version 0.8
// 14/0/2023 20:49:37


package rs.ac.bg.etf.pp1.ast;

public class ErrDesignatorAssignment extends DesignatorAssign {

    public ErrDesignatorAssignment () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ErrDesignatorAssignment(\n");

        buffer.append(tab);
        buffer.append(") [ErrDesignatorAssignment]");
        return buffer.toString();
    }
}
