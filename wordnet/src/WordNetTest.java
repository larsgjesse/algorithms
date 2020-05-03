import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WordNetTest {
	static WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");

	@Test
	public void isNoun() {
		assertTrue(wordnet.isNoun("AND_gate"));
		assertFalse(wordnet.isNoun("lars"));
	}

	@Test
	public void distance() {
		assertEquals(2, wordnet.distance("increase", "action"));
		assertEquals(1, wordnet.distance("increase", "process"));
		assertEquals(0, wordnet.distance("increase", "increase"));
	}

	@Test
	public void sap() {
		assertEquals("process physical_process", wordnet.sap("increase", "action"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void cyclic() {
		new WordNet("synsets3.txt", "hypernyms3InvalidCycle.txt");
	}

	@Test(expected = IllegalArgumentException.class)
	public void twoRoots() {
		new WordNet("synsets3.txt", "hypernyms3InvalidTwoRoots.txt");
	}
}
