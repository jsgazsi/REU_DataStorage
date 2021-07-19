
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Justin Gazsi
 */
public class DataStorage {

    static ArrayList<Transaction> GenBlockTXs = new ArrayList<Transaction>();

    static int NUM_NODES = 30;                                              //Number of Nodes in Network
    public static ArrayList<Node> Nodes = new ArrayList<Node>();            //Network of Nodes
    public static Block GenBlock;// = new Block(new Transaction(-1), "0", 1);  //Genesis Block
    public static Quorum Quorum; //= new Quorum();                             //Class to generate Quorum
    //public static ArrayList<Node> QuorumGroup;                              //Create List to Hold Quorum
    public static ArrayList<Boolean> GenQuorum = new ArrayList<Boolean>();

    //MAIN DRIVER
    public static void main(String[] args) throws InterruptedException, IOException {
        GenBlockTXs.add(new Transaction(-1));
        GenBlock = new Block(GenBlockTXs, "0", 1, GenQuorum);

        //Populate Network with Nodes
        for (int i = 0; i < NUM_NODES; i++) {
            Nodes.add(new Node());
        }

        //***** EXPERIMENT FUNCTIONS *****//
        //quorumDistributionExp();
        //randomDistributionExp();

        //Generate Quorum and Print info
        //Quorum = new Quorum();
        //printQuorumInfo();

        
        //Bad block proposal and transaction tests
            //Test creating proposing multiple invalid blocks
            //for (int i = 0; i < 5; i++) Nodes.get(0).proposeBlock();

            //test voting with bad transaction - NodeID not in the network
            //Nodes.get(0).broadcastTransaction(new Transaction(89));

        
        
        //Connect network and print report
        connectNetwork();
        printNetworkConnections();

        //Test Generate 3 Blocks with 3 transactions each, Quorum Validates Blocks
        for (int i = 0; i < 3; i++) {
            
            Quorum = new Quorum();
            printQuorumInfo();
            
            //bad transaction test
            Nodes.get(0).broadcastTransaction(new Transaction(89)); 
            
            //Generate some transactions and broadcast to the mempools
            for (int j = 0; j < 3; j++) {
            //Thread.sleep(1); //To not overlap timestamps
            Nodes.get(0).broadcastTransaction(new Transaction(89)); //bad transaction test
            Nodes.get(j).broadcastTransaction(Nodes.get(j).createTransaction());
            }
            
            
            //Nodes in Network will check if they are in Quorum and validate block
            for (Node node : Nodes) {
                node.validateBlock();
            }
            
            //Oldest Quorum member proposes a block. Validation follows in function
            Quorum.getQuroumGroup().get(0).proposeBlock();
            
   
        }
        
        printNodeBlockchain(Nodes.get(0));
        printMemPool();
        
        //*** DO NOT USE WITH LARGE # of NODES
        //printMemPool();
        //printBlockchainInfo(); //for all nodes, use for testing with small # of nodes

    } //end main driver

    //***** FUNCTIONS *****//
    //Function for printing blockchains of each network node
    static void printBlockchainInfo() {
        System.out.println();
        System.out.println("Blockchain INFO");
        for (int i = 0; i < Nodes.size(); i++) {
            System.out.println("NodeID: " + Nodes.get(i).getNodeID());// + " QID: " + Nodes.get(i).getQuorumID());
            System.out.println("--------------------------------");
            for (Block block : Nodes.get(i).getBlockchain()) {
                System.out.println("Block#: " + block.getBlockNum());
                System.out.println("TimeStamp: " + block.getTimeStamp());
                System.out.println(block.getTxList());
                System.out.println(block.getQVotes());
                System.out.println("Prev Hash: " + block.getPreviousHash());
                System.out.println("Curr Hash: " + block.getHash());
                System.out.println("");
                //System.out.println(node1.blockchain.size());
            }
        }
    }

    static void printNodeBlockchain(Node node) {
        System.out.println();
        System.out.println("NodeID: " + node.getNodeID() + " - Blockchain Info");
        System.out.println("--------------------------------");
        for (Block block : node.getBlockchain()) {
            System.out.println("Block#: " + block.getBlockNum());
            System.out.println("TimeStamp: " + block.getTimeStamp());
            //System.out.println(block.getTransaction().getData());
            System.out.println(block.getTxList());
            System.out.println(block.getQVotes());
            System.out.println("Prev Hash: " + block.getPreviousHash());
            System.out.println("Curr Hash: " + block.getHash());
            System.out.println("");
            //System.out.println(node1.blockchain.size());
        }
    }

    static void printQuorumInfo() {
        System.out.println("-----------------------");
        System.out.println("Nodes in Quorum");
        System.out.println();
        for (int i = 0; i < Quorum.getQuroumGroup().size(); i++) {
            System.out.print("NodeID: " + Quorum.getQuroumGroup().get(i).getNodeID() + "\t");
            System.out.println("Vote: " + Quorum.getVotes().get(i));
        }
        System.out.println();

    }

    static void connectNetwork() {
        Random rand = new Random();

        for (Node node : Nodes) {
            int count = rand.nextInt(10) + 5;  //Random connection to peers

            //While # of peers is less than desired size, get a random peer to connect to
            while (node.getPeers().size() < count) {
                Node peer = Nodes.get(rand.nextInt(Nodes.size()));

                //Peer must not be in list already and peer must not equal itself, or must try to get new peer
                while (node.getPeers().contains(peer) || (node.getNodeID() == peer.getNodeID())) {
                    peer = Nodes.get(rand.nextInt(Nodes.size()));
                }
                //Connect to listen to peers
                node.addPeer(peer);
                //peer.addPeer(node);  //for two-way connection
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
            if (Nodes.get(i).getPeers().size() < min) {
                min = Nodes.get(i).getPeers().size();
            }
            if (Nodes.get(i).getPeers().size() > max) {
                max = Nodes.get(i).getPeers().size();
            }
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
            System.out.println();
        }
        System.out.println();
    }

    static void printMemPool() {
        System.out.println("");
        for (Node node : Nodes) {
            System.out.println("NodeID: " + node.getNodeID() + " MemPool:");
            for (int i = 0; i < node.getMemPool().size(); i++) {
                System.out.print(node.getMemPool().get(i) + " - TX_Info: ");
                System.out.println(node.getMemPool().get(i).getData());
            }
        }
    }

    //***** EXPERIMENTS *****//
    static void scalability(int numNodes, int tps) throws InterruptedException{
        //Create Nodes
        for (int i = 0; i < numNodes; i++) {
            Nodes.add(new Node());
        }
        connectNetwork();
        
        //Begin LOOP - TODO Determine Loop Parameters
            //TIME - Begin
            Quorum = new Quorum();
            //TIME - Quorum Creation (this - begin)
            //TODO: Inner Loop: 
                //broadcast #tps for #seconds
            //End Inner Loop
            //TIME: TX creation and propogation (this - quorum creation)
            //Nodes in Network will check if they are in Quorum and validate block
            for (Node node : Nodes) {
                node.validateBlock();
            }
            //TIME: Quorum Validation (this - Tx propogation/creation)
            //Node proposes Block (and propogates through network, nodes validate and add Block)
            Quorum.getQuroumGroup().get(0).proposeBlock();
            // TIME - (Block Validation and Propogation)(Total = this - begin) 
            
        //End Loop
    }
    
    
    
    //Experiment to test quorum distribution using last block hash as seed (compare to java random)
    static void hashDistributionExp() throws InterruptedException, IOException {
        File file = new File("QuorumCounts.csv");
        if (!file.exists()) {
            file.createNewFile();
        }

        PrintWriter pw = new PrintWriter(new FileOutputStream(new File("QuorumCounts.csv"), true));
        int[] quorumCounts = new int[50];
        Quorum = new Quorum();

        for (int x = 0; x < 1000; x++) {  //Number of Trials

            for (int i = 0; i < 1000; i++) { //Number Quorum Generations - log how many times node was selected 
                //Thread.sleep(1); //Add a millisecond to change timestamp for increase hash randomness
                //Generate Block and to Get Quorum based on Block Hash
                Nodes.get(0).proposeBlock();
                Quorum.getHashQuorum(Nodes.get(0).getBlockchain().get(Nodes.get(0).getBlockchain().size() - 1).getHash());

                //Log the nodes that were selected for the quorum
                for (int j = 0; j < Quorum.getQuroumGroup().size(); j++) {
                    quorumCounts[Quorum.getQuroumGroup().get(j).getNodeID() - 1] += 1;

                }
            }

            //Record results
            for (int count : quorumCounts) { //print to screen and file
                //System.out.println(count);
                pw.print(count + ",");
            }
            pw.println(); //add new line for next run
            quorumCounts = new int[50];  //reset array counts back to zero
        }
        pw.close();
    }

    //Experiment to test quorum distribution of java.util random (compare to block hash generation)
    static void randomDistributionExp() throws InterruptedException, IOException {
        File file = new File("RandomCounts.csv");
        if (!file.exists()) {
            file.createNewFile();
        }

        PrintWriter pw = new PrintWriter(new FileOutputStream(new File("RandomCounts.csv"), true));

        int[] randomCounts = new int[50];
        Quorum = new Quorum();

        for (int x = 0; x < 1000; x++) {  //Number of Trials

            for (int i = 0; i < 1000; i++) { //Number Quorum Generations - log how many times node was selected 
                //Thread.sleep(1); //Add a millisecond to change timestamp for increase hash randomness
                //Generate Block and Get Quorum based on Block Hash
                //Nodes.get(0).generateBlock();
                Quorum.getRandomQuorum();

                //Log the nodes that were selected for the quorum
                for (int j = 0; j < Quorum.getQuroumGroup().size(); j++) {
                    randomCounts[Quorum.getQuroumGroup().get(j).getNodeID() - 1] += 1;

                }
            }

            //Record results
            for (int count : randomCounts) { //print to screen and file
                //System.out.println(count);
                pw.print(count + ",");
            }
            pw.println(); //add new line for next run
            randomCounts = new int[50];  //reset array counts back to zero
        }
        pw.close();
    }
    
    

}
