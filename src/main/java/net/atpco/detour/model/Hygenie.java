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
public class Hygenie {

	public String contactless;
	public String socialDistancing;
	public String temperatureCheck;
	public String masks;
	public String handStantizer;

}