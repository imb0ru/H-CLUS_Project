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
		if (args.length == 0) {
			System.err.println("Nessun indirizzo ip e numero di porta inseriti.");
			System.exit(1);
		} else if (args.length == 1) {
				System.err.println("Nessun numero di porta inserito.");
				System.exit(1);
		} else if (args.length > 2) {
			System.err.println("Troppi argomenti inseriti.");
			System.exit(1);
		}

		String ip = args[0];

		try{
			String[] parts = ip.split("\\.");
			if(parts.length != 4){
				System.err.println("Indirizzo ip non valido.");
				System.exit(1);
			}
			for(String part : parts){
				int num = Integer.parseInt(part);
				if(num < 0 || num > 255){
					System.err.println("Indirizzo ip non valido.");
					System.exit(1);
				}
			}
		} catch (NumberFormatException e) {
			System.err.println("Indirizzo ip non valido.");
			System.exit(1);
		}

		int port;
		try {
			port = Integer.parseInt(args[1]);
			if (port < 0 || port > 65535) {
				System.err.println("Numero di porta non valido: " + args[1]);
				System.exit(1);
			}
		} catch (NumberFormatException e) {
			System.err.println("Numero di porta non valido: " + args[1]);
			System.exit(1);
			return;
		}

		try {
			new Client(ip, port);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
