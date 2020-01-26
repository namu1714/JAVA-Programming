package Calculator;
import java.util.*;

enum Precedence { lparen, rparen, plus, minus, times, divide, eos, operand }

public class Eval {
	private ArrayList<String> infix;
	private ArrayList<String> postfix;
	
	Eval(String exp){
		infix = new ArrayList<>();
		postfix = new ArrayList<>();
		
		int startP = 0;
		int endP = 0;
		
		//수식에서 각 값과 연산자를 떼어 String 형태로 변환 후 저장 
		int i = 0;
		while(i < exp.length()) {	
			for(i=startP;i<exp.length();i++) {
				char c = exp.charAt(i);
				if(c=='-' || c=='+' || c=='*' || c=='/' || c=='(' || c==')') {
					endP = i;
					if(startP!=endP) 
						infix.add(exp.substring(startP, endP));
					infix.add(exp.substring(endP, endP+1));
					startP = endP+1;
					break;
				}
			}//for
		}//while
		if(startP != i) {
			infix.add(exp.substring(startP, exp.length()));
		}
	}
	
	public static Precedence getToken(String symbol) {
		switch(symbol) {
		case "(" : return Precedence.lparen;
		case ")" : return Precedence.rparen;
		case "+" : return Precedence.plus;
		case "-" : return Precedence.minus;
		case "/" : return Precedence.divide;
		case "*" : return Precedence.times;
		default  : return Precedence.operand;
		}
	}
	
	public static String tokenToStr (Precedence token) {
		switch (token.ordinal()) {
		case 0: return "(";
		case 1: return ")";
		case 2: return "+";
		case 3: return "-";
		case 4: return "*";
		case 5: return "/";
		}
		return "error";
	}
	
	public void infixToPostfix() {
		Stack<Precedence> stack = new Stack<>();
		int isp[] = {0, 19, 12, 12, 13, 13, 0}; //lparen, rparen, plus, minus, times, divide, eos
		int icp [] = {20, 19, 12, 12, 13, 13, 0};
		Precedence token;
		
		stack.add(Precedence.eos);
		
		for(int n=0;n<infix.size();n++) {
			token=getToken(infix.get(n));
			if(token==Precedence.operand)
				postfix.add(infix.get(n));
			else if (token==Precedence.rparen) {
				while(stack.lastElement()!=Precedence.lparen)
					postfix.add(tokenToStr(stack.pop()));
				stack.pop();	
			}
			else {
				while(isp[stack.lastElement().ordinal()]>=icp[token.ordinal()])
					postfix.add(tokenToStr(stack.pop()));
				stack.push(token);
			}
		}//for
		
		while((token=stack.pop())!=Precedence.eos)
			postfix.add(tokenToStr(token));
	}
	
	public double calPostfix() {
		Precedence token;
		Stack<Double> stack = new Stack<>();
		double op1, op2;
		
		for(int n = 0;n<postfix.size();n++) {
			token=getToken(postfix.get(n));
			if(token==Precedence.operand)
				stack.push(Double.parseDouble(postfix.get(n)));
			else {
				op2 = stack.pop();
				op1 = stack.pop();
				switch(token) {
				case plus   : 
					stack.push(op1+op2);
					break;
				case minus  : 
					stack.push(op1-op2);
					break;
				case times  : 
					stack.push(op1*op2);
					break;
				case divide : 
					stack.push(op1/op2);
					break;
				}
			}
		}
		return stack.pop();
	}
}
