import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Map;




public class AdjacencyTable {

    private GraphNode[] table;

    public AdjacencyTable(String[] nodes) {
	        /**
         * The constructor AdjacencyTable(String[] nodes) takes an array of Strings
         *  and creates a fresh hash table containing one GraphNode object labelled
         *  with each string in the array. You may assume all the strings are distinct.
         *  Initially each node has no neighbours. 
         */
        this.table = new GraphNode[nodes.length*2];
        for (String nodeLabel : nodes) {
            int hashcode = nodeLabel.hashCode();
            int hashValue = (hashcode & 0x7fffffff) % this.table.length;
            //if the index in the table is not empty,
           //it performs linear probing 
            while (table[hashValue] != null) {
                hashValue = (hashValue + 1) % this.table.length;
            }

            table[hashValue] = new GraphNode(nodeLabel);
        }
        }
    


    public GraphNode[] getTable() {
        /**
         * method used to return the attribute table containing the nodes to the Adjacency table
         * @return: the attribute table
         */
        return table;
    }

    public boolean find(String s) {
        /**
         * @param: String s: string to be found in the table.
         * @return: Bool - return true if the table does contain the string in the table,
         * else return false if it doesn't
         */

    //System.out.println(s);
    int hashcode = s.hashCode();
    int hashvalue = (hashcode & 0x7fffffff) % this.table.length;
    while (table[hashvalue] != null) {
        if (table[hashvalue].getLabel().equals(s)) {
            return true;
        }
        hashvalue = (hashvalue + 1) % this.table.length;
    }
    return false;
    }
    public GraphNode get(String s) {
        /**
         *Method that gets the string from the table 
         @params: String s: checks to see if the string is in the Adjacency table and returns it if it exists
         */
	// add your table lookup code here
    int hashcode = s.hashCode();
    int hashValue = (hashcode & 0x7fffffff) % this.table.length;


    while (table[hashValue] != null) {
        if (table[hashValue].getLabel().equals(s)) {
            return table[hashValue];
         }
        hashValue = (hashValue + 1) % this.table.length;
        }

    return null; // Node not found

    }

    public String getPath(String s, String t) {
        /**
         * Method that takes in a string s which indicates the start of the path to the end string t.
         * Then perform a Breadth First Search to find the path from s to t
         * @params: String s: the start string
         * String t: the end string
         * @return String path: Path from s to t
         */
        // add your code here
    
        if (!find(s) || !find(t)) {
            //
            return "There is no path from " + s + " to " + t;
    
        }
        Map<String, String> visitedNodes = new HashMap<>();
        Queue<String> nodesToVisit = new ArrayDeque<>();
        boolean endNodeReached = false;
        nodesToVisit.offer(s);
        visitedNodes.put(s, null);
    
        while (!nodesToVisit.isEmpty() && !endNodeReached) {
            // creates the queue for BFS
            String currString = nodesToVisit.poll();
            GraphNode currNode = get(currString);
    
            for (String neighbour : currNode.getNeighbours()) {
                if (neighbour != null && !visitedNodes.containsKey(neighbour)) {
                    nodesToVisit.offer(neighbour);
                    visitedNodes.put(neighbour, currString);
                    if (neighbour.equals(t)) {
                        endNodeReached = true;
                        // Exit the loop once the target node is reached
                    }
                }
            }
    
        }//checks to see if end node is reached or if t and s are the same
        if (endNodeReached||s.equals(t)) {
            List<String> path = new ArrayList<>();
            String currString = t;
    
            while (currString != null) {
                path.add(currString);
                currString = visitedNodes.get(currString);
            }
            StringBuilder resultString = new StringBuilder();
            for (int i = path.size() - 1; i >= 0; i--) {
                resultString.append(path.get(i));
                if (i > 0) {
                    resultString.append("-");
                }
            }
            return resultString.toString();
        } else {
            return "There is no path from " + s + " to " + t;
        }
    }
    

    public boolean existsPath(String s, String t) {
        /**
         * Method that checks to see if a path ecists between node s and node t
         * @params: String s: start node
         * String t: end node
         * @return boolean: returns true if the path exists i.e. the path length is >0 otherwise
         * returns false
         */
	// add your code here
	return pathLength(s, t)>0; 
    }

    public int pathLength(String s, String t) {
        /**
         * Method that returns the length of the path from s to t if the path exists,
         * otherwise it returns 0
         * @params: String s: start node
         * String t: end node
         * @return int pathLength: the lenght of the path between s and t.
         */
    //add your code here
    String path = getPath(s, t);
    if (path.equals("There is no path from "+s+" to "+t)){
        return 0;
    }
	return path.split("-").length; 
    }


    public static AdjacencyTable weaver() {
        /**
         * Method that plays the weaver puzzle game
         * @params: none
         * @return: AdjacencyTable - the path from the starting word to another if it exists
         */
        //add your code here
        String[] dictionary = WeaverWords.words; //takes the words from the weaver words class
        AdjacencyTable weaverTable = new AdjacencyTable(dictionary);
    

        Map<String, List<String>> wordNeighbours = new HashMap<>();
    
        
        for (String word : dictionary) {
            wordNeighbours.put(word, new ArrayList<>());
            for (String otherWord : dictionary) {
                if (!word.equals(otherWord) && isNeighbour(word, otherWord)) {
                    wordNeighbours.get(word).add(otherWord);
                }
            }
        }
    
        
        for (String word : dictionary) {
            List<String> neighbours = wordNeighbours.get(word);
            for (String neighbour : neighbours) {
                weaverTable.addEdge(word, neighbour);
            }
        }
    
        return weaverTable;
    }


    
    private static boolean isNeighbour(String word1, String word2) {
        /**
         * Method that checks if the 2 words are neighbours by 1 letter i.e. word1 is reachable to word 2 by 1 letter
         * @params: String word1: string to compare
         * String word2: word that is checked against word 1 to see if it is 1 letter off the original word
         * @return: boolean - returns true if word 1 is 1 letter off word 2 else returns false
         */
        if (word1.length() != word2.length()) {
            return false;
        }
    
        int diffCount = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                diffCount++;
            }
            if (diffCount > 1) {
                return false;
            }
        }
    
        return diffCount == 1;
    }
    private void addEdge(String word1, String word2) {
        /**
         * Method that makes word 1 and word 2 neighbours of each other
         * @params: String word1: converted into a GraphNode type and is added as a neighbour to word 2
         * String word 2: converted into a GraphNode type and is added as a neighbour to word 1
         * @return: None
         */
        GraphNode node1 = get(word1);
        GraphNode node2 = get(word2);

        node1.addNeighbour(word2);
        node2.addNeighbour(word1);
    }



    public static void main(String[] args) {
        String[] nodes = {"A","B","C","D","E"};
        AdjacencyTable t = new AdjacencyTable(nodes);
        GraphNode n = t.get("A");
        n.addNeighbour("B");
        n.addNeighbour("D");
        n = t.get("B");
        n.addNeighbour("C");
        n.addNeighbour("D");
        n = t.get("D");
        n.addNeighbour("E");
        System.out.println(t.getPath("A","E"));
        System.out.println(t.pathLength("A","E"));
        System.out.println(t.pathLength("A","D"));
        System.out.println(t.getPath("A","D"));
        System.out.println(t.getPath("E","A"));
        System.out.println(t.pathLength("E","E"));
    }

}
