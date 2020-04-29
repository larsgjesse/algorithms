import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

public class PointTest {

	@Test
	public void order() {
		Point a = new Point(1, 2);
		assertEquals(1, a.compareTo(new Point(1, 1)));
		assertEquals(1, a.compareTo(new Point(0, 2)));
		assertEquals(0, a.compareTo(new Point(1, 2)));
		assertEquals(-1, a.compareTo(new Point(2, 2)));
		assertEquals(-1, a.compareTo(new Point(0, 3)));
	}

	@Test
	public void slopes() {
		Point a = new Point(1, 2);
		assertEquals(3.0 / 2.0, a.slopeTo(new Point(3, 5)), 1e-6);
		assertEquals(Double.NEGATIVE_INFINITY, a.slopeTo(a), 1e-6);
		assertEquals(Double.POSITIVE_INFINITY, a.slopeTo(new Point(1, 3)), 1e-6);
	}

	@Test
	public void slopeOrder() {
		Point a = new Point(1, 2);
		Comparator<Point> comp = a.slopeOrder();

		Point b = new Point(2, 2);
		Point c = new Point(2, 3);
		assertEquals(-1, comp.compare(b, c));
		assertEquals(1, comp.compare(c, b));
		assertEquals(0, comp.compare(c, new Point(3, 4)));
	}
}
