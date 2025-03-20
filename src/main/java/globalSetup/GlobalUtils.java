package globalSetup;

import java.security.SecureRandom;
import java.util.Random;

public class GlobalUtils {
	

	/**
	 * Generate random Numbers of desired length
	 * 
	 * @param Digits : Integer : Number of characters of desired random number".
	 */
	public int RandomNumber(int digits) {
	    if (digits <= 0) {
	        throw new IllegalArgumentException("Number of digits must be greater than 0");
	    }

	    int min = (int) Math.pow(10, digits - 1); // Minimum value for the given digits
	    int max = (int) Math.pow(10, digits) - 1; // Maximum value for the given digits

	    Random ran = new Random();
	    int ran_int = ran.nextInt((max - min) + 1) + min; // Generate random number between min and max

	    return ran_int;
	}

	/**
	 * Generate random string of desired length with A-z,a-z,1-9 and special
	 * characters
	 * 
	 * @param Length : Integer : Number of characters of desired random string".
	 */

	public String RandomString(int length) {
		// Define the characters to be used in the random string
		final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

		// Use SecureRandom for more secure random number generation
		final SecureRandom RANDOM = new SecureRandom();

		StringBuilder randomchar = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			int randomIndex = RANDOM.nextInt(CHARACTERS.length());
			randomchar.append(CHARACTERS.charAt(randomIndex));
		}

		// Generate a random string of X characters
		String randomString = randomchar.toString();
		// System.out.println("Random String: " + randomString);
		return randomString;
	}
}
