import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Digraph;

public class SAPTest {

	SAP sap;

	@Before
	public void before() {
		Digraph G = new Digraph(13);
		G.addEdge(7, 3);
		G.addEdge(8, 3);
		G.addEdge(3, 1);
		G.addEdge(4, 1);
		G.addEdge(5, 1);
		G.addEdge(9, 5);
		G.addEdge(10, 5);
		G.addEdge(11, 10);
		G.addEdge(12, 10);
		G.addEdge(1, 0);
		G.addEdge(2, 0);

		sap = new SAP(G);
	}

	@Test
	public void digraph1() {
		assertEquals(4, sap.length(3, 11));
		assertEquals(1, sap.ancestor(3, 11));
		assertEquals(3, sap.length(9, 12));
		assertEquals(5, sap.ancestor(9, 12));
		assertEquals(4, sap.length(7, 2));
		assertEquals(0, sap.ancestor(7, 2));
		assertEquals(-1, sap.length(1, 6));
		assertEquals(-1, sap.ancestor(1, 6));
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeArg() {
		sap.ancestor(-1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void TooLargeIndex() {
		sap.ancestor(13, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullArg() {
		sap.ancestor(null, null);
	}
}
