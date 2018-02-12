public class Miner extends Thread{
    Block block;
    int difficulty;

    public Miner(Block block, int difficulty){
        this.block = block;
        this.difficulty = difficulty;
    }

    public void update(){
        stop();
    }

    public  void run(){
        String hash;
        String zeroes = new String(new char[difficulty]).replace('\0', '0');
        String base = "";
        while (!block.getHash().substring(0, difficulty).equals(zeroes)) {
            block.setProof(block.getProof()+1);
            try {
                base = block.calculateBaseForHash();
                hash = HashAlgorithm.toSha256(base);
                if(!block.getHash().substring(0, difficulty).equals(zeroes))
                    block.setHash(hash);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        block.setFlag(true);
    }


}
