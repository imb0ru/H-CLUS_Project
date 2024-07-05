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
            } catch (TelegramApiException var3) {
                TelegramApiException e = var3;
                e.printStackTrace();
            }

            singleton = new Server(port);
        }

    }

    private void run() {
        try {
            ServerSocket s = new ServerSocket(this.PORT);
            ServerSocket var2 = s;

            try {
                System.out.println("Started: " + String.valueOf(s));

                while(true) {
                    Socket socket = s.accept();
                    System.out.println("Connessione client: " + String.valueOf(socket));

                    try {
                        new ClientHandler(socket, bot);
                    } catch (IOException var6) {
                        System.out.println("Errore nella creazione del socket: " + String.valueOf(socket));
                        socket.close();
                    }
                }
            } catch (Throwable var7) {
                if (s != null) {
                    try {
                        var2.close();
                    } catch (Throwable var5) {
                        var7.addSuppressed(var5);
                    }
                }

                throw var7;
            }
        } catch (IOException var8) {
            System.out.println("Errore...");
        }
    }
}
