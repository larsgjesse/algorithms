import java.util.ArrayList;
import java.util.TreeMap;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

	private static class Team {
		public String name;
		public int wins;
		public int losses;
		public int remaining;
		public int[] against;
		public boolean isEliminated;
		public int[] certificate;
	}

	private final TreeMap<String, Integer> teamIDs;
	private final Team[] teams;

	public BaseballElimination(String filename) {
		In input = new In(filename);
		int n = input.readInt();
		teams = new Team[n];
		teamIDs = new TreeMap<>();
		readTeams(input, n);
		findEliminated();
	}

	public int numberOfTeams() {
		return teams.length;
	}

	public Iterable<String> teams() {
		Bag<String> result = new Bag<>();
		for (Team team : teams) {
			result.add(team.name);
		}
		return result;
	}

	public int wins(String team) {
		return getTeamChecked(team).wins;
	}

	public int losses(String team) {
		return getTeamChecked(team).losses;
	}

	public int remaining(String team) {
		return getTeamChecked(team).remaining;
	}

	public int against(String team1, String team2) {
		Team t1 = getTeamChecked(team1);
		Integer id2 = teamIDs.get(team2);
		if (id2 == null)
			throw new IllegalArgumentException();
		return t1.against[id2];
	}

	public boolean isEliminated(String team) {
		return getTeamChecked(team).isEliminated;
	}

	public Iterable<String> certificateOfElimination(String team) {
		Team t = getTeamChecked(team);
		Queue<String> result = null;
		if (t.isEliminated) {
			result = new Queue<>();
			for (int i : t.certificate) {
				result.enqueue(teams[i].name);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		BaseballElimination division = new BaseballElimination(args[0]);
		for (String team : division.teams()) {
			if (division.isEliminated(team)) {
				StdOut.print(team + " is eliminated by the subset R = { ");
				for (String t : division.certificateOfElimination(team)) {
					StdOut.print(t + " ");
				}
				StdOut.println("}");
			} else {
				StdOut.println(team + " is not eliminated");
			}
		}
	}

	private void readTeams(In input, int n) {
		for (int i = 0; i < n; i++) {
			Team team = new Team();
			team.name = input.readString();
			team.wins = input.readInt();
			team.losses = input.readInt();
			team.remaining = input.readInt();

			team.against = new int[n];
			for (int j = 0; j < n; j++) {
				team.against[j] = input.readInt();
			}
			teamIDs.put(team.name, i);
			teams[i] = team;
		}
	}

	private void findEliminated() {
		int maxWins = 0;
		int maxTeam = -1;
		for (int i = 0; i < teams.length; i++) {
			if (teams[i].wins > maxWins) {
				maxWins = teams[i].wins;
				maxTeam = i;
			}
		}
		for (int i = 0; i < teams.length; i++) {
			Team team = teams[i];
			if (team.wins + team.remaining < maxWins) {
				team.isEliminated = true;
				team.certificate = new int[] { maxTeam };
			} else if (teams.length >= 3) {
				eliminationByMaxFlow(i);
			}
		}
	}

	private void eliminationByMaxFlow(int t) {
		Team team = teams[t];
		FlowNetwork fn = buildNetwork(t);
		FordFulkerson ff = new FordFulkerson(fn, 0, fn.V() - 1);
		ArrayList<Integer> teamsInMincut = new ArrayList<>();
		for (int i = 0; i < teams.length; i++) {
			if (i == t)
				continue;
			if (ff.inCut(teamVertex(t, i)))
				teamsInMincut.add(i);
		}

		team.isEliminated = !teamsInMincut.isEmpty();
		if (team.isEliminated) {
			team.certificate = teamsInMincut.stream().mapToInt(i -> i).toArray();
		}
	}

	private FlowNetwork buildNetwork(int t) {
		int n = teams.length - 1;
		int vertices = 1 + n * (n - 1) / 2 + n + 1;
		FlowNetwork fn = new FlowNetwork(vertices);
		Team team = teams[t];
		int wrx = team.wins + team.remaining;
		int gv = 1;
		for (int i = 0; i < teams.length; i++) {
			if (i == t)
				continue;
			int tvi = teamVertex(t, i);
			for (int j = i + 1; j < teams.length; j++) {
				if (j == t)
					continue;
				if (teams[i].against[j] > 0) {
					int tvj = teamVertex(t, j);
					fn.addEdge(new FlowEdge(0, gv, teams[i].against[j]));
					fn.addEdge(new FlowEdge(gv, tvi, Double.POSITIVE_INFINITY));
					fn.addEdge(new FlowEdge(gv, tvj, Double.POSITIVE_INFINITY));
				}
				gv += 1;
			}
			fn.addEdge(new FlowEdge(tvi, fn.V() - 1, wrx - teams[i].wins));
		}
		return fn;
	}

	private int teamVertex(int t, int o1) {
		if (o1 >= t)
			o1 -= 1;
		return 1 + (teams.length - 1) * (teams.length - 2) / 2 + o1;
	}

	private Team getTeamChecked(String name) {
		Integer id = teamIDs.get(name);
		if (id == null)
			throw new IllegalArgumentException();
		return teams[id];
	}
}
