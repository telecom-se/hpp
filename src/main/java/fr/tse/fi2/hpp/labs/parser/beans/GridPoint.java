package fr.tse.fi2.hpp.labs.parser.beans;

public class GridPoint {
	/**
	 * Coordinates on the Grid
	 */
	int x, y;

	public GridPoint(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
