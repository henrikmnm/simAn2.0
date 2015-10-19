import java.util.ArrayList;
import java.util.Random;

/**
 * Created by henrikmnm on 19.10.2015.
 */
public abstract class Simulate {

    // Instatiate two lists that hold the neighbouring nodes to the current node and the total nodes created.

    private ArrayList<Node> totalNodes = new ArrayList<>();
    private ArrayList<Node> neighbourNodes = new ArrayList<>();
    private Node currentNode;
    private double successThreshold;
    private double temperature;
    private double dT;
    private generalEvaluate ev;


    // Constructor to instatiate the abstract class with a given success threshold, a start-temperature and a decrement
    // the temperature will be reduced with, effectively assigning how many steps the algorith will perform.
    public Simulate(double success, double temperature, double dT){
        this.successThreshold = success;
        this.temperature = temperature;
        this.dT = dT;
        ev = new generalEvaluate() {
            @Override
            public void calculateScore(Node node) {

            }
        };

        currentNode = new Node() {

            @Override
            public void setScore(double score) {

            }

            @Override
            public double returnScore() {
                return 0;
            }
        };

    }
    // Method for executing the algorithm.
    public void simulate(){
        ev.calculateScore(currentNode);

        // Start while loop.
        while(temperature > 0){

            // Generate neighbours on the node that is the current one set by the algorithm during the previous iteration.
            generateNeighbours();

            // Evaluate all of the neighbours and assign them a score.
            for (Node node : neighbourNodes){
                ev.calculateScore(node);
            }


            double bestScore = 0;
            Node bestNode = new Node() {
                @Override
                public void setScore(double score) {

                }

                @Override
                public double returnScore() {
                    return 0;
                }
            };

            // Find the neighbour with the best score.
            for (Node node : neighbourNodes){
                if (node.returnScore() > bestScore){
                    bestScore = node.returnScore();
                    bestNode = node;
                }
            }
            double q = (bestScore-currentNode.returnScore())/currentNode.returnScore();

            double p = Math.exp(-q/temperature);

            if (p>1)
                p = 1;

            double x = new Random().nextDouble();

            // Exploit the best neighbour.
            if(x > p){
                currentNode = bestNode;
                totalNodes.addAll(neighbourNodes);
                neighbourNodes.clear();
            }
            // Explore new neighbours.
            else{
                generateNeighbours();
            }


            temperature -= dT;
        }
        double bestScore = 0;
        Node bestNode = new Node() {
            @Override
            public void setScore(double score) {

            }

            @Override
            public double returnScore() {
                return 0;
            }
        };
        for (Node node: neighbourNodes){
            if (node.returnScore() > bestScore){
                bestScore = node.returnScore();
                bestNode = node;
            }
        }
        System.out.println("The best node is: "+bestNode.toString());
        System.out.println("The best score is: "+bestScore);
    }

    // General method for creating neighbours.
    public void generateNeighbours(){

    }

}
