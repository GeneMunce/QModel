package QModel;

import java.util.ArrayList;

public class TokenStats extends ArrayList<Integer>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6190913685011044127L;
	private int[] count;

	   	
	public int max() {
        int m = Integer.MIN_VALUE;
		for (int i : this) {
            if (i > m) m = i;
        }
		return m;
	}

	public int min() {
        int m = Integer.MAX_VALUE;
		for (int i : this) {
            if (i < m) m = i;
        }
		return m;
	}
	
	public int sum() {
		int s = 0;
		for (int i : this) {
            s += i;
        }		
		return s;
	}
	
	public double ave() {
		double d = 0;
		d = (double) sum() / (double) this.size();	
		return d;
	}
	
	private int bins = 10;
	private int bin = 1;
	
	public int[] hist(int bins) {
		this.bins = bins;
		int max = max();
		this.bin = (max / bins)+1;
		this.count = new int[bins];
		int index;
		for(int i : this) {
			index = i/bin;
			this.count[index]++;
		}
		return count;
	}
	public void printHist() {
		if (count.length <= 0) {
			System.out.println("Error: Call hist before printHist");
			return;
		}
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < this.count.length; i++) {
			sb.append(i*this.bin);
			sb.append("-");
			sb.append(((i+1)*this.bin) -1);
			sb.append(":");
			sb.append(this.count[i]);
			sb.append("\n");
		}
		System.out.println(sb);
	}

	public int[] getCount() {
		return count;
	}

	public int getBins() {
		return bins;
	}

	public int getBin() {
		return bin;
	}

}

