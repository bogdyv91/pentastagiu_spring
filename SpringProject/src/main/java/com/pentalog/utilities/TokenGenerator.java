package com.pentalog.utilities;

/**
 * This class has a static function that returns a random token used in
 * authentification table.
 * 
 * @author Vacariuc Bogdan
 *
 */

public final class TokenGenerator {

	private TokenGenerator() {
		
	}
	
	public static String getRandomToken() {
		String alphaNumericString = Constants.TOKEN_GENERATOR_STRING_1 + Constants.TOKEN_GENERATOR_STRING_2 + Constants.TOKEN_GENERATOR_STRING_3;
		StringBuilder sb = new StringBuilder(20);

		for (int i = 0; i < 20; i++) {
			int index = (int) (alphaNumericString.length() * Math.random());
			sb.append(alphaNumericString.charAt(index));
		}
		return sb.toString();
	}
}
