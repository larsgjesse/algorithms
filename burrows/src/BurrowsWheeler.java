import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
	// apply Burrows-Wheeler transform,
	// reading from standard input and writing to standard output
	public static void transform() {
		String input = BinaryStdIn.readString();
		CircularSuffixArray csa = new CircularSuffixArray(input);
		int N = input.length();
		byte[] inBytes = input.getBytes();
		byte[] output = new byte[N];
		for (int i = 0; i < N; i++) {
			int csaIndex = csa.index(i);
			if (csaIndex == 0)
				BinaryStdOut.write(i);
			output[i] = inBytes[(csaIndex + N - 1) % N];
		}
		for (int i = 0; i < N; i++) {
			BinaryStdOut.write(output[i]);
		}
		BinaryStdOut.flush();
	}

	// apply Burrows-Wheeler inverse transform,
	// reading from standard input and writing to standard output
	public static void inverseTransform() {
		int first = BinaryStdIn.readInt();
		String t = BinaryStdIn.readString();
		byte[] a = t.getBytes();
		int N = a.length;
		int[] next = getNext(a);
		byte[] result = new byte[next.length];
		int n = next[first];

		for (int i = 0; i < result.length; i++) {
			result[i] = a[n];
			n = next[n];
		}
		for (int i = 0; i < N; i++) {
			BinaryStdOut.write(result[i]);
		}
		BinaryStdOut.flush();
	}

	private static final int R = 256;

	private static int[] getNext(byte[] a) {
		int N = a.length;
		int[] result = new int[N];
		int[] count = new int[R + 1];
		// Compute frequency counts.
		for (int i = 0; i < N; i++)
			count[(a[i] & 0xff) + 1]++;
		// Transform counts to indices.
		for (int r = 0; r < R; r++)
			count[r + 1] += count[r];

		// Distribute the records.
		for (int i = 0; i < N; i++)
			result[count[a[i] & 0xff]++] = i;

		return result;
	}

	// if args[0] is "-", apply Burrows-Wheeler transform
	// if args[0] is "+", apply Burrows-Wheeler inverse transform
	public static void main(String[] args) {
		if (args.length != 1 || args[0].length() != 1) {
			throw new IllegalArgumentException();
		}
		char arg = args[0].charAt(0);
		if (arg != '-' && arg != '+') {
			throw new IllegalArgumentException();
		}
		if (arg == '+') {
			inverseTransform();
		} else {
			transform();
		}
	}

}
