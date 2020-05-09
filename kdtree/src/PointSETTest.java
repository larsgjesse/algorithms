import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSETTest {
	PointSET pointSet = new PointSET();

	@Test
	public void insert() {
		assertTrue(pointSet.isEmpty());
		assertEquals(0, pointSet.size());
		pointSet.insert(new Point2D(0.1, 0.1));
		assertFalse(pointSet.isEmpty());
		assertEquals(1, pointSet.size());
		pointSet.insert(new Point2D(0.1, 0.1));
		assertEquals(1, pointSet.size());
		pointSet.insert(new Point2D(0.2, 0.2));
		assertEquals(2, pointSet.size());
	}

	@Test
	public void range() {
		Point2D a = new Point2D(0.1, 0.1);
		Point2D b = new Point2D(0.2, 0.2);
		Point2D c = new Point2D(0.3, 0.3);
		pointSet.insert(a);
		pointSet.insert(b);
		pointSet.insert(c);
		checkRange(pointSet.range(new RectHV(0.1, 0.1, 0.2, 0.2)), a, b);
		checkRange(pointSet.range(new RectHV(0.11, 0.11, 0.5, 0.19)));
	}

	@Test
	public void nearest() {
		Point2D a = new Point2D(0.1, 0.1);
		Point2D b = new Point2D(0.2, 0.2);
		Point2D c = new Point2D(0.3, 0.3);
		pointSet.insert(a);
		pointSet.insert(b);
		pointSet.insert(c);

		assertEquals(a, pointSet.nearest(new Point2D(0.11, 0.11)));
		assertEquals(b, pointSet.nearest(new Point2D(0.21, 0.1)));
	}

	private void checkRange(Iterable<Point2D> ps, Point2D... point2ds) {
		SortedSet<Point2D> pss = new TreeSet<>();
		for (Point2D p : ps)
			pss.add(p);
		assertArrayEquals(point2ds, pss.toArray());
	}
}
