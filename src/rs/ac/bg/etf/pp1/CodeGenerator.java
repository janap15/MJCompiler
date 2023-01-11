package rs.ac.bg.etf.pp1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.Addop;
import rs.ac.bg.etf.pp1.ast.AddopMinusSign;
import rs.ac.bg.etf.pp1.ast.AddopPlusSign;
import rs.ac.bg.etf.pp1.ast.AddopTerms;
import rs.ac.bg.etf.pp1.ast.AndBegin;
import rs.ac.bg.etf.pp1.ast.BreakStatement;
import rs.ac.bg.etf.pp1.ast.CondFactNoRelop;
import rs.ac.bg.etf.pp1.ast.CondFactRelop;
import rs.ac.bg.etf.pp1.ast.ContinueStatement;
import rs.ac.bg.etf.pp1.ast.DesignatorArrayLBracket;
import rs.ac.bg.etf.pp1.ast.DesignatorAssignStatement;
import rs.ac.bg.etf.pp1.ast.DesignatorMethodCall;
import rs.ac.bg.etf.pp1.ast.DesignatorMultiAssign;
import rs.ac.bg.etf.pp1.ast.DesignatorMultiBegin;
import rs.ac.bg.etf.pp1.ast.DesignatorMultiSingle;
import rs.ac.bg.etf.pp1.ast.DesignatorPostDecrement;
import rs.ac.bg.etf.pp1.ast.DesignatorPostIncrement;
import rs.ac.bg.etf.pp1.ast.ElseBegin;
import rs.ac.bg.etf.pp1.ast.ElseStatement;
import rs.ac.bg.etf.pp1.ast.FactorConst;
import rs.ac.bg.etf.pp1.ast.FactorDesignator;
import rs.ac.bg.etf.pp1.ast.FactorNewArray;
import rs.ac.bg.etf.pp1.ast.FactorNewConstructor;
import rs.ac.bg.etf.pp1.ast.IfBegin;
import rs.ac.bg.etf.pp1.ast.IfCond;
import rs.ac.bg.etf.pp1.ast.IfCondition;
import rs.ac.bg.etf.pp1.ast.IfStatement;
import rs.ac.bg.etf.pp1.ast.MethodCall;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.Mulop;
import rs.ac.bg.etf.pp1.ast.MulopDividesSign;
import rs.ac.bg.etf.pp1.ast.MulopModuloSign;
import rs.ac.bg.etf.pp1.ast.MulopTimesSign;
import rs.ac.bg.etf.pp1.ast.NoDesignatorMulti;
import rs.ac.bg.etf.pp1.ast.NoElse;
import rs.ac.bg.etf.pp1.ast.OrBegin;
import rs.ac.bg.etf.pp1.ast.PrintExpr;
import rs.ac.bg.etf.pp1.ast.PrintExprNum;
import rs.ac.bg.etf.pp1.ast.ReadStatement;
import rs.ac.bg.etf.pp1.ast.Relop;
import rs.ac.bg.etf.pp1.ast.RelopEqual;
import rs.ac.bg.etf.pp1.ast.RelopGreaterEqualThan;
import rs.ac.bg.etf.pp1.ast.RelopGreaterThan;
import rs.ac.bg.etf.pp1.ast.RelopLessEqual;
import rs.ac.bg.etf.pp1.ast.RelopLessThan;
import rs.ac.bg.etf.pp1.ast.RelopNotEqual;
import rs.ac.bg.etf.pp1.ast.ReturnExpr;
import rs.ac.bg.etf.pp1.ast.TermMulopFactors;
import rs.ac.bg.etf.pp1.ast.TermNegative;
import rs.ac.bg.etf.pp1.ast.Then;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.ac.bg.etf.pp1.ast.WhileBegin;
import rs.ac.bg.etf.pp1.ast.WhileCondition;
import rs.ac.bg.etf.pp1.ast.WhileStatement;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int dataSize = 0;
	private int mainPC;
	
	private Stack<List<Integer>> inverseCondAdr = new Stack<>();
	private Stack<List<Integer>> rightCondAdr = new Stack<>();
	private Stack<List<Integer>> breakCondAdr = new Stack<>();
	
	private Stack<Integer> elseAdr = new Stack<>();
	private Stack<Integer> whileAdr = new Stack<>();
	private Stack<Integer> lastRelop = new Stack<>();
	
	private ArrayList<Obj> multiAssignList = new ArrayList<>();
	
	public int getdataSize() {
		return dataSize;
	}

	public int getMainPC() {
		return mainPC;
	}
	
	public void setDataSize(int size) {
		dataSize = size;
	}

	@Override
	public void visit(MethodDecl methodDecl) {
		Obj methObj = methodDecl.getMethodTypeName().obj;
		
		if (methObj.getType() != Tab.noType) { // runtime error
			Code.put(Code.trap);
			Code.put(-1);
		} else {
			Code.put(Code.exit);
			Code.put(Code.return_);
		}	
	}
	
	@Override
	public void visit(MethodTypeName methodTypeName) {
		if ("main".equals(methodTypeName.getMethodName())) {
			mainPC = Code.pc;
			// TODO vft
		}
		Obj methodObj = methodTypeName.obj;
		methodObj.setAdr(Code.pc);
		
		// generate entry
		Code.put(Code.enter);
		Code.put(methodObj.getLevel());  // params cnt
		Code.put(methodObj.getLocalSymbols().size());  // params and locals
	}
	
	@Override
	public void visit(ReturnExpr returnExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(ReadStatement readStatement) {
		Obj designatorObj = readStatement.getDesignator().obj;
		
		if (designatorObj.getType() != Tab.charType) {
			Code.put(Code.read);
		}
		else Code.put(Code.bread);
		
		Code.store(designatorObj);
	}
	
	@Override
	public void visit(PrintExpr printExpr) {
		if (printExpr.getExpr().struct != Tab.charType) {
			Code.loadConst(5);
			Code.put(Code.print);
		}
		else {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}
	
	@Override
	public void visit(PrintExprNum printExprNum) {
		int printWidth = printExprNum.getPrintNum();
		Code.loadConst(printWidth);
		
		if (printExprNum.getExpr().struct != Tab.charType) {
			Code.put(Code.print);
		} 
		else Code.put(Code.bprint);
	}
	
	@Override
	public void visit(DesignatorAssignStatement designatorAssignStatement) {
		Code.store(designatorAssignStatement.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorPostDecrement designatorPostDecrement) {
		Obj designatorObj = designatorPostDecrement.getDesignator().obj;
		
		if (designatorObj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		} 
		else if (designatorObj.getKind() == Obj.Fld) {
			Code.put(Code.dup);
		}
		
		Code.load(designatorObj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designatorObj);
	}
	
	@Override
	public void visit(DesignatorPostIncrement designatorPostIncrement) {
		Obj designatorObj = designatorPostIncrement.getDesignator().obj;
		
		if (designatorObj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		} 
		else if (designatorObj.getKind() == Obj.Fld) {
			Code.put(Code.dup);
		}
		
		Code.load(designatorObj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designatorObj);
	}
	
	@Override
	public void visit(DesignatorMethodCall designatorMethodCall) {
		if (designatorMethodCall.getMethodCall().struct != Tab.noType) {
			Code.put(Code.pop);    // ima povratnu vrednost na steku
		}
	}
	
	@Override
	public void visit(MethodCall methodCall) {
		Obj methodObj = methodCall.getMethodCallBegin().obj;
		// TODO klase
		int offset = methodObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	
	@Override
	public void visit(AddopTerms addopTerms) {
		Addop addop = addopTerms.getAddop();
		
		if (addop instanceof AddopPlusSign) {
			Code.put(Code.add);
		}
		else if (addop instanceof AddopMinusSign) {
			Code.put(Code.sub);
		}
	}
	
	@Override
	public void visit(TermMulopFactors mulopFactor) {
		Mulop mulop = mulopFactor.getMulop();
		
		if (mulop instanceof MulopTimesSign) {
			Code.put(Code.mul);
		}
		else if (mulop instanceof MulopDividesSign) {
			Code.put(Code.div);
		}
		else if (mulop instanceof MulopModuloSign) {
			Code.put(Code.rem);
		}
	}
	
	@Override
	public void visit(TermNegative termNegative) {
		Code.put(Code.neg);
	}
	
	@Override
	public void visit(FactorDesignator factorDesignator) {
		Code.load(factorDesignator.getDesignator().obj);
	}
	
	@Override
	public void visit(FactorConst factorConst) {
		Code.load(factorConst.getConstType().obj);
	}
	
	@Override
	public void visit(FactorNewArray factorNewArray) {
		Struct arrType = factorNewArray.getType().struct;
		
		Code.put(Code.newarray);
		if (arrType != Tab.charType) {
			Code.put(1);
		}
		else Code.put(0);
	}
	
	@Override
	public void visit(FactorNewConstructor factorNewConstructor) {
		// TODO
	}
	
	@Override
	public void visit(DesignatorArrayLBracket designatorArrayLBracket) {
		Code.load(designatorArrayLBracket.obj);
	}
	
	@Override
	public void visit(DesignatorMultiSingle designator) {
		multiAssignList.add(designator.getDesignator().obj);
	}
	
	@Override
	public void visit(NoDesignatorMulti designator){
		multiAssignList.add(null);
	}
	
	@Override
	public void visit(DesignatorMultiBegin DesignatorMultiBegin) {
		multiAssignList = new ArrayList<>();
	}
	
	@Override
	public void visit(DesignatorMultiAssign designatorMultiAssign) {
		Obj designatorObj = designatorMultiAssign.getDesignator().obj;
		// TODO arraylength
		for (int i = multiAssignList.size() - 1; i >= 0; i--) {
			Obj multiDst = multiAssignList.get(i);
			if (multiDst != null) {
				System.err.println(multiDst.getName());
			}
			if (multiDst != null) {
				Code.load(designatorObj);
				Code.loadConst(i);
				Code.load(new Obj(Obj.Elem, designatorObj.getName(), designatorObj.getType().getElemType()));
				Code.store(multiDst);
			}
		}
		
		multiAssignList.clear();
	}
	
	@Override
	public void visit(CondFactRelop condFactRelop) {
		Relop relop = condFactRelop.getRelop();
		
		if (relop instanceof RelopEqual) {
			lastRelop.add(Code.eq);
		}
		else if (relop instanceof RelopNotEqual) {
			lastRelop.add(Code.ne);
		}
		else if (relop instanceof RelopGreaterThan) {
			lastRelop.add(Code.gt);
		}
		else if (relop instanceof RelopGreaterEqualThan) {
			lastRelop.add(Code.ge);
		} 
		else if (relop instanceof RelopLessThan) {
			lastRelop.add(Code.lt);
		}
		else if (relop instanceof RelopLessEqual) {
			lastRelop.add(Code.le);
		}
	}
	
	@Override
	public void visit(CondFactNoRelop noRelop) {
		Code.loadConst(0);    // mora se staviti nesto za if (true)
		lastRelop.push(Code.ne);
	}
	
	@Override
	public void visit(IfBegin ifBegin) {
		rightCondAdr.push(new ArrayList<>());
		inverseCondAdr.push(new ArrayList<>());
	}
	
	@Override
	public void visit(IfCond ifCond) {
		int relop = lastRelop.pop();
		Code.putFalseJump(relop, 0);
		
		List<Integer> adrs = inverseCondAdr.peek();
		adrs.add(Code.pc - 2);
	}
	
	@Override
	public void visit(IfStatement ifStatement) {
		rightCondAdr.pop();
		inverseCondAdr.pop();
	}
	
	@Override
	public void visit(AndBegin andBegin) {
		int relop = lastRelop.pop();
		Code.putFalseJump(relop, 0);
		
		List<Integer> adrs = inverseCondAdr.peek();
		adrs.add(Code.pc - 2);
	}
	
	@Override
	public void visit(ElseBegin elseBegin) {
		Code.putJump(0);
		elseAdr.add(Code.pc - 2);
		
		List<Integer> adrs = inverseCondAdr.peek();
		Iterator<Integer> iter = adrs.iterator();
		
		while(iter.hasNext()) {
			Integer adr = iter.next();
			Code.fixup(adr);
		}
	}
	
	@Override
	public void visit(ElseStatement elseStatement) {
		int adr = elseAdr.pop();
		Code.fixup(adr);
	}
	
	@Override
	public void visit(NoElse noElse) {
		List<Integer> adrs = inverseCondAdr.peek();
		Iterator<Integer> iter = adrs.iterator();
		
		while(iter.hasNext()) {
			Integer adr = iter.next();
			Code.fixup(adr);
		}
	}
	
	@Override
	public void visit(Then then) {
		List<Integer> adrs = rightCondAdr.peek();
		Iterator<Integer> iter = adrs.iterator();
		
		while(iter.hasNext()) {
			Integer adr = iter.next();
			Code.fixup(adr);
		}
	}
	
	
	@Override
	public void visit(OrBegin orBegin) {
		int relop = lastRelop.pop();
		Code.putFalseJump(Code.inverse[relop], 0);
		
		List<Integer> rightAdr = rightCondAdr.peek();
		rightAdr.add(Code.pc - 2);
		
		List<Integer> inverseAdr = inverseCondAdr.peek();
		Iterator<Integer> iter = inverseAdr.iterator();
		
		while (iter.hasNext()) {
			Integer adr = iter.next();
			Code.fixup(adr);
		}
	}
	
	@Override
	public void visit(WhileBegin  whileBegin) {
		rightCondAdr.push(new ArrayList<>());
		inverseCondAdr.push(new ArrayList<>());
		breakCondAdr.push(new ArrayList<>());
		whileAdr.push(Code.pc);
	}
	
	@Override
	public void visit(WhileCondition whileCond) {
		int relop = lastRelop.pop();
		Code.putFalseJump(relop, 0);
		
		List<Integer> adrs = inverseCondAdr.peek();
		adrs.add(Code.pc - 2);
	}
	
	@Override
	public void visit(WhileStatement whileStatement) {
		int adrBeginWhile = whileAdr.pop();
		Code.putJump(adrBeginWhile);
		
		List<Integer> inverseAdr = inverseCondAdr.pop();
		Iterator<Integer> iter = inverseAdr.iterator();
		
		while (iter.hasNext()) {
			Integer adr = iter.next();
			Code.fixup(adr);
		}
		
		List<Integer> breakAdr = breakCondAdr.pop();
		iter = breakAdr.iterator();
		while (iter.hasNext()) {
			Integer adr = iter.next();
			Code.fixup(adr);
		}
		
		rightCondAdr.pop();
	}
	
	@Override
	public void visit(BreakStatement breakStatement) {
		Code.putJump(0);
		breakCondAdr.peek().add(Code.pc - 2);
	}
	
	@Override
	public void visit(ContinueStatement continueStatement) {
		Code.putJump(whileAdr.peek());
	}
	
	
}
	
