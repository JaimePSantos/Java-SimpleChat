import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {

        try (Socket socket = new Socket("127.0.0.1", 12345)) {
            ServerConnection serverOut = new ServerConnection(socket);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

            new Thread(serverOut).start();
            while (true) {
                String command = keyboard.readLine();
                if ( command.equals("quit") )
                    break;
                out.println(command);

            }

            socket.shutdownInput();
            socket.shutdownOutput();
        }
    }
}
