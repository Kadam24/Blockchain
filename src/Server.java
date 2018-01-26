import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    private static final int PORT = 9001;


    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running.");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }

    private static class Handler extends Thread {
        private Socket socket;
        private PrintWriter out;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    out.println("ILE");
                    out.println("DANE");
     //               out.println("DO KOGO");
       //             out.println("ILE");
                    out.println("OK");
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}