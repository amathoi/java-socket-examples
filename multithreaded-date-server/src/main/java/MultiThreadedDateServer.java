import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by thiendev1337 on 8/17/14.
 */
public class MultiThreadedDateServer {
    public final static int PORT = 9090;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                new DateThread(server.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class DateThread extends Thread {
    private Socket socket;

    public DateThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Writer writer = new OutputStreamWriter(this.socket.getOutputStream());
            writer.write(new Date() + "\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.out.println("Close connection");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}