import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MultiJabberServer {
    static final int PORT = 5050;
    static final Logger logger = LogManager.getLogger(MultiJabberServer.class.getName());

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(PORT);
        logger.info("Server started");
        try {
            while (true) {
                // Блокируется до возникновения нового соединения:
                Socket socket = s.accept();
                try {
                    new ServeOneJabber(socket);
                }
                catch (IOException e) {
                    // Если завершится неудачей, закрывается сокет,
                    // в противном случае, нить закроет его:
                    socket.close();
                }
            }
        }
        finally {
            s.close();
        }
    }
}
