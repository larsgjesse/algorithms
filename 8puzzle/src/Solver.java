import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

	private static class SearchNode implements Comparable<SearchNode> {

		private final Board board;
		private final int move;
		private final SearchNode previous;

		public SearchNode(Board board, int move, SearchNode previous) {
			this.board = board;
			this.move = move;
			this.previous = previous;
		}

		@Override
		public int compareTo(SearchNode that) {
			return Integer.compare(move + board.manhattan(), that.move + that.board.manhattan());
		}
	}

	private MinPQ<SearchNode> queue;
	private MinPQ<SearchNode> twinQueue;
	private SearchNode last;

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		if (initial == null) {
			throw new IllegalArgumentException();
		}
		queue = new MinPQ<>();
		twinQueue = new MinPQ<>();
		queue.insert(new SearchNode(initial, 0, null));
		twinQueue.insert(new SearchNode(initial.twin(), 0, null));
		solve();
		queue = null;
		twinQueue = null;
	}

	// is the initial board solvable? (see below)
	public boolean isSolvable() {
		return last != null;
	}

	// min number of moves to solve initial board
	public int moves() {
		return (last != null) ? last.move : -1;
	}

	// sequence of boards in a shortest solution
	public Iterable<Board> solution() {
		Stack<Board> result = new Stack<>();
		SearchNode sn = last;
		while (sn != null) {
			result.push(sn.board);
			sn = sn.previous;
		}
		return result;
	}

	// test client (see below)
	public static void main(String[] args) {
	}

	private void solve() {
		while (true) {
			if (populate(queue)) {
				return;
			}
			if (populate(twinQueue)) {
				last = null;
				return;
			}
		}
	}

	private boolean populate(MinPQ<SearchNode> queue) {
		SearchNode s = queue.delMin();
		if (s.board.isGoal()) {
			last = s;
			return true;
		}
		for (Board b : s.board.neighbors()) {
			if (s.previous != null && b.equals(s.previous.board))
				continue;
			queue.insert(new SearchNode(b, s.move + 1, s));
		}
		return false;
	}
}
