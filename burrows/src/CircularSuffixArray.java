import edu.princeton.cs.algs4.Quick3way;

public class CircularSuffixArray {

	private final int[] indices;
	// circular suffix array of s

	private static class CircularItem implements Comparable<CircularItem> {
		byte[] value;
		int start;
		int end;

		public CircularItem(byte[] value, int start, int end) {
			this.value = value;
			this.start = start;
			this.end = end;
		}

		@Override
		public int compareTo(CircularItem that) {
			for (int i = start, j = that.start; i < end; i++, j++) {
				int vi = value[i] & 0xff;
				int vj = that.value[j] & 0xff;
				if (vi < vj)
					return -1;
				if (vi > vj)
					return 1;

			}
			return 0;
		}
	}

	public CircularSuffixArray(String s) {
		if (s == null)
			throw new IllegalArgumentException();
		int N = s.length();
		CircularItem[] circularStrings = new CircularItem[N];
		byte[] str = s.getBytes();
		byte[] repeated = new byte[str.length * 2];
		System.arraycopy(str, 0, repeated, 0, str.length);
		System.arraycopy(str, 0, repeated, str.length, str.length);
		for (int i = 0; i < N; i++)
			circularStrings[i] = new CircularItem(repeated, i, i + N);
		Quick3way.sort(circularStrings);
		indices = new int[N];
		for (int i = 0; i < N; i++) {
			indices[i] = circularStrings[i].start;
		}
	}

	// length of s
	public int length() {
		return indices.length;
	}

	// returns index of ith sorted suffix
	public int index(int i) {
		if (i < 0 || i >= indices.length)
			throw new IllegalArgumentException();
		return indices[i];
	}

}
