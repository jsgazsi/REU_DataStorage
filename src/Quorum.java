
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
        
        //long seed = stringToSeed(DataStorage.GenBlock.getHash());
        Random rand = new Random();

        //Add specified number of random nodes to Quorum group
        for (int i = 0; i < NUM; i++) {
            Node node = DataStorage.Nodes.get(rand.nextInt(DataStorage.Nodes.size()));
            //Ensure no duplicate nodes in list
            while (quorum.contains(node)) {
                node = DataStorage.Nodes.get(rand.nextInt(DataStorage.Nodes.size()));
            }
            quorum.add(node);
        }
        return quorum;
    }
    
    //Function to take a hash and convert it to a long for Random seed
    static long stringToSeed(String s) {
        if (s == null) {
            return 0;
        }
        long hash = 0;
        for (char c : s.toCharArray()) {
            hash = 31L * hash + c;
        }
        return hash;
    }

}
