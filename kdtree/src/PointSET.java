import java.util.TreeSet;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
	private final TreeSet<Point2D> points;

	public PointSET() {
		points = new TreeSet<>();
	}

	public boolean isEmpty() {
		return points.isEmpty();
	}

	public int size() {
		return points.size();
	}

	public void insert(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		points.add(p);
	}

	public boolean contains(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		return points.contains(p);
	}

	public void draw() {
		for (Point2D point : points)
			StdDraw.point(point.x(), point.y());
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new IllegalArgumentException();
		Bag<Point2D> result = new Bag<>();
		for (Point2D point : points) {
			if (point.x() >= rect.xmin() && point.x() <= rect.xmax() && point.y() >= rect.ymin()
					&& point.y() <= rect.ymax())
				result.add(point);
		}
		return result;
	}

	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		Point2D candidate = null;
		double dist = 0.0;
		for (Point2D point : points) {
			double d = point.distanceSquaredTo(p);
			if (candidate == null || d < dist) {
				dist = d;
				candidate = point;
			}
		}
		return candidate;
	}
}
