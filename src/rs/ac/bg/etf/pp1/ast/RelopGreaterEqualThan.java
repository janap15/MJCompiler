// generated with ast extension for cup
// version 0.8
// 11/0/2023 23:45:0


package rs.ac.bg.etf.pp1.ast;

public class RelopGreaterEqualThan extends Relop {

    public RelopGreaterEqualThan () {
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
        buffer.append("RelopGreaterEqualThan(\n");

        buffer.append(tab);
        buffer.append(") [RelopGreaterEqualThan]");
        return buffer.toString();
    }
}
