// generated with ast extension for cup
// version 0.8
// 14/0/2023 20:49:37


package rs.ac.bg.etf.pp1.ast;

public class NoReturn extends ReturnType {

    public NoReturn () {
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
        buffer.append("NoReturn(\n");

        buffer.append(tab);
        buffer.append(") [NoReturn]");
        return buffer.toString();
    }
}
