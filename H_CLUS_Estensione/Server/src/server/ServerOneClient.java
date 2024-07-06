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

class ServerOneClient extends Thread {
    private final Socket clientSocket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private Data data;
    TelegramBotsApi bot;

    public ServerOneClient(Socket socket, TelegramBotsApi bot) throws IOException {
        this.clientSocket = socket;
        this.out = new ObjectOutputStream(this.clientSocket.getOutputStream());
        this.in = new ObjectInputStream(this.clientSocket.getInputStream());
        this.bot = bot;
        this.start();
    }

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

    private void handleLoadData() throws IOException, ClassNotFoundException {
        String tableName = (String)this.in.readObject();

        try {
            this.data = new Data(tableName);
            this.out.writeObject("OK");
        } catch (NoDataException e) {
            this.out.writeObject(e.getMessage());
        }

    }

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
