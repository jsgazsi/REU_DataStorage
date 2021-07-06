
import java.util.Date;

/**
 *
 * @author Justin Gazsi
 */


public class Block {

    //Blocks contain previous hash and data of transaction
    public String hash;
    public String previousHash;
    public String data; //Tx data stub <- Turn into a Transaction object List
    public long timeStamp;

    //Block Constructor
    //public Block(String data, String previousHash) {
    public Block(String data) {
        this.data = data;
        if (DataStorage.blockchain.size() == 0){
            this.previousHash = "0";
        } else {
            this.previousHash = DataStorage.blockchain.get(DataStorage.blockchain.size() - 1).hash;
        }
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }
    
    //Function to calculate Hash
    public String calculateHash() {
        //Calling crypt class
        String hash = crypt.sha256(data + Long.toString(timeStamp) + previousHash);
        
        return hash;
    }
    



}
