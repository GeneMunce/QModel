/*
* Copyright (c) 2017, Gene Munce
* All rights reserved.
*/
package QModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javafx.scene.control.TextField;

public class Create extends Station {
	/**
	 *
	 */
	static int currentCreateId = 100;
	private static final long serialVersionUID = -4334563588867927474L;
	private int tokensToCreate;
	private Queue<Token> freeTokenList;
	private int time;
	private Queue<Token> activeToken = new LinkedList<>();


	/*
	 * createType function a b c -----------------------|-------------------- 0
	 * constant rate x x 1 normal mean std_dev x 2 triangular min max most likely
	 */

	public Create(ArrayList<TextField> tfa, Queue<Token> freeTokenList) {
		super();
		this.rand = new Random();
		this.setStationName(tfa.get(0).getCharacters().toString());
		this.setStationType(Integer.parseInt(tfa.get(1).getCharacters().toString()));
		this.setServiceTimeA(Integer.parseInt(tfa.get(2).getCharacters().toString()));
		this.setServiceTimeB(Integer.parseInt(tfa.get(3).getCharacters().toString()));
		this.setTokenLimit(Integer.parseInt(tfa.get(4).getCharacters().toString()));
		this.tokensToCreate = Integer.parseInt(tfa.get(4).getCharacters().toString());

		this.setStationId(currentStationId++);
		this.setWorkTime(0);
		this.setWaitTime(0);
		this.setBlockTime(0);
		this.setIdleTime(0);
		this.setTokensProcessed(0);
		this.setReleaseTime(getServiceTime());
		System.out.println("New Create - ID: " + this.getStationId() + 
				" Type: " + getStationType() +
				" A: " + this.getServiceTimeA() +
				" B: " + this.getServiceTimeB() +
				" Concurent: " + this.getTokenLimit() + 
				" Tokens: " + this.tokensToCreate);

		this.freeTokenList = freeTokenList;
		this.time = 0;

		// fill the array with the number of concurrent tokens to process
		for (int i = 0; i < tokensToCreate; i++) {
			Token tempT = freeTokenList.poll();
			tempT.newToStation(getNewReleaseTime(), getStationId(), getStationType());
			activeToken.add(tempT);
		}
	}


	public void tick() {
		time++;

		for(Token t : activeToken) {
			t.ticklocalWorkingTime();
		}
		Iterator<Token> t = activeToken.iterator();
		int j =0;
		while(t.hasNext()) {
			Token it = t.next();
			if (it.isTokenDone()) {
				// Release a token
				//System.out.println("Create1 releasing a token" + time);
				it.initToken(time);
				this.offer(it);
				t.remove();
				j++;
			}
		}

		for(int i = 0; i<j; i++) {
			// Add a new token t replace the one being released
			Token tempToken = freeTokenList.poll();
			tempToken.newToStation(getServiceTime(), getStationId(), getStationType());
			activeToken.add(tempToken);
		}


	}

	private int getNewReleaseTime() {
		int r = getServiceTime();
		return r;
	}

/*	private void ReleaseTokens() {
		for (int i = tokensToCreate; i >= 1; i--) {
			this.add(freeTokenList.poll());
		}
		setWorkTime(getWorkTime() + 1);
	}
*/
	@Override
	public Token poll() {
		setTokensProcessed(getTokensProcessed() + 1);
		return super.poll();
	}


}
