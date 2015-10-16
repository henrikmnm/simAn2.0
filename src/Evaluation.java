import java.util.ArrayList;

/**
 * Created by Fincken on 14.10.2015.
 */
public class Evaluation {
    private int k;

    public Evaluation(int k){
        this.k = k;
    }

    public void legalCheck(Carton carton){
        ArrayList<ArrayList<cartonNode>> board = carton.getCarton();
        // Horizontal check
        for (ArrayList<cartonNode> list: board) {
            int eggCount = 0;
            for(cartonNode node: list){
                if(node.isEgg())
                    eggCount += 1;
                if(eggCount > k && node.isEgg())
                    node.setBad();
            }
        }

        for (int i = 0; i < board.size(); i++) {
            int eggCount = 0;
            for (int j = 0; j < board.get(i).size(); j++) {
                if(board.get(j).get(i).isEgg())
                    eggCount += 1;
                if(eggCount>k  && board.get(j).get(i).isEgg())
                    board.get(j).get(i).setBad();
            }
        }

        for (int i = 0; i < board.get(0).size(); i++) {
            int eggCount = 0;
            int xCount = i;
            int yCount = board.size()-1;
            while(yCount>=i){
                if(board.get(yCount).get(xCount).isEgg())
                    eggCount += 1;
                if(eggCount>k  && board.get(yCount).get(xCount).isEgg())
                    board.get(yCount).get(xCount).setBad();
                xCount++;
                yCount--;
            }
        }

        for (int i = board.size()-2,j=0 ; i >=0 ; i--,j++) {
            int eggCount = 0;
            int xCount = i;
            int yCount = 0;
            while(yCount<board.size()-1-j) {
                if (board.get(yCount).get(xCount).isEgg())
                    eggCount += 1;
                if (eggCount > k  && board.get(yCount).get(xCount).isEgg())
                    board.get(yCount).get(xCount).setBad();
                xCount--;
                yCount++;
            }
        }
        for (int i = 0; i < board.size() ; i++) {
            int eggCount = 0;
            int xCount = i;
            int yCount = 0;
            while(yCount<board.size()-i){
                if(board.get(yCount).get(xCount).isEgg())
                    eggCount += 1;
                if(eggCount>k  && board.get(yCount).get(xCount).isEgg())
                    board.get(yCount).get(xCount).setBad();
                xCount++;
                yCount++;
            }
        }

        for (int i = board.size()-2,j=1; i >=0 ; i--,j++) {
            int eggCount = 0;
            int xCount = i;
            int yCount = board.size()-1;
            while(yCount>=j) {
                if (board.get(yCount).get(xCount).isEgg())
                    eggCount += 1;
                if (eggCount > k && board.get(yCount).get(xCount).isEgg())
                    board.get(yCount).get(xCount).setBad();
                xCount--;
                yCount--;
            }
        }

    }

    public void scoreFunction(Carton carton){
        double legalEggs = 0;
        double badEggs = 0;

        for (int i = 0; i <carton.getyMax(); i++) {
            for (int j = 0; j < carton.getxMax(); j++) {
                if(!carton.getNode(i,j).isBad()&&carton.getNode(i,j).isEgg()){
                    legalEggs+= 1;
                }
                else if (carton.getNode(i,j).isBad()){
                    badEggs+= 1;
                }
            }
        }
        carton.setFScore((legalEggs)/(legalEggs+badEggs));
    }
}
