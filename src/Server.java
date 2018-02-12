import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final int PORT = 9001;
    public static List<Block> chain = new ArrayList<Block>();
    public static List<Tranzaction> transactionsToAdd = new ArrayList<Tranzaction>();
    //private static final Server testBlock = new Server();
    private static Server testBlock;
    public static int difficulty;

    private Server() {
        chain.add(createGenesisBlock());
        difficulty = 4;
    }

    public static Server getBlockchain(){
        if(testBlock == null)
            synchronized(Server.class) {
                if (testBlock == null)
                    testBlock = new Server();
            }
        return testBlock;
    }

    public static Block getLastBlock() {
        return chain.get(chain.size() - 1);
    }

    public static long proofOfWork() {
        return getLastBlock().getProof();
    }

    public static void addBlock(Block b) throws InterruptedException {

        b.setPrevHash(getLastBlock().getHash());
        b.setIndex(getLastBlock().getIndex() + 1);
        b.setTimestamp(b.getActualDate());
        b.setTransactions(b.getTransactions());
        b.mineBlock(difficulty);

        chain.add(b);
    }

    public static boolean isChainValid() throws Exception {
        for (int i = 1; i <= chain.size() - 1; i++) {
            Block currentBlock = chain.get(i);
            Block prevBlock = chain.get(i - 1);
            String base = currentBlock.calculateBaseForHash();
            try {
                String h = HashAlgorithm.toSha256(base);
                if (!currentBlock.getHash().equals(h)) {
                    System.out.println("Invalid hash");
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (currentBlock.getPrevHash() != prevBlock.getHash()) {
                System.out.println("Invalid previous hash");
                return false;
            }

            if (currentBlock.getIndex() != prevBlock.getIndex() + 1) {
                System.out.println("Invalid index");
                return false;
            }
        }
        return true;
    }


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

    public static Block createGenesisBlock() {
        List<Tranzaction> transactions = new ArrayList<Tranzaction>();
        return new Block(0, "A new block", transactions);
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
                    out.println("OK");
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}