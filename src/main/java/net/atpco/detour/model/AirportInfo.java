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
public class AirportInfo {
	public String airportName;
	public String airportCode;
	public String cityCode;
	public String countryCode;
	public String status;
	public Hygenie hygenie;
	public AirportRestriction airportRestriction;
	public String openDate;
	private String rating;
	private String rupa;
}
