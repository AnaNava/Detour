package net.atpco.detour.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class DetourResponse {
	
	private String origin;
    private String destination;
    private String carrier;
	private double amount;
	private ShoppingAttributes shoppingAttributes;
	private List<Flights> flightsList;
	private String ngsRating;
	private List<AirportInfo> airportInfoList;
	private DestinationInfo destinationInfo;
	
}
