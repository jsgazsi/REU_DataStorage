
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Justin Gazsi
 */
//Mimic behavior of a Node
public class Node {

    //Class variables
    private int nodeID;
    private ArrayList<Block> blockchain = new ArrayList<Block>();
    private ArrayList<Node> peers = new ArrayList<Node>();
    private ArrayList<Transaction> memPool = new ArrayList<Transaction>();


    //Constructor
    public Node() throws InterruptedException {
        //Create local copy of Blockchain and populate with public Genesis Block
        //blockchain = 
        //this.blockchain.add(DataStorage.GenBlock);
        this.blockchain.add(DataStorage.GenBlock);

        //assign NodeID 
        this.nodeID = DataStorage.Nodes.size() + 1;

    }

    // ***** FUNCTIONS *****//
    public Transaction createTransaction() {
        Transaction tx = new Transaction(this.nodeID);
        return tx;
    }

    public void validateBlock() {

        boolean nodeVote = true;
        //Check if node is in Quorum
        if (DataStorage.Quorum.getQuroumGroup().contains(this)) {
            System.out.print("I am node: " + this.nodeID + " \tI am in the quorum - ");
            for (Transaction tx : this.memPool) {
                boolean txIsFound = false;
                for (int j = 0; j < DataStorage.Nodes.size(); j++) {
                    if (tx.getNodeID() == DataStorage.Nodes.get(j).getNodeID()) {
                        txIsFound = true;
                    }

                }
                if (!txIsFound) {
                    nodeVote = false; //Bad transaction found! Do not vote for the block
                }
            }
            System.out.println("I voted: " + nodeVote);
            
            DataStorage.Quorum.getVotes().add(nodeVote);
            

        }

    }

    public void generateBlock() {

//        for (boolean vote: DataStorage.Quorum.getVotes()) {
//            System.out.println(vote);
//            if (!vote == true)
//                System.out.println("Error, invalid Block");
//        }

            this.blockchain.add(new Block(this.memPool, this.blockchain.get(this.blockchain.size() - 1).getHash(), this.blockchain.size() + 1));
            System.out.println("Successfully added Block");
            
            //clear the mempool
            this.memPool.clear();
            
            //Simulates broadcasting block to network
            for (Node node: DataStorage.Nodes) {
                node.getLongestChain();
            }
        
    }

    //Function to broadcast transaction through network
    public void broadcastTransaction(Transaction tx) {

        if (!this.memPool.contains(tx)) {
            this.memPool.add(tx);
        }
        //broadcast transaction to connected peers
        for (Node peer : this.peers) {
            if (!peer.getMemPool().contains(tx)) {
                peer.getMemPool().add(tx);
                peer.broadcastTransaction(tx); //peers recursively propogate through network
            }
        }

    }

    public ArrayList<Transaction> getMemPool() {
        return memPool;
    }

    public void setMemPool(ArrayList<Transaction> memPool) {
        this.memPool = memPool;
    }

    public void getLongestChain() {
        int maxID = this.nodeID;
        for (Node node: DataStorage.Nodes) {
            if (node.getBlockchain().size() > this.blockchain.size()) {
                maxID = node.getNodeID();
            }
        }
        
        if (maxID != this.nodeID) {
            this.blockchain.clear();
            this.memPool.clear();
            for (Block block: DataStorage.Nodes.get(maxID - 1).getBlockchain()) {
                this.blockchain.add(block);
            }
        }
        

    }

    //***** Getters and Setters ****//
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

    public ArrayList<Block> getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(ArrayList<Block> blockchain) {
        this.blockchain = blockchain;
    }

//    public int getQuorumID() {
//        return quorumID;
//    }
//
//    public void setQuorumID(int quorumID) {
//        this.quorumID = quorumID;
//    }
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
