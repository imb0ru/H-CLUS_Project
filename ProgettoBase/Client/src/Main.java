import client.Client;

import java.io.IOException;

/**
 * Classe main del Client.
 */
public class Main {

	/**
	 * Punto di partenza dell'applicazione lato client.
	 *
	 * @param args argomenti passati da terminale (non vengono gestiti)
	 */
	public static void main(String[] args) {
		String ip = args[0];
		int port = Integer.parseInt(args[1]);

		try {
			new Client(ip, port);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
