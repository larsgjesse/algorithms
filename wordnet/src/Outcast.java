
public class Outcast {
	private final WordNet net;

	public Outcast(WordNet wordnet) {
		net = wordnet;

	}

	public String outcast(String[] nouns) {
		if (nouns == null || nouns.length < 2)
			throw new IllegalArgumentException();
		int n = nouns.length;
		int[] dists = new int[n];
		int maxDist = 0;
		int maxIndex = -1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				int d = net.distance(nouns[i], nouns[j]);
				dists[i] += d;
				if (dists[i] > maxDist) {
					maxIndex = i;
					maxDist = dists[i];
				}
				dists[j] += d;
				if (dists[j] > maxDist) {
					maxIndex = j;
					maxDist = dists[j];
				}
			}
		}
		return nouns[maxIndex];
	}
}
