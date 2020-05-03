import java.util.ArrayList;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;

public class WordNet {
	private final Digraph net;
	private final RedBlackBST<String, Bag<Integer>> nounIDs;
	private final ArrayList<String[]> synsets;
	private final SAP sap;

	// constructor takes the name of the two input files
	public WordNet(String synsetsFile, String hypernymsFile) {
		if (synsetsFile == null || hypernymsFile == null) {
			throw new IllegalArgumentException();
		}
		nounIDs = new RedBlackBST<>();
		synsets = new ArrayList<>();
		readSynsets(synsetsFile);
		net = new Digraph(synsets.size());
		readHypernyms(hypernymsFile);

		// Check for cycles
		if (new DirectedCycle(net).hasCycle()) {
			throw new IllegalArgumentException();
		}
		// Check multiple roots
		int root = -1;
		for (int i = 0; i < net.V(); i++) {
			if (net.outdegree(i) == 0) {
				if (root != -1)
					throw new IllegalArgumentException();
				root = i;
			}
		}
		sap = new SAP(net);
	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return nounIDs.keys();
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		if (word == null) {
			throw new IllegalArgumentException();
		}
		return nounIDs.contains(word);
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		Iterable<Integer> synsetsA = getSynset(nounA);
		Iterable<Integer> synsetsB = getSynset(nounB);

		return sap.length(synsetsA, synsetsB);
	}

	// a synset (second field of synsets.txt) that is the common ancestor of nounA
	// and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		Iterable<Integer> synsetsA = getSynset(nounA);
		Iterable<Integer> synsetsB = getSynset(nounB);
		int ancestor = sap.ancestor(synsetsA, synsetsB);
		if (ancestor == -1)
			return null;

		String[] nouns = synsets.get(ancestor);
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < nouns.length; i++) {
			result.append(nouns[i]);
			if (i != nouns.length - 1)
				result.append(' ');
		}
		return result.toString();
	}

	private Iterable<Integer> getSynset(String noun) {
		if (noun == null)
			throw new IllegalArgumentException();
		Iterable<Integer> result = nounIDs.get(noun);
		if (result == null)
			throw new IllegalArgumentException();
		return result;
	}

	private void readSynsets(String fileName) {
		In input = new In(fileName);
		while (input.hasNextLine()) {
			String[] items = input.readLine().split(",");
			int id = Integer.parseInt(items[0]);
			String[] nouns = items[1].split(" ");
			for (String noun : nouns) {
				Bag<Integer> bag = nounIDs.get(noun);
				if (bag == null) {
					bag = new Bag<Integer>();
					nounIDs.put(noun, bag);
				}
				bag.add(id);
			}
			synsets.add(nouns);
		}
	}

	private void readHypernyms(String fileName) {
		In input = new In(fileName);
		while (input.hasNextLine()) {
			String[] items = input.readLine().split(",");
			int id = Integer.parseInt(items[0]);
			for (int i = 1; i < items.length; i++) {
				int hyp = Integer.parseInt(items[i]);
				net.addEdge(id, hyp);
			}
		}
	}
}
