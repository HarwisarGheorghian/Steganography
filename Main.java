import java.awt.Color;
import java.util.ArrayList;
public class Main{

    public static void main(String[] args){
     /*Picture beach = new Picture("beach.jpg");
      Picture robot = new Picture("robot.jpg");
      Picture flower1 = new Picture("flower1.jpg");

      Picture hidden1 = hidePicture(beach, robot, 65, 208);
      Picture hidden2 = hidePicture(hidden1, flower1, 280, 110);

      Picture unhidden = revealPicture(hidden2);
      unhidden.explore();

      Picture swan = new Picture("swan.jpg");
      Picture swan2 = new Picture("swan.jpg");
      System.out.println("Swan and swan2 are the same: " + isSame(swan, swan2));

      swan = testClearLow(swan);
      System.out.println("Swan and swan2 are the same (after clearLow run on swan): " + isSame(swan, swan2));

      Picture arch = new Picture("arch.jpg");
      Picture arch2 = new Picture("arch.jpg");
      Picture koala = new Picture("koala.jpg");
      Picture robot1 = new Picture("robot.jpg");
      ArrayList<Pixel> pointList = findDifferences(arch, arch2);
      System.out.println("PointList after comparing two identical pictures " +
      "has a size of " + pointList.size());

      pointList = findDifferences(arch, koala);
      System.out.println("PointList after comparing two different sized pictures " +
      "has a size of " + pointList.size());
      arch2 = hidePicture(arch, robot1, 65, 102);
      pointList = findDifferences(arch, arch2);
      System.out.println("PointList after hiding a picture has a size of " +
      pointList.size());

      arch.show();
      arch2.show();*/

      Picture hall = new Picture("femaleLionAndHall.jpg");
      
    }
    /***
     * Draws a rectangle around the part of the picture that is different
     * @param source
     * @return
     */
    public static Picture showDifferentArea(Picture source, ArrayList<Pixel> pixels){
        Picture pic = new Picture(pixels.get(pixels.size() - 1).getY() - pixels.get(0).getY(),
        pixels.get(pixels.size() - 1).getX() - pixels.get(0).getX());
        Pixel[][] recPixels = pic.getPixels2D();
        Pixel startPixel = pixels.get(0);
        Pixel endPixel = pixels.get(pixels.size() - 1);
        for(int i = 0; i < pic.getHeight(); i++){
            recPixels[i + startPixel.getX()][startPixel.getY()].setColor(new Color(255, 0, 0));
            recPixels[i + startPixel.getX()][endPixel.getY()].setColor(new Color(255, 0, 0));
        }


    }

    /***
     * find the differences between the two pictures by comparing the pixels
     * @param Pic1
     * @param pic2
     * @return an arraylist of type pixel
     */

    public static ArrayList<Pixel> findDifferences(Picture pic1, Picture pic2){
        Pixel[][] pic1pixels = pic1.getPixels2D();
        Pixel[][] pic2pixels = pic2.getPixels2D();
        ArrayList<Pixel> coords = new ArrayList<Pixel>();
        if(canHide(pic1, pic2, 0, 0)){
            for(int i = 0; i < pic1.getHeight(); i++){
                for(int j = 0; j < pic2.getWidth(); j++){
                    if(pic1pixels[i][j].getRed() != pic2pixels[i][j].getRed() || pic1pixels[i][j].getBlue() != pic2pixels[i][j].getBlue() 
                    || pic1pixels[i][j].getGreen() != pic2pixels[i][j].getGreen()){
                        coords.add(pic1pixels[i][j]);
                    }
                }
            }
            return coords;
        } else {
            return coords;
        }
    }

    /***
     * Check if two pictures are the same
     * @param pic1
     * @param pic2
     * @return
     */
    
    public static boolean isSame(Picture pic1, Picture pic2){
        Pixel[][] pic1pixels = pic1.getPixels2D();
        Pixel[][] pic2pixels = pic2.getPixels2D();
        if(pic1.getHeight() == pic2.getHeight() && pic1.getWidth() == pic2.getWidth()){
            for(int i = 0; i < pic1.getHeight(); i++){
                for(int j = 0; j < pic1.getWidth(); j++){
                    if(pic1pixels[i][j].getRed() != pic2pixels[i][j].getRed() || pic1pixels[i][j].getBlue() != pic2pixels[i][j].getBlue() 
                    || pic1pixels[i][j].getGreen() != pic2pixels[i][j].getGreen()){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /***
     * Creates a new Piucture with data from secret hidden in data from source =
     * @param source is not null
     * @param secret is not null
     * @return true if secret can be hidden in source, false otherwise.
     */

    public static Picture hidePicture(Picture source, Picture secret, int startRow, int startColumn){
        if(canHide(source, secret, 0, 0)){
            Picture copy = new Picture(source);
            Pixel[][] sourcePix = copy.getPixels2D();
            Pixel[][] secretPix = secret.getPixels2D();
            for(int i = 0; i < secret.getHeight(); i++){
                for(int j = 0; j < secret.getWidth(); j++){
                    setLow(sourcePix[i + startRow][j + startColumn], secretPix[i][j].getColor());
                }
            }
            return copy;
        } else {
            System.out.println("HEH");
            return null;
        }
    }

    /***
     * Determines whether scret can be hidden in source, which is true if source and secret
     * are the same dimensions.
     * @param source is not null
     * @param secret is not null
     * @return true if secret can be hidden in source, false otherwise.
     */

    public static boolean canHide(Picture source, Picture secret, int startRow, int startColumn){
        if(startColumn + secret.getWidth() <= source.getWidth() && startRow + secret.getHeight() <= source.getHeight()){
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