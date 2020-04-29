import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;

public class CollinearPointsTest {

	private Point[] buildPoints(String args) {
		ArrayList<Point> result = new ArrayList<Point>();
		try (Scanner s = new Scanner(args)) {
			do {
				int x = s.nextInt();
				int y = s.nextInt();
				result.add(new Point(x, y));
			} while (s.hasNextInt());
		}
		return result.toArray(new Point[result.size()]);
	}

	private Point[] readFromFile(String file) throws IOException {
		String content = new String(Files.readAllBytes(Paths.get(file)));
		try (Scanner s = new Scanner(content)) {
			int np = s.nextInt();
			Point[] result = new Point[np];
			for (int i = 0; i < np; i++) {
				int x = s.nextInt();
				int y = s.nextInt();
				result[i] = new Point(x, y);
			}
			return result;
		}
	}

	@Test
	public void noSegments() {
		Point[] input = buildPoints("0 0 1 1 1 2 2 3 3 4 4 6");
		BruteCollinearPoints bcp = new BruteCollinearPoints(input);
		assertEquals(0, bcp.numberOfSegments());
		FastCollinearPoints fcp = new FastCollinearPoints(input);
		assertEquals(0, fcp.numberOfSegments());
	}

	@Test
	public void oneSegment() {
		Point[] input = buildPoints("0 0 1 1 1 2 2 3 3 4 4 5");
		BruteCollinearPoints bcp = new BruteCollinearPoints(input);
		assertEquals(1, bcp.numberOfSegments());
		FastCollinearPoints fcp = new FastCollinearPoints(input);
		assertEquals(1, fcp.numberOfSegments());
	}

	@Test
	public void twoSegments() {
		Point[] input = buildPoints("0 0 1 1 1 2 2 3 3 4 4 5 3 2 4 1 1 4 6 8");
		BruteCollinearPoints bcp = new BruteCollinearPoints(input);
		assertEquals(2, bcp.numberOfSegments());
		FastCollinearPoints fcp = new FastCollinearPoints(input);
		assertEquals(2, fcp.numberOfSegments());
	}

	@Test
	public void nullInput() {
		try {
			new BruteCollinearPoints(null);
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			new FastCollinearPoints(null);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void nullPoint() {
		Point[] input = new Point[] { new Point(1, 1), null };
		try {
			new BruteCollinearPoints(input);
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			new FastCollinearPoints(input);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void lessThan4Points() {
		Point[] input = buildPoints("1 1 2 2 3 3");
		BruteCollinearPoints bcp = new BruteCollinearPoints(input);
		assertEquals(0, bcp.numberOfSegments());
		FastCollinearPoints fcp = new FastCollinearPoints(input);
		assertEquals(0, fcp.numberOfSegments());
	}

	@Test
	public void oneSegmentOf5Points() {
		Point[] input = buildPoints("0 0 1 1 1 2 2 3 3 4 4 5 5 6");
		FastCollinearPoints fcp = new FastCollinearPoints(input);
		assertEquals(1, fcp.numberOfSegments());
	}

	@Test
	public void verticalLine() {
		Point[] input = buildPoints("10 1 10 12 10 16 10 30 10 4");
		FastCollinearPoints fcp = new FastCollinearPoints(input);
		assertEquals(1, fcp.numberOfSegments());
	}

	@Test
	public void duplicatePoint() {
		Point[] input = buildPoints("0 0 1 1 1 2 2 3 3 4 4 5 4 5");
		try {
			new BruteCollinearPoints(input);
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			new FastCollinearPoints(input);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void horizontalLine() {
		Point[] input = buildPoints("1638 16657 10187 16657 10425 16657 19028 16657");
		FastCollinearPoints fcp = new FastCollinearPoints(input);
		assertEquals(1, fcp.numberOfSegments());
	}

	@Test
	public void anotherLine() {
		Point[] input = buildPoints("1000 18000 2000 22000 3000 26000 3500 28000 4000 30000");
		FastCollinearPoints fcp = new FastCollinearPoints(input);
		assertEquals(1, fcp.numberOfSegments());
	}

	@Test
	public void matrix() {
		final int N = 2048 / 8;
		Point[] points = new Point[N * 8];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < 8; j++) {
				points[i * 8 + j] = new Point(i + 10, j + 20);
			}
		}
		FastCollinearPoints fcp = new FastCollinearPoints(points);
	}

	@Test
	public void kw1260() throws IOException {
		Point[] input = readFromFile("kw1260.txt");
		FastCollinearPoints fcp = new FastCollinearPoints(input);
		assertEquals(288, fcp.numberOfSegments());
	}

	@Test
	public void rs1423() throws IOException {
		Point[] input = readFromFile("rs1423.txt");
		FastCollinearPoints fcp = new FastCollinearPoints(input);
		assertEquals(443, fcp.numberOfSegments());
	}
}
