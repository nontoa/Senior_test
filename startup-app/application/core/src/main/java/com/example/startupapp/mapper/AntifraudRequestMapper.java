package com.example.startupapp.mapper;

import com.example.startupapp.dto.AntifraudRequestDto;
import com.example.startupapp.dto.payments.CardDto;

public class AntifraudRequestMapper {

	/**
	 * Constructor.
	 */
	private AntifraudRequestMapper() {
		// Empty constructor.
	}

	public static AntifraudRequestDto mapAntifraudRequest(final CardDto card){

		return AntifraudRequestDto
				.builder()
				.cardHolderName(card.getHolder())
				.cardNumber(card.getNumber())
				.cardCsc(card.getCsc())
				.holderDocument(card.getDocument())
				.month(card.getExpiration().getMonth())
				.year(card.getExpiration().getYear())
				.build();
	}

}
