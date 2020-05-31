import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
	private static class Encoder {
		private static class Node {
			public int value;

			public Node(int value) {
				this.value = value;
			}

			public Node next;
		}

		Node[] nodes = new Node[256];
		Node head;

		public Encoder() {
			Node prev = null;
			for (int i = 0; i < 256; i++) {
				nodes[i] = new Node(i);
				if (prev != null)
					prev.next = nodes[i];
				prev = nodes[i];
			}
			head = nodes[0];
		}

		public int encode(int b) {
			Node newHead = nodes[b];
			int result = newHead.value;
			if (result == 0)
				return result;
			Node n = head;
			while (n.next != newHead) {
				n.value++;
				n = n.next;
			}
			n.value++;
			n.next = newHead.next;
			newHead.next = head;
			head = newHead;
			head.value = 0;
			return result;
		}
	}

	private static class Decoder {
		private int[] table = new int[256];

		public Decoder() {
			for (int i = 0; i < table.length; i++) {
				table[i] = i;
			}
		}

		public int decode(int b) {
			int result = table[b];
			while (b != 0) {
				table[b] = table[--b];
			}
			table[0] = result;
			return result;
		}
	}

	// apply move-to-front encoding, reading from standard input and writing to
	// standard output
	public static void encode() {
		Encoder encoder = new Encoder();
		while (!BinaryStdIn.isEmpty()) {
			BinaryStdOut.write((byte) encoder.encode(BinaryStdIn.readByte() & 0xFF));
		}
		BinaryStdOut.flush();
	}

	// apply move-to-front decoding, reading from standard input and writing to
	// standard output
	public static void decode() {
		Decoder decoder = new Decoder();
		while (!BinaryStdIn.isEmpty()) {
			BinaryStdOut.write((byte) decoder.decode(BinaryStdIn.readByte() & 0xFF));
		}
		BinaryStdOut.flush();
	}

	// if args[0] is "-", apply move-to-front encoding
	// if args[0] is "+", apply move-to-front decoding
	public static void main(String[] args) {
		if (args.length != 1 || args[0].length() != 1) {
			throw new IllegalArgumentException();
		}
		char arg = args[0].charAt(0);
		if (arg != '-' && arg != '+') {
			throw new IllegalArgumentException();
		}
		if (arg == '+') {
			decode();
		} else {
			encode();
		}
	}

}
