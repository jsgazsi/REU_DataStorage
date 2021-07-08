
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Justin Gazsi
 */
public class Quorum {

    //Determine number of Quorum members desired
    private static int NUM = 10;

    
    //This function returns an ArrayList of Quorum members from the Network Node list
    public ArrayList<Node> getRandomQuorum() {
        ArrayList<Node> quorum = new ArrayList<Node>();
        Random rand = new Random();

        //Add specified number of random nodes to Quorum group
        for (int i = 0; i < NUM; i++) {
            Node node = DataStorage.Nodes.get(rand.nextInt(DataStorage.Nodes.size()));
            //Ensure no duplicate nodes in list
            while (quorum.contains(node)){
                node = DataStorage.Nodes.get(rand.nextInt(DataStorage.Nodes.size()));
            }
            quorum.add(node);
        }
        return quorum;
    }

}
