
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author PID# 6224488
 */
public class DataStorage {

    //Global ArrayList (Blockchain)
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    static int NUM_BLOCKS = 10;

    //MAIN DRIVER
    public static void main(String[] args) {
        //Create Genesis Block
        blockchain.add(new Block("Genesis Block", "0"));

        //add some more blocks
        for (int i = 2; i <= NUM_BLOCKS; i++) {
            String data = "Block# " + String.valueOf(i) + " - Tx's data stub";
            //System.out.println(data);
            
            //Uncomment to tamper with blockchain, insert invalid block
//            if (i == 5){
//                 blockchain.add(new Block(data, "Tampered here"));
//            }
           
            blockchain.add(new Block(data, blockchain.get(blockchain.size() - 1).hash));
            
        }

        //Print Blockchain info
        for (Block block : blockchain) {
            System.out.println("TimeStamp: " + block.timeStamp);
            System.out.println(block.data);
            System.out.println("Prev Hash: " + block.previousHash);
            System.out.println("Hash: " + block.hash);
            System.out.println("");
        }
        
        System.out.println("Is blockchain valid: " + isChainValid());

    }

    // Function to check
// validity of the blockchain
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
