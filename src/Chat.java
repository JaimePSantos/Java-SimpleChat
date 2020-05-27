import java.io.PrintWriter;
import java.util.Hashtable;

public class Chat {
    Hashtable<String, PrintWriter> clients;

    public Chat() {
        this.clients = new Hashtable<>();
    }

    public boolean register(String id, PrintWriter out) {
        synchronized (this.clients) {
            if ( !(this.clients.containsKey(id)) ) {
                this.clients.put(id, out);
                System.out.println(id + " Successfully registered.");
                return true;
            }
            return false;
        }
    }

    public void disconnect
            (String id) {
        synchronized (this.clients) {
            this.clients.remove(id);
        }
    }

    synchronized public void broadcast(String id, String line) {
        String msg = id + ": " + line;

        for (String user : this.clients.keySet()) {
            if ( !user.equals(id) ) {
                this.clients.get(user).println(msg);
                this.clients.get(user).flush();
            }
        }
    }
}
