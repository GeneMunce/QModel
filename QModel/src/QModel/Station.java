/*
* Copyright (c) 2017, Gene Munce
* All rights reserved.
*/
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
	private int serviceTimeA;
	private int serviceTimeB;
	private int tokenLimit;
	private int releaseTime;
	Random rand;

	public Station() {
		super();
	}

	public Station(ArrayList<TextField> tfa) {
		super();
		this.rand = new Random();
		this.setStationName(tfa.get(0).getCharacters().toString());
		this.setStationType(Integer.parseInt(tfa.get(1).getCharacters().toString()));
		this.setServiceTimeA(Integer.parseInt(tfa.get(2).getCharacters().toString()));
		this.setServiceTimeB(Integer.parseInt(tfa.get(3).getCharacters().toString()));
		this.setTokenLimit(Integer.parseInt(tfa.get(4).getCharacters().toString()));

		this.setStationId(currentStationId++);
		this.setWorkTime(0);
		this.setWaitTime(0);
		this.setBlockTime(0);
		this.setIdleTime(0);
		this.setTokensProcessed(0);
		this.setReleaseTime(getServiceTime());
		System.out.println("New Station - ID: " + this.getStationId() + 
				" Type: " + getStationType() +
				" A: " + this.getServiceTimeA() +
				" B: " + this.getServiceTimeB() +
				" Limit: " + this.getTokenLimit());
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
		case 0: // Constant
			r = this.serviceTimeA;
			break;
		case 1: // Random
			//r = rand.nextInt(this.serviceTimeA) + 1;
			r = (int) (Math.random() * (double) (serviceTimeA - serviceTimeB)) + serviceTimeB;
			break;
		case 2: // Triangular
			r = TriangularDist(serviceTimeB, (serviceTimeA + serviceTimeB) / 2 , serviceTimeA);
			break;
		default:
			r = serviceTimeA;
			System.out.println("Wrong type sent to Create: " + stationId);
		}
		return r;

	}

	public void setServiceTimeA(int s) {
		this.serviceTimeA = s;
	}

	public void setServiceTimeB(int s) {
		this.serviceTimeB = s;
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

	public int getServiceTimeA() {
		return serviceTimeA;
	}

	public int getServiceTimeB() {
		return serviceTimeB;
	}

	/*
	 * ==== Arrival rate routines
	 */

	public int TriangularDist(int a, int b, int c) {
		int r;
		int p = rand.nextInt(4);
		r = b;
		
		switch(p) {
		case 0:
			r = a;
			break;
		case 1:
			r = b;
			break;
		case 2:
			r = b;
			break;
		case 3:
			r = c;
			break;
		default:
			r = b;
			break;
		}
		
		return r;
	}


}
