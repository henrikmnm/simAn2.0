import java.util.ArrayList;

/**
 * Created by Fincken on 14.10.2015.
 */
public class cartonNode {
    private boolean isEgg;
    private int x;
    private int y;
    private boolean isBad;
    private char cartonChar;

    public cartonNode(char isEgg, int x, int y){
        this.x = x;
        this.y = y;
        this.cartonChar = isEgg;
        switch (isEgg){
            case '.': this.isEgg = false;
                break;
            case '0': this.isEgg = true;
                break;
        }
    }

    public boolean isEgg() {
        return isEgg;
    }

    public void setEgg() {
        this.isEgg = true;
        this.cartonChar = '0';

    }
    public void removeEgg(){
        this.isEgg = false;
        this.cartonChar = '.';
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isBad() {
        return isBad;
    }

    public void setBad() {
        this.isBad = true;
        this.cartonChar = '1';
    }

    public void setGood() {
        this.isBad = false;
        this.cartonChar = '0';
    }

    public String toString(){
        return this.cartonChar + "";
    }
}
