package net.atpco.detour.model;

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
public class Ammenities {
	private String seatPitch;
	private String seatType;
	private String seatSelection;
	private String directAisleAccess;
	private String wifi_quality;
	private String fresh_food_quality;
	
}
