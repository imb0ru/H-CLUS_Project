package server;

import bot.TelegramBot;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Server {
    private final int PORT;
    private static Server singleton = null;
    private static TelegramBotsApi bot;

    private Server(int port) {
        this.PORT = port;
        this.run();
    }

    public static void instanceMultiServer(String token, int port) {
        if (singleton == null) {
            try {
                bot = new TelegramBotsApi(DefaultBotSession.class);
                bot.registerBot(new TelegramBot(token, "localhost", port));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            singleton = new Server(port);
        }

    }

    private void run() {
        try {
            ServerSocket s = new ServerSocket(this.PORT);
            try {
                System.out.println("Started: " + s);

                while(true) {
                    Socket socket = s.accept();
                    System.out.println("Connessione client: " + socket);

                    try {
                        new ClientHandler(socket, bot);
                    } catch (IOException e) {
                        System.out.println("Errore nella creazione del socket: " + socket);
                        socket.close();
                    }
                }
            } catch (Throwable e) {
                if (s != null) {
                    try {
                        s.close();
                    } catch (Throwable e1) {
                        e.addSuppressed(e1);
                    }
                }

                throw e;
            }
        } catch (IOException var8) {
            System.out.println("Errore...");
        }
    }
}
