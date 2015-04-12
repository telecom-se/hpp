package fr.tse.fi2.hpp.labs.beans;

public class Route {
	/**
	 * Pickup location (where the route starts)
	 */
	GridPoint pickup;
	/**
	 * Dropoff location (where the route ends)
	 */
	GridPoint dropoff;

	public Route(GridPoint pickup, GridPoint dropoff) {
		super();
		this.pickup = pickup;
		this.dropoff = dropoff;
	}

	public Route() {
		super();
	}

	/**
	 * 
	 * @return Pickup location (where the route starts)
	 */
	public GridPoint getPickup() {
		return pickup;
	}

	/**
	 * 
	 * @param pickup
	 *            location (where the route starts)
	 */
	public void setPickup(GridPoint pickup) {
		this.pickup = pickup;
	}

	/**
	 * 
	 * @return Dropoff location (where the route ends)
	 */
	public GridPoint getDropoff() {
		return dropoff;
	}

	/**
	 * 
	 * @param dropoff
	 *            location (where the route ends)
	 */
	public void setDropoff(GridPoint dropoff) {
		this.dropoff = dropoff;
	}

}
