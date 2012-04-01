package nl.hr.energize.eview;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Cookie {

	/**
	 * The HTTP name of the COOKIE constant ".ASPXAUTH".
	 */
	final static String COOKIE_NAME = ".ASPXAUTH";

	/**
	 * The cookie value that has the secret credentials from the "_eview_cookie" file.
	 */
	final static String COOKIE = getCookie();

	/**
	 * The file name "_eview_cookie".
	 */
	private final static String FILE_NAME = "_eview_cookie";

	/**
	 * Get the cookie from the "_eview_cookie" file.
	 * @return The secret cookie
	 */
	private static String getCookie() {
		try {
			Scanner scanner = new Scanner(new File(FILE_NAME));
			return scanner.nextLine();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private Cookie() {}

}
