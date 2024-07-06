package server;

import clustering.HierachicalClusterMiner;
import clustering.InvalidClustersNumberException;
import clustering.InvalidDepthException;
import data.Data;
import data.InvalidSizeException;
import data.NoDataException;
import distance.AverageLinkDistance;
import distance.ClusterDistance;
import distance.SingleLinkDistance;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import org.telegram.telegrambots.meta.TelegramBotsApi;

/**
 * Gestisce la comunicazione con un singolo client attraverso un socket
 * e processa le richieste del client in un thread separato.
 */
class ServerOneClient extends Thread {
    private final Socket clientSocket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private Data data;
    TelegramBotsApi bot;


    /**
     * Costruisce l'oggetto e avvia il thread per la gestione del client.
     *
     * @param socket Il socket del client.
     * @param bot    L'istanza di TelegramBotsApi.
     * @throws IOException Se c'è un errore nell'aprire gli stream.
     */
    public ServerOneClient(Socket socket, TelegramBotsApi bot) throws IOException {
        this.clientSocket = socket;
        this.out = new ObjectOutputStream(this.clientSocket.getOutputStream());
        this.in = new ObjectInputStream(this.clientSocket.getInputStream());
        this.bot = bot;
        this.start();
    }

    /**
     * Gestisce le richieste del client in un ciclo continuo.
     */
    public void run() {
        try {
            while(true) {
                Object request = this.in.readObject();
                if (request instanceof Integer) {
                    int requestType = (Integer)request;
                    switch (requestType) {
                        case 0:
                            this.handleLoadData();
                            break;
                        case 1:
                            this.handleClustering();
                            break;
                        case 2:
                            this.handleLoadDendrogramFromFile();
                            break;
                        default:
                            this.out.writeObject("Tipo di richiesta non valido");
                    }
                } else {
                    this.out.writeObject("Tipo di richiesta non valido");
                }
            }
        } catch (IOException e) {
            System.out.println("Disconnessione client: " + this.clientSocket);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                this.clientSocket.close();
                this.out.close();
                this.in.close();
            } catch (IOException e) {
                System.err.println("Errore nella chiusura del socket o degli ObjectStream");
            }

        }
    }

    /**
     * Carica i dati dalla tabella del database.
     *
     * @throws IOException            Se c'è un errore nella lettura o nella scrittura.
     * @throws ClassNotFoundException Se il tipo di oggetto ricevuto non è {@code String}.
     */
    private void handleLoadData() throws IOException, ClassNotFoundException {
        String tableName = (String)this.in.readObject();

        try {
            this.data = new Data(tableName);
            this.out.writeObject("OK");
        } catch (NoDataException e) {
            this.out.writeObject(e.getMessage());
        }

    }

    /**
     * Esegue il clustering sui dati e salva il dendrogramma.
     *
     * @throws IOException            Se c'è un errore nella lettura, esecuzione del clustering o salvataggio.
     * @throws ClassNotFoundException Se il tipo di oggetto ricevuto non è {@code Integer} o {@code String}.
     */
    private void handleClustering() throws IOException, ClassNotFoundException {
        if (this.data == null) {
            this.out.writeObject("Dati non caricati");
        } else {
            int depth = (Integer)this.in.readObject();
            int distanceType = (Integer)this.in.readObject();

            try {
                HierachicalClusterMiner clustering = new HierachicalClusterMiner(depth);
                ClusterDistance distance = distanceType == 1 ? new SingleLinkDistance() : new AverageLinkDistance();
                clustering.mine(this.data, distance);
                this.out.writeObject("OK");
                this.out.writeObject(clustering.toString(this.data));
                String fileName = (String)this.in.readObject();
                clustering.salva(fileName);
                this.out.writeObject("OK");
                this.out.writeObject("Salvataggio effettuato con successo!");
            } catch (InvalidClustersNumberException | IOException | InvalidDepthException | InvalidSizeException e) {
                this.out.writeObject(e.getMessage());
            }

        }
    }

    /**
     * Carica un dendrogramma da un file.
     *
     * @throws IOException            Se c'è un errore nella lettura del file o nella comunicazione.
     * @throws ClassNotFoundException Se il tipo di oggetto ricevuto non è {@code String}.
     */
    private void handleLoadDendrogramFromFile() throws IOException, ClassNotFoundException {
        String fileName = (String)this.in.readObject();

        try {
            HierachicalClusterMiner clustering = HierachicalClusterMiner.loadHierachicalClusterMiner(fileName);
            if (this.data == null) {
                this.out.writeObject("Dati non caricati");
                return;
            }

            if (clustering.getDepth() > this.data.getNumberOfExample()) {
                this.out.writeObject("Numero di esempi maggiore della profondità del dendrogramma!");
            } else {
                this.out.writeObject("OK");
                this.out.writeObject(clustering.toString(this.data));
            }
        } catch (ClassNotFoundException | InvalidDepthException | IOException e) {
            this.out.writeObject(e.getMessage());
        }

    }
}
