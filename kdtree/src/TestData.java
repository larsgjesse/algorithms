import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdRandom;

public class TestData {

	public static void main(String[] args) {
		Out data = new Out("points.txt");
		for (int i = 0; i < 1000; i++) {
			double x = StdRandom.uniform();
			double y = StdRandom.uniform();
			data.println(x + " " + y);
		}

	}

}
