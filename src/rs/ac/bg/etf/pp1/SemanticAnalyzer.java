package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;


public class SemanticAnalyzer extends VisitorAdaptor{
	
	Logger log = Logger.getLogger(getClass());
	
	private boolean returnFound = false;
	private boolean errorDetected = false;
	private boolean mainFound = false;
	
	private Obj currentMethod = null;
	private Struct currentType = Tab.noType;
	private Struct currentClass = Tab.noType;
	private String currentClassName = "";
	private int currentClassConstr = 0;
	
	private final int localVarsCnt = 256;
	private final int globalVarsCnt = 65536;
	private final int classFieldsCnt = 65536;
	private final String joker = "$";
	private final String MAIN = "main";
	
	private int globVarsCnt = 0;
	
	private ArrayList<Struct> multiAssignList = new ArrayList<>();
	private  Stack<ArrayList<Struct>> funcCallActualParamsStack = new Stack<>();
	private Map<String, Integer> constructorNum = new HashMap<>();
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder();
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append("Semanticka greska na liniji ").append(line).append(": ").append(message);
		log.info(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public boolean passed() {
		return !errorDetected;
	}
	
	private boolean isConstructor(String methodName) {
		return methodName.startsWith(joker);
	}

	public int getGlobVarsCnt() {
		return globVarsCnt;
	}
	// dst = src
	private boolean isCompatibleWithAssign(Struct source, Struct destination) {
		//report_info("src = " + source.getKind() + ", dst =" + destination.getKind(), null);
		if (source.getKind() != Struct.Class || destination.getKind() != Struct.Class) {
			return source.assignableTo(destination);
		}
		
		if (source == destination || source == Tab.nullType) return true;
		
		Struct parent = source.getElemType();
		//report_info("parent " + parent.toString(), null);
		while (parent != null) {
			if (parent.assignableTo(destination)) {
				return true;
			}
			parent = parent.getElemType();
		}
		
		return false;
		
	}

	@Override
	public void visit(ProgramName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		Tab.openScope();
	}
	
	@Override
	public void visit(Program program) {
		globVarsCnt = Tab.currentScope.getnVars();
		if (globVarsCnt > globalVarsCnt) {
			report_error("Ne sme se imati vise od " + globalVarsCnt + " promenljivih!", null);
		}
		Tab.chainLocalSymbols(program.getProgramName().obj);
		Tab.closeScope();
	}

	@Override
	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());

		if (typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola!", null); 
			currentType = Tab.noType;
			type.struct = Tab.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				currentType = typeNode.getType();
				type.struct = typeNode.getType();
			}
			else {
				report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", null);
				currentType = Tab.noType;
				type.struct = Tab.noType;
			}
		}
	}
	

	@Override
	public void visit(GlobalVarIdentOne varIdent) {
		if (currentType == Tab.noType) {
			report_error("Tip globalne promenljive " + varIdent.getVarName() + " nije validan!", varIdent);
			return;
		}
		
		if (Tab.currentScope.findSymbol(varIdent.getVarName()) == null) {
			Tab.insert(Obj.Var, varIdent.getVarName(), currentType);
		}
		else {		// variable exists
			report_error("Simbol " + varIdent.getVarName() + " je vec upotrebljen!", varIdent);
		}
	}
	
	@Override
	public void visit(GlobalVarIdentifierArray varIdentArr) {
		if (currentType == Tab.noType) {
			report_error("Tip globalne promenljive " + varIdentArr.getVarName() + " nije validan!", varIdentArr);
			return;
		}
		
		if (Tab.currentScope.findSymbol(varIdentArr.getVarName()) == null) {
			Struct arrayStruct = new Struct(Struct.Array, currentType);
			Tab.insert(Obj.Var, varIdentArr.getVarName(), arrayStruct);
		}
		else {		// variable exists
			report_error("Simbol " + varIdentArr.getVarName() + " je vec upotrebljen!", varIdentArr);
		}
	}
	
	@Override
	public void visit(LocalVarIdentifierOne varIdent) {
		if (currentType == Tab.noType) {
			report_error("Tip lokalne promenljive " + varIdent.getVarName() + " nije validan!", varIdent);
			return;
		}
		
		if (Tab.currentScope.findSymbol(varIdent.getVarName()) == null) {
			report_info("local var " +  varIdent.getVarName() + " " + currentType.getKind(), null);
			Tab.insert(Obj.Var, varIdent.getVarName(), currentType);
		}
		else {		// variable exists
			report_error("Simbol " + varIdent.getVarName() + " je vec upotrebljen!", varIdent);
		}
	}
	
	@Override
	public void visit(LocalVarIdentifierArray varIdentArr) {
		if (currentType == Tab.noType) {
			report_error("Tip lokalne promenljive " + varIdentArr.getVarName() + " nije validan!", varIdentArr);
			return;
		}
		
		if (Tab.currentScope.findSymbol(varIdentArr.getVarName()) == null) {
			Struct arrayStruct = new Struct(Struct.Array, currentType);
			Tab.insert(Obj.Var, varIdentArr.getVarName(), arrayStruct);
		}
		else {		// variable exists
			report_error("Simbol " + varIdentArr.getVarName() + " je vec upotrebljen!", varIdentArr);
		}
	}
	
	
	@Override
	public void visit(ConstIdent constIdent) {
		if (currentType == Tab.noType) {
			report_error("Tip konstante " + constIdent.getConstName() + " nije validan!", constIdent);
			return;
		}
		
		if (Tab.currentScope.findSymbol(constIdent.getConstName()) != null) {
			report_error("Simbol " + constIdent.getConstName() + " je vec upotrebljen!", constIdent);
			return;
		}
		
		if (currentType != constIdent.getConstType().obj.getType()) {
			report_error("Tipovi nisu kompatibilni pri dodeli vresnosti konstante!", constIdent);
			return;
		}
		
		Obj constIdentObjNode = Tab.insert(Obj.Con, constIdent.getConstName(), constIdent.getConstType().obj.getType());
		constIdentObjNode.setAdr(constIdent.getConstType().obj.getAdr());
	}
	
	@Override
	public void visit(NumConst numberConst) {
		Obj newObjNode = new Obj(Obj.Con, "", Tab.intType);
		newObjNode.setAdr(numberConst.getNumVal());
		numberConst.obj = newObjNode;
		report_info("Koriscenje konstante " + numberConst.getNumVal(), numberConst);
		
	}
	
	@Override
	public void visit(CharConst charConst) {
		Obj newObjNode = new Obj(Obj.Con, "", Tab.charType);
		newObjNode.setAdr(charConst.getCharVal());
		charConst.obj = newObjNode;
		report_info("Koriscenje konstante " + charConst.getCharVal(), charConst);
	}
	
	@Override
	public void visit(BoolConst boolConst) {
		Obj newObjNode = new Obj(Obj.Con, "", TabSym.boolType);
		if (boolConst.getBoolVal() == true) newObjNode.setAdr(1);
		else newObjNode.setAdr(0);
		boolConst.obj = newObjNode;
		report_info("Koriscenje konstante " + boolConst.getBoolVal(), boolConst);
		
	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Funkcija " + currentMethod.getName() + " nema return iskaz!", null);
		}
		
		if (currentMethod.getName().equals(MAIN) && currentMethod.getLevel() != 0) {
			report_error("Main metoda ne sme imati parametre!", null);
		}
		
		// number of vars
		if (Tab.currentScope.getnVars() > localVarsCnt) {
			report_error("Ne sme se korsititi vise od " + localVarsCnt + " lokalnih promenljivih! (Greska u metodi " + currentMethod.getName() + ")", null);
		}
		
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		returnFound = false;
		currentMethod = null;
	}
	
	@Override
	public void visit(ClassName className) {
		if (Tab.currentScope.findSymbol(className.getClassName()) != null) {
			report_error("Simbol " + className.getClassName() + " je vec upotrebljen!", className);
			currentClass = new Struct(Struct.None);
			className.obj = new Obj(Obj.Type, className.getClassName(), currentClass);
			return;
		}
		
		currentClass = new Struct(Struct.Class);
		currentClassName = className.getClassName();
		className.obj = Tab.insert(Obj.Type, currentClassName, currentClass);
		//report_info("object = " + className.obj.getName(), className);
		Tab.openScope();
		Tab.insert(Obj.Fld, "VPTR", Tab.intType);		
		
	}
	
	@Override
	public void visit(TypeExtends extendsType) {
		Struct parentType = extendsType.getType().struct;
		
		//report_info("kind = " + parentType.getKind(), extendsType);
		if (parentType.getKind() != Struct.Class) {
			report_error("Ne moze se prosirivati klasa koja nije unutrasnja klasa!", extendsType);
			return;
		}
		
		if (currentClassName.equals(extendsType.getType().getTypeName())) {
			report_error("Klasa ne moze prosirivati samu sebe!", extendsType);
			return;
		}
		
		currentClass.setElementType(parentType);
		
		
		Collection<Obj> parentClassMembers = parentType.getMembers();
		Iterator<Obj> iter = parentClassMembers.iterator();
		
		while(iter.hasNext()) {
			Obj member = iter.next();
			if (!member.getName().equals("VPTR") && member.getKind() == Obj.Fld) Tab.currentScope.addToLocals(member);
		}
	}
	
	@Override
	public void visit(ClassDecl classDecl) {
		Struct parentType = currentClass.getElemType();
		//if (parentType == null) report_info("null", null);
		if (parentType != null) {
			Collection<Obj> parentClassMembers = parentType.getMembers();
			Iterator<Obj> iterMeth = parentClassMembers.iterator();
			
			while (iterMeth.hasNext()) {
				
				Obj member = iterMeth.next();
				if (member.getKind() == Obj.Meth && Tab.currentScope().findSymbol(member.getName()) == null && !isConstructor(member.getName())) {
					
					Tab.openScope();
					Obj thisObjNode = Tab.insert(Obj.Var, "this", currentClass);
					thisObjNode.setFpPos(1);
					
					Collection<Obj> localSymbols = member.getLocalSymbols();
					Iterator<Obj> iterLocal = localSymbols.iterator();
					
					while (iterLocal.hasNext()) {
						Obj localMember = iterLocal.next();
						
						if (!localMember.getName().equals("this")) Tab.currentScope.addToLocals(localMember);
					}
					
					
					Obj methodObj = new Obj(Obj.Meth, member.getName(), member.getType(), member.getAdr(), member.getLevel());
					Tab.chainLocalSymbols(methodObj);
					Tab.closeScope();
					Tab.currentScope.addToLocals(methodObj);
				}
			
			}
		}
		
		Tab.chainLocalSymbols(currentClass);
	
		Tab.closeScope();
		//report_info("curr = " + currentClass.getMembersTable(), null);
		//Tab.dump();
		if (currentClass.getNumberOfFields() > classFieldsCnt) report_error("Ne sme se koristiti vise od " + classFieldsCnt + " polja!", null);
		
		constructorNum.put(currentClassName, currentClassConstr);
		
		currentClassName = "";
		currentClass = Tab.noType;
		currentClassConstr = 0;
	}
	
	@Override
	public void visit(MethodTypeName methodTypeName) {
		boolean errFound = false;
		
		if (Tab.currentScope.findSymbol(methodTypeName.getMethodName()) != null) {
			report_error("Simbol " + methodTypeName.getMethodName() + " je vec upotrebljen!", methodTypeName);
			errFound = true;
		}
		
		if (currentType == Tab.noType) {
			report_error("Tip povratne vrednosti funkcije " + methodTypeName.getMethodName() + " nije validan!", methodTypeName);
			errFound = true;
		}
		
		// proveru za klasu 
		
		if (currentClass != Tab.noType) {
			Struct parentType = currentClass.getElemType();
			if (parentType != null) {
				Obj overridenMethod = parentType.getMembersTable().searchKey(methodTypeName.getMethodName());
				//report_info("overriden meth" + overridenMethod, null);
				if (overridenMethod != null && overridenMethod.getKind() != Obj.Meth && overridenMethod.getType() == methodTypeName.getReturnType().struct) {
					report_error("Povratni tip redefinisane metode mora biti isti!", methodTypeName);
					errFound = true;
				}
			}
		}
		else if (currentClass == Tab.noType && methodTypeName.getMethodName().equals(MAIN)) {
			if (!mainFound) {
				if (methodTypeName.getReturnType().struct == Tab.noType) mainFound = true;
				else report_error("Povratna vrednost main funkcije mora biti void!", null);
			}
			else {
				errFound = true;
			}
		}
		
		
		if (!errFound) currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethodName(), methodTypeName.getReturnType().struct);
		else currentMethod = new Obj(Obj.Meth, methodTypeName.getMethodName(), methodTypeName.getReturnType().struct);
		
		methodTypeName.obj = currentMethod;
		Tab.openScope();
		
		// this kod klase 
		if (currentClass != Tab.noType) {
			Obj thisObjNode = Tab.insert(Obj.Var, "this", currentClass);
			thisObjNode.setFpPos(1);
		}
		
	
		report_info("Obradjuje se funkcija " + methodTypeName.getMethodName(), methodTypeName);
	}
	
	@Override
	public void visit(MethodCallBegin methodCallBegin) {
		funcCallActualParamsStack.add(new ArrayList<Struct>());
		methodCallBegin.obj = methodCallBegin.getDesignator().obj;
	};
	
	@Override
	public void visit(ActualParams actualParams) {
		funcCallActualParamsStack.peek().add(actualParams.getExpr().struct);
	}
	
	@Override
	public void visit(ActualParam actualParam) {
		funcCallActualParamsStack.peek().add(actualParam.getExpr().struct);

	}
	
	@Override
	public void visit(MethodCall methodCall) {
		Obj designator = methodCall.getMethodCallBegin().getDesignator().obj;
		
		if (designator.getKind() != Obj.Meth) {
			report_error("Simbol ne predstavlja ime funkcije ili metode klase!", methodCall);
			methodCall.struct = Tab.noType;
			funcCallActualParamsStack.pop();
			return;
		}
		
		ArrayList<Struct> actParams = funcCallActualParamsStack.pop();
		methodCall.struct = designator.getType();
		
		Collection<Obj> formalParams;
		if (!designator.equals(currentMethod)) formalParams = designator.getLocalSymbols();
		else formalParams = Tab.currentScope.values();
		
		Iterator<Obj> formalIter = formalParams.iterator();
		Iterator<Obj> thisIter = formalParams.iterator();
		Iterator<Struct> actIter = actParams.iterator();
		int formalParamsCount = designator.getLevel();
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
			if (!isCompatibleWithAssign(formalParamStruct, actParamStruct)) {
				report_error("Stvarni parametri poziva funkcije ne odgovaraju parametrima formalnih argumenata funkcije!", methodCall);
				return;
			}
			formalParamsCount--;
			
		}
		
		
		if (actIter.hasNext() || formalParamsCount != 0) {
			report_error("Broj stvarnih i formalnih parametara nije isti!", methodCall);		
		}
		
		
	}
	
	@Override
	public void visit(ConstructorBegin constructorBegin) {
		if (constructorBegin.getName().equals(currentClassName)) {
			String constructorName = joker + currentClassName + ++currentClassConstr;
			//report_info("begin" + constructorName, null);
			constructorBegin.obj = currentMethod = Tab.insert(Obj.Meth, constructorName, Tab.noType);
			Tab.openScope();
			Obj thisObjNode = Tab.insert(Obj.Var, "this", currentClass);
			thisObjNode.setFpPos(1);
		}
		else {
			//report_info(currentClassName + constructorBegin.getName(), null);
			report_error("Konstruktor se mora zvati kao i klasa!", constructorBegin);
		}
	}
	
	@Override
	public void visit(ConstructorDecl constructorDecl) {
		Tab.chainLocalSymbols(currentMethod);
		//report_info("konstruktor " + currentMethod.getName(), null);
		Tab.closeScope();
		report_info("konstruktor " + currentMethod.getName() + currentMethod.getLevel(), null);

		returnFound = false;
		currentMethod = null;
	}
	
	
	@Override
	public void visit(ClassFieldIdentifierOne classFieldIdentifierOne) {
		if (currentType == Tab.noType) {
			report_error("Tip polja klase " + classFieldIdentifierOne.getFieldName() + " nije validan!", classFieldIdentifierOne);
			return;
		}
		
		report_info("field " + classFieldIdentifierOne.getFieldName() + " " + currentType.getKind(), null);
		if (Tab.currentScope.findSymbol(classFieldIdentifierOne.getFieldName()) == null) {
			Tab.insert(Obj.Fld, classFieldIdentifierOne.getFieldName(), currentType);
		}
		else {		// variable exists
			report_error("Simbol " + classFieldIdentifierOne.getFieldName() + " je vec upotrebljen!", classFieldIdentifierOne);
		}
	}
	
	@Override
	public void visit(ClassFieldIdentifierArray classFieldIdentifierArray) {
		if (currentType == Tab.noType) {
			report_error("Tip polja klase " + classFieldIdentifierArray.getFieldName() + " nije validan!", classFieldIdentifierArray);
			return;
		}
		
		if (Tab.currentScope.findSymbol(classFieldIdentifierArray.getFieldName()) == null) {
			Struct arrayStruct = new Struct(Struct.Array, currentType);
			Tab.insert(Obj.Fld, classFieldIdentifierArray.getFieldName(), arrayStruct);
		}
		else {		// variable exists
			report_error("Simbol " + classFieldIdentifierArray.getFieldName() + " je vec upotrebljen!", classFieldIdentifierArray);
		}
	}
	
	
	@Override
	public void visit(RetType retType) {
		retType.struct = retType.getType().struct;
	}
	
	@Override
	public void visit(NoReturn noRetType) {
		currentType = TabSym.voidType;
		noRetType.struct = Tab.noType;
	}
	
	
	@Override
	public void visit(FormParams formParams) {
		currentMethod.setLevel(Tab.currentScope.getnVars());
		// voditi racuna za override
		
		if (currentClass != Tab.noType) {
			Struct parentType = currentClass.getElemType();
			
			if (parentType != null) {
				
				Obj baseMethod = parentType.getMembersTable().searchKey(currentMethod.getName());
				
				if (baseMethod != null) {
					Collection<Obj> baseArgs = baseMethod.getLocalSymbols();
					Collection<Obj> overridenArgs = Tab.currentScope().getLocals().symbols();
					Iterator<Obj> baseIter = baseArgs.iterator();
					Iterator<Obj> overridenIter = overridenArgs.iterator();
					
					// preripi this
					if (baseIter.hasNext() && overridenIter.hasNext()) {
						baseIter.next();
						overridenIter.next();
					}
					else {
						report_error("Broj parametara kod redefinisanog metoda nije isti!", formParams);						
						return;
					}
					
					boolean hasError = false;
					while (baseIter.hasNext() && overridenIter.hasNext()) {
						Obj baseObj = baseIter.next();
						Obj overridenObj = overridenIter.next();
						
						if (baseObj.getType().getKind() == Struct.Array && overridenObj.getType().getKind() != Struct.Array) {
							hasError = true;
							break;
						}
						else if (baseObj.getType().getKind() != Struct.Array && overridenObj.getType().getKind() == Struct.Array) {
							hasError = true;
							break;
						}
						else if (baseObj.getType().getKind() == Struct.Array && overridenObj.getType().getKind() != Struct.Array) {
							if (!isCompatibleWithAssign(baseObj.getType().getElemType(), overridenIter.next().getType().getElemType())) {
								hasError = true;
								break;
							}
						}
						else { 
							if (!isCompatibleWithAssign(baseObj.getType(), overridenObj.getType())) {
								hasError = true;
								break;
							}
						}
					}
					
					if (baseIter.hasNext() || overridenIter.hasNext()) {
						hasError = true;
						report_error("Broj parametara kod redefinisanog metoda nije isti!", formParams);		
					}
					
					if (hasError) {
						report_error("Parametri nisu kompatibilni pri dodeli!", formParams);				
					}
				}
			}
				
		}
	}
	
	@Override
	public void visit(NoFormPars noFormParams) {
		currentMethod.setLevel(Tab.currentScope.getnVars());
		// voditi racuna za override
	}
	
	
	@Override
	public void visit(FormalParamDeclaration formalParam) {
		if (Tab.currentScope.findSymbol(formalParam.getFormalParamName()) != null) {
			report_error("Simbol " + formalParam.getFormalParamName() + " je vec upotrebljen!", formalParam);
			return;
		}
		
		if (currentType == Tab.noType) {
			report_error("Tip formalnog parametra funkcije " + formalParam.getFormalParamName() + " nije validan!", formalParam);
			return;
		}
		
		Obj obj = Tab.insert(Obj.Var, formalParam.getFormalParamName(), currentType);
		obj.setFpPos(Tab.currentScope.getnVars() + 1);
	}
	
	@Override
	public void visit(FormalParamDeclarationArray formalParam) {
		if (Tab.currentScope.findSymbol(formalParam.getFormalParamName()) != null) {
			report_error("Simbol " + formalParam.getFormalParamName() + " je vec upotrebljen!", formalParam);
			return;
		}
		
		if (currentType == Tab.noType) {
			report_error("Tip formalnog parametra funkcije " + formalParam.getFormalParamName() + " nije validan!", formalParam);
			return;
		}
		
		Struct arrayStruct = new Struct(Struct.Array, currentType);
		Obj obj = Tab.insert(Obj.Var, formalParam.getFormalParamName(), arrayStruct);
		obj.setFpPos(Tab.currentScope.getnVars() + 1);
	}
	
	int whileLen = 0;
	int foreachLen = 0;
	
	@Override
	public void visit(IfCond ifCondition) {
			if (ifCondition.getCondition().struct != TabSym.boolType)
				report_error("Tip uslova u if logickoj konstrukcija mora biti boolean tipa!", ifCondition);
	}
	
	@Override
	public void visit(WhileBegin whileBegin) {
		whileLen++;
	}
	
	@Override
	public void visit(ForeachBegin foreachBegin) {
		Obj designatorObj = foreachBegin.getDesignator().obj;
		Obj identifier = Tab.find(foreachBegin.getForeachIdent());
		
		if (designatorObj.getType().getKind() != Struct.Array) {
			report_error("Tip " + designatorObj.getName() + " mora biti niz!", foreachBegin);
			return;
		}
		
		if (identifier.getKind() != Obj.Var) {
			report_error("Simbol " + identifier.getName() + " mora biti lokalna ili globalna promenljiva!", foreachBegin);
			return;
		}
		
		if (identifier.getType() != designatorObj.getType().getElemType()) {
			report_error("Simbol " + identifier.getName() + " mora biti istog tipa kao i elementi niza" + designatorObj.getName() + "!", foreachBegin);
			return;
		}
		
		foreachLen++;
		
	}
	
	@Override
	public void visit(WhileCondition whileCondition) {
		if (whileCondition.getCondition().struct != TabSym.boolType)
			report_error("Tip uslova u while logickoj konstrukcija mora biti boolean tipa!", whileCondition);
	}
	
	@Override
	public void visit(BreakStatement breakStatement) {
		if (whileLen == 0 && foreachLen == 0) 
			report_error("Break iskaz se moze koristiti samo unutar while ili foreach petlje!", breakStatement);
	}
	
	@Override
	public void visit(ContinueStatement continueStatement) {
		if (whileLen == 0 && foreachLen == 0) 
			report_error("Continue iskaz se moze koristiti samo unutar while ili foreach petlje!", continueStatement);
	}
	
	@Override
	public void visit(ReturnExpr returnExpr) {
		returnFound = true;
		
		if (!currentMethod.getType().compatibleWith(returnExpr.getExprOptional().struct)) {
			report_error("Tip izraza u return naredbi mora niti istog tipa kao povratna vrednost funkcije " + currentMethod.getName() + "!", returnExpr);
			return;
		}
		
		if (currentMethod == Tab.noObj) {
			report_error("Naredba return se ne sme zvati izvan tela funkcija!", returnExpr);

		}
	}
	
	
	@Override
	public void visit(ReadStatement readStmt) {
		Obj obj = readStmt.getDesignator().obj;
		
		if (obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem && obj.getKind() != Obj.Fld) {
			report_error("Parametar " + obj.getName() + " read funkcije mora biti promenljiva, element niza ili polje unutar objekta!", readStmt);
			return;
		}
		
		if (obj.getType() != Tab.charType && obj.getType() != Tab.intType && obj.getType() != TabSym.boolType) {
			report_error("Parametar " + obj.getName() + " read funkcije mora biti int, char ili bool tipa!", readStmt);
		}
	}
	
	@Override
	public void visit(PrintExprNum printExpr) {
		if (printExpr.getExpr().struct != Tab.charType && printExpr.getExpr().struct != Tab.intType && printExpr.getExpr().struct != TabSym.boolType) {
			report_error("Parametar print funkcije mora biti int, char ili bool tipa!", printExpr);
		}
	}
	
	@Override
	public void visit(PrintExpr printExpr) {
		if (printExpr.getExpr().struct != Tab.charType && printExpr.getExpr().struct != Tab.intType && printExpr.getExpr().struct != TabSym.boolType) {
			report_error("Parametar print funkcije mora biti int, char ili bool tipa!", printExpr);
		}
	}
	
	@Override
	public void visit(ExprOptionalSingle exprOptional) {
		exprOptional.struct = exprOptional.getExpr().struct;
	}
	
	@Override
	public void visit(NoExpr noExpr) {
		noExpr.struct = Tab.noType;
	}
	
	
	@Override
	public void visit(DesignatorAssignStatement designatorAssignStmt) {
		Obj obj = designatorAssignStmt.getDesignator().obj;
		
		if (obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem && obj.getKind() != Obj.Fld) {
			report_error("Simbol " + obj.getName() + " sa leve strane znaka dodele mora biti promenljiva, element niza ili polje unutar objekta! ", designatorAssignStmt);
			return;
		}
		Struct src = designatorAssignStmt.getDesignatorAssign().struct;
		Struct dst = designatorAssignStmt.getDesignator().obj.getType();

		//report_info("" + src + dst, null);
		if (!isCompatibleWithAssign(src, dst)) {
			report_error("Leva i desna strana moraju biti kompatibilne pri dodeli! ", designatorAssignStmt);
		}
	}
	
	@Override
	public void visit(DesignatorAssignment designatorAssign) {
		designatorAssign.struct = designatorAssign.getExpr().struct;
	}
	
	@Override
	public void visit(ErrDesignatorAssignment err) {
		err.struct = Tab.noType;
	}
	
	@Override
	public void visit(DesignatorPostIncrement designatorInc) {
		Obj obj = designatorInc.getDesignator().obj;
		
		if (obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem && obj.getKind() != Obj.Fld) {
			report_error("Simbol " + obj.getName() + " kod operacije inkrementiranja mora biti promenljiva, element niza ili polje unutar objekta!", designatorInc);
			return;
		}
		
		if (obj.getType() != Tab.intType) {
			report_error("Simbol " + obj.getName() + " kod operacije inkrementiranja mora biti tipa int!", designatorInc);
		}
	}
	
	@Override
	public void visit(DesignatorPostDecrement designatorDec) {
		Obj obj = designatorDec.getDesignator().obj;
		
		if (obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem && obj.getKind() != Obj.Fld) {
			report_error("Simbol " + obj.getName() + " kod operacije dekrementiranja mora biti promenljiva, element niza ili polje unutar objekta!", designatorDec);
			return;
		}
		
		if (obj.getType() != Tab.intType) {
			report_error("Simbol " + obj.getName() + " kod operacije dekrementiranja mora biti tipa int!", designatorDec);
		}
	}
	
	@Override
	public void visit(DesignatorIdent designatorIdent) {
		Obj designatorObj = Tab.noObj;
		
		if (currentClass != Tab.noType) {
			Struct parentType = currentClass.getElemType();
			if (parentType != null) {
				designatorObj = parentType.getMembersTable().searchKey(designatorIdent.getDesignatorName());
			}
		}
		
		if (designatorObj == null || designatorObj == Tab.noObj) {
			designatorObj = Tab.find(designatorIdent.getDesignatorName());
			if (designatorObj == Tab.noObj) {
				report_error("Simbol " + designatorIdent.getDesignatorName() + " nije deklarisan!", designatorIdent);
			}
		}
	
		designatorIdent.obj = designatorObj;
	}
	
	@Override
	public void visit(DesignatorArray designatorArray) {
		if (designatorArray.getDesignatorArrayLBracket().obj.getType().getKind() != Struct.Array) {
			report_error("Tip " + designatorArray.getDesignatorArrayLBracket().obj.getName() + " mora biti niz!", designatorArray);
			return;
		}
		
		if (designatorArray.getExpr().struct != Tab.intType) {
			report_error("Indeks niza mora biti int tipa!", designatorArray);
			return;
		}
		designatorArray.obj = new Obj(Obj.Elem, designatorArray.getDesignatorArrayLBracket().obj.getName(), 
				designatorArray.getDesignatorArrayLBracket().obj.getType().getElemType());
		report_info("Detekcija pristupa elementu niza", designatorArray);
	}
	
	@Override
	public void visit(DesignatorArrayLBracket designatorArrayLBracket) {
		designatorArrayLBracket.obj = designatorArrayLBracket.getDesignator().obj;
	} 
	
	@Override
	public void visit(DesignatorClass designatorClass) {
		Obj designatorObj = designatorClass.getDesignator().obj;
				
		if (designatorObj.getType().getKind() != Struct.Class) {
			report_error(designatorObj.getName() + " mora biti tipa unutrasnje klase!", designatorClass);
			return;
		}
		// pristup sa this?
		
		Obj obj;
		if (currentClass != Tab.noType && designatorObj.getType() == currentClass) {
			obj = Tab.currentScope.getOuter().getLocals().searchKey(designatorClass.getClassMembName());
		}
		else obj = designatorObj.getType().getMembersTable().searchKey(designatorClass.getClassMembName());
		
		if (obj == null) {
			report_error("Mora se pristupati samo poljima ili metodama unutrasnje klase!", designatorClass);
			return;
		}
		
		if (obj.getKind() == Obj.Meth) {
			report_info("Pristupa se metodi klase ", designatorClass);
		}
		else if (obj.getKind() == Obj.Fld) {
			report_info("Pristupa se polju klase ", designatorClass);
		}
		else {
			report_error("Moze se pristupati samo poljima ili metodama unutrasnje klase!", designatorClass);
			return;
		}
		
		designatorClass.obj = obj;
	}
	
	@Override
	public void visit(DesignatorMultiSingle designator) {
		int kind = designator.getDesignator().obj.getKind();
		if (kind != Obj.Var && kind != Obj.Fld && kind != Obj.Elem) {
			report_error("Simbol sa leve strane jednakosti mora oznacavati promenljivu, element niza ili polje unutar objekta!", designator);
			return;
		}
		//report_info("design mutli " + designator.getDesignator().obj.getType().getKind(), null);
		multiAssignList.add(designator.getDesignator().obj.getType());
	}
	
	
	
	@Override
	public void visit(DesignatorMultiBegin DesignatorMultiBegin) {
		multiAssignList = new ArrayList<>();
	}
	
	@Override
	public void visit(DesignatorMultiAssign designatorMultiAssign) {
		Struct designatorType = designatorMultiAssign.getDesignator().obj.getType();

		for (Struct elem : multiAssignList) {
			if (!isCompatibleWithAssign(designatorType.getElemType(), elem)) 
				report_error("Designator sa desne strane znaka za dodelu vrednosti mora biti kompatibilan pri dodeli sa tipom svih "
					+ "designatora sa leve strane znaka za dodelu vrednosti!", designatorMultiAssign);
		}
	}
	
	@Override
	public void visit(ConditionsOr conds) {
		conds.struct = conds.getCondTerm().struct;
	}
	
	@Override
	public void visit(CondTermsAnd cond) {
		cond.struct = cond.getCondFact().struct;
	}
	
	@Override
	public void visit(CondTerminal cond) {
		cond.struct = cond.getCondTerm().struct;
	}
	
	@Override
	public void visit(CondF cond) {
		cond.struct = cond.getCondFact().struct;
	}
	
	@Override
	public void visit(AddopTerms addExpr) {
		Struct termExpr = addExpr.getExpr().struct;
		Struct term = addExpr.getTerm().struct;
		if (term.equals(termExpr) && term == Tab.intType) {
			addExpr.struct = Tab.intType;
		}
		else {
			report_error("Nekompatibilni tipovi u izrazu za sabiranje, moraju biti tipa int!", addExpr);
			addExpr.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(ExprTerm exprTerm) {
		exprTerm.struct = exprTerm.getSingleTerm().struct;
	}
	
	@Override
	public void visit(CondFactRelop factRelop) {
		Struct expr = factRelop.getExpr().struct;
		Struct expr1 = factRelop.getExpr1().struct;
		
		if (!expr.compatibleWith(expr1)) {
			report_error("Nekompatibilni tipovi u realcionom izrazu!", factRelop);
			return;
		}
		
		if ((expr.getKind() == Struct.Class || expr.getKind() == Struct.Array) && !(factRelop.getRelop() instanceof RelopEqual || factRelop.getRelop() instanceof RelopNotEqual)) {
			report_error("Kod promenljivih tipa klase ili niza realcioni operatori moraju biti == ili !=", factRelop);
		}
		
		factRelop.struct = TabSym.boolType;
	}
	
	@Override
	public void visit(CondFactNoRelop noFactRelop) {
		if (noFactRelop.getExpr().struct != TabSym.boolType) {
			report_error("Realcioni izraz mora biti bool tipa!", noFactRelop);
		}
		noFactRelop.struct = TabSym.boolType;
	}
	
	@Override
	public void visit(TermNegative termNeg) {
		if (termNeg.getTerm().struct == Tab.intType) {
			termNeg.struct = Tab.intType;
		}
		else {
			report_error("Operand mora biti tipa int!", termNeg);
			termNeg.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(TermSingle term) {
		term.struct = term.getTerm().struct;
	}
	
	@Override
	public void visit(TermMulopFactors mulopFactors) {
		Struct factor = mulopFactors.getFactor().struct;
		Struct term = mulopFactors.getTerm().struct;
		
		if (term.equals(factor) && term == Tab.intType) {
			mulopFactors.struct = Tab.intType;
		}
		else {
			report_error("Nekompatibilni tipovi u izrazu, moraju biti tipa int!", mulopFactors);
			mulopFactors.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(FactorSingle factor) {
		factor.struct = factor.getFactor().struct;
	}
	
	@Override
	public void visit(FactorDesignator designator) {
		Obj designatorObj = designator.getDesignator().obj;
		
		if(designatorObj.getKind() != Obj.Var && designatorObj.getKind() != Obj.Con 
				&& designatorObj.getKind() != Obj.Fld && designatorObj.getKind() != Obj.Elem) {
			report_error("Identifikator mora biti promenljiva, konstanta, polje ili element niza", designator);
			designator.struct = Tab.noType;
		}
		else {
			designator.struct = designatorObj.getType();
		}
	}
	
	@Override
	public void visit(FactorConst cons) {
		cons.struct = cons.getConstType().obj.getType();
	}
	
	@Override
	public void visit(FactorNewArray newArr) {
		if (newArr.getExpr().struct == Tab.intType) {
			Struct arrStruct = new Struct(Struct.Array, newArr.getType().struct);
			newArr.struct = arrStruct;
		}
		else {
			report_error("Izraz za velicinu niza mora biti tipa int!", newArr);
		}
	}
	
	@Override
	public void visit(FactorNewConstructor factorNewConstructor) {
		if (factorNewConstructor.getType().struct.getKind() != Struct.Class) {
			report_error("Tip kod naredbe sa new mora da oznacava tip unutrasnje klase!", factorNewConstructor);
			return;
		}
		
		ArrayList<Struct> actParams = funcCallActualParamsStack.pop();
		
		//report_info("act pars size = " + actParams.size(), null);
		if (actParams.size() == 0) {
			//factorNewConstructor.getConstructorCallBegin().obj = ; podrazumevani
			factorNewConstructor.struct =  factorNewConstructor.getType().struct;
			return; // poziv podrazumvanog constr
		}
		
		int constructorCnt = constructorNum.get(factorNewConstructor.getType().getTypeName());
		boolean found = false;
		
		String className = factorNewConstructor.getType().getTypeName();
		Obj classObj = Tab.find(className);
		//report_info(classObj.getName(), null);
		for (int i = 1; i <= constructorCnt; i++) {
			String constructorName = joker + className + i;
			Obj constructor = classObj.getType().getMembersTable().searchKey(constructorName);
			Collection<Obj> tabela = classObj.getType().getMembersTable().symbols();
			for (Obj obj : tabela) {
				System.out.println(obj.getName());
			}
			report_info(constructor.getName(), null);
			Collection<Obj> formalParams = constructor.getLocalSymbols();
			
			Iterator<Obj> formalIter = formalParams.iterator();
			Iterator<Obj> thisIter = formalParams.iterator();
			Iterator<Struct> actIter = actParams.iterator();
			int formalParamsCount = constructor.getLevel();
			
			Obj formalObj = thisIter.next();
			//report_info("name = " + formalObj.getName(), null);
			if (formalObj.getName().equals("this") && formalObj.getType().getKind() == Struct.Class) {
				//report_info("this", null);
				formalIter.next();
				formalParamsCount--;
			}
			
			while (formalParamsCount > 0 && actIter.hasNext()) {
				formalObj = formalIter.next();
				Struct formalParamStruct = formalObj.getType();
				Struct actParamStruct = actIter.next();
				if (!isCompatibleWithAssign(formalParamStruct, actParamStruct)) {
					report_error("Stvarni parametri poziva funkcije ne odgovaraju parametrima formalnih argumenata funkcije!", factorNewConstructor);
					return;
				}
				formalParamsCount--;
				
			}
			
			
			if (!actIter.hasNext() && formalParamsCount == 0) {
				found = true;
				factorNewConstructor.getConstructorCallBegin().obj = formalObj;
				factorNewConstructor.struct =  factorNewConstructor.getType().struct;
				break;
			}
		}
		
		if (!found) report_error("Broj stvarnih i formalnih parametara nije isti!", factorNewConstructor);
		
	}
	
	@Override
	public void visit(ConstructorCallBegin constructorCallBegin) {
		funcCallActualParamsStack.add(new ArrayList<Struct>());
	}
	
	@Override
	public void visit(FactorExprGroup exprGroup) {
		exprGroup.struct = exprGroup.getExpr().struct;
	}
	
	@Override
	public void visit(FactorMethodCall factorMethodCall) {
		factorMethodCall.struct = factorMethodCall.getMethodCall().struct;
	}
	

}
