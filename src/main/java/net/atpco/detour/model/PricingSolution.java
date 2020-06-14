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

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class PricingSolution {
	
	private String origin;
    private String destination;
    private String carrier;
	private String amount;
	private String departureDate;
	private String ngsRating;
	private String detourScore;
	private CountryInfo countryInfo;
	private List<Fares> fares;
	private List<Flights> flightsList;
	private List<AirportInfo> airportInfoList;
	private CityInfo destinationCityInfo;
	@JsonIgnore
	private String countryCode;
}
