
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Justin Gazsi
 */


//Keep track of number of nodes to assign ID
class NodeCount {
    static int NODE_CNT = 0;
}

//Mimic behavior of a Node
public class Node {

    int nodeID;
    int quorumID;
    
    //local copy of blockchain
    //public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static LinkedList<Block> blockchain = new LinkedList<Block>();

    //Node Constructor
    public Node() {
        NodeCount.NODE_CNT += 1;
        this.nodeID = NodeCount.NODE_CNT;
        //When new node is created, copy current blockchain ledger
        this.blockchain = DataStorage.blockchain;
        //assign quorum group ID randomly
        this.quorumID = new Random().nextInt(3) + 1;
    }
    
    public Transaction createTransaction() {
        Transaction tx = new Transaction();
        return tx;
    }
    
    //function to broadcast transaction
    public int broadcastTransaction(Transaction tx) {
        int qGroup = new Random().nextInt(3) + 1;
        //create while loop to keep generating until qGroup != nodes quorum id.
        return qGroup;
    }

}
