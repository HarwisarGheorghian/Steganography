import java.awt.Color;
public class Main{

    public static void main(String[] args){
      Picture beach2 = new Picture("beach.jpg");
      beach2.explore();
      Picture copy2 = testSetLow(beach2, Color.PINK);
      copy2.explore();
    }

    public static void clearLow(Pixel p){
        int newRed = p.getRed() >> 4 << 4;
        int newGreen = p.getGreen() >> 4 << 4;
        int newBlue = p.getBlue() >> 4 << 4;
        p.setRed(newRed);
        p.setBlue(newBlue);
        p.setGreen(newGreen);

    }

    /**
   * Makes a new Picture object with the lowest two bits of each picture cleared
   * @param a Picture object to use to use
   * @return a new Picture object
   */

    public static Picture testClearLow(Picture pic){
        Pixel[][] pixelsInImage = pic.getPixels2D();
        for(Pixel[] pix: pixelsInImage){
            for(Pixel pix2 : pix){
                clearLow(pix2);
            }
        }
        return pic;
    }


    /**
   * Adds the highest two bits of parameter C from each color to each color on pixel p
   * @param Pixel p which is being changed
   * @param Color c which is used on pixel p
   */
    public static void setLow(Pixel p, Color c){
      clearLow(p);
      int fromC_Red = c.getRed() / 64;
      int fromC_Green = c.getGreen() / 64;
      int fromC_Blue = c.getBlue() / 64;
      p.setRed(p.getRed() + fromC_Red);
      p.setGreen(p.getGreen() + fromC_Green);
      p.setBlue(p.getBlue() + fromC_Blue);
    }

    public static Picture testSetLow(Picture p, Color c){
        Pixel[][] pixelsInImage = p.getPixels2D();
        for(Pixel[] pix: pixelsInImage){
            for(Pixel pix2 : pix){
                setLow(pix2, Color.PINK);
            }
        }
        return p;
    }
}