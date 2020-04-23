import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException();
		}
		int k = Integer.parseInt(args[0]);
		if (k < 0) {
			throw new IllegalArgumentException();
		}
		RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
		while (!StdIn.isEmpty()) {
			randomizedQueue.enqueue(StdIn.readString());
		}
		if (k > randomizedQueue.size()) {
			throw new IllegalArgumentException();
		}
		Iterator<String> it = randomizedQueue.iterator();
		for (int i = 0; i < k; i++) {
			StdOut.println(it.next());
		}
	}
}