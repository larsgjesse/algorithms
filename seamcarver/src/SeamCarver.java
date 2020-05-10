import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
	private Picture picture;
	private int width, height;
	private int[] horizontalSeam;
	private int[] verticalSeam;
	private SeamGraph seamGraph;

	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture) {
		if (picture == null) {
			throw new IllegalArgumentException();
		}
		setPicture(new Picture(picture));
	}

	// current picture
	public Picture picture() {
		return new Picture(picture);
	}

	// width of current picture
	public int width() {
		return width;
	}

	// height of current picture
	public int height() {
		return height;
	}

	// energy of pixel at column x and row y
	public double energy(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			throw new IllegalArgumentException();
		}
		if (x == 0 || x == width - 1 || y == 0 || y == height - 1)
			return 1000.0;
		return seamGraph.energyUnchecked(x, y);
	}

	// sequence of indices for horizontal seam
	public int[] findHorizontalSeam() {
		if (horizontalSeam == null) {
			horizontalSeam = new SeamGraph(picture).seam(true);
		}
		int[] result = new int[horizontalSeam.length];
		System.arraycopy(horizontalSeam, 0, result, 0, result.length);
		return result;
	}

	// sequence of indices for vertical seam
	public int[] findVerticalSeam() {
		if (verticalSeam == null) {
			verticalSeam = new SeamGraph(picture).seam(false);
		}
		int[] result = new int[verticalSeam.length];
		System.arraycopy(verticalSeam, 0, result, 0, result.length);
		return result;
	}

	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam) {
		validateSeam(seam, width, height);
		int[] y = new int[width];
		Picture newPicture = new Picture(width, height - 1);
		for (int i = 0; i < height - 1; i++) {
			for (int j = 0; j < width; j++) {
				if (y[j] == seam[j])
					y[j] += 1;
				newPicture.setRGB(j, i, picture.getRGB(j, y[j]));
				y[j] += 1;
			}
		}
		setPicture(newPicture);
	}

	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam) {
		validateSeam(seam, height, width);
		Picture newPicture = new Picture(width - 1, height);
		for (int i = 0; i < height; i++) {
			int s = seam[i];
			for (int j = 0; j < s; j++) {
				newPicture.setRGB(j, i, picture.getRGB(j, i));
			}
			for (int j = s + 1; j < width; j++) {
				newPicture.setRGB(j - 1, i, picture.getRGB(j, i));
			}
		}
		setPicture(newPicture);
	}

	private void validateSeam(int[] seam, int expectedSeamLength, int expectedSeamRange) {
		if (seam == null || seam.length != expectedSeamLength || expectedSeamRange <= 1) {
			throw new IllegalArgumentException();
		}
		int b = seam[0];
		for (int a : seam) {
			if (a < 0 || a > expectedSeamRange - 1 || Math.abs(a - b) > 1)
				throw new IllegalArgumentException();
			b = a;
		}
	}

	private void setPicture(Picture newPicture) {
		this.picture = newPicture;
		this.width = picture.width();
		this.height = picture.height();
		horizontalSeam = null;
		verticalSeam = null;
		seamGraph = new SeamGraph(picture);
	}
}
