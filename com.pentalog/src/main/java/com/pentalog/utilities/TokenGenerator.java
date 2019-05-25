package com.pentalog.utilities;

/**
 * This class has a static function that returns a random token used in
 * authentification table.
 * 
 * @author Vacariuc Bogdan
 *
 */

public class TokenGenerator {

	public static String getRandomToken() {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(20);

		for (int i = 0; i < 20; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}
}
