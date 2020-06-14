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
public class CityInfo {
	
	public String cityName;
	public String airportCode;
	public String cityCode;
	public String countryCode;
	public String status;
	public CategoryInfo categoryInfo;
	public CityRestriction cityRestriction;
	public String endDate;

}