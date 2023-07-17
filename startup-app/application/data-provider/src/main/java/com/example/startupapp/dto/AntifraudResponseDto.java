package com.example.startupapp.dto;

import com.example.startupapp.constants.AntifraudStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

/**
 * Data transfer object to define the antifraud response from the antifraud mock.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Jacksonized
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AntifraudResponseDto {

	/**
	 * Antifraud status
	 */
	private AntifraudStatus status;

	/**
	 * Antifraud Message
	 */
	private String message;

}
