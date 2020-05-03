import java.util.Collections;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {
	private final Digraph G;

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		this.G = new Digraph(G);
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		return length(Collections.singleton(v), Collections.singleton(w));
	}

	// a common ancestor of v and w that participates in a shortest ancestral path;
	// -1 if no such path
	public int ancestor(int v, int w) {
		return ancestor(Collections.singleton(v), Collections.singleton(w));
	}

	// length of shortest ancestral path between any vertex in v and any vertex in
	// w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		Ancestor a = commonAncestorPath(v, w);
		if (a == null)
			return -1;
		return a.pathA.distTo(a.ancestor) + a.pathB.distTo(a.ancestor);
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such
	// path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		Ancestor a = commonAncestorPath(v, w);
		if (a == null)
			return -1;
		return a.ancestor;
	}

	private static class Ancestor {
		public final int ancestor;
		public final BreadthFirstDirectedPaths pathA;
		public final BreadthFirstDirectedPaths pathB;

		public Ancestor(int ancestor, BreadthFirstDirectedPaths pathA, BreadthFirstDirectedPaths pathB) {
			this.ancestor = ancestor;
			this.pathA = pathA;
			this.pathB = pathB;
		}
	}

	private Ancestor commonAncestorPath(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null) {
			throw new IllegalArgumentException();
		}
		for (Integer i : v) {
			if (i == null || i < 0 || i >= G.V())
				throw new IllegalArgumentException();
		}
		for (Integer i : w) {
			if (i == null || i < 0 || i >= G.V())
				throw new IllegalArgumentException();
		}
		Ancestor result = null;
		int minDist = Integer.MAX_VALUE;
		BreadthFirstDirectedPaths dfsv = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths dfsw = new BreadthFirstDirectedPaths(G, w);

		for (int i = 0; i < G.V(); i++) {
			if (dfsv.hasPathTo(i) && dfsw.hasPathTo(i)) {
				int dist = dfsv.distTo(i) + dfsw.distTo(i);
				if (dist < minDist) {
					result = new Ancestor(i, dfsv, dfsw);
					minDist = dist;
				}
			}
		}

		return result;
	}
}
