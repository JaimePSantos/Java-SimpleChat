import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatWorker implements Runnable {
    private Socket socket;
    private Chat chat;
    private String user;
    private BufferedReader in;
    private PrintWriter out;

    public ChatWorker(Socket socket, Chat chat) throws IOException {
        this.socket = socket;
        this.chat = chat;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream());

    }

    public void run() {
        try {
            this.user = in.readLine();
            while (!this.chat.register(this.user, this.out)) {
                System.out.println("Duplicate account");
                out.println("Choose another nickname.");
                out.flush();
                this.user = in.readLine();
            }
            System.out.println(this.user + " registered.");
            out.println("Successfully registered as " + this.user);
            out.flush();

            String message = in.readLine();
            while (message != null && !message.equals("quit")) {
                this.chat.broadcast(this.user, message);
                message = in.readLine();
            }

            this.chat.disconnect(this.user);
            System.out.println(this.user + " disconnected.");

            in.close();
            out.close();
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.chat.disconnect(this.user);
        }


    }
}
