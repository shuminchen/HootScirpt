package edu.brandeis.cs12b.pa11.parser;

import edu.brandeis.cs12b.pa11.lexer.Lexeme;
import edu.brandeis.cs12b.pa11.lexer.LexemeType;
import edu.brandeis.cs12b.pa11.lexer.Lexer;

public class ParseTreeNode {
	private Lexeme value;
	private ParseTreeNode left;
	private ParseTreeNode right;




	public ParseTreeNode(Lexeme value) {
		this.value = value;
	}

	public ParseTreeNode getLeft() {
		return left;
	}

	public void setLeft(ParseTreeNode left) {
		this.left = left;
	}
	public ParseTreeNode getRight() {
		return right;
	}
	public void setRight(ParseTreeNode right) {
		this.right = right;
	}

	public Lexeme getLexeme() {
		return value;
	}

	@Override
	public String toString() {
		return "digraph G {\n" + toString("") + "\n}";
	}
	
	public String toString(String prefix, boolean withPrefix) {
		return "digraph G {\n" + toString(prefix) + "\n}";
	}

	private String toString(String path) {
		StringBuilder toR = new StringBuilder();
		String myLabel = getLexeme().getType().toString() + path;

		String label = getLexeme().getValueAsString();
		if (label == null && getLexeme().getType() == LexemeType.EQUALS)
			label = "=";
		
		toR.append(myLabel + " [ label=\"" + label + "\" ];\n");


		if (getLeft() != null) {
			String newPath = path + "L";
			toR.append(myLabel 
					+ " -> " 
					+ getLeft().getLexeme().getType().toString() + newPath 
					+ ";\n");
			toR.append(getLeft().toString(newPath));
		}

		if (getRight() != null) {
			String newPath = path + "R";
			toR.append(myLabel 
					+ " -> " 
					+ getRight().getLexeme().getType().toString() + newPath 
					+ ";\n");
			toR.append(getRight().toString(newPath));
		}

		return toR.toString();
	}


	public static void main(String[] args) {
		String s = "myVar = 6 + 5;\n" + 
				"x = myVar * 2;\n" + 
				"y = (x * 2) / 5.5;\n";
		Parser p = new Parser(new Lexer(s));
		
		
		int i = 0;
		for (ParseTreeNode ptn : p.parse()) {
			System.out.println(ptn.toString("" + i, true));
			i++;
		}
	}

}
