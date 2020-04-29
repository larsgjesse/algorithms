import java.util.ArrayList;
import java.util.Comparator;

import edu.princeton.cs.algs4.MergeX;

public class FastCollinearPoints {
	private class Segment implements Comparable<Segment> {
		private final Point a;
		private final Point b;

		public Segment(Point a, Point b) {
			this.a = a;
			this.b = b;
		}

		@Override
		public int compareTo(Segment that) {
			int c1 = a.compareTo(that.a);
			if (c1 != 0)
				return c1;
			return b.compareTo(that.b);
		}

	}

	private LineSegment[] segments;

	public FastCollinearPoints(Point[] points) {
		if (points == null) {
			throw new IllegalArgumentException();
		}
		for (Point point : points) {
			if (point == null) {
				throw new IllegalArgumentException();
			}
		}

		ArrayList<Segment> segments = findCollinear(points);
		this.segments = new LineSegment[segments.size()];
		for (int i = 0; i < this.segments.length; i++) {
			this.segments[i] = new LineSegment(segments.get(i).a, segments.get(i).b);
		}
	}

	public int numberOfSegments() {
		return segments.length;
	}

	public LineSegment[] segments() {
		LineSegment[] result = new LineSegment[segments.length];
		System.arraycopy(segments, 0, result, 0, numberOfSegments());
		return result;
	}

	private ArrayList<Segment> findCollinear(Point[] points) {
		ArrayList<Segment> result = new ArrayList<Segment>();
		if (points.length < 4) {
			return result;
		}
		Point[] sortedPoints = new Point[points.length];
		System.arraycopy(points, 0, sortedPoints, 0, points.length);

		for (int i = 0; i < points.length; i++) {
			Point p = points[i];
			Comparator<Point> comp = p.slopeOrder();
			MergeX.sort(sortedPoints, comp);
			double currentSlope = p.slopeTo(sortedPoints[1]);
			if (currentSlope == Double.NEGATIVE_INFINITY) {
				throw new IllegalArgumentException();
			}

			int sequentiallyIdenticalSlopes = 1;
			for (int j = 2; j < sortedPoints.length; j++) {
				double nextSlope = p.slopeTo(sortedPoints[j]);
				if (nextSlope == Double.NEGATIVE_INFINITY) {
					throw new IllegalArgumentException();
				}
				if (Double.compare(nextSlope, currentSlope) == 0
						|| Math.abs((nextSlope - currentSlope) / currentSlope) < 1e-8) {
					sequentiallyIdenticalSlopes += 1;
				} else {
					if (sequentiallyIdenticalSlopes >= 3) {
						foundSegment(p, sortedPoints, j - sequentiallyIdenticalSlopes, j, result);
					}
					sequentiallyIdenticalSlopes = 1;
					currentSlope = nextSlope;
				}
			}
			if (sequentiallyIdenticalSlopes >= 3) {
				foundSegment(p, sortedPoints, sortedPoints.length - sequentiallyIdenticalSlopes, sortedPoints.length,
						result);
			}
		}
		return result;
	}

	private void foundSegment(Point p, Point[] others, int start, int end, ArrayList<Segment> segments) {
		Point max = p;
		Point min = p;
		for (int i = start; i < end; i++) {
			if (others[i].compareTo(min) < 0)
				min = others[i];
			if (others[i].compareTo(max) > 0)
				max = others[i];
		}
		addUnique(min, max, segments);
	}

	// Add a set of points defining a line segment, but only if the same set of
	// points is not already found.
	private void addUnique(Point min, Point max, ArrayList<Segment> segments) {
		Segment s = new Segment(min, max);
		int lo = 0;
		int hi = segments.size() - 1;
		while (lo <= hi) {
			// Key is in a[lo..hi] or not present.
			int mid = lo + (hi - lo) / 2;
			if (s.compareTo(segments.get(mid)) < 0)
				hi = mid - 1;
			else if (s.compareTo(segments.get(mid)) > 0)
				lo = mid + 1;
			else
				return; // Duplicate of existing entry
		}
		segments.add(lo, s);
	}
}
