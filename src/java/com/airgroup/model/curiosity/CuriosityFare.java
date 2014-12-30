/**
 * 
 */
package com.airgroup.model.curiosity;

import org.joda.time.DateTime;

import com.airgroup.model.Fare;
import com.airgroup.model.FareType;
import com.airgroup.model.Search;

/**
 * @author linhnd1
 * 
 */
public class CuriosityFare extends Fare<CuriositySegment> {
	public static CuriosityFare newFromTrip(Search search) {
		CuriosityFare fare = new CuriosityFare();
		if (search.isOneway())
			fare.setFareType(FareType.oneway);
		else {
			fare.setFareType(FareType.roundtrip);
		}
		fare.setCreatedAt(DateTime.now());

		return fare;
	}

	public CuriosityFare setTotalPrice(float totalPrice, Search search) {
		float price = totalPrice / search.getPassengersCount();
		setPrice(price);
		setPricePerAdult(price);

		if (search.hasChildren()) {
			setPricePerChild(price);
		}

		return this;
	}

	public CuriosityFare addTotalPrice(float totalPrice, Search search) {
		float price = totalPrice / search.getPassengersCount();
		addPrice(price);
		addPricePerAdult(price);

		if (search.hasChildren()) {
			addPricePerChild(price);
		}

		return this;
	}

	public CuriosityFare addPrice(float price) {
		setPrice(getPrice() + price);
		return this;
	}

	public CuriosityFare addPricePerAdult(float pricePerAdult) {
		setPricePerAdult(getPricePerAdult() + pricePerAdult);
		return this;
	}

	public CuriosityFare addPricePerChild(float pricePerChild) {
		setPricePerChild(getPricePerChild() + pricePerChild);
		return this;
	}
}
