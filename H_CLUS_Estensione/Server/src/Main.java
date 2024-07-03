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
        if (args.length == 0 || args.length > 2) {
            System.err.println("Utilizzo: java Main <BOT_TOKEN> <SERVER_PORT>");
            System.exit(1);
        }

        String botToken = args[0];
        // Controllo sul bot token (lunghezza minima e non null)
        if (botToken == null || botToken.isEmpty()) {
            System.err.println("Bot token non valido.");
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

        System.out.println("Server avviato sulla porta " + port);
        Server.instanceMultiServer(botToken, port);
    }
}
