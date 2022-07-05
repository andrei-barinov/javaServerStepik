import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class JabberClient {
    public static void main(String[] args) throws IOException {

        InetAddress addr = InetAddress.getByName(null);
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, 5050);
        try {
            System.out.println("socket = " + socket);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())), true);
            for (int i = 0; i < 10000; i++) {
                out.println("client1: " + i);
                String str = in.readLine();
                System.out.println(str);
            }
            out.println("Bye.");
        } finally {
            socket.close();
        }
    }
}
