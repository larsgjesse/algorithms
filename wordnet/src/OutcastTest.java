import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OutcastTest {

	static WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");

	@Test
	public void samples() {
		Outcast outcast = new Outcast(wordnet);
		assertEquals("table", outcast.outcast(new String[] { "horse", "zebra", "cat", "bear", "table" }));
		assertEquals("bed", outcast.outcast(
				new String[] { "water", "soda", "bed", "orange_juice", "milk", "apple_juice", "tea", "coffee" }));
		assertEquals("potato", outcast.outcast(new String[] { "apple", "pear", "peach", "banana", "lime", "lemon",
				"blueberry", "strawberry", "mango", "watermelon", "potato" }));
	}
}
