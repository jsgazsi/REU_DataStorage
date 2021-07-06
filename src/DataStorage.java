
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Justin Gazsi
 */

public class DataStorage {

    //Global ArrayList (Blockchain)
    //public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static LinkedList<Block> blockchain = new LinkedList<Block>();
    static int NUM_BLOCKS = 10;

    //MAIN DRIVER
    public static void main(String[] args) {
        //Create Genesis Block
        //blockchain.add(new Block("Genesis Block", "0"));
        blockchain.add(new Block("Genesis Block"));

        //add some more blocks
        for (int i = 2; i <= NUM_BLOCKS; i++) {
            String data = "Block# " + String.valueOf(i) + " - Tx's data stub";
            //Uncomment to tamper with blockchain, insert invalid block
//            if (i == 5){
//                 blockchain.add(new Block(data, "Tampered here"));
//            }
           
            //blockchain.add(new Block(data, blockchain.get(blockchain.size() - 1).hash));
            blockchain.add(new Block(data));
        }

        //Print Blockchain info
        for (Block block : blockchain) {
            System.out.println("TimeStamp: " + block.timeStamp);
            System.out.println(block.data);
            System.out.println("Prev Hash: " + block.previousHash);
            System.out.println("Hash: " + block.hash);
            System.out.println("");
        }
        
        System.out.println("Is blockchain valid: " + isChainValid() + "\n");
        
        Node node1 = new Node();
        Node node2 = new Node();
        System.out.println("NodeID: " + node1.nodeID + " Quorum Group: " + node1.quorumID);
        System.out.println("NodeID: " + node2.nodeID + " Quorum Group: " + node2.quorumID);
        System.out.println("");
        
        //mint new block to node1 blockchain and print
        node1.blockchain.add(new Block("Test TX"));
        for (Block block : node1.blockchain) {
            System.out.println("TimeStamp: " + block.timeStamp);
            System.out.println(block.data);
            System.out.println("Prev Hash: " + block.previousHash);
            System.out.println("Hash: " + block.hash);
            System.out.println("");
        }
        
        //Testing some output
        Transaction tx = node1.createTransaction();
        System.out.println("Node ID: " + tx.nodeID + "\n" + 
                "TimeStamp: " + tx.timeStamp + "\n" + 
                "TX ID: " + tx.TxID);


    }

    
    
    
    
    // Function to check validity of the blockchain
    public static Boolean isChainValid() {
        
        Block currentBlock;
        Block previousBlock;

        // Iterating through all the blocks
        for (int i = 1; i < blockchain.size(); i++) {

            // Storing the current block and the previous block
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);

            // Checking if the current hash is equal to the calculated hash or not
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Hashes are not equal");
                return false;
            }

            // Checking of the previous hash is equal to the calculated previous hash or not
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes are not equal");
                return false;
            }
        }

        // If all the hashes are equal to the calculated hashes, then the blockchain is valid
        return true;
    }

}
