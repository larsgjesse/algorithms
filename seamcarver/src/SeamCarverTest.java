import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.Picture;

public class SeamCarverTest {

	static final double epsilon = 0.001;
	Picture tbf = new Picture("3x4.png");
	SeamCarver seamCarver3x4 = new SeamCarver(tbf);

	@Test
	public void picture() {
		assertEquals(tbf, seamCarver3x4.picture());
	}

	@Test
	public void height() {
		assertEquals(tbf.height(), seamCarver3x4.height());
	}

	@Test
	public void width() {
		assertEquals(tbf.width(), seamCarver3x4.width());
	}

	@Test
	public void energy() {
		assertEquals(1000.0, seamCarver3x4.energy(0, 0), epsilon);
		assertEquals(1000.0, seamCarver3x4.energy(1, 0), epsilon);
		assertEquals(1000.0, seamCarver3x4.energy(2, 0), epsilon);
		assertEquals(1000.0, seamCarver3x4.energy(0, 1), epsilon);
		assertEquals(Math.sqrt(52225.0), seamCarver3x4.energy(1, 1), epsilon);
		assertEquals(1000.0, seamCarver3x4.energy(2, 1), epsilon);
		assertEquals(1000.0, seamCarver3x4.energy(0, 2), epsilon);
		assertEquals(Math.sqrt(52024.0), seamCarver3x4.energy(1, 2), epsilon);
		assertEquals(1000.0, seamCarver3x4.energy(2, 2), epsilon);
		assertEquals(1000.0, seamCarver3x4.energy(0, 3), epsilon);
		assertEquals(1000.0, seamCarver3x4.energy(1, 3), epsilon);
		assertEquals(1000.0, seamCarver3x4.energy(2, 3), epsilon);
	}

	@Test
	public void removeVerticalSeam() {
		seamCarver3x4.removeVerticalSeam(new int[] { 0, 1, 2, 1 });
		Picture expected = new Picture(tbf.width() - 1, tbf.height());
		expected.setRGB(0, 0, tbf.getRGB(1, 0));
		expected.setRGB(1, 0, tbf.getRGB(2, 0));
		expected.setRGB(0, 1, tbf.getRGB(0, 1));
		expected.setRGB(1, 1, tbf.getRGB(2, 1));
		expected.setRGB(0, 2, tbf.getRGB(0, 2));
		expected.setRGB(1, 2, tbf.getRGB(1, 2));
		expected.setRGB(0, 3, tbf.getRGB(0, 3));
		expected.setRGB(1, 3, tbf.getRGB(2, 3));
		assertEquals(expected, seamCarver3x4.picture());
		assertEquals(expected.width(), seamCarver3x4.width());
		assertEquals(expected.height(), seamCarver3x4.height());
	}

	@Test
	public void removeHorizontalSeam() {
		seamCarver3x4.removeHorizontalSeam(new int[] { 1, 2, 3 });
		Picture expected = new Picture(tbf.width(), tbf.height() - 1);
		expected.setRGB(0, 0, tbf.getRGB(0, 0));
		expected.setRGB(1, 0, tbf.getRGB(1, 0));
		expected.setRGB(2, 0, tbf.getRGB(2, 0));
		expected.setRGB(0, 1, tbf.getRGB(0, 2));
		expected.setRGB(1, 1, tbf.getRGB(1, 1));
		expected.setRGB(2, 1, tbf.getRGB(2, 1));
		expected.setRGB(0, 2, tbf.getRGB(0, 3));
		expected.setRGB(1, 2, tbf.getRGB(1, 3));
		expected.setRGB(2, 2, tbf.getRGB(2, 2));
		assertEquals(expected, seamCarver3x4.picture());
		assertEquals(expected.width(), seamCarver3x4.width());
		assertEquals(expected.height(), seamCarver3x4.height());
	}

	@Test
	public void findHorizontalSeam() {
		int[] seam = seamCarver3x4.findHorizontalSeam();
		assertArrayEquals(new int[] { 1, 2, 1 }, seam);
	}

	@Test
	public void findVerticalSeam() {
		int[] seam = seamCarver3x4.findVerticalSeam();
		assertArrayEquals(new int[] { 0, 1, 1, 0 }, seam);
	}

	@Test
	public void singleLine() {
		seamCarver3x4.removeHorizontalSeam(seamCarver3x4.findHorizontalSeam());
		assertEquals(3, seamCarver3x4.height());
		seamCarver3x4.removeHorizontalSeam(seamCarver3x4.findHorizontalSeam());
		assertEquals(2, seamCarver3x4.height());
		seamCarver3x4.removeHorizontalSeam(seamCarver3x4.findHorizontalSeam());
		assertEquals(1, seamCarver3x4.height());
		try {
			seamCarver3x4.removeHorizontalSeam(seamCarver3x4.findHorizontalSeam());
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void removeVerticalSeamIllegalArguments() {
		try {
			seamCarver3x4.removeVerticalSeam(null);
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			seamCarver3x4.removeVerticalSeam(new int[] { 1 });
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			seamCarver3x4.removeVerticalSeam(new int[] { 1, 0, -1, 0 });
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			seamCarver3x4.removeVerticalSeam(new int[] { 2, 2, 0, 1 });
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void removeHorizontalSeamIllegalArguments() {
		try {
			seamCarver3x4.removeHorizontalSeam(null);
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			seamCarver3x4.removeHorizontalSeam(new int[] { 1 });
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			seamCarver3x4.removeHorizontalSeam(new int[] { 0, -1, 0 });
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			seamCarver3x4.removeHorizontalSeam(new int[] { 2, 2, 0 });
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void horizontal5x6() {
		Picture sbf = new Picture("6x5.png");
		SeamCarver seamCarver5x6 = new SeamCarver(sbf);
		assertArrayEquals(new int[] { 3, 4, 3, 2, 1 }, seamCarver5x6.findVerticalSeam());
		assertArrayEquals(new int[] { 1, 2, 1, 2, 1, 0 }, seamCarver5x6.findHorizontalSeam());
	}
}
