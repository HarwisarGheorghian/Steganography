import java.awt.Color;
public class Main{

    public static void main(String[] args){
      Picture beach2 = new Picture("beach.jpg");
      beach2.explore();
      Picture copy2 = testSetLow(beach2, Color.PINK);
      copy2.explore();
      Picture copy3 = revealPicture(copy2);
      copy3.explore();
    }

    /**
     * Sets the highest two bits of each pixel's colors
     * to the lowest two bits of each pixel's colors
     * @param Picture named hidden
     * @return Picture object
     */

    public static Picture revealPicture(Picture hidden){
        Picture copy = new Picture(hidden);
        Pixel[][] pixels = copy.getPixels2D();
        Pixel[][] source = hidden.getPixels2D();
        for(int r = 0; r < pixels.length; r++){
            for(int c = 0; c < pixels[0].length; c++){
                Color col = source[r][c].getColor();
                pixels[r][c].setRed((col.getRed() % 4) * 64 + source[r][c].getRed() % 64);
                pixels[r][c].setBlue((col.getBlue() % 4) * 64 + source[r][c].getBlue() % 64);
                pixels[r][c].setGreen((col.getGreen() % 4) * 64 + source[r][c].getGreen() % 64);
            }
        }
        return copy;
    }
    /***
     * Clear the lowest rightmost bits in a pixel
     * @param Pixel p
     */
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
                setLow(pix2, c);
            }
        }
        return p;
    }
}