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
	private int a;
	private int b;
	private int c;
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
		this.setServiceTime(Integer.parseInt(tfa.get(2).getCharacters().toString()));
		this.setTokenLimit(Integer.parseInt(tfa.get(3).getCharacters().toString()));

		this.setStationId(currentStationId++);
		this.setWorkTime(0);
		this.setWaitTime(0);
		this.setBlockTime(0);
		this.setIdleTime(0);
		this.setTokensProcessed(0);
		this.setReleaseTime(getServiceTime());

		this.a = Integer.parseInt(tfa.get(2).getCharacters().toString());
		this.b = 0;
		this.c = 0;
		this.tokensToCreate = Integer.parseInt(tfa.get(3).getCharacters().toString());
		this.freeTokenList = freeTokenList;
		this.time = 0;

		// fill the array with the number of concurrent tokens to process
		for (int i = 0; i < tokensToCreate; i++) {
			activeToken.add(freeTokenList.poll());
			activeToken.peek().newToStation(getNewReleaseTime(), getStationId(), getStationType());
		}
	}

	public Create(String stationName, int createType, int tokensToCreate, int a, int b, int c,
			Queue<Token> freeTokenList) {
		super();
		this.rand = new Random();
		this.setStationName(stationName);
		this.setStationType(createType);
		this.tokensToCreate = tokensToCreate;
		this.a = a;
		this.b = b;
		this.c = c;
		this.freeTokenList = freeTokenList;
		this.time = 0;

		// fill the array with the number of concurrent tokens to process
		for (int i = 0; i < tokensToCreate; i++) {
			activeToken.add(freeTokenList.poll());
			activeToken.peek().newToStation(getServiceTime(), getStationId(), getStationType());

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
		int r;
	
		switch (getStationType()) {
		case 0:
			r = a;
			break;
		case 1:
			r = rand.nextInt(a) + 1;
			break;
		case 2:
			r = TriangularDist(a, b, c);
			break;
		default:
			r = a;
			System.out.println("Wrong type sent to Create: " + getStationId());
		}
		//System.out.println("r = " + r);
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