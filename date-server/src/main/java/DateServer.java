import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by thiendev1337 on 8/17/14.
 */
public class DateServer {
    public static void main(String[] args) {
        try (
                ServerSocket server = new ServerSocket(9090);
                Socket socket = server.accept();
        ) {
            Writer out = new OutputStreamWriter(socket.getOutputStream(), "ASCII");
            out.write(new Date() + "\r\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
