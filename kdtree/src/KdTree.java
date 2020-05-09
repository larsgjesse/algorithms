import java.awt.Color;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	private static class Node {
		final Point2D point;
		public Node left;
		public Node right;

		public Node(Point2D p) {
			point = p;
		}
	}

	private Node root;
	private int size = 0;

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void insert(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		if (root == null) {
			root = new Node(p);
			size += 1;
			return;
		}
		boolean isX = true;
		Node n = root;
		Node next = null;
		int compare;
		do {
			compare = isX ? Double.compare(p.x(), n.point.x()) : Double.compare(p.y(), n.point.y());

			next = null;
			if (compare < 0) {
				next = n.left;
			} else if (compare > 0) {
				next = n.right;
			} else if (n.point.equals(p)) {
				return;
			} else {
				next = n.left;
			}
			isX = !isX;
			if (next != null)
				n = next;
		} while (next != null);
		if (compare <= 0) {
			n.left = new Node(p);
		} else {
			n.right = new Node(p);
		}
		size += 1;
	}

	public boolean contains(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		boolean isX = true;
		Node n = root;
		while (n != null) {
			int compare = isX ? Double.compare(p.x(), n.point.x()) : Double.compare(p.y(), n.point.y());

			if (compare < 0) {
				n = n.left;
			} else if (compare > 0) {
				n = n.right;
			} else {
				if (n.point.equals(p))
					return true;
				n = n.left;
			}
			isX = !isX;
		}
		return false;
	}

	public void draw() {
		draw(new RectHV(0.0, 0.0, 1.0, 1.0), root, true);
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new IllegalArgumentException();
		Bag<Point2D> result = new Bag<>();
		searchRange(result, rect, root, true);
		return result;
	}

	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		if (root == null)
			return null;
		return searchNearest(p, root, new RectHV(0.0, 0.0, 1.0, 1.0), null, true).candidate;
	}

	private void searchRange(Bag<Point2D> points, RectHV rect, Node n, boolean isX) {
		if (n == null)
			return;
		if (rect.contains(n.point)) {
			points.add(n.point);
		}
		int compareLeft = isX ? Double.compare(n.point.x(), rect.xmin()) : Double.compare(n.point.y(), rect.ymin());
		if (compareLeft >= 0)
			searchRange(points, rect, n.left, !isX);
		int compareRight = isX ? Double.compare(n.point.x(), rect.xmax()) : Double.compare(n.point.y(), rect.ymax());
		if (compareRight <= 0)
			searchRange(points, rect, n.right, !isX);
	}

	private static class Candidate {
		public final double dist;
		public final Point2D candidate;

		public Candidate(double dist, Point2D p) {
			this.candidate = p;
			this.dist = dist;
		}
	}

	private Candidate searchNearest(Point2D p, Node n, RectHV rect, Candidate best, boolean isX) {
		Candidate me = new Candidate(p.distanceSquaredTo(n.point), n.point);
		if (best == null || me.dist < best.dist)
			best = me;
		int compare = isX ? Double.compare(p.x(), n.point.x()) : Double.compare(p.y(), n.point.y());

		RectHV[] splits = splitRect(rect, n.point, isX);
		RectHV left = splits[0];
		RectHV right = splits[1];
		if (compare < 0) {
			if (n.left != null && left.distanceSquaredTo(p) < best.dist) {
				best = searchNearest(p, n.left, left, best, !isX);
			}
			if (n.right != null && right.distanceSquaredTo(p) < best.dist) {
				best = searchNearest(p, n.right, right, best, !isX);
			}
		} else {
			if (n.right != null && right.distanceSquaredTo(p) < best.dist) {
				best = searchNearest(p, n.right, right, best, !isX);
			}
			if (n.left != null && left.distanceSquaredTo(p) < best.dist) {
				best = searchNearest(p, n.left, left, best, !isX);
			}
		}
		return best;
	}

	private RectHV[] splitRect(RectHV rect, Point2D p, boolean isX) {
		RectHV[] result = new RectHV[2];
		result[0] = isX ? new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax())
				: new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());

		result[1] = isX ? new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax())
				: new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
		return result;
	}

	private void draw(RectHV rect, Node n, boolean isX) {
		if (n == null)
			return;
		double x1 = n.point.x();
		double x2 = x1;
		double y1 = n.point.y();
		double y2 = y1;
		if (isX) {
			y1 = rect.ymin();
			y2 = rect.ymax();
		} else {
			x1 = rect.xmin();
			x2 = rect.xmax();
		}
		StdDraw.setPenColor(isX ? Color.RED : Color.BLUE);
		StdDraw.line(x1, y1, x2, y2);
		RectHV[] splits = splitRect(rect, n.point, isX);
		draw(splits[0], n.left, !isX);
		draw(splits[1], n.right, !isX);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.01);
		StdDraw.point(n.point.x(), n.point.y());
		StdDraw.setPenRadius();
	}

}
