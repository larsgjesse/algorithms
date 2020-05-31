import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class CircularSuffixArrayTest {
	CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");

	@Test
	public void length() {
		assertEquals(12, csa.length());
	}

	@Test
	public void index() {
		assertEquals(11, csa.index(0));
		assertEquals(10, csa.index(1));
		assertEquals(7, csa.index(2));
		assertEquals(0, csa.index(3));
		assertEquals(3, csa.index(4));
		assertEquals(5, csa.index(5));
		assertEquals(8, csa.index(6));
		assertEquals(1, csa.index(7));
		assertEquals(4, csa.index(8));
		assertEquals(6, csa.index(9));
		assertEquals(9, csa.index(10));
		assertEquals(2, csa.index(11));
	}

	@Test
	public void correctness() {
		byte[] bytes = new byte[] { (byte) 0xd6, 0x61, (byte) 0x90, (byte) 0xef, (byte) 0xf4, 0x5e, (byte) 0x99,
				(byte) 0xce, (byte) 0xd4, (byte) 0xd9 };
		CircularSuffixArray ll = new CircularSuffixArray(new String(bytes));
		assertEquals(5, ll.index(0));
	}
}
