/*
* Copyright (c) 2017, Gene Munce
* All rights reserved.
*/
package QModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javafx.scene.control.TextField;

public class Service extends Station{
	private static final long serialVersionUID = -3833116895900786135L;
	static int currentStationId = 200;
	private Queue<Token> activeToken = new LinkedList<>();
	ArrayList<int[]> delayList = new ArrayList<>();

	public Service(ArrayList<TextField> tfa) {
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
		System.out.println("New Service - ID: " + this.getStationId() + 
				" Type: " + getStationType() +
				" A: " + this.getServiceTimeA() +
				" B: " + this.getServiceTimeB() +
				" Limit: " + this.getTokenLimit());

	}

	public Service(String stationName, int serviceTime, int tokenLimit, int stationType) {
		super();
		this.rand = new Random();
		this.setStationName(stationName);
		this.setServiceTimeA(serviceTime);
		this.setTokenLimit(tokenLimit);
		this.setStationId(currentStationId++);
		this.setWorkTime(0);
		this.setWaitTime(0);
		this.setBlockTime(0);
		this.setIdleTime(0);
		this.setTokensProcessed(0);
		this.setReleaseTime(serviceTime);
		System.out.println("Creating a Service Station " + stationName);
	}

	public boolean isFull() {
		if (activeToken.size() >= getTokenLimit()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean offer(Token e) {
		// If there is room, receive a token and initialize it for the station
		if(activeToken.size() < getTokenLimit()) {
			e.newToStation(getServiceTime(), getStationId(), getStationType());
			return activeToken.offer(e);
		}
		else {
			return false;
		}
	}

	@Override
	public Token poll() {
		//
		if(!this.isEmpty()) {
			setTokensProcessed(getTokensProcessed() + 1);
			this.peek().leavingStation();
			return super.poll();

		}
		return null;
	}

	public void tick() {
		// if there are still tokens in the output queue then we are blocked
		// if output is not empty assume it is also full -- may need to revisit this
		if(!this.isEmpty()) {
			this.addBlockTime();
			for (Token t: this) {
				t.addWaitTime(1);
				t.ticklocalDelayTime();
			}
		}

		// if there is there are no tokens to process we are idle
		if(activeToken.isEmpty())
		{ this.addIdleTime(); }

		else {  // we are processing tokens

			// loop through all tokens
			for(Token t: activeToken) {
				t.ticklocalWorkingTime();
				// Update Station work time
				setWorkTime(getWorkTime() + 1);
			}

			// should we move a token to the output queue?
			while(!activeToken.isEmpty() && activeToken.peek().getlocalWorkingTime() <= 0) {
				this.add(activeToken.poll());
				//System.out.println("Service moving token to output Q");
			}
		}
	}

	public Queue<Token> getActiveToken() {
		return activeToken;
	}


}
