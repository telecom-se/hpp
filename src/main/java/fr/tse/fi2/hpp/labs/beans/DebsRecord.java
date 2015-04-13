package fr.tse.fi2.hpp.labs.beans;

/**
 * Immutable Bean that maps (with almost no processing) a CSV record. The only
 * processing is the pickup/dropoff dates being transformed into unix timestamps.
 * 
 * <pre>
 * medallion	an md5sum of the identifier of the taxi - vehicle bound
 * hack_license	an md5sum of the identifier for the taxi license
 * pickup_datetime	time when the passenger(s) were picked up
 * dropoff_datetime	time when the passenger(s) were dropped off
 * trip_time_in_secs	duration of the trip
 * trip_distance	trip distance in miles
 * pickup_longitude	longitude coordinate of the pickup location
 * pickup_latitude	latitude coordinate of the pickup location
 * dropoff_longitude	longitude coordinate of the drop-off location
 * dropoff_latitude	latitude coordinate of the drop-off location
 * payment_type	the payment method - credit card or cash
 * fare_amount	fare amount in dollars
 * surcharge	surcharge in dollars
 * mta_tax	    tax in dollars
 * tip_amount	tip in dollars
 * tolls_amount	bridge and tunnel tolls in dollars
 * total_amount	total paid amount in dollars
 * 
 * </pre>
 * 
 * @author Julien
 */
public class DebsRecord {

	/** an md5sum of the identifier of the taxi - vehicle bound. */
	final String medallion;

	/** an md5sum of the identifier for the taxi license. */
	final String hack_license;

	/** time when the passenger(s) were picked up. */
	final long pickup_datetime;

	/** time when the passenger(s) were dropped off. */
	final long dropoff_datetime;

	/** duration of the trip. */
	final long trip_time_in_secs;

	/** trip distance in miles. */
	final float trip_distance;

	/** longitude coordinate of the pickup location. */
	final float pickup_longitude;

	/** latitude coordinate of the pickup location. */
	final float pickup_latitude;

	/** longitude coordinate of the drop-off location. */
	final float dropoff_longitude;

	/** latitude coordinate of the drop-off location. */
	final float dropoff_latitude;

	/** the payment method - credit card or cash. */
	final String payment_type;

	/** fare amount in dollars. */
	final float fare_amount;

	/** surcharge in dollars. */
	final float surcharge;

	/** tax in dollars. */
	final float mta;

	/** tip in dollars. */
	final float tip_amount;

	/** bridge and tunnel tolls in dollars. */
	final float tolls_amount;
	/** total paid amount in dollars **/
	final float total_amount;
	/**
	 * Indicate if this is the end of the event stream
	 */
	final private boolean poisonPill;

	/**
	 * Instantiates a new debs record.
	 * 
	 * @param medallion
	 *            the medallion
	 * @param hack_license
	 *            the hack_license
	 * @param pickup_datetime
	 *            the pickup_datetime
	 * @param dropoff_datetime
	 *            the dropoff_datetime
	 * @param trip_time_in_secs
	 *            the trip_time_in_secs
	 * @param trip_distance
	 *            the trip_distance
	 * @param pickup_longitude
	 *            the pickup_longitude
	 * @param pickup_latitude
	 *            the pickup_latitude
	 * @param dropoff_longitude
	 *            the dropoff_longitude
	 * @param dropoff_latitude
	 *            the dropoff_latitude
	 * @param payment_type
	 *            the payment_type
	 * @param fare_amount
	 *            the fare_amount
	 * @param surcharge
	 *            the surcharge
	 * @param mta
	 *            the mta
	 * @param tip_amount
	 *            the tip_amount
	 * @param tolls_amount
	 *            the tolls_amount
	 */
	public DebsRecord(String medallion, String hack_license,
			long pickup_datetime, long dropoff_datetime,
			long trip_time_in_secs, float trip_distance,
			float pickup_longitude, float pickup_latitude,
			float dropoff_longitude, float dropoff_latitude,
			String payment_type, float fare_amount, float surcharge, float mta,
			float tip_amount, float tolls_amount, float total_amount,
			boolean poisonPill) {
		super();
		this.medallion = medallion;
		this.hack_license = hack_license;
		this.pickup_datetime = pickup_datetime;
		this.dropoff_datetime = dropoff_datetime;
		this.trip_time_in_secs = trip_time_in_secs;
		this.trip_distance = trip_distance;
		this.pickup_longitude = pickup_longitude;
		this.pickup_latitude = pickup_latitude;
		this.dropoff_longitude = dropoff_longitude;
		this.dropoff_latitude = dropoff_latitude;
		this.payment_type = payment_type;
		this.fare_amount = fare_amount;
		this.surcharge = surcharge;
		this.mta = mta;
		this.tip_amount = tip_amount;
		this.tolls_amount = tolls_amount;
		this.total_amount = total_amount;
		this.poisonPill = poisonPill;
	}

	/**
	 * @return the medallion
	 */
	public final String getMedallion() {
		return medallion;
	}

	/**
	 * @return the hack_license
	 */
	public final String getHack_license() {
		return hack_license;
	}

	/**
	 * @return the pickup_datetime
	 */
	public final long getPickup_datetime() {
		return pickup_datetime;
	}

	/**
	 * @return the dropoff_datetime
	 */
	public final long getDropoff_datetime() {
		return dropoff_datetime;
	}

	/**
	 * @return the trip_time_in_secs
	 */
	public final long getTrip_time_in_secs() {
		return trip_time_in_secs;
	}

	/**
	 * @return the trip_distance
	 */
	public final float getTrip_distance() {
		return trip_distance;
	}

	/**
	 * @return the pickup_longitude
	 */
	public final float getPickup_longitude() {
		return pickup_longitude;
	}

	/**
	 * @return the pickup_latitude
	 */
	public final float getPickup_latitude() {
		return pickup_latitude;
	}

	/**
	 * @return the dropoff_longitude
	 */
	public final float getDropoff_longitude() {
		return dropoff_longitude;
	}

	/**
	 * @return the dropoff_latitude
	 */
	public final float getDropoff_latitude() {
		return dropoff_latitude;
	}

	/**
	 * @return the payment_type
	 */
	public final String getPayment_type() {
		return payment_type;
	}

	/**
	 * @return the fare_amount
	 */
	public final float getFare_amount() {
		return fare_amount;
	}

	/**
	 * @return the surcharge
	 */
	public final float getSurcharge() {
		return surcharge;
	}

	/**
	 * @return the mta
	 */
	public final float getMta() {
		return mta;
	}

	/**
	 * @return the tip_amount
	 */
	public final float getTip_amount() {
		return tip_amount;
	}

	/**
	 * @return the tolls_amount
	 */
	public final float getTolls_amount() {
		return tolls_amount;
	}

	/**
	 * 
	 * @return <code>true</code> if the event stream has finished
	 */
	public boolean isPoisonPill() {

		return poisonPill;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (isPoisonPill()) {
			return "Poison Pill";
		} else {
			return "DebsRecord [medallion=" + medallion + ", hack_license="
					+ hack_license + ", pickup_datetime=" + pickup_datetime
					+ ", dropoff_datetime=" + dropoff_datetime
					+ ", trip_time_in_secs=" + trip_time_in_secs
					+ ", trip_distance=" + trip_distance
					+ ", pickup_longitude=" + pickup_longitude
					+ ", pickup_latitude=" + pickup_latitude
					+ ", dropoff_longitude=" + dropoff_longitude
					+ ", dropoff_latitude=" + dropoff_latitude
					+ ", payment_type=" + payment_type + ", fare_amount="
					+ fare_amount + ", surcharge=" + surcharge + ", mta=" + mta
					+ ", tip_amount=" + tip_amount + ", tolls_amount="
					+ tolls_amount + ", poisonPill=" + poisonPill + "]";
		}
	}

}
