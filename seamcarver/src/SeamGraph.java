import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;

public class SeamGraph {
	private Picture picture;

	public SeamGraph(Picture picture) {
		this.picture = picture;
	}

	public int[] seam(boolean horizontal) {
		final int seamLength = horizontal ? picture.width() : picture.height();
		final int weightLength = horizontal ? picture.height() : picture.width();
		int[][] edgeTo = new int[seamLength][weightLength];
		double[] weights = new double[weightLength];
		double[] newWeights = new double[weightLength];
		for (int i = 0; i < seamLength; i++) {
			double[] energy = energy(i, !horizontal);
			for (int j = 0; j < weightLength; j++) {
				edgeTo[i][j] = relax(weights, j, energy[j], newWeights);
			}
			double[] swap = weights;
			weights = newWeights;
			newWeights = swap;
		}
		return minPath(edgeTo, weights);
	}

	private static int[] minPath(int[][] edgeTo, double[] weights) {
		double minW = weights[0];
		int minP = 0;
		for (int i = 1; i < weights.length; i++) {
			if (weights[i] < minW) {
				minP = i;
				minW = weights[i];
			}
		}
		int[] path = new int[edgeTo.length];
		for (int i = path.length - 1; i >= 0; i--) {
			path[i] = minP;
			minP = edgeTo[i][minP];
		}
		return path;
	}

	private static int relax(double[] weights, int j, double e, double[] newWeights) {
		double w = (j <= 0) ? Double.POSITIVE_INFINITY : e + weights[j - 1];
		double w0 = e + weights[j];
		double w1 = (j >= weights.length - 1) ? Double.POSITIVE_INFINITY : e + weights[j + 1];
		int d = -1;
		if (w0 < w) {
			d = 0;
			w = w0;
		}
		if (w1 < w) {
			d = 1;
			w = w1;
		}
		newWeights[j] = w;
		return j + d;
	}

	private double[] energy(int index, boolean horizontal) {
		double[] result = new double[horizontal ? picture.width() : picture.height()];
		if (index == 0 || index == (horizontal ? picture.height() : picture.width()) - 1) {
			Arrays.fill(result, 1000.0);
			return result;
		}
		result[0] = 1000.0;
		result[result.length - 1] = 1000.0;
		for (int j = 1; j < result.length - 1; j++) {
			if (horizontal)
				result[j] = energyUnchecked(j, index);
			else
				result[j] = energyUnchecked(index, j);
		}
		return result;
	}

	double energyUnchecked(int x, int y) {
		int x1rgb = picture.getRGB(x - 1, y);
		int x2rgb = picture.getRGB(x + 1, y);
		int y1rgb = picture.getRGB(x, y - 1);
		int y2rgb = picture.getRGB(x, y + 1);
		int rx = ((x1rgb >> 16) & 0xff) - ((x2rgb >> 16) & 0xff);
		int gx = ((x1rgb >> 8) & 0xff) - ((x2rgb >> 8) & 0xff);
		int bx = (x1rgb & 0xff) - (x2rgb & 0xff);
		int ry = ((y1rgb >> 16) & 0xff) - ((y2rgb >> 16) & 0xff);
		int gy = ((y1rgb >> 8) & 0xff) - ((y2rgb >> 8) & 0xff);
		int by = (y1rgb & 0xff) - (y2rgb & 0xff);
		int dx2 = rx * rx + gx * gx + bx * bx;
		int dy2 = ry * ry + gy * gy + by * by;
		return Math.sqrt(dx2 + dy2);
	}
}
