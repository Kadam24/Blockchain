import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Block implements SubjectBase{



    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag (Boolean flag) {
        this.flag = flag;
        if (this.flag == true)
            notifyMiners();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getProof() {
        return proof;
    }

    public void setProof(long proof) {
        this.proof = proof;
    }

    public List<Tranzaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Tranzaction> transactions) {
        this.transactions = transactions;
    }

    private int index;
    private Boolean flag;
    private Date timestamp;
    private String prevHash;
    private String hash;
    private String data;
    private long proof;
    private List<Tranzaction> transactions;
    private List<Miner> miners;
    private Miner[] tab = new Miner[3];

    public Block(int index, String data, List<Tranzaction> transactions) {
        this.index = index;
        this.timestamp = Calendar.getInstance().getTime();
        this.data = data;

        miners = new ArrayList<>();
        if (Server.chain.size() == 0) {
            this.prevHash = "";

        } else {

            String prevHash = Server.getLastBlock().hash;
            this.prevHash = prevHash;
        }

        this.transactions = transactions;

        String base = "";
        String newHash = "";

        try {

            base = calculateBaseForHash();
            newHash = HashAlgorithm.toSha256(base);
            this.setHash(newHash);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMiner(Miner miner){
        miners.add(miner);
    }

    public void removeMiner(Miner miner){
        miners.remove(miner);
    }

    public void notifyMiners(){
        for (Miner m :tab){
            m.update();
        }
    }


    public void mineBlock(int difficulty) throws InterruptedException {

        int cnt = 3;

        for (int i = 0; i < cnt; i++) {
            tab[i] = new Miner(this, difficulty);
            addMiner(tab[i]);
        }


        setFlag(false);

        for(Miner m:tab) {
            m.start();
        }

        for(Miner m:tab) {
            m.join();
        }

    }


    public Date getActualDate() {
        return Calendar.getInstance().getTime();
    }

    public String calculateBaseForHash() throws Exception {
        String base = Integer.toString(index) + Long.toString(proof) + prevHash + data;
        return base;
    }

}
