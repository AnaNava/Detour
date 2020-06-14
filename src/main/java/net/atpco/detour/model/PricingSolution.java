package net.atpco.detour.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@Slf4j
public class PricingSolution {
	
	private String origin;
    private String destination;
    private String carrier;
	private String amount;
	private String departureDate;
	private String ngsRating;
	private String detourScore;
	private CountryInfo countryInfo;
	private List<AirportInfo> airportInfoList;
	private CityInfo destinationCityInfo;
	private AirlineInfo airlineInfo;
	private List<Fares> fares;
	private List<Flights> flightsList;
	@JsonIgnore
	private String countryCode;
	private int pricingSolutionIndex;
	
	public void ratePricingSolution() {
		double totalscore = 0.0;		
		if (airlineInfo == null) {
			airlineInfo = new AirlineInfo();
			airlineInfo.setAirline(carrier);
		}
		totalscore = getTravelPrefScore() + getDestinationStatusScore() 
						+ getAirlineScore() + getDestinationHygieneScore();
		int totalpercent = (int) (totalscore *100);
		log.info(" totalpercent - " + totalpercent  * 100);
		if (totalpercent > 0 && totalpercent < 30) {
			detourScore = "Good" ; 
		} else if (totalpercent < 60) {
			detourScore = "Better" ; 
		} else {
			detourScore = "Best" ;
		}
		log.info(" detourScore - " + detourScore);
	}
	

	private double getTravelPrefScore() {
		int travelscore =0;
		if (destinationCityInfo != null) {
			if (destinationCityInfo.getCategoryInfo() != null) {
				if (destinationCityInfo.getCategoryInfo().getAdventure() != null 
						&& destinationCityInfo.getCategoryInfo().getAdventure().equals("Y")) {
					travelscore = travelscore+2;
				}
				if (destinationCityInfo.getCategoryInfo().getBeach() != null 
						&& destinationCityInfo.getCategoryInfo().getBeach().equals("Y")) {
					travelscore = travelscore+2;
				}
				if (destinationCityInfo.getCategoryInfo().getFood() != null 
						&& destinationCityInfo.getCategoryInfo().getFood().equals("Y")) {
					travelscore = travelscore+2;
				}
				if (destinationCityInfo.getCategoryInfo().getCulture() != null 
						&& destinationCityInfo.getCategoryInfo().getCulture().equals("Y")) {
					travelscore = travelscore+2;
				}
				if (destinationCityInfo.getCategoryInfo().getAnimals() != null 
						&& destinationCityInfo.getCategoryInfo().getAnimals().equals("Y")) {
					travelscore = travelscore+2;
				}
			}		
		}
		String ngsRate = ngsRating.substring(0,ngsRating.indexOf("("));
		log.info("ngsRating - " + ngsRating + "     ngsRate - " + ngsRate);
		switch(ngsRate) {
			case "ONE" :
				travelscore = travelscore + 1;
			case "TWO" :
				travelscore = travelscore + 2;
			case "THREE" :
				travelscore = travelscore + 3;
			case "FOUR" :
				travelscore = travelscore + 4;
			case "FIVE" :
				travelscore = travelscore + 5;
			default :
				travelscore = travelscore + 0;	
		}
	  
	    log.info(" travelscore - " + travelscore);
		return (travelscore/15)*.3;		
	}
	
	private double getDestinationStatusScore() {
		int destinationStatusScore =0;
		if (destinationCityInfo != null) {
			if (destinationCityInfo.getStatus() != null) {
				if (destinationCityInfo.getCityRestriction().getNonessential() != null 
						&& destinationCityInfo.getCityRestriction().getNonessential().equals("Y")) {
					destinationStatusScore = destinationStatusScore + 10;
				} else if (destinationCityInfo.getCityRestriction().getNonessential() != null 
						&& destinationCityInfo.getCityRestriction().getNonessential().equals("M")) {
					destinationStatusScore = destinationStatusScore + 5;
				}
			}
		}
		if (countryInfo.getStatus() != null 
				&& destinationCityInfo.getStatus().equals("Open")) {
			destinationStatusScore = destinationStatusScore + 10;
		} else if (countryInfo.getStatus() != null 
				&& destinationCityInfo.getStatus().equals("Restricted")){
			destinationStatusScore = destinationStatusScore + 5;
		}
		if ((!airportInfoList.isEmpty()) &&  airportInfoList.size() > 0) {
			if (airportInfoList.get(0).getStatus() != null 
					&& airportInfoList.get(0).getStatus().equals("Open")) {
				destinationStatusScore = destinationStatusScore + 10;
			} else if (airportInfoList.get(0).getStatus() != null 
					&& airportInfoList.get(0).getStatus().equals("Restricted")){
				destinationStatusScore = destinationStatusScore + 5;
			}
		}											
		log.info(" destinationStatusScore - " + destinationStatusScore);
		return (destinationStatusScore/30)*.4;		
	}
	
	
	private double getAirlineScore() {
		int airlineScore = 0;
		if (airlineInfo == null) {
			airlineInfo = new AirlineInfo();
			airlineInfo.setAirline(carrier);
		}
		if (airlineInfo.getMaskProvided().equals("Y")) {
			airlineScore = airlineScore + 1;
		}
		if (airlineInfo.getCleanAirCirculation().equals("Y")) {
			airlineScore = airlineScore + 1;
		}
		if (airlineInfo.getLargerHandSanitizerAllowed().equals("Y")) {
			airlineScore = airlineScore + 1;
		}
		if (airlineInfo.getFlexibleRebooking().equals("Y")) {
			airlineScore = airlineScore + 1;
		}
		if (airlineInfo.getMoreSpace().equals("Y")) {
			airlineScore = airlineScore + 1;
		}
		log.info(" airlineScore - " + airlineScore + "airlineRating -  " + (airlineScore/5)*.2);
		return (airlineScore/5)*.2;		
	}
	
	private double getDestinationHygieneScore() {
		int destinationHygieneScore =0;
		if ((!airportInfoList.isEmpty()) &&  airportInfoList.size() > 0) {
			if (airportInfoList.get(0).getHygenie() != null  
					&& airportInfoList.get(0).getHygenie().getContactless() != null
					&& airportInfoList.get(0).getHygenie().getContactless().equals("Y")) {
				destinationHygieneScore = destinationHygieneScore + 1;
			} 
			if (airportInfoList.get(0).getHygenie() != null  
					&& airportInfoList.get(0).getHygenie().getHandStantizer() != null
					&& airportInfoList.get(0).getHygenie().getHandStantizer().equals("Y")) {
				destinationHygieneScore = destinationHygieneScore + 1;
			} 
			if (airportInfoList.get(0).getHygenie() != null  
					&& airportInfoList.get(0).getHygenie().getSocialDistancing() != null
					&& airportInfoList.get(0).getHygenie().getSocialDistancing().equals("Y")) {
				destinationHygieneScore = destinationHygieneScore + 1;
			} 
			if (airportInfoList.get(0).getHygenie() != null  
					&& airportInfoList.get(0).getHygenie().getTemperatureCheck() != null
					&& airportInfoList.get(0).getHygenie().getTemperatureCheck().equals("Y")) {
				destinationHygieneScore = destinationHygieneScore + 1;
			} 
			if (airportInfoList.get(0).getHygenie() != null  
					&& airportInfoList.get(0).getHygenie().getMasks() != null
					&& airportInfoList.get(0).getHygenie().getMasks().equals("Y")) {
				destinationHygieneScore = destinationHygieneScore + 1;
			} 
		}	
		log.info(" destinationHygieneScore - " + destinationHygieneScore+ "destinationHygieneRating -  " + (destinationHygieneScore/5)*.1);
		return (destinationHygieneScore/5)*.1;		
	}
}
