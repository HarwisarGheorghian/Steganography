import java.awt.Color;
public class Main{

    public static void main(String[] args){
      Picture beach = new Picture("beach.jpg");
      Picture arch = new Picture("blue-mark.jpg");
      if(canHide(beach, arch)){
        Picture newbeach = hidePicture(beach, arch);
        newbeach.explore();
      } else {
        System.out.println("HEH");
      }

      Picture revealed = revealPicture(beach);
      revealed.explore(); 
      
    }

    /***
     * Creates a new Piucture with data from secret hidden in data from source
     * @param source is not null
     * @param secret is not null
     * @return true if secret can be hidden in source, false otherwise.
     */

    public static Picture hidePicture(Picture source, Picture secret){
        Picture copy = new Picture(source);
        Pixel[][] sourcePix = copy.getPixels2D();
        Pixel[][] secretPix = secret.getPixels2D();
        for(int i = 0; i < source.getHeight(); i++){
            for(int j = 0; j < source.getWidth(); j++){
                setLow(sourcePix[i][j], secretPix[i][j].getColor());
            }
        }
        return copy;
    }

    /***
     * Determines whether scret can be hidden in source, which is true if source and secret
     * are the same dimensions.
     * @param source is not null
     * @param secret is not null
     * @return true if secret can be hidden in source, false otherwise.
     */

    public static boolean canHide(Picture source, Picture secret){
        if(source.getWidth() == secret.getWidth() && source.getHeight() == secret.getHeight()){
            return true;
        } else {
            return false;
        }
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
                pixels[r][c].setRed((col.getRed() % 4) * 64 + pixels[r][c].getRed() % 64);
                pixels[r][c].setBlue((col.getBlue() % 4) * 64 + pixels[r][c].getBlue() % 64);
                pixels[r][c].setGreen((col.getGreen() % 4) * 64 + pixels[r][c].getGreen() % 64);
            }
        }
        return copy;
    }
    /***
     * Clear the lowest rightmost bits in a pixel
     * @param Pixel p
     */
    public static void clearLow(Pixel p){
        int newRed = p.getRed() / 4 * 4;
        int newGreen = p.getGreen() / 4 * 4;
        int newBlue = p.getBlue() / 4 * 4;
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