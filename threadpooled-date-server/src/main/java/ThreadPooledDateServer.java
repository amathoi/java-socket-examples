import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by thiendev1337 on 8/17/14.
 */
public class ThreadPooledDateServer {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        try (ServerSocket server = new ServerSocket(9090)) {
            System.out.println("server> starting");
            while (true) {
                threadPool.submit(new DateCallable(server.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("server> start error");
        }
    }
}

class DateCallable implements Callable<Void> {
    private Socket socket;

    DateCallable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public Void call() {
        try {
            Writer writer = new OutputStreamWriter(socket.getOutputStream());
            writer.write(new Date() + "\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.socket.close();
                System.out.println("server> connection closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}