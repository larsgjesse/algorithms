import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

public class BaseballEliminationTest {

	static BaseballElimination teams4 = new BaseballElimination("teams4.txt");
	static BaseballElimination teams5 = new BaseballElimination("teams5.txt");

	@Test
	public void t4() {
		assertEquals(4, teams4.numberOfTeams());
		assertEquals(83, teams4.wins("Atlanta"));
		assertEquals(77, teams4.wins("Montreal"));
		assertEquals(79, teams4.losses("Philadelphia"));
		assertEquals(6, teams4.remaining("New_York"));
		assertEquals(2, teams4.against("Philadelphia", "Montreal"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidTeam() {
		teams4.losses("Ikast");
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidTeam2() {
		teams4.against("Philadelphia", "Ikast");
	}

	@Test
	public void trivialElimination() {
		assertTrue(teams4.isEliminated("Montreal"));
		Iterator<String> it = teams4.certificateOfElimination("Montreal").iterator();
		assertTrue(it.hasNext());
		assertEquals("Atlanta", it.next());
		assertFalse(it.hasNext());
	}

	@Test
	public void flowElimination4() {
		assertFalse(teams4.isEliminated("Atlanta"));
		assertFalse(teams4.isEliminated("New_York"));
		assertTrue(teams4.isEliminated("Philadelphia"));
		Iterator<String> it = teams4.certificateOfElimination("Philadelphia").iterator();
		assertTrue(it.hasNext());
		assertEquals("Atlanta", it.next());
		assertTrue(it.hasNext());
		assertEquals("New_York", it.next());
		assertFalse(it.hasNext());
	}

	@Test
	public void flowElimination5() {
		assertFalse(teams5.isEliminated("New_York"));
		assertFalse(teams5.isEliminated("Baltimore"));
		assertFalse(teams5.isEliminated("Boston"));
		assertFalse(teams5.isEliminated("Toronto"));
		assertNull(teams5.certificateOfElimination("Toronto"));
		assertTrue(teams5.isEliminated("Detroit"));
		Iterator<String> it = teams5.certificateOfElimination("Detroit").iterator();
		assertTrue(it.hasNext());
		assertEquals("New_York", it.next());
		assertTrue(it.hasNext());
		assertEquals("Baltimore", it.next());
		assertTrue(it.hasNext());
		assertEquals("Boston", it.next());
		assertTrue(it.hasNext());
		assertEquals("Toronto", it.next());
		assertFalse(it.hasNext());
	}
}
