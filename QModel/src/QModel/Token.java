/*
* Copyright (c) 2017, Gene Munce
* All rights reserved.
*/
package QModel;

public class Token {
	private static int currentTokenId = 10000;
	private int tokenId;
	private int createTime;
	private int workTime;
	private int waitTime;
	private int delayTime;
	private int currentStationId;
	private int currentStationType;
	private int localWorkingTime;
	private int localDelayTime;
	private boolean retired;

	// for first use of a token off of the freeTokenList
	public void initToken(int time) {
		this.localWorkingTime = 0;
		this.waitTime = 0;
		this.workTime = 0;
		this.delayTime = 0;
		this.createTime = time;
	}

	// for entry into a station
	public void newToStation(int serviceTime, int stationID, int stationType) {
		this.localWorkingTime = serviceTime;
		this.localDelayTime = 0;
		this.currentStationId = stationID;
		this.currentStationType = stationType;

	}

	// for exit from a station and collect local statistics into the total token statistics
	public void leavingStation() {
		//this.workTime += this.localWorkingTime;
		//this.delayTime += this.localDelayTime;
	}

	// for placing a token back onto the freeTokenList
	public void retireToken() {

	}

	public void initLocalWorkingTime(int serviceTime) {
		this.localWorkingTime = serviceTime;
		this.localDelayTime = 0;
	}

	public int ticklocalWorkingTime() {
		localDelayTime++;
		addWorkTime(1);
		addDelayTime(1);
		return --localWorkingTime;
	}

	public boolean isTokenDone() {
		if (localWorkingTime <= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public int ticklocalDelayTime() {
		addDelayTime(1);
		return ++localDelayTime;
	}

	public int getTokenId() {
		return tokenId;
	}

	public int getWorkTime() {
		return workTime;
	}

	public void addWorkTime(int workTime) {
		this.workTime += workTime;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void addWaitTime(int waitTime) {
		this.waitTime += waitTime;
	}

	public int getDelayTime() {
		return this.delayTime;
	}

	public void addDelayTime(int delayTime) {
		this.delayTime += delayTime;
	}

	public int getCurrentStationId() {
		return currentStationId;
	}

	public void setCurrentStationId(int currentStationId) {
		this.currentStationId = currentStationId;
	}

	public int getCurrentStationType() {
		return currentStationType;
	}

	public void setCurrentStationType(int currentStationType) {
		this.currentStationType = currentStationType;
	}

	public int getlocalWorkingTime() {
		return localWorkingTime;
	}

	public int getlocalDelayTime() {
		return localDelayTime;
	}

	public boolean isRetired() {
		return retired;
	}

	public void setRetired(boolean retired) {
		this.retired = retired;
	}

	public Token(int createId, int createStationType) {
		this.tokenId = currentTokenId++;
		this.workTime = 0;
		this.waitTime = 0;
		this.localWorkingTime = 0;
		this.localDelayTime = 0;
		this.currentStationId = createId;
		this.currentStationType = createStationType;
		this.retired = false;
	}

	public Token() {
		this.tokenId = currentTokenId++;
		this.workTime = 0;
		this.waitTime = 0;
		this.localWorkingTime = 0;
		this.localDelayTime = 0;
		this.currentStationId = 0;
		this.currentStationType = 0;
		this.retired = false;
	}

}
