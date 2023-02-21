import java.awt.Color;
import java.util.ArrayList;
public class Main{

    public static void main(String[] args){
        Picture arch = new Picture("arch.jpg");
        hideText(arch, "HI");
        arch.explore();

        revealText(arch);
        arch.explore();
      
    }

    /***
     * @param String s
     * @return ArrayList result
     */
    public static ArrayList<Integer> encodeString(String s) {
        s = s.toUpperCase();
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < s.length(); i++) {
            if (s.substring(i, i+1).equals(" ")) {
                result.add(27);
            } else {
                result.add(alpha.indexOf(s.substring(i, i+1))+1);
            }
        }

        result.add(0);
        return result;
    }

        /**
    * Returns the string represented by the codes arraylist.
    * 1 - 26, 27 = space
    * @param codes encoded string
    * @return decoded string
    */

    private static String decodeString(ArrayList<Integer> codes) {
        String result = "";
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < codes.size(); i++) {
            if (codes.get(i) == 27) {
                result = result + " ";
            } else {
                result = result + alpha.substring(codes.get(i), codes.get(i) + 1);
            }
        }

        return result;
    }

    /**
    * Given a number from 0 - 63, creates an returns a 3-element
    * int array consisting of the integers representing the
    * pairs of bits in the number from right to left
    * @param num number ot be broken up
    * return bit pairs in number
    */

    private static int[] getBitPairs(int num) {
        int[] bits = new int[3];
        int code = num;
        for (int i = 0; i < 3; i++) {
            bits[i] = code%4;
            code = code/4;
        }

        return bits;
    
    }

        /**
    * Hide a string (must be only capital letters and spaces) in a
    * picture.
    * The string always starts in the upper left corner.
    * @param source picture to hide string in
    * param s string to hide
    * @return picture with hidden string
    */

    public static void hideText(Picture source, String s) {
        Pixel[][] pixels = source.getPixels2D();
        ArrayList<Integer> encoded = encodeString(s);

        for (int i = 0; i < encoded.size(); i++) {
            Pixel p = pixels[0][i];
            clearLow(p);

            int[] bits = getBitPairs(encoded.get(i));
            
            p.setRed(p.getRed() + (bits[0]));
            p.setGreen(p.getGreen() + (bits[1]));
            p.setBlue(p.getBlue() + (bits[2]));
        }
        
    }

    /**
    * Returns a string hidden in the picture
    * @param source picture with hidden string
    * @return revealed string
    */

    public static String revealText(Picture source) {
        Pixel[][] pixels = source.getPixels2D();
        ArrayList<Integer> codes = new ArrayList<Integer>();
        for(int i = 0; i < pixels[0].length; i++){
            int[] bits = {pixels[0][i].getRed() % 4, pixels[0][i].getBlue() % 4, pixels[0][i].getGreen() % 4};
            int num = Integer.parseInt(Integer.toBinaryString(bits[0] * 16 + bits[1] * 4 + bits[2]), 2);

            if(num == 0){
                break;
            } else {
                codes.add(num);
            }
        }
        return decodeString(codes);
    }


    /***
     * Draws a rectangle around the part of the picture that is different
     * @param source
     * @return
     */
    public static Picture showDifferentArea(Picture source, ArrayList<Pixel> pixels){
        Picture replacement = new Picture(source);
        Pixel startPixel = pixels.get(0);
        Pixel endPixel = pixels.get(pixels.size() - 1);
        
        for(int i = startPixel.getX(); i <= endPixel.getX(); i++){
            replacement.getPixel(i, startPixel.getY()).setColor(new Color(255, 0, 0));
            replacement.getPixel(i, endPixel.getY()).setColor(new Color(255, 0, 0));
        }

        for(int i = startPixel.getY(); i <= endPixel.getY(); i++){
            replacement.getPixel(startPixel.getX(), i).setColor(new Color(255, 0, 0));
            replacement.getPixel(endPixel.getX(), i).setColor(new Color(255, 0, 0));
        }
        return replacement;

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
            for(int i = 0; i < pic1pixels.length; i++){
                for(int j = 0; j < pic1pixels[0].length; j++){
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