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
public class UTAInfo {
	
	private String brandName;	
	private String carryOnAllowance;
	private String checkedBagAllowance;	
	private String seatSelection;	
	private String cancellation;
    private String advanceChange;
}
