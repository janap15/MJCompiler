// generated with ast extension for cup
// version 0.8
// 9/0/2023 19:17:5


package rs.ac.bg.etf.pp1.ast;

public class DesignatorMethodCall extends DesignatorOther {

    private MethodCall MethodCall;

    public DesignatorMethodCall (MethodCall MethodCall) {
        this.MethodCall=MethodCall;
        if(MethodCall!=null) MethodCall.setParent(this);
    }

    public MethodCall getMethodCall() {
        return MethodCall;
    }

    public void setMethodCall(MethodCall MethodCall) {
        this.MethodCall=MethodCall;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodCall!=null) MethodCall.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodCall!=null) MethodCall.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodCall!=null) MethodCall.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorMethodCall(\n");

        if(MethodCall!=null)
            buffer.append(MethodCall.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorMethodCall]");
        return buffer.toString();
    }
}
