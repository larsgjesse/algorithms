import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
	private List<LineSegment> segments;
	private Point[] points;

	public BruteCollinearPoints(Point[] points) {
		if (points == null) {
			throw new IllegalArgumentException();
		}
		for (Point point : points) {
			if (point == null) {
				throw new IllegalArgumentException();
			}
		}
		this.points = new Point[points.length];
		System.arraycopy(points, 0, this.points, 0, points.length);
		segments = findCollinear();
	}

	public int numberOfSegments() {
		return segments.size();
	}

	public LineSegment[] segments() {
		return segments.toArray(new LineSegment[segments.size()]);
	}

	private List<LineSegment> findCollinear() {
		List<LineSegment> result = new ArrayList<LineSegment>();
		Arrays.sort(points);
		for (int i = 0; i < points.length; i++) {
			for (int j = i + 1; j < points.length; j++) {
				double slope1 = points[i].slopeTo(points[j]);
				if (slope1 == Double.NEGATIVE_INFINITY) {
					throw new IllegalArgumentException();
				}
				for (int k = j + 1; k < points.length; k++) {
					double slope2 = points[i].slopeTo(points[k]);
					if (slope2 == Double.NEGATIVE_INFINITY) {
						throw new IllegalArgumentException();
					}
					if (Math.abs(slope1 - slope2) > 1e-6)
						continue;
					for (int m = k + 1; m < points.length; m++) {
						double slope3 = points[i].slopeTo(points[m]);
						if (slope3 == Double.NEGATIVE_INFINITY) {
							throw new IllegalArgumentException();
						}
						if (Math.abs(slope1 - slope3) > 1e-6)
							continue;
						result.add(new LineSegment(points[i], points[m]));
					}
				}
			}
		}
		return result;
	}
}