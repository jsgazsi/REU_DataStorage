
import java.util.Date;

/**
 *
 * @author Justin Gazsi
 */
public class Block {

    //Blocks contain previous hash and data of transaction
    private String hash;
    private String previousHash;
    private String data; //Tx data stub <- Turn into a Transaction object List
    private Transaction transaction;
    private long timeStamp;
    private int blockNum;

    //Block Constructor
    public Block(Transaction tx, String prevHash, int blockNum) {
        this.transaction = tx;
        this.timeStamp = new Date().getTime();

        if (tx.getNodeID() == -1) {
            this.previousHash = "0";
            tx.setData("GENESIS BLOCK");// = "Genesis Block";
        } else {
            //this.previousHash = DataStorage.publicBlockchain.(size() - 1).hash;
            this.previousHash = prevHash;
        }
        this.blockNum = blockNum;
        this.hash = calculateHash();

    }

    //Getters and Setters
    public int getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(int blockNum) {
        this.blockNum = blockNum;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    //Function to calculate Hash
    public String calculateHash() {
        //Calling crypt class
        String hash = crypt.sha256(data + Long.toString(timeStamp) + previousHash);
        return hash;
    }

}
