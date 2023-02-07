import java.awt.Color;
public class Main{

    public static void main(String[] args){
        Picture beach = new Picture("beach.jpg");
        beach.explore();
       // Picture copy = testClearLow(beach);
       // copy.explore();
    }

    public static void clearLow(Pixel p){
        int newRed = p.getRed() >> 2 << 2;
        int newGreen = p.getGreen() >> 2 << 2;
        int newBlue = p.getBlue() >> 2 << 2;
        p.setRed(newRed);
        p.setBlue(newBlue);
        p.setGreen(newGreen);

    }

    /**
   * Makes a new Picture object with the lowest two bits of each picture cleared
   * @param a Picture object to use to use
   * @return a new Picture object
   */

    public static Picture testClearLow(){

    }
}