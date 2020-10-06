package com.bl.junitMood.HotelReservationSystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HotelReservation 
{
	private long numDates;
	Map<Hotel, Integer> hotelNameToCostMap = new HashMap<>(); 
	private int hotelCost;
	
	public boolean printWelcome() {
		 System.out.println( "Welcome to the hotel reservation system." );
		 return true;
	}
	
	public void getNumOfDatesInRange(String startDate, String endDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		LocalDate start = LocalDate.parse(startDate, formatter);
		LocalDate end = LocalDate.parse(endDate, formatter);
		numDates =  ChronoUnit.DAYS.between(start, end);
	}
	
	//this function gives the days (1 for Sunday, 2-Mon...) from given date
	public int getDayFromDate(String startDate) throws ParseException {
		Date start = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);  
	    Calendar c = Calendar.getInstance();
		c.setTime(start);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek;
	}
	
	// calculate total cost for a particular hotel
	public void calculateTotalCost(String startDate, Hotel hotel, boolean isSpecial) throws ParseException {
		int dayOfWeek = getDayFromDate(startDate);
		hotelCost = 0;
		for(int day = 0; day<= numDates; day++) {
			dayOfWeek = day + dayOfWeek;
			if (dayOfWeek > 7) {
				dayOfWeek = 7;
			}
			
			if (dayOfWeek == 1 || dayOfWeek == 7) {			// 1 is for Sunday and 7 for Saturday
				if (isSpecial) {
					hotelCost = hotelCost + hotel.getSpecialWeekEndRate();
				} else {
					hotelCost = hotelCost + hotel.getRegularWeekEndRate();
				}
			} else {
				if (isSpecial) {
					hotelCost = hotelCost + hotel.getSpecialDailyRate();
				} else {
					hotelCost = hotelCost + hotel.getRegularDailyRate();
				}
			}
		}
		System.out.println("isSprecial: "+ isSpecial + " Cost for hotel- " 
							+ hotel.getHotelName() +" "+ hotelCost);
		hotelNameToCostMap.put(hotel, hotelCost);
	}
	
	//this function finds the hotel with lowest rates and return name of hotel
	// only weekdays considered
	public String findCheapestHotel(ArrayList<Hotel> hotels ) {
		Hotel hotel = hotels.stream()
							 .min(Comparator.comparing(Hotel::getRegularDailyRate))
							 .orElse(null);
		System.out.println("Only weekdays and regular, cheapest hotel is: " +hotel.getHotelName());
		return hotel.getHotelName();
	}
	
	// to find min cost and max rating among total cost of all hotels
	public String findBestCheapestHotelInMap() {
		int minCost = hotelNameToCostMap.entrySet()
									.stream()
						            .min(Map.Entry.comparingByValue())
						            .get().getValue();
		
		Hotel hotel = hotelNameToCostMap.entrySet().stream()
						  .filter(hotelMap -> minCost == hotelMap.getValue())
						  .max((hotel1, hotel2) -> hotel1.getKey().getRatings() > hotel2.getKey().getRatings() ? 1 : -1)
						  .get().getKey();
		
		System.out.println("Hotel name: "+ hotel.getHotelName()+ " Ratings: "+hotel.getRatings());
		return hotel.getHotelName();
	}
	
	// returns the name of the hotel with min total cost and best ratings
	// isSpecial = 1 when calculating for reward customer and 0 for regular customer
	public String findCheapestHotelByDates(String sDate, String eDate, ArrayList<Hotel> hotels, boolean isSpecial) {
		hotels.forEach((hotel) -> {
			try {
				getNumOfDatesInRange(sDate, eDate);
				calculateTotalCost(sDate, hotel, isSpecial);
				
			} catch (ParseException e) {
				System.out.println("Exception msg: " + e.getMessage());
			}
		});
		String hotelName = findBestCheapestHotelInMap();
		return hotelName;
	}
	
	// finds the hotel with highest ratings
	public String findBestRatedHotel(ArrayList<Hotel> hotels) {
		Hotel hotel = hotels.stream()
							.max(Comparator.comparing(Hotel::getRegularDailyRate))
							.orElse(null);
		return hotel.getHotelName();
	}
	
	
}


