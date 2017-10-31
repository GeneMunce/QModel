package QModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javafx.scene.control.TextField;

public class Station extends LinkedList<Token>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8336529697159565717L;
	static int currentStationId = 200;
	private int stationId;
	private String stationName;
	private int stationType;
	private int workTime;
	private int idleTime;
	private int waitTime;
	private int blockTime;
	private int tokensProcessed;
	private int serviceTime;
	private int tokenLimit;
	private int releaseTime;
	Random rand;	

	public Station() {
		super();
	}
	
	public Station(String stationName) {
		super();
		this.rand = new Random();
		this.stationId = currentStationId++;
		this.stationName = stationName;
		this.workTime = 0;
		this.idleTime = 0;
		this.waitTime = 0;
		this.blockTime = 0;
		this.tokensProcessed = 0;
	}

	public Station(String stationName, int serviceTime, int tokenLimit, int stationType) {
		super();
		this.rand = new Random();
		this.setStationName(stationName);
		this.setServiceTime(serviceTime);
		this.setTokenLimit(tokenLimit);
		this.setStationId(currentStationId++);
		this.setWorkTime(0);
		this.setWaitTime(0);
		this.setBlockTime(0);
		this.setIdleTime(0);
		this.setTokensProcessed(0);
		this.setReleaseTime(serviceTime);

	}

	public Station(ArrayList<TextField> tfa) {
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
		
	}

	public void tick() {
	}
	
	public boolean isFull() {
		if (this.size() >= getTokenLimit()) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public int getStationType() {
		return stationType;
	}

	public void setStationType(int stationType) {
		this.stationType = stationType;
	}

	public int getWorkTime() {
		return workTime;
	}

	public void setWorkTime(int workTime) {
		this.workTime = workTime;
	}

	public int getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(int idleTime) {
		this.idleTime = idleTime;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public int getBlockTime() {
		return blockTime;
	}

	public void setBlockTime(int blockTime) {
		this.blockTime = blockTime;
	}

	public int getTokensProcessed() {
		return tokensProcessed;
	}

	public void setTokensProcessed(int tokensProcessed) {
		this.tokensProcessed = tokensProcessed;
	}
	
	public int getServiceTime() {
		int r;
		
		switch (getStationType()) {
		case 0:
			r = this.serviceTime;
			break;
		case 1:
			r = rand.nextInt(this.serviceTime) + 1;
			break;
		case 2:
			r = TriangularDist(serviceTime, serviceTime * 2, serviceTime*3);
			break;
		default:
			r = serviceTime;
			System.out.println("Wrong type sent to Create: " + stationId);
		}
		//System.out.println("r = " + r);
		return r;

	}

	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	public int getTokenLimit() {
		return tokenLimit;
	}

	public void setTokenLimit(int tokenLimit) {
		this.tokenLimit = tokenLimit;
	}

	public int getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(int releaseTime) {
		this.releaseTime = releaseTime;
	}

	public int addIdleTime() {
		return this.idleTime++;
	}
	
	public void addBlockTime() {
		blockTime++;
		return;
	}
	
	public void addWaitTime() {
		waitTime++;
		return;
	}

	/*
	 * ==== Arrival rate routines 
	 */
	
	public int TriangularDist(int a, int b, int c) {
		int r;
//		int p = rand.nextInt(2);
		int p = (int) (Math.random() * 10);
		r = b;
		/*
		switch(p) {
		case 0:
			r = a;
			break;
		case 1:
			r = a;
			break;
		case 2:
			r = a;
			break;
		case 3:
			r = a;
			break;
		case 4:
			r = b;
			break;
		case 5:
			r = b;
			break;
		case 6:
			r = b;
			break;
		case 7:
			r = c;
			break;
		case 8:
			r = c;
			break;
		case 9:
			r = c;
			break;
		}
		*/
		return r*p;
	}

	
}