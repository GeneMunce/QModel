/*
* Copyright (c) 2017, Gene Munce
* All rights reserved.
*/
package QModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javafx.scene.control.TextField;


public class Executor {

	private static Queue<Token> freeTokenList;
	private static List<Station> exStationList;
	private int simulationCycles;
	private int tokenProcessed;
	private TokenStats tokenWorkTime;
	private TokenStats tokenWaitTime;
	private TokenStats tokenDelayTime;


	public Executor(int startTokenCount, ArrayList<TextField> tfa) {
		// This is the main class for the Qmodel
		// Initialize the run variables
		this.simulationCycles = Integer.parseInt(tfa.get(5).getCharacters().toString());
		int freeTokenCount = startTokenCount;

		// Create the token list
		freeTokenList = new GenerateTokens(freeTokenCount, freeTokenCount);
		this.tokenWorkTime = new TokenStats();
		this.tokenWaitTime = new TokenStats();
		this.tokenDelayTime = new TokenStats();

		/*
		 * Create and Connect your model components here
		 * Add your model components to the ArrayList
		 * They will be processed from this list
		*/
		Create create1 = new Create(tfa, freeTokenList);
		exStationList = new ArrayList<Station>();
		exStationList.add(create1);
	}

	public void addStation(ArrayList<TextField> tfa) {
		Station station1 = new Service(tfa);
		exStationList.add(station1);

	}

	public TokenStats run(int runCycles) {
		// Start the model simulation loop
		this.simulationCycles = runCycles;

		this.tokenProcessed = 0;
		tokenWaitTime.clear();
		tokenWorkTime.clear();
		tokenDelayTime.clear();

		Token inspectToken;
		for (int time = 0; time < simulationCycles; time++) {
			// tick all stations in reverse
			for (Station s: exStationList) {
				s.tick();
			}

			for( int i = 0; i < exStationList.size() - 1;i++) {
				while(!exStationList.get(i).isEmpty() && !exStationList.get(i+1).isFull()) {
					exStationList.get(i+1).offer(exStationList.get(i).poll());
					//System.out.println("Station " + i + " " + time);
				}
			}
			Station exitStation = exStationList.get(exStationList.size()-1);
			while(!exitStation.isEmpty()) {
				inspectToken = exitStation.poll();
				tokenProcessed++;

				// Add this token's stats to the distribution
				tokenWaitTime.add(inspectToken.getWaitTime());
				tokenWorkTime.add(inspectToken.getWorkTime());
				tokenDelayTime.add(inspectToken.getDelayTime());

				freeTokenList.offer(inspectToken);
			}

			// every n ticks
				// update station statistics
				// cleanup retired tokens and update statistics

		}
		// stop simulation

		// cleanup the token list and update statistics
		// freeTokenList.clear();
		return tokenDelayTime;
	}


	public void showStatistics() {
		// cleanup station list and update statistics
		for( Station e : exStationList) {
			System.out.println(e.getStationName()
					+ " Work: " + e.getWorkTime()
					+ " Idle: " + e.getIdleTime()
					// + " Wait: " + e.getWaitTime()
					+ " Block: " + e.getBlockTime()
					+ " tokens processed: " + e.getTokensProcessed());
		}
		System.out.println("tokens processed: " + tokenProcessed);
		// Token Stats
		StringBuffer sb = new StringBuffer();
		sb.append("Token Work min ");
		sb.append(tokenWorkTime.min());
		sb.append(" max ");
		sb.append(tokenWorkTime.max());
		sb.append(" ave ");
		sb.append(tokenWorkTime.ave());
		sb.append("\nToken Wait min ");
		sb.append(tokenWaitTime.min());
		sb.append(" max ");
		sb.append(tokenWaitTime.max());
		sb.append(" ave ");
		sb.append(tokenWaitTime.ave());
		sb.append("\nToken Delay min ");
		sb.append(tokenDelayTime.min());
		sb.append(" max ");
		sb.append(tokenDelayTime.max());
		sb.append(" ave ");
		sb.append(tokenDelayTime.ave());
		System.out.println(sb);
		tokenDelayTime.hist(20);
		tokenDelayTime.printHist();
		//tokenDelayTime.plotHist();

		//Application.launch(args);

		//exStationList.clear();

	}

}
