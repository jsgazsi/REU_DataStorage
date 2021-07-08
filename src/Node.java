
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Justin Gazsi
 */
//Mimic behavior of a Node
public class Node {

    private ArrayList<Block> blockchain; //local copy of blockchain
    private int nodeID;
    private ArrayList<Node> peers = new ArrayList<Node>();
    //private int quorumID;
    

    //Node Constructor
    public Node() {
        //Create local copy of Blockchain and populate with public Genesis Block
        blockchain = new ArrayList<Block>();
        this.blockchain.add(DataStorage.GenBlock);
        
        //assign NodeID 
        this.nodeID = DataStorage.Nodes.size() + 1;
        
        //Network Connections of peers
        
        

        //while(True)
        //so often check that you have longest chain - update if not longest
        //listen for quroum call and if part of quorum
        //do something - contact other quorum members.
        //validate txs
        //generatate block
        //resume listengine
    } //End constructor

    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    public ArrayList<Node> getPeers() {
        return peers;
    }

    public void setPeers(ArrayList<Node> peers) {
        this.peers = peers;
    }
    
    public void addPeer(Node node) {
        this.peers.add(node);
    }

//    public int getQuorumID() {
//        return quorumID;
//    }
//
//    public void setQuorumID(int quorumID) {
//        this.quorumID = quorumID;
//    }

    public ArrayList<Block> getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(ArrayList<Block> blockchain) {
        this.blockchain = blockchain;
    }

    public Transaction createTransaction() {
        Transaction tx = new Transaction(this.nodeID);
        return tx;
    }

    public void generateBlock() {
        this.blockchain.add(new Block(new Transaction(this.nodeID), 
                this.blockchain.get(blockchain.size() - 1).getHash(),
                this.blockchain.size() + 1));
    }

    //function to broadcast transaction
    public int broadcastTransaction(Transaction tx) {
        int qGroup = new Random().nextInt(3) + 1;
        //create while loop to keep generating until qGroup != nodes quorum id.
        return qGroup;
    }

    public void getLongestChain() {
        //loop
        //set an ID for longest chain
        //Loop through Nodes list
        //If Node.Blockhain > longest chain, then update ID for longest.
        //End loop
        //XXXX  Clear current blockchain and make deep copy of longest chain from ID
        //

    }

    // Function to check validity of the blockchain
//    public static Boolean isChainValid() {
//        
//        Block currentBlock;
//        Block previousBlock;
//
//        // Iterating through all the blocks
//        for (int i = 1; i < blockchain.size(); i++) {
//
//            // Storing the current block and the previous block
//            currentBlock = blockchain.get(i);
//            previousBlock = blockchain.get(i - 1);
//
//            // Checking if the current hash is equal to the calculated hash or not
//            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
//                System.out.println("Hashes are not equal");
//                return false;
//            }
//
//            // Checking of the previous hash is equal to the calculated previous hash or not
//            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
//                System.out.println("Previous Hashes are not equal");
//                return false;
//            }
//        }
//
//        // If all the hashes are equal to the calculated hashes, then the blockchain is valid
//        return true;
//    }
}
