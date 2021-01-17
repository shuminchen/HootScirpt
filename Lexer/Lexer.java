package edu.brandeis.cs12b.pa11.lexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Lexer implements Iterator<Lexeme> {
	private List<String> tokens = new ArrayList<String>();
//	private List<Lexeme> lexemeList = new ArrayList<Lexeme>(); 
	private int current=-1;
	
	public Lexer(String toLex) {
		split(toLex);	
	}
	//myVal=(2+3)*3
	public void split(String toLex) {
		for(int i=0;i<toLex.length();i++) {
			if(!Character.isWhitespace(toLex.charAt(i))){
				String token = " ";
				if(Character.isDigit(toLex.charAt(i))) {
					int start=i;
					int end=i+1;
					int dotNum =0;
					while(end<toLex.length() && (Character.isDigit(toLex.charAt(end)) ||(toLex.charAt(end) == '.'&& dotNum<1))){
						if(toLex.charAt(end) == '.') {
							dotNum++;
						}
						end++;
					}
					token = toLex.substring(start, end);
					i=end-1;
				}else if(Character.isLetter(toLex.charAt(i))){
					int start =i;
					int end = i+1;
					while(end<toLex.length() && Character.isLetter(toLex.charAt(end))) {
						end++;
					}
					token = toLex.substring(start,end);
					i=end-1;
				}else {
					token = Character.toString(toLex.charAt(i));
				}
				this.tokens.add(token);
				System.out.println(token);
			}
			
		}
	}
	
	
	
	
	@Override
	public boolean hasNext() {
		this.current++;
		if(this.current<this.tokens.size()) {
			return true;
		}
		return false;
	}

	@Override
	public Lexeme next() {
		Lexeme lexeme = null;
		if(current<this.tokens.size()) {
			String lexemeString = this.tokens.get(current);	
			System.out.println("lexString: "+lexemeString);
			if(lexemeString.equals("=")) {
				lexeme = new Lexeme(LexemeType.EQUALS);	
			}
			else if (lexemeString.equals(";")) {
				lexeme = new Lexeme(LexemeType.SEMICOLON);
			}
			else if(lexemeString.equals("?")) {
				lexeme = new Lexeme(LexemeType.USER_INPUT);
			}
			else if(lexemeString.equals("+")||lexemeString.equals("-")||lexemeString.equals("*")||lexemeString.equals("/")||lexemeString.equals("^")) {
				lexeme = new Lexeme(LexemeType.OPERATOR,lexemeString);
			}
			else if(lexemeString.equals("(")) {
				lexeme = new Lexeme(LexemeType.LEFT_PAREN);
			}
			else if (lexemeString.equals(")")) {
				lexeme = new Lexeme(LexemeType.RIGHT_PAREN);
			}
			else if(Character.isDigit(lexemeString.charAt(0))) {
				lexeme = new Lexeme(LexemeType.NUMBER, lexemeString);
			}else if(Character.isLetter(lexemeString.charAt(0))) {
				lexeme = new Lexeme(LexemeType.VARIABLE,lexemeString);
			}
		}	
		return lexeme;
	}
	
	


	private boolean isVariable(String lexemeString) {
		// TODO Auto-generated method stub
		if (lexemeString == null || lexemeString.isEmpty()) {
	        return false;
		}
		for (int i=0;i<lexemeString.length();i++) {
			if(!Character.isLetter(lexemeString.charAt(i))){
				return false;
			}
		}
		
		return true;
	}


	public boolean isNumber(String lexemeString) {
		// TODO Auto-generated method stub
		 if (lexemeString == null || lexemeString.isEmpty()) {
		        return false;
		 }
		 int i = 0;
		 if (lexemeString.charAt(0) == '-') {
			 if (lexemeString.length() > 1) {
				 i++;
		     } else {
		         return false;
		     }
		 }
		 for (; i < lexemeString.length(); i++) {
		      if (!Character.isDigit(lexemeString.charAt(i))||lexemeString.charAt(i)!='.') {
		         return false;
		      }
		 }
		
		return true;
	}
	
	
}
