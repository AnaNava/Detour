package net.atpco.detour.model;

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
public class Flights {
	private String flightNumber;
	private String cabin;
	private Ammenities ammenities; //objectl,m,n
	//Restrictions
	@JsonIgnore
	private String restrictions;
}
