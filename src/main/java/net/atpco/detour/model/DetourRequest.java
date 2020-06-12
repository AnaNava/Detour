package net.atpco.detour.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetourRequest {
	
	private String origin;
	private String prefDestinaionType;
	private String travelFrom;
    private String travelTo;
    private String travelPrefType;
    private String travelType;
    private String distance;
	
}
