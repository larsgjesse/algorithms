import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTreeTest {
	KdTree pointSet = new KdTree();

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
	public void insert2() {
		pointSet.insert(new Point2D(0.875, 0.375));
		pointSet.insert(new Point2D(0.0, 0.25));
		pointSet.insert(new Point2D(0.375, 0.0));
		pointSet.insert(new Point2D(0.875, 0.875));
		assertEquals(4, pointSet.size());
	}

	@Test
	public void insert3() {
		pointSet.insert(new Point2D(0.0, 0.5));
		assertEquals(1, pointSet.size());
		pointSet.insert(new Point2D(0.5, 0.25));
		assertEquals(2, pointSet.size());
		pointSet.insert(new Point2D(0.25, 0.0));
		assertEquals(3, pointSet.size());
		pointSet.insert(new Point2D(0.25, 0.75));
		assertEquals(4, pointSet.size());
		pointSet.insert(new Point2D(0.5, 1.0));
		assertEquals(5, pointSet.size());
		pointSet.insert(new Point2D(0.0, 0.25));
		assertEquals(6, pointSet.size());
		pointSet.insert(new Point2D(0.5, 0.0));
		assertEquals(7, pointSet.size());
		pointSet.insert(new Point2D(0.75, 0.0));
		assertEquals(8, pointSet.size());
		pointSet.insert(new Point2D(0.0, 0.25));
		assertEquals(8, pointSet.size());
	}

	@Test
	public void contains() {
		pointSet.insert(new Point2D(0.5, 0.63));
		assertFalse(pointSet.contains(new Point2D(0.5, 0.5)));
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
