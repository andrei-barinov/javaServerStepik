import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class SocketServerExample extends Thread {
    @Override
    public void run() {
        try {
            new SocketServerExample().startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Selector selector;
    private Map<SocketChannel, List> dataMapper;
    private InetSocketAddress listenAddress;
    private ByteBuffer buf = ByteBuffer.allocate(1024);


    public SocketServerExample() throws IOException {
        listenAddress = new InetSocketAddress("localhost", 5050);
        dataMapper = new HashMap<SocketChannel,List>();
    }

    // create server channel
    private void startServer() throws IOException {
        this.selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);

        // retrieve server socket and bind to port
        serverChannel.socket().bind(listenAddress);
        serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server started");

        while (true) {
            // wait for events
            this.selector.select();

            //work on selected keys
            Iterator keys = this.selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = (SelectionKey) keys.next();

                // this is necessary to prevent the same key from coming up
                // again the next time around.
                keys.remove();

                if (!key.isValid()) {
                    continue;
                }

                if (key.isAcceptable()) {
                    this.accept(key);
                }
                else if (key.isReadable()) {
                    this.read(key);
                }
            }
        }
    }

    //accept a connection made to this channel's socket
    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);


        // register channel with selector for further IO
        dataMapper.put(channel, new ArrayList());
        channel.register(this.selector, SelectionKey.OP_READ);
    }

    //read from the socket channel
    private void read(SelectionKey key) throws IOException {
        SocketChannel ch = (SocketChannel) key.channel();
        StringBuilder sb = new StringBuilder();

        buf.clear();
        int read = 0;
        while( (read = ch.read(buf)) > 0 ) {
            buf.flip();
            byte[] bytes = new byte[buf.limit()];
            buf.get(bytes);
            sb.append(new String(bytes));
            buf.clear();
        }

        String msg = sb.toString();

        if(read<0 || sb.toString().equals("Bye.\r\n")) {
            ch.close();
        }

        broadcast(msg, key);
    }

    private void broadcast(String msg, SelectionKey key) throws IOException {
        ByteBuffer msgBuf=ByteBuffer.wrap(msg.getBytes());
            if(key.isValid() && key.channel() instanceof SocketChannel) {
                SocketChannel sch=(SocketChannel) key.channel();
                sch.write(msgBuf);
                msgBuf.rewind();
            }

    }
}
