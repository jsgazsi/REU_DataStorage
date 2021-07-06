
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Justin Gazsi
 */
public class DataStorage {

    static int NUM_BLOCKS = 10;
    static int NODE_CNT = 0;

    //Network list of attached nodes
    public static ArrayList<Node> Nodes = new ArrayList<Node>();
    public static ArrayList<Block> publicBlockchain = new ArrayList<Block>();

    //MAIN DRIVER
    public static void main(String[] args) throws InterruptedException {

        //Create Genesis Block
        publicBlockchain.add(new Block("Genesis Block", "0"));
        //publicBlockchain.add(new Block("Block 2", publicBlockchain.get(publicBlockchain.size() - 1).hash));

//        //add some more blocks
//        for (int i = 2; i <= NUM_BLOCKS; i++) {
//            String data = "Block# " + String.valueOf(i) + " - Tx's data stub";
        //Uncomment to tamper with blockchain, insert invalid block
//            if (i == 5){
//                 blockchain.add(new Block(data, "Tampered here"));
//            }
//           
//            //blockchain.add(new Block(data, blockchain.get(blockchain.size() - 1).hash));
//            blockchain.add(new Block(data));
//        }
        Nodes.add(new Node());
        Nodes.get(0).generateBlock();
        Nodes.get(0).generateBlock();
        Nodes.get(0).generateBlock();

        Nodes.add(new Node());
        Thread.sleep(1000);
        Nodes.get(1).generateBlock();
        //Nodes.get(1).generateBlock();

        System.out.println(Nodes.get(0).getNodeID());
        System.out.println(Nodes.get(1).getNodeID());
        System.out.println("");
        //System.out.println(Nodes.size());
        //Nodes.get(0).generateBlock();

//        Node node1 = new Node();
//        node1.generateBlock();
//        node1.generateBlock();   
//        
//        Node node2 = new Node();
//        node2.generateBlock();
//        Node node3 = new Node();
//        System.out.println("NodeID: " + node1.nodeID + " Quorum Group: " + node1.quorumID);
//        System.out.println("NodeID: " + node2.nodeID + " Quorum Group: " + node2.quorumID);
//        System.out.println("NodeID: " + node3.nodeID + " Quorum Group: " + node3.quorumID);
//        System.out.println("");
//        
        //Verify blockchain
        //System.out.println("Is blockchain valid: " + node1.isChainValid() + "\n");
        //mint new block to node1 blockchain and print
//      node1.blockchain.add(new Block("Test ADD TX"));

        System.out.println("NodeID: " + Nodes.get(0).getNodeID());
        for (Block block : Nodes.get(0).getBlockchain()) {
            System.out.println("TimeStamp: " + block.timeStamp);
            System.out.println("Data: " + block.data);
            System.out.println("Prev Hash: " + block.previousHash);
            System.out.println("Hash: " + block.hash);
            System.out.println("");
            //System.out.println(node1.blockchain.size());
        }
//        
        System.out.println("NodeID: " + Nodes.get(1).getNodeID());
        for (Block block : Nodes.get(1).getBlockchain()) {
            System.out.println("TimeStamp: " + block.timeStamp);
            System.out.println("Data: " + block.data);
            System.out.println("Prev Hash: " + block.previousHash);
            System.out.println("Hash: " + block.hash);
            System.out.println("");
        }

        //Print Blockchain info
        System.out.println("--------------------------------");
//        for (Block block : DataStorage.blockchain) {
//            System.out.println("TimeStamp: " + block.timeStamp);
//            System.out.println(block.data);
//            System.out.println("Prev Hash: " + block.previousHash);
//            System.out.println("Hash: " + block.hash);
//            System.out.println("");
//        }

        //Testing some output
        Transaction tx = Nodes.get(0).createTransaction();
        System.out.println("Node ID: " + tx.nodeID + "\n" + 
                "TimeStamp: " + tx.timeStamp + "\n" + 
                "TX ID: " + tx.TxID);
    }

}
