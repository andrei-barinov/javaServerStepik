import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

class ServeOneJabber extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    static final Logger logger = LogManager.getLogger(ServeOneJabber.class.getName());

    public ServeOneJabber(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
        start(); // вызываем run()
    }

    public void run() {
        try {
            while (true) {
                String str = in.readLine();
                if (str.equals("Bye.")){
                    break;
                }
                out.println(str);
            }
        }
        catch (IOException e) {
            System.err.println("IO Exception");
        }
        finally {
            try {
                socket.close();
            }
            catch (IOException e) {
                System.err.println("Socket not closed");
            }
        }
    }
}
