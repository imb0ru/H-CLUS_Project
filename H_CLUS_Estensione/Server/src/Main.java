import server.MultiServer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
        if (args.length == 0 || args.length > 3) {
            System.err.println("Utilizzo: java Main <BOT_TOKEN> <IP_ADDRESS> <SERVER_PORT>");
            System.exit(1);
        }

        String botToken = args[0];
        // Controllo sul bot token (lunghezza minima e non null)
        if (botToken == null || botToken.isEmpty()) {
            System.err.println("Bot token non valido.");
            System.exit(1);
        }

        String address = args[1];
        if (!isValidIPAddress(address)) {
            System.err.println("Indirizzo IP non valido: " + address);
            System.exit(1);
        }

        int port;
        try {
            port = Integer.parseInt(args[2]);
            if (port < 0 || port > 65535) {
                System.err.println("Numero di porta non valido: " + args[2]);
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.err.println("Numero di porta non valido: " + args[2]);
            System.exit(1);
            return;
        }

        System.out.println("Server avviato sulla porta " + port);
        MultiServer.instanceMultiServer(botToken, address, port);
    }

    private static boolean isValidIPAddress(String ip) {
        String ipPattern =
                "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5])$";
        Pattern pattern = Pattern.compile(ipPattern);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }
}
