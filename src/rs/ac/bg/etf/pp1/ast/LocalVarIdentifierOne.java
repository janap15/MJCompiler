// generated with ast extension for cup
// version 0.8
// 16/0/2023 15:0:37


package rs.ac.bg.etf.pp1.ast;

public class LocalVarIdentifierOne extends LocalVarIdent {

    private String varName;

    public LocalVarIdentifierOne (String varName) {
        this.varName=varName;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
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
        buffer.append("LocalVarIdentifierOne(\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LocalVarIdentifierOne]");
        return buffer.toString();
    }
}
