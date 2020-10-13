package com.bl.junitMood.HotelReservationSystem;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HotelReservationTest {
	//	UC1 creating hotel objects
	Hotel hotel1 = new Hotel("LakeWood", 110, 90, 3, 80, 80);
	Hotel hotel2 = new Hotel("Bridgewood", 150, 50, 4, 110, 50);
	Hotel hotel3 = new Hotel("Ridgewood", 220, 150, 5, 100, 40);
	HotelReservation hotelReservation = null;
	ArrayList<Hotel> hotelList;

	@Before
	public void initialize() {
		hotelReservation = new HotelReservation();
		hotelList = new ArrayList<>();
		hotelList.add(hotel1);
		hotelList.add(hotel2);
		hotelList.add(hotel3);
	}

	@Test
	public void shouldPrintWelcomeMsg() {
		boolean welcome = hotelReservation.printWelcome();
		Assert.assertEquals(true, welcome);
	}

	//finding cheapest hotel when considering only weekdays
	@Test
	public void shouldReturnLakeWood_whenAllRegularWeekdays() {
		String hotelName = hotelReservation.findCheapestHotel(hotelList);
		Assert.assertEquals("LakeWood", hotelName);
	}

	// finding best hotel for regular customers considering dates range
	@Test
	public void shouldReturnBridgewood_whenAllRegularDays() {
		String startDate = "11/09/2020";
		String endDate = "12/09/2020";
		String hotelName = hotelReservation.findCheapestHotelByDates(startDate, endDate, hotelList, false);
		Assert.assertEquals("Bridgewood", hotelName);
	}

	// finding hotel with best ratings
	@Test
	public void shouldReturnRidgewood_whenFindingBestRatedHotel() {
		String hotelName = hotelReservation.findBestRatedHotel(hotelList);
		Assert.assertEquals("Ridgewood", hotelName);
	}

	// finding best hotel for special customers considering dates range
	@Test
	public void shouldReturnRidgewood_whenAllDays_forSpecialCustomers() {
		String startDate = "11/09/2020";
		String endDate = "12/09/2020";
		String hotelName = hotelReservation.findCheapestHotelByDates(startDate, endDate, hotelList, true);
		Assert.assertEquals("Ridgewood", hotelName);
	}
}
