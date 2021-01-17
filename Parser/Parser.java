package edu.brandeis.cs12b.pa11.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import edu.brandeis.cs12b.pa11.lexer.Lexeme;
import edu.brandeis.cs12b.pa11.lexer.LexemeType;

public class Parser {
	private List<Lexeme> lexemesList = new ArrayList<Lexeme>();
//	private Iterator<Lexeme> lexemes;
	private List<ParseTreeNode> treeNode = new ArrayList<ParseTreeNode>();
	
	public Parser(Iterator<Lexeme> lexemes) {
	//	this.lexemes = lexemes;
		while(lexemes.hasNext()) {
			this.lexemesList.add(lexemes.next());
	//	this.treeNode.add(new ParseTreeNode(lexemes.next()));
		}	
	}

	public List<ParseTreeNode> parse() {
		List<ParseTreeNode> roots = new ArrayList<ParseTreeNode>();
	//	while(this.lexemes.hasNext()) {	
		//	Lexeme variable = lexemes.next();
		//		lexemes.next();
		for(int i=0;i<lexemesList.size();i++) {
			Lexeme variable = lexemesList.get(i);
			i++;
			List<Lexeme> expression = new ArrayList<Lexeme>();
	
			do{
				i++;
				expression.add(lexemesList.get(i));
			}while(!expression.get((expression.size()-1)).getType().equals(LexemeType.SEMICOLON));
		//		expression.add(lexemes.next());
			
			
			
			expression.remove(expression.size()-1);
			ParseTreeNode toAdd = new ParseTreeNode(new Lexeme(LexemeType.EQUALS));
			toAdd.setLeft(new ParseTreeNode(variable));
			toAdd.setRight(eval_expression(expression));
			roots.add(toAdd);			
			}	
		return roots;

	}
	
	public ParseTreeNode eval_expression (List<Lexeme> expression){
		if(expression.size()==0) {
			return null;
		}
		if(expression.size()==1) {
			ParseTreeNode node = new ParseTreeNode(expression.get(0));
			return node;
		}
		ParseTreeNode left = null;
		ParseTreeNode right = null;
		ParseTreeNode operator = null;
		System.out.println("expression size: "+ expression.size());
		if(expression.size()>1) {
			if (expression.get(0).getType().equals(LexemeType.LEFT_PAREN)) {
				int endIndex = getMatchinParen(expression);
				List<Lexeme> leftExpression = expression.subList(1, endIndex);
			//	leftExpression.remove();
				System.out.println(leftExpression.toString());
				left = eval_expression(leftExpression);//??? not sure
				if(endIndex == expression.size()-1) {// not sure: if there are no symbols after the end paran
					return left;
				}
			
				operator = new ParseTreeNode( expression.get(endIndex+1));
				List<Lexeme> rightExpression = expression.subList(endIndex+2, expression.size());
				if(rightExpression.get(rightExpression.size()-1).getType().equals(LexemeType.RIGHT_PAREN)) {
					rightExpression.remove(rightExpression.size()-1);
				}
				right = eval_expression( rightExpression);
				
			}else {
				left = new ParseTreeNode(expression.get(0));
				operator = new ParseTreeNode(expression.get(1));
				if(expression.size()==3) {
					right = new ParseTreeNode(expression.get(2));
				}else {
					right = eval_expression(expression.subList(2, expression.size()));
				}
				
			}
		}
		
		
		operator.setLeft(left);
		operator.setRight(right);
		
		return operator;	//fix this
	}

	private int getMatchinParen(List<Lexeme> expression) {
		// TODO Auto-generated method stub
		Stack<Character> stack = new Stack<Character>();
		int i=0;
		stack.push('(');	
		do {
			i++;
			if(expression.get(i).getType().equals(LexemeType.LEFT_PAREN)) {
				stack.push('(');
			}
			if(expression.get(i).getType().equals(LexemeType.RIGHT_PAREN)) {
				stack.pop();
			}
		}while(i<expression.size()&&(!stack.isEmpty()));
		
		return i;
	}

	
}
