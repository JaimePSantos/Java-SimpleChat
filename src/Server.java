import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ServerStart {
    private static int port = 12345;
    private static int n = 5;
    private static ArrayList<ChatWorker> clients = new ArrayList<>();
    private Chat chat = new Chat();

    public void siga() throws IOException, InterruptedException {

        ExecutorService servers = Executors.newFixedThreadPool(n);
        ServerSocket sSock = new ServerSocket(port);

        while (true) {
            System.out.println("Waiting for connection...");
            Socket clSock = sSock.accept();
            System.out.println("Client connected!");
            ChatWorker clientThread = new ChatWorker(clSock, this.chat);
            clients.add(clientThread);
            servers.execute(clientThread);
        }
    }
}

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        new ServerStart().siga();
    }

}
