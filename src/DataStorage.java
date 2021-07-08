
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 *
 * @author Justin Gazsi
 */
public class DataStorage {

    static int NUM_NODES = 100;                                                 //Number of Nodes in Network

    public static ArrayList<Node> Nodes = new ArrayList<Node>();                //Network of Nodes
    public static Block GenBlock = new Block(new Transaction(-1), "0", 1);      //Genesis Block
    public static Queue<Transaction> memPool = new LinkedList<Transaction>();   //MemPool of Transactions
    //public static Quorum Quorum = new Quorum();                               //Class to generate Quorum
    public static ArrayList<Node> QuorumGroup;                                  //Create List to Hold Quorum

    //MAIN DRIVER
    public static void main(String[] args) throws InterruptedException {

        //Populate Network with Nodes
        for (int i = 0; i < NUM_NODES; i++) {
            Nodes.add(new Node());
        }

        //printBlockchainInfo(); //lets see what we have, OLD - prints blockchain of all Nodes.
        
        
        //Test adding and viewing MemPool
        System.out.println("MemPool INFO");
        memPool.add(new Transaction(10));
        memPool.add(new Transaction(12));
        System.out.println(memPool);
        System.out.println(memPool.peek().getData());

        //Test picking random Quorums
        QuorumGroup = new Quorum().getRandomQuorum();
        printQuorumMembers(QuorumGroup);
        
        QuorumGroup = new Quorum().getRandomQuorum();
        printQuorumMembers(QuorumGroup);
        


        connectNetwork();   //Connect the network of nodes to peers
        printNetworkConnections();

    } //end main driver

    
    
    //***** FUNCTIONS *****//
    
    //Function for printing blockchains of each network node
    static void printBlockchainInfo() {
        for (int i = 0; i < Nodes.size(); i++) {
            System.out.println("NodeID: " + Nodes.get(i).getNodeID());// + " QID: " + Nodes.get(i).getQuorumID());
            System.out.println("--------------------------------");
            for (Block block : Nodes.get(i).getBlockchain()) {
                System.out.println("Block#: " + block.getBlockNum());
                System.out.println("TimeStamp: " + block.getTimeStamp());
                System.out.println(block.getTransaction().getData());
                System.out.println("Prev Hash: " + block.getPreviousHash());
                System.out.println("Curr Hash: " + block.getHash());
                System.out.println("");
                //System.out.println(node1.blockchain.size());
            }
        }
    }

    static void printQuorumMembers(ArrayList<Node> QGroup) {
        System.out.println("-----------------------");
        System.out.println("Nodes in QuorumGroup");

        for (Node node : QuorumGroup) {
            System.out.print(node.getNodeID() + ", ");
        }
        System.out.println();
    }

    static void connectNetwork() {
        Random rand = new Random();

        for (Node node : Nodes) {
            int count = rand.nextInt(9) + 2;  //Random connection to peers between 5-10
            
            //While # of peers is less than desired size, get a random peer
            while (node.getPeers().size() < count) {
                Node peer = Nodes.get(rand.nextInt(Nodes.size()));
         
                while (node.getPeers().contains(peer)) {  //Peer must not be in list already, or get new peer
                    peer = Nodes.get(rand.nextInt(Nodes.size()));
                }
                if (node.getNodeID() != peer.getNodeID()) {     //Peer must not be equal to itself
                    //Connect peers to eachother
                    node.addPeer(peer);
                    peer.addPeer(node);
                }

            }
            //Uncomment below to see full history of peer connections as they are created
            //printNetworkConnections();
            
        }

    }
    
    //Function to print peer connections
    static void printNetworkConnections() {
        System.out.println("");
        System.out.println("Peer Connection List");
        System.out.println("--------------------");
            for (Node n : Nodes) {
                System.out.print("NodeID: " + n.getNodeID() + " - Peers: ");
                for (int i = 0; i < n.getPeers().size(); i++) {
                    System.out.print(n.getPeers().get(i).getNodeID() + " ");
                }
                System.out.println("");
            }
    }

}
