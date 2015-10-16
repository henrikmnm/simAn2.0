import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Fincken on 15.10.2015.
 */
public class SimAn {
    ArrayList<Carton> totalBoards = new ArrayList<Carton>();
    ArrayList<Carton> neighbours = new ArrayList<Carton>();
    private Carton currentCarton;
    private int maxEggs;
    private double fTarget;
    private double temperature;
    private double dT;

    public SimAn(int m,int n, int k, double fTarget, double temperature, double dT){
        currentCarton = new Carton(m, n);
        this.maxEggs = k;
        this.fTarget = fTarget;
        this.temperature = temperature;
        this.dT = dT;
        simulate();
    }


    public void simulate(){

        Evaluation ev = new Evaluation(this.maxEggs);

        while (temperature > 0 ){
            ev.legalCheck(this.currentCarton);
            ev.scoreFunction(this.currentCarton);
            if (this.currentCarton.getFScore() > this.fTarget) {
                totalBoards.add(currentCarton);
                System.out.println("Beste score: "+currentCarton.getFScore());
                break;
            }
            createNeighborBoards();

            for(Carton carton: neighbours){
                ev.legalCheck(carton);
                ev.scoreFunction(carton);
            }

            double bestScore = 0;
            int bestCartonIndex = 0;
            String nb = "";
            for (int i = 0; i < neighbours.size(); i++) {
                if (neighbours.get(i).getFScore() > bestScore){
                    bestScore = neighbours.get(i).getFScore();
                    nb += neighbours.get(i).getFScore()+", ";
                    bestCartonIndex = i;
                }
            }

            double fMax = neighbours.get(bestCartonIndex).getFScore();

            double q = (fMax-currentCarton.getFScore())/currentCarton.getFScore();
            double tempP = Math.exp(-q/temperature);
            if(tempP > 1) {
                tempP = 1;
            }

            double x = new Random().nextDouble();
            if(x > tempP){
                totalBoards.add(currentCarton);
                currentCarton = neighbours.remove(bestCartonIndex);
            }
            else {
                int randInt = new Random().nextInt(neighbours.size());
                totalBoards.add(currentCarton);
                currentCarton = neighbours.remove(randInt);
            }
            for(Carton carton : neighbours){
                totalBoards.add(carton);
            }
            neighbours.clear();
            temperature -= dT;
        }
        double fMax = 0;
        int bestBoard = 0;
        for (int i = 0; i < totalBoards.size(); i++) {
            if(totalBoards.get(i).getFScore() > fMax){
                fMax = totalBoards.get(i).getFScore();
                bestBoard = i;
            }
        }
        System.out.println("Best board is: \n" + totalBoards.get(bestBoard).toString());
        System.out.println("Best score is: \n" + totalBoards.get(bestBoard).getFScore());



    }

    public void createNeighborBoards(){
        Carton newNeighbour = currentCarton;

        for (int i = 0; i < newNeighbour.getyMax(); i++) {
            for (int j = 0; j < newNeighbour.getxMax(); j++) {
                cartonNode thisNode = newNeighbour.getNode(j,i);
                if(thisNode.isBad()&&thisNode.isEgg()){
                    thisNode.removeEgg();
                }
            }
        }

        for (int i = 0; i < currentCarton.getCarton().size()*maxEggs; i++) {

            int randY = new Random().nextInt(currentCarton.getyMax());
            int randX = new Random().nextInt(currentCarton.getyMax());
            cartonNode randNode = newNeighbour.getNode(randX, randY);

            if(randNode.isEgg()){
                randNode.removeEgg();
            }else{
                randNode.setEgg();
            }
            neighbours.add(newNeighbour);
        }

    }

/*    public void createNeighborBoards(cartonNode Node){

        //Check Right
        if(Node.getX()!=currentCarton.getxMax()-1 && !currentCarton.getNode(Node.getX()+1, Node.getY()).isEgg()){
            Carton neighbour = currentCarton;
            int x = Node.getX();
            int y = Node.getY();
            cartonNode currentTemp = Node;
            cartonNode rightNode = neighbour.getNode(x+1,y);
            currentTemp.setX(x+1);
            currentTemp.setGood();
            rightNode.setX(x);
            rightNode.removeEgg();
            neighbour.getCarton().get(y).set(x + 1, currentTemp);
            neighbour.getCarton().get(y).set(x, rightNode);
            neighbours.add(neighbour);
        }
        //Check Left
        if(Node.getX()!=0 && !currentCarton.getNode(Node.getX()-1, Node.getY()).isEgg()){
            Carton neighbour = currentCarton;
            int x = Node.getX();
            int y = Node.getY();
            cartonNode currentTemp = Node;
            cartonNode rightNode = neighbour.getNode(x-1,y);
            currentTemp.setX(x-1);
            currentTemp.setGood();
            rightNode.setX(x);
            rightNode.removeEgg();
            neighbour.getCarton().get(y).set(x - 1, currentTemp);
            neighbour.getCarton().get(y).set(x, rightNode);
            neighbours.add(neighbour);
        }
        //Check Up
        if(Node.getY()!=0 && !currentCarton.getNode(Node.getX(), Node.getY()-1).isEgg()){
            Carton neighbour = currentCarton;
            int x = Node.getX();
            int y = Node.getY();
            cartonNode currentTemp = Node;
            cartonNode rightNode = neighbour.getNode(x,y-1);
            currentTemp.setY(y - 1);
            currentTemp.setGood();
            rightNode.setY(y);
            rightNode.removeEgg();
            neighbour.getCarton().get(y-1).set(x, currentTemp);
            neighbour.getCarton().get(y).set(x, rightNode);
            neighbours.add(neighbour);
        }
        //Check Down
        if(Node.getY()!=currentCarton.getyMax()-1 && !currentCarton.getNode(Node.getX(), Node.getY()+1).isEgg()){
            Carton neighbour = currentCarton;
            int x = Node.getX();
            int y = Node.getY();
            cartonNode currentTemp = Node;
            cartonNode rightNode = neighbour.getNode(x,y+1);
            currentTemp.setY(y + 1);
            currentTemp.setGood();
            rightNode.setY(y);
            rightNode.removeEgg();
            neighbour.getCarton().get(y+1).set(x, currentTemp);
            neighbour.getCarton().get(y).set(x, rightNode);
            neighbours.add(neighbour);
        }
    }*/

    public String toString(){
        return currentCarton.toString();
    }

    public Carton getCurrentCarton(){
        return this.currentCarton;
    }


    public static void main(String[] args){

        SimAn sim = new SimAn(5,5,2,0.9,3,0.0001);
        System.out.println("Temperature: "+sim.temperature);


    }
}
