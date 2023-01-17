package rs.ac.bg.etf.pp1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.ActualParam;
import rs.ac.bg.etf.pp1.ast.ActualParams;
import rs.ac.bg.etf.pp1.ast.Addop;
import rs.ac.bg.etf.pp1.ast.AddopMinusSign;
import rs.ac.bg.etf.pp1.ast.AddopPlusSign;
import rs.ac.bg.etf.pp1.ast.AddopTerms;
import rs.ac.bg.etf.pp1.ast.AndBegin;
import rs.ac.bg.etf.pp1.ast.BreakStatement;
import rs.ac.bg.etf.pp1.ast.ClassDecl;
import rs.ac.bg.etf.pp1.ast.ClassName;
import rs.ac.bg.etf.pp1.ast.CondFactNoRelop;
import rs.ac.bg.etf.pp1.ast.CondFactRelop;
import rs.ac.bg.etf.pp1.ast.ConstructorBegin;
import rs.ac.bg.etf.pp1.ast.ConstructorCallBegin;
import rs.ac.bg.etf.pp1.ast.ConstructorDecl;
import rs.ac.bg.etf.pp1.ast.ContinueStatement;
import rs.ac.bg.etf.pp1.ast.DesignatorArrayLBracket;
import rs.ac.bg.etf.pp1.ast.DesignatorAssignStatement;
import rs.ac.bg.etf.pp1.ast.DesignatorClass;
import rs.ac.bg.etf.pp1.ast.DesignatorForEach;
import rs.ac.bg.etf.pp1.ast.DesignatorIdent;
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
import rs.ac.bg.etf.pp1.ast.ForeachBegin;
import rs.ac.bg.etf.pp1.ast.IfBegin;
import rs.ac.bg.etf.pp1.ast.IfCond;
import rs.ac.bg.etf.pp1.ast.IfCondition;
import rs.ac.bg.etf.pp1.ast.IfStatement;
import rs.ac.bg.etf.pp1.ast.MethodCall;
import rs.ac.bg.etf.pp1.ast.MethodCallBegin;
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

	private int mainPC;
	
	private enum MethodType {
		GLOBAL_FUNC, CLASS_METH, CONSTRUCTOR;
	}
	
	private Stack<List<Integer>> inverseCondAdr = new Stack<>();
	private Stack<List<Integer>> rightCondAdr = new Stack<>();
	private Stack<List<Integer>> breakCondAdr = new Stack<>();
	
	private Stack<Integer> elseAdr = new Stack<>();
	private Stack<Integer> whileOrForeachBeginAdr = new Stack<>();
	private int foreachDepth = 0;
	
	private Stack<Integer> lastRelop = new Stack<>();
	
	private ArrayList<Obj> multiAssignList = new ArrayList<>();
	
	private List<Obj> definedClasses = new ArrayList<>();
	private boolean insideClassDef = false;
	
	private  Stack<ArrayList<Struct>> constructorCallActualParamsStack = new Stack<>();
	
	private Stack<MethodType> calledMethodsStack = new Stack<>();

	public int getMainPC() {
		return mainPC;
	}

	private final String joker = "$";
	private boolean isConstructor(String methodName) {
		return methodName.startsWith(joker);
	}
	
	private boolean isCompatibleWithAssign(Struct source, Struct destination) {
		if (source.getKind() != Struct.Class || destination.getKind() != Struct.Class) {
			return source.assignableTo(destination);
		}
		
		if (source == destination || source == Tab.nullType) return true;
		
		Struct parent = source.getElemType();
		while (parent != null) {
			if (parent.assignableTo(destination)) {
				return true;
			}
			parent = parent.getElemType();
		}
		return false;
	}
	
	public CodeGenerator() {
		// predefined functions		
		Obj chrObj = Tab.find("chr");
		chrObj.setAdr(Code.pc);
		
		Code.put(Code.enter);
		Code.put(1); Code.put(1);
		// load0
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		Obj ordObj = Tab.find("ord");
		ordObj.setAdr(Code.pc);
		
		Code.put(Code.enter);
		Code.put(1); Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		Obj lenObj = Tab.find("len");
		lenObj.setAdr(Code.pc);
		
		Code.put(Code.enter);
		Code.put(1); Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	private void createVirtualFunctionsTable() {
		for(Obj definedClassObj : definedClasses) {
			definedClassObj.setAdr(Code.dataSize);
			Collection<Obj> classMembers = definedClassObj.getType().getMembers();
			Iterator<Obj> iter = classMembers.iterator();
			
			while (iter.hasNext()) {
				Obj obj = iter.next();
				if (obj.getKind() != Obj.Meth || isConstructor(obj.getName())) continue; // skip fields
				
				// method name chars
				for (char c : obj.getName().toCharArray()) {
					Code.loadConst(c);
					Code.put(Code.putstatic);
					Code.put2(Code.dataSize++);
				}
				// -1 last char
				Code.loadConst(-1);
				Code.put(Code.putstatic);
				Code.put2(Code.dataSize++);
			
				// method address
				Code.loadConst(obj.getAdr());
				Code.put(Code.putstatic);
				Code.put2(Code.dataSize++);
			}
			// -2 end of vft
			Code.loadConst(-2);
			Code.put(Code.putstatic);
			Code.put2(Code.dataSize++);
		}
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
	public void visit(ConstructorDecl constructorDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(MethodTypeName methodTypeName) {
		if ("main".equals(methodTypeName.getMethodName())) {
			mainPC = Code.pc;
			createVirtualFunctionsTable();
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
		if (foreachDepth > 0) {			// pop (arr & ind)s from stack
			for (int i = 0; i < foreachDepth; i++) {
				Code.put(Code.pop);
				Code.put(Code.pop);
			}
		}
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
	public void visit(MethodCallBegin methodCallBegin) {
		Obj methodObj = methodCallBegin.getDesignator().obj;
		boolean isClassMeth = false;
		
		Collection<Obj> fileds = methodObj.getLocalSymbols();
		for (Obj field : fileds) {
			if ("this".equals(field.getName())) {
				isClassMeth = true;
				break;
			}
		}
		
		if (isClassMeth) {
			calledMethodsStack.push(MethodType.CLASS_METH);
			Code.put(Code.dup);   // this
		}
		else {
			calledMethodsStack.push(MethodType.GLOBAL_FUNC);
		}
	}
	
	@Override
	public void visit(MethodCall methodCall) {
		calledMethodsStack.pop();
		
		Obj methodObj = methodCall.getMethodCallBegin().obj;
		boolean isClassMeth = false;
		
		Collection<Obj> fileds = methodObj.getLocalSymbols();
		for (Obj field : fileds) {
			if ("this".equals(field.getName())) {
				isClassMeth = true;
				break;
			}
		}
		
		if (!isClassMeth) {
			int offset = methodObj.getAdr() - Code.pc;
			Code.put(Code.call);
			Code.put2(offset);
		}
		else {
			// class method has to be invoken virtually
			Code.put(Code.getfield);
			Code.put2(0);				// first fields i vftp
			
			Code.put(Code.invokevirtual);
			for (char c : methodObj.getName().toCharArray()) 
				Code.put4(c);
			Code.put4(-1);
		}
	}
	
	@Override
	public void visit(ActualParam actualParam) {
		if (calledMethodsStack.peek() == MethodType.CLASS_METH) {
			Code.put(Code.dup_x1);
			Code.put(Code.pop);     // swapuje zbog this
		}
		
		if (calledMethodsStack.peek() == MethodType.CONSTRUCTOR) {
			constructorCallActualParamsStack.peek().add(actualParam.getExpr().struct);
		}
			
	}
	
	@Override
	public void visit(ActualParams actualParams) {
		if (calledMethodsStack.peek() == MethodType.CLASS_METH) {
			Code.put(Code.dup_x1);
			Code.put(Code.pop);     // swapuje zbog this
		}
		
		if (calledMethodsStack.peek() == MethodType.CONSTRUCTOR) {
			constructorCallActualParamsStack.peek().add(actualParams.getExpr().struct);
		}
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
	public void visit(ConstructorBegin constructorBegin) {
		Obj methodObj = constructorBegin.obj;
		methodObj.setAdr(Code.pc);
		
		// generate entry
		Code.put(Code.enter);
		Code.put(methodObj.getLevel());  // params cnt
		Code.put(methodObj.getLocalSymbols().size());  // params and locals
	}
	
	@Override
	public void visit(ConstructorCallBegin constructorCallBegin) {
		calledMethodsStack.push(MethodType.CONSTRUCTOR);
		constructorCallActualParamsStack.add(new ArrayList<>());
		
		Struct classType = ((FactorNewConstructor)(constructorCallBegin.getParent())).getType().struct;
		
		Code.put(Code.new_);
		Code.put2(classType.getNumberOfFields() * 4);   //jedna rec
		
		int adrVirtualFUnctionsTable = 0;
		
		Iterator<Obj> iter = definedClasses.iterator();
		while(iter.hasNext()) {
			Obj classObj = iter.next();
			
			if (classType == classObj.getType()) {
				adrVirtualFUnctionsTable = classObj.getAdr();
				break;
			}
		}
		
		Code.put(Code.dup);
		Code.loadConst(adrVirtualFUnctionsTable);
		Code.put(Code.putfield);
		Code.put2(0);
		Code.put(Code.dup);
	}
	
	
	@Override
	public void visit(FactorNewConstructor factorNewConstructor) {
		calledMethodsStack.pop();
		Struct classType = factorNewConstructor.getType().struct;
		ArrayList<Struct> actualPars = constructorCallActualParamsStack.pop();

		Iterator<Obj> iter = definedClasses.iterator();
		Obj classObj = null;
		while(iter.hasNext()) {
			classObj = iter.next();
			if (classType == classObj.getType()) {
				break;
			}
		}
		
		Obj constructor = null;
		boolean found = false;
		
		if (classObj != null) {
			Collection<Obj> fileds = classObj.getType().getMembers();
			for (Obj constr : fileds) {
				if (isConstructor(constr.getName())) {
					
					Collection<Obj> formalParams = constr.getLocalSymbols();
					
					Iterator<Struct> actIter = actualPars.iterator();
					Iterator<Obj> formalIter = formalParams.iterator();
					Iterator<Obj> thisIter = formalParams.iterator();
					
					int formalParamsCount = constr.getLevel();
					Obj formalObj;
					
					if (formalParamsCount > 0) {
						formalObj = thisIter.next();
						if (formalObj.getName().equals("this") && formalObj.getType().getKind() == Struct.Class) {
							formalIter.next();
							formalParamsCount--;
						}
					}
					while (formalParamsCount > 0 && actIter.hasNext()) {
						formalObj = formalIter.next();
						Struct formalParamStruct = formalObj.getType();
						Struct actParamStruct = actIter.next();
						if (!isCompatibleWithAssign(actParamStruct, formalParamStruct)) {
							break;
						}
						formalParamsCount--;
						
					}	
					
					if (!actIter.hasNext() && formalParamsCount == 0) {
						found = true;
					}
					
				}
				if (found) {
					constructor = constr;
					break;
				}
			}
		}
		
		if (found == true) {
			int offset = constructor.getAdr() - Code.pc;
			Code.put(Code.call);
			Code.put2(offset);
		}
		else {
			Code.put(Code.pop);
		}
		
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
		Code.load(designatorObj);
		// obilazak sa desna na levo
		for (int i = multiAssignList.size() - 1; i >= 0; i--) {
			Obj multiDst = multiAssignList.get(i);
			if (multiDst != null) {
				if (multiDst.getKind() == Obj.Elem) {
					// arr, ind, designatorarray
					Code.put(Code.dup_x2);
					// designatorarray, arr, ind, designatorarray
				} 
				else if (multiDst.getKind() == Obj.Fld) {
					// obj, designatorarray
					Code.put(Code.dup_x1);
					// designatorarray, obj, designatorarray
				}
				else {
					// designatorarray
					Code.put(Code.dup);
					// designatorarray, designatorarray
				}
				Code.loadConst(i);
				// designatorarray, *, designatorarray, ind
				Code.load(new Obj(Obj.Elem, designatorObj.getName(), designatorObj.getType().getElemType()));
				// designatorarray, *, designatorarray[ind]
				Code.store(multiDst);
				// designatorarray
			}
		}
		// designatorarray
		Code.put(Code.pop);
		// nista
		multiAssignList.clear();
	}
	
	@Override
	public void visit(DesignatorClass designatorClass) {
		Code.load(designatorClass.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorIdent designatorIdent) {
		Obj currentClass = null;
		if (definedClasses.size() > 0)
			currentClass = definedClasses.get(definedClasses.size()-1);
		
		if (designatorIdent.obj.getKind() == Obj.Fld)
			Code.put(Code.load_n);		// this
		else {
			if (designatorIdent.obj.getKind() == Obj.Meth && insideClassDef && 
					currentClass.getType().getMembersTable().searchKey(designatorIdent.obj.getName()) != null) {
				Code.put(Code.load_n);
			}
		}
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
		whileOrForeachBeginAdr.push(Code.pc);
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
		int adrBeginWhile = whileOrForeachBeginAdr.pop();
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
	public void visit(ForeachBegin  foreachBegin) {
		foreachDepth++;
		
		rightCondAdr.push(new ArrayList<>());
		inverseCondAdr.push(new ArrayList<>());
		breakCondAdr.push(new ArrayList<>());
		
		Obj arrObj = foreachBegin.getDesignator().obj;
		Code.load(arrObj);
		// arr
		Code.loadConst(0);
		// arr, ind
		
		whileOrForeachBeginAdr.push(Code.pc); //ovdenak se vrces na sledeicu iterejciju foricha
		
		Code.put(Code.dup2);
		// arr, ind, arr, ind
		Code.put(Code.dup_x1);
		// arr, ind, ind, arr, ind 
		Code.put(Code.pop);
		// arr, ind, ind, arr
		Code.put(Code.arraylength);
		// arr, ind, ind, len
		Code.putFalseJump(Code.ne, 0);
		// arr, ind	
		List<Integer> adrs = inverseCondAdr.peek();			
		adrs.add(Code.pc - 2);			
		
		Code.put(Code.dup2);
		// arr, ind, arr, ind
		Code.load(new Obj(Obj.Elem, arrObj.getName(), arrObj.getType().getElemType()));
		// arr, ind, arr[ind]
		Obj currentElem = foreachBegin.obj;
		Code.store(currentElem);	// ident = arr[ind]
		// arr, ind														
	}
	
	@Override
	public void visit(DesignatorForEach designatorForEach) {
		foreachDepth--;
		
		Code.loadConst(1);	
		// ind, 1
		Code.put(Code.add);
		// ind + 1
		
		int adrBeginForeach = whileOrForeachBeginAdr.pop();
		Code.putJump(adrBeginForeach);		// next iteration foreach
		
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
		
		Code.put(Code.pop);
		Code.put(Code.pop);
		// arr & ind popped
	}
	
	@Override
	public void visit(BreakStatement breakStatement) {
		Code.putJump(0);
		breakCondAdr.peek().add(Code.pc - 2);
	}
	
	@Override
	public void visit(ContinueStatement continueStatement) {
		Code.putJump(whileOrForeachBeginAdr.peek());
	}
	
	@Override
	public void visit(ClassName className) {
		insideClassDef = true;
		Obj classObj = className.obj;
		definedClasses.add(classObj);
		
		Struct parent = classObj.getType().getElemType();
		if (parent != null) {
			Collection<Obj> classMembers = classObj.getType().getMembers();
			Iterator<Obj> iter = classMembers.iterator();
			
			while (iter.hasNext()) {
				Obj classMember = iter.next();
				// postavljanje adresa iz natklase
				if (classMember.getKind() == Obj.Meth && parent.getMembersTable().searchKey(classMember.getName()) != null)
						classMember.setAdr(parent.getMembersTable().searchKey(classMember.getName()).getAdr());
			}
		}
	}
	
	@Override
	public void visit(ClassDecl classDecl) {
		insideClassDef = false;
	}
	
}
	
