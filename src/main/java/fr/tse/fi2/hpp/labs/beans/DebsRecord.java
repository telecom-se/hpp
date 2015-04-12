package fr.tse.fi2.hpp.labs.beans;

/**
 * Beans that maps (with no processing) a CSV record.
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
	String medallion;

	/** an md5sum of the identifier for the taxi license. */
	String hack_license;

	/** time when the passenger(s) were picked up. */
	long pickup_datetime;

	/** time when the passenger(s) were dropped off. */
	long dropoff_datetime;

	/** duration of the trip. */
	long trip_time_in_secs;

	/** trip distance in miles. */
	float trip_distance;

	/** longitude coordinate of the pickup location. */
	float pickup_longitude;

	/** latitude coordinate of the pickup location. */
	float pickup_latitude;

	/** longitude coordinate of the drop-off location. */
	float dropoff_longitude;

	/** latitude coordinate of the drop-off location. */
	float dropoff_latitude;

	/** the payment method - credit card or cash. */
	String payment_type;

	/** fare amount in dollars. */
	float fare_amount;

	/** surcharge in dollars. */
	float surcharge;

	/** tax in dollars. */
	float mta;

	/** tip in dollars. */
	float tip_amount;

	/** bridge and tunnel tolls in dollars. */
	float tolls_amount;

	/**
	 * Empty constructor.
	 */
	public DebsRecord() {
		super();
	}

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
			float tip_amount, float tolls_amount) {
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
	}

	/**
	 * @return the medallion
	 */
	public String getMedallion() {
		return medallion;
	}

	/**
	 * @param medallion
	 *            the medallion to set
	 */
	public void setMedallion(String medallion) {
		this.medallion = medallion;
	}

	/**
	 * @return the hack_license
	 */
	public String getHack_license() {
		return hack_license;
	}

	/**
	 * @param hack_license
	 *            the hack_license to set
	 */
	public void setHack_license(String hack_license) {
		this.hack_license = hack_license;
	}

	/**
	 * @return the pickup_datetime
	 */
	public long getPickup_datetime() {
		return pickup_datetime;
	}

	/**
	 * @param pickup_datetime
	 *            the pickup_datetime to set
	 */
	public void setPickup_datetime(long pickup_datetime) {
		this.pickup_datetime = pickup_datetime;
	}

	/**
	 * @return the dropoff_datetime
	 */
	public long getDropoff_datetime() {
		return dropoff_datetime;
	}

	/**
	 * @param dropoff_datetime
	 *            the dropoff_datetime to set
	 */
	public void setDropoff_datetime(long dropoff_datetime) {
		this.dropoff_datetime = dropoff_datetime;
	}

	/**
	 * @return the trip_time_in_secs
	 */
	public long getTrip_time_in_secs() {
		return trip_time_in_secs;
	}

	/**
	 * @param trip_time_in_secs
	 *            the trip_time_in_secs to set
	 */
	public void setTrip_time_in_secs(long trip_time_in_secs) {
		this.trip_time_in_secs = trip_time_in_secs;
	}

	/**
	 * @return the trip_distance
	 */
	public float getTrip_distance() {
		return trip_distance;
	}

	/**
	 * @param trip_distance
	 *            the trip_distance to set
	 */
	public void setTrip_distance(float trip_distance) {
		this.trip_distance = trip_distance;
	}

	/**
	 * @return the pickup_longitude
	 */
	public float getPickup_longitude() {
		return pickup_longitude;
	}

	/**
	 * @param pickup_longitude
	 *            the pickup_longitude to set
	 */
	public void setPickup_longitude(float pickup_longitude) {
		this.pickup_longitude = pickup_longitude;
	}

	/**
	 * @return the pickup_latitude
	 */
	public float getPickup_latitude() {
		return pickup_latitude;
	}

	/**
	 * @param pickup_latitude
	 *            the pickup_latitude to set
	 */
	public void setPickup_latitude(float pickup_latitude) {
		this.pickup_latitude = pickup_latitude;
	}

	/**
	 * @return the dropoff_longitude
	 */
	public float getDropoff_longitude() {
		return dropoff_longitude;
	}

	/**
	 * @param dropoff_longitude
	 *            the dropoff_longitude to set
	 */
	public void setDropoff_longitude(float dropoff_longitude) {
		this.dropoff_longitude = dropoff_longitude;
	}

	/**
	 * @return the dropoff_latitude
	 */
	public float getDropoff_latitude() {
		return dropoff_latitude;
	}

	/**
	 * @param dropoff_latitude
	 *            the dropoff_latitude to set
	 */
	public void setDropoff_latitude(float dropoff_latitude) {
		this.dropoff_latitude = dropoff_latitude;
	}

	/**
	 * @return the payment_type
	 */
	public String getPayment_type() {
		return payment_type;
	}

	/**
	 * @param payment_type
	 *            the payment_type to set
	 */
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	/**
	 * @return the fare_amount
	 */
	public float getFare_amount() {
		return fare_amount;
	}

	/**
	 * @param fare_amount
	 *            the fare_amount to set
	 */
	public void setFare_amount(float fare_amount) {
		this.fare_amount = fare_amount;
	}

	/**
	 * @return the surcharge
	 */
	public float getSurcharge() {
		return surcharge;
	}

	/**
	 * @param surcharge
	 *            the surcharge to set
	 */
	public void setSurcharge(float surcharge) {
		this.surcharge = surcharge;
	}

	/**
	 * @return the mta
	 */
	public float getMta() {
		return mta;
	}

	/**
	 * @param mta
	 *            the mta to set
	 */
	public void setMta(float mta) {
		this.mta = mta;
	}

	/**
	 * @return the tip_amount
	 */
	public float getTip_amount() {
		return tip_amount;
	}

	/**
	 * @param tip_amount
	 *            the tip_amount to set
	 */
	public void setTip_amount(float tip_amount) {
		this.tip_amount = tip_amount;
	}

	/**
	 * @return the tolls_amount
	 */
	public float getTolls_amount() {
		return tolls_amount;
	}

	/**
	 * @param tolls_amount
	 *            the tolls_amount to set
	 */
	public void setTolls_amount(float tolls_amount) {
		this.tolls_amount = tolls_amount;
	}

}
