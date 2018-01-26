import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    public static int difficulty;
    public static List<Block> chain = new ArrayList<Block>();
    public static List<Tranzaction> transactionsToAdd = new ArrayList<Tranzaction>();

    public Blockchain() {
        chain.add(createGenesisBlock());
        difficulty = 3;
    }

    public void newTransaction(Tranzaction t) {
        getLastBlock().getTransactions().add(t);
    }

    public static void addBlock(Block b) {

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

    public static long proofOfWork() {
        return getLastBlock().getProof();
    }

    public Block createGenesisBlock() {
        List<Tranzaction> transactions = new ArrayList<Tranzaction>();
        return new Block(0, "A new block", transactions);
    }

    public static Block getLastBlock() {
        return chain.get(chain.size() - 1);
    }

    public static void main(String[] args) throws Exception {

        Blockchain testBlock = new Blockchain();
        System.out.println("Mining 1st block...");
        Tranzaction t1 = new Tranzaction("kinga", "marysia", 10.1);
        Tranzaction t2 = new Tranzaction("kinga", "agnieszka", 14.33);
        transactionsToAdd.add(t1);
        transactionsToAdd.add(t2);
        Miner.mineNewBlock("mining first block ");
        chain.remove(0);
        System.out.println("PoW for first block "+ proofOfWork());
        System.out.println("Hash:" + testBlock.getLastBlock().getHash());
        System.out.println("Prev hash:" + testBlock.getLastBlock().getPrevHash());
        Tranzaction t3 = new Tranzaction("a ktos", "marysia", 5.33);
        Tranzaction t4 = new Tranzaction("adam", "kinga", 12);
        transactionsToAdd.remove(t1);
        transactionsToAdd.remove(t2);
        transactionsToAdd.add(t3);
        transactionsToAdd.add(t4);
        Miner.mineNewBlock("mining second block ");
        System.out.println("PoW for second block "+ proofOfWork());
        System.out.println("Hash:" + testBlock.getLastBlock().getHash());
        System.out.println("Prev hash:" + testBlock.getLastBlock().getPrevHash());
        if (isChainValid()) {
            System.out.println("you can download the chain, because it is valid, have fun :)");
        }

    }
}
