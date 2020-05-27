import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerConnection implements Runnable {
    private BufferedReader in;
    private Socket server;

    public ServerConnection(Socket s) throws IOException {
        server = s;
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
    }

    public void run() {
        try {
            while (true) {
                System.out.print("> ");
                String serverResponse = in.readLine();
                if ( serverResponse == null )
                    break;
                System.out.println("> " + serverResponse);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
