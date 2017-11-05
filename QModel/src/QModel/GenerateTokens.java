/*
* Copyright (c) 2017, Gene Munce
* All rights reserved.
*/
package QModel;

import java.util.LinkedList;

/**
 * @author genem
 *
 */
public class GenerateTokens extends LinkedList<Token> {
	/**
	 *
	 */
	private static final long serialVersionUID = 7585486798524549054L;
	private int incrementTokens;

	public GenerateTokens(int startTokens, int incrementTokens) {
		super();
		this.incrementTokens = incrementTokens;
		System.out.println("Creating Token List");
		for(int i = startTokens; i > 0; i--) {this.add(new Token());}

	}

	@Override
	public Token poll() {
		if(this.isEmpty()) {
			for(int i = incrementTokens; i > 0; i--) {this.add(new Token());}
			System.out.println("Adding to Token List");
		}
		return super.poll();
	}

	@Override
	public boolean offer(Token t) {
		t.initToken(0);
		return super.offer(t);
	}

}
