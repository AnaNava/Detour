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
public class CountryInfo {

	public String countryName;
	public String countryCode;
	public String status;
	public Restriction restriction;
	public Procedure procedure;
	public String endDate;
	
}