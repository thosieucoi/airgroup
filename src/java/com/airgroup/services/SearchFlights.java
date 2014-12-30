package com.airgroup.services;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;


import com.ning.http.client.Cookie;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import com.airgroup.datasource.LocationDatasource;
import com.airgroup.helper.CollectionHelper;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.util.Utils;

public abstract class SearchFlights {
	private static final LocationDatasource LOCATION_DATASOURCE = LocationDatasource.get();
	//protected Logger logger = LoggerFactory.getLogger(super.getClass());
	
	protected abstract Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException;

	public abstract List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException;

	public boolean checkFare(CuriosityFare fare, CuriositySearch search) {
		List<CuriositySegment> outboundSegments = fare.getOutboundSegments();
		if (CollectionHelper.isPresent(outboundSegments)) {
			if (fare.getOutboundDepartureCode() == null) {
				fare.setOutboundDepartureCode(outboundSegments.get(0).getDepartureCode());
			}

			if (fare.getOutboundArrivalCode() == null) {
				fare.setOutboundArrivalCode(outboundSegments
					.get(outboundSegments.size() - 1)
					.getArrivalCode());
			}

		}

		List<CuriositySegment> inboundSegments = fare.getInboundSegments();
		if (CollectionHelper.isPresent(inboundSegments)) {
			if (fare.getInboundDepartureCode() == null) {
				fare.setInboundDepartureCode(inboundSegments.get(0).getDepartureCode());
			}

			if (fare.getInboundArrivalCode() == null) {
				fare.setInboundArrivalCode(inboundSegments
					.get(inboundSegments.size() - 1)
					.getArrivalCode());
			}

		}

		if (!(areFareSegmentsCorrect(search, fare))) {
			return false;
		}

		if (fare.getPrice() == 0.0F) {
			fare.setPrice((fare.getPricePerAdult() * search.getAdultsCount() + fare
				.getPricePerChild() * search.getChildrenCount()) /
							search.getPassengersCount());
		}

		if (StringUtils.isBlank(fare.getCurrencyCode())) {
			Utils.printLogg("checkFare returns false ~> Fare currency code is blank [fare={"+fare+"}]");
			return false;
		}

		return true;
	}

	public RequestBuilder RequestBuilderWithHelper(List<Cookie> cookies) {
		RequestBuilder builder = new RequestBuilder();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				builder.addCookie(cookie);
			}
		}
		return builder;
	}

	public boolean areFareSegmentsCorrect(CuriositySearch search, CuriosityFare fare) {
		DateTime outboundDate = search.getOutboundDateWithZone();

		if (!(areSegmentsCorrect(
			fare.getOutboundSegments(),
			outboundDate,
			fare.getOutboundDepartureCode(),
			fare.getOutboundArrivalCode()))) {
			Utils.printLogg("areFareSegmentsCorrect returns false ~> !areSegmentsCorrect(fare.getOutboundSegments(), fare.getDepartureCode(), fare.getArrivalCode())");
			return false;
		}

		if ((fare.isRoundtrip()) &&
			(!(areSegmentsCorrect(
				fare.getInboundSegments(),
				outboundDate,
				fare.getOutboundArrivalCode(),
				fare.getOutboundDepartureCode())))) {
			Utils.printLogg("areFareSegmentsCorrect returns false ~> !areSegmentsCorrect(fare.getInboundSegments(), fare.getArrivalCode(), fare.getDepartureCode())");
			return false;
		}

		return true;
	}

	private boolean isSegmentCorrect(CuriositySegment segment) {
		if (StringUtils.isBlank(segment.getAirlineCode())) {
			Utils.printLogg(
					"isSegmentCorrect returns false ~> Segment airline code is blank [segment={"+segment+"}]"+
					segment);
			return false;
		}

		if (StringUtils.isBlank(segment.getFlightNumber())) {
			Utils.printLogg(
						"isSegmentCorrect returns false ~> Segment flight number is blank [segment={"+segment+"}]");

			return false;
		}

		if ((segment.getDepartureTime() == null) || (segment.getArrivalTime() == null)) {
			return false;
		}

		return (!(segment.getDepartureTime().isAfter(segment.getArrivalTime())));
	}

	private boolean areSegmentsCorrect(List<CuriositySegment> segments, DateTime outboundDate,
			String departureCode, String arrivalCode) {
		if (CollectionHelper.isEmpty(segments)) {
			Utils.printLogg(
						"areSegmentsCorrect returns false ~> CollectionHelper.isEmpty(segments) [segments={"+segments+"}]");
			return false;
		}

		for (CuriositySegment segment : segments) {
			if (!(isSegmentCorrect(segment))) {
				Utils.printLogg(
							"areSegmentsCorrect returns false ~> !isSegmentCorrect(segment) [segment={"+segment+"}]");
				return false;
			}
		}

		CuriositySegment firstSegment = segments.get(0);

		if (!(LOCATION_DATASOURCE.equals(firstSegment.getDepartureCode(), departureCode))) {
			Utils.printLogg(
						"areSegmentsCorrect returns false ~> firstSegment.getDepartureCode() != departureCode [departure_code={"+departureCode+"},first_segment={"+firstSegment+"}]");
			return false;
		}

		if (outboundDate.isAfter(firstSegment.getDepartureTime())) {
			Utils.printLogg(
						"areSegmentsCorrect returns false ~> outboundDate > firstSegment.getDepartureTime() [outbound_date={"+outboundDate+"},first_segment={"+firstSegment+"}]");
			return false;
		}

		CuriositySegment lastSegment = segments.get(segments.size() - 1);

		if (!(LOCATION_DATASOURCE.equals(lastSegment.getArrivalCode(), arrivalCode))) {
			Utils.printLogg(
						"areSegmentsCorrect returns false ~> lastSegment.getArrivalCode() != arrivalCode [arrival_code={"+arrivalCode+"},last_segment={"+lastSegment+"}]");
			return false;
		}

		if (segments.size() >= 2) {
			for (int i = 0; i <= segments.size() - 2; ++i) {
				CuriositySegment segment = segments.get(i);
				CuriositySegment nextSegment = segments.get(i + 1);

				if (!(LOCATION_DATASOURCE.equals(
					segment.getArrivalCode(),
					nextSegment.getDepartureCode()))) {
					Utils.printLogg(
								"areSegmentsCorrect returns false ~> segment.getArrivalCode() != nextSegment.getDepartureCode() [segment={"+segment+"},next_segment={"+nextSegment+"}]");
					return false;
				}

				if ((segment.getDepartureTime() == null) || (segment.getArrivalTime() == null)) {
					Utils.printLogg(
								"areSegmentsCorrect returns false ~> segment.getDepartureTime() == null || segment.getArrivalTime() == null [segment={"+segment+"}]");
					return false;
				}

				if ((nextSegment.getDepartureTime() == null) ||
					(nextSegment.getArrivalTime() == null)) {
					Utils.printLogg(
								"areSegmentsCorrect returns false ~> nextSegment.getDepartureTime() == null || nextSegment.getArrivalTime() == null [next_segment={"+nextSegment+"}]");
					return false;
				}

				if (segment.getArrivalTime().isAfter(nextSegment.getDepartureTime())) {
					Utils.printLogg(
								"areSegmentsCorrect returns false ~> segment.getArrivalTime() > nextSegment.getDepartureTime() [segment={"+segment+"},next_segment={"+nextSegment+"}]");

					return false;
				}
			}
		}
		return true;
	}
}
