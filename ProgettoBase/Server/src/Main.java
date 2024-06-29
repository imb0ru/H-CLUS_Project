import server.Server;

/**
 * Classe main del Server.
 */
public class Main {

    /**
     * Punto di partenza dell'applicazione lato server.
     *
     * @param args argomenti passati da terminale
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Nessun numero di porta inserito.");
            System.exit(1);
        }

        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Numero di porta non valido: " + args[0]);
            System.exit(1);
            return; // To satisfy return type of main
        }

        System.out.println("Server avviato sulla porta " + port);
        Server.instanceMultiServer(port);
    }
}
