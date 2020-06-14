package net.atpco.detour.model;

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
public class AirlineInfo {
	private String airline;
	private String cleanAirCirculation = "Y";
	private String maskProvided = "Y";
	private String largerHandSanitizerAllowed = "Y";
	private String flexibleRebooking = "Y";
	private String moreSpace = "Y";
}
