package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import org.apache.log4j.Logger;

public class RuleVisitor extends VisitorAdaptor{

	Logger log = Logger.getLogger(getClass());
	
	int printCallCnt = 0;
	int varDeclCnt = 0;
	
	public void visit(LocalVarIdentifierOne vardecl) {
		varDeclCnt++;
	}
	
	public void visit(PrintExpr print) {
		printCallCnt++;
	}
}
