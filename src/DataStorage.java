
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 *
 * @author Justin Gazsi
 */
public class DataStorage {

    static int NUM_NODES = 30;                                              //Number of Nodes in Network
    public static ArrayList<Node> Nodes = new ArrayList<Node>();            //Network of Nodes
    public static Block GenBlock = new Block(new Transaction(-1), "0", 1);  //Genesis Block
    public static Quorum Quorum = new Quorum();                             //Class to generate Quorum
    public static ArrayList<Node> QuorumGroup;                              //Create List to Hold Quorum

    //MAIN DRIVER
    public static void main(String[] args) throws InterruptedException {

        //Populate Network with Nodes
        for (int i = 0; i < NUM_NODES; i++) {
            Nodes.add(new Node());
        }
        
        //Test picking random Quorums
        QuorumGroup = Quorum.getRandomQuorum();
        printQuorumMembers(QuorumGroup);

        QuorumGroup = Quorum.getRandomQuorum();
        printQuorumMembers(QuorumGroup);
        
        //Test creating valid blockchain
        for (int i = 0; i < 5; i++) Nodes.get(0).generateBlock();
        printNodeBlockchain(Nodes.get(0));
        
        
        //Connect network and print report
        connectNetwork();
        printNetworkConnections();
        
        //Generate some transactions and broadcast to the mempools
        Nodes.get(0).broadcastTransaction(Nodes.get(0).createTransaction());
        for (int i = 0; i < 5; i++) {
            Thread.sleep(111); //To not overlap timestamps
            Nodes.get(i).broadcastTransaction(Nodes.get(i).createTransaction());
        }
        
        printMemPool();

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
    
    static void printNodeBlockchain(Node node) {
        System.out.println();
        System.out.println("NodeID: " + node.getNodeID());
            System.out.println("--------------------------------");
            for (Block block : node.getBlockchain()) {
                System.out.println("Block#: " + block.getBlockNum());
                System.out.println("TimeStamp: " + block.getTimeStamp());
                System.out.println(block.getTransaction().getData());
                System.out.println("Prev Hash: " + block.getPreviousHash());
                System.out.println("Curr Hash: " + block.getHash());
                System.out.println("");
                //System.out.println(node1.blockchain.size());
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
            int count = rand.nextInt(5) + 2;  //Random connection to peers between 3-10

            //While # of peers is less than desired size, get a random peer
            while (node.getPeers().size() < count) {
                Node peer = Nodes.get(rand.nextInt(Nodes.size()));

                //Peer must not be in list already and peer must not equal itself, or must try to get new peer
                while (node.getPeers().contains(peer) || (node.getNodeID() == peer.getNodeID())) {
                    peer = Nodes.get(rand.nextInt(Nodes.size()));
                }

                //Connect peers
                node.addPeer(peer);
                peer.addPeer(node);

            }
            //Uncomment below to see full history of peer connections as they are created
            //printNetworkConnections();

        }

    }

    //Function to print peer connections
    static void printNetworkConnections() {
        int min = 10, max = 0, sum = 0, avg;
        for (int i = 0; i < Nodes.size(); i++) {
            sum += Nodes.get(i).getPeers().size();
            if (Nodes.get(i).getPeers().size() < min) min = Nodes.get(i).getPeers().size();
            if (Nodes.get(i).getPeers().size() > max) max = Nodes.get(i).getPeers().size();
        }
        
        avg = sum / Nodes.size();
        
        
        System.out.println("");
        System.out.println("Peer Connection List");
        System.out.println("CONNECTIONS: Min: " + min + " Max: " + max + " Avg: " + avg);
        System.out.println("--------------------");
        for (Node n : Nodes) {
            System.out.print("NodeID: " + n.getNodeID() + " - Peers: ");
            for (int i = 0; i < n.getPeers().size(); i++) {
                System.out.print(n.getPeers().get(i).getNodeID() + " ");
            }
            System.out.println("");
        }
    }
    
    static void printMemPool() {
        System.out.println("");
        for (Node node: Nodes) {
            System.out.println("NodeID: " + node.getNodeID() + " MemPool:");
            for (int i = 0; i < node.getMemPool().size(); i++) {
                System.out.print(node.getMemPool().get(i) + " - TX_Info: ");
                System.out.println(node.getMemPool().get(i).getData());
            }
        }
    }

}
