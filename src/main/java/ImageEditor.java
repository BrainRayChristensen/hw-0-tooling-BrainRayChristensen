import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class ImageEditor {
    //variable declaration
    private String header;
    private int width;
    private int height;
    private int maxColorValue;
    private Pixel[][] pixels;
    private Pixel[][] pixelsCopy;

    //member functions
    private void build(Scanner myScanner){
        header = myScanner.next();
        width = myScanner.nextInt();
        height = myScanner.nextInt();
        maxColorValue = myScanner.nextInt();
        pixels = new Pixel[height][width];
        pixelsCopy = new Pixel[height][width];

        //build pixel array data structure
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                int red = myScanner.nextInt();
                int green = myScanner.nextInt();
                int blue = myScanner.nextInt();
                pixels[i][j] = new Pixel(red, green, blue);
                pixelsCopy[i][j] = new Pixel(red, green, blue);
            }
        }
    }

    private void print(String[] args) {
        StringBuilder myString = new StringBuilder();
        myString.append(header);
        myString.append("\n");
        myString.append(width);
        myString.append(" ");
        myString.append(height);
        myString.append("\n");
        myString.append(maxColorValue);
        myString.append("\n");


        //print out pixel array.
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                myString.append(pixelsCopy[i][j].getRed());
                myString.append("\n");
                myString.append(pixelsCopy[i][j].getGreen());
                myString.append("\n");
                myString.append(pixelsCopy[i][j].getBlue());
                myString.append("\n");
            }
        }

        try{
            File outFile = new File(args[1]);
            PrintWriter output = new PrintWriter(outFile);
            output.print(myString.toString());
            //System.out.println(myString);
            output.close();
        }
        catch(Exception e){
            System.out.println("here");
        }
    }

    //image conversion functions
    private void greyscale(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                int temp;
                temp = ((pixels[i][j].getRed() + pixels[i][j].getGreen() + pixels[i][j].getBlue()) / 3);
                pixelsCopy[i][j].setRed(temp);
                pixelsCopy[i][j].setGreen(temp);
                pixelsCopy[i][j].setBlue(temp);
            }
        }
    }

    private void invert(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                pixelsCopy[i][j].setRed(maxColorValue - pixels[i][j].getRed());
                pixelsCopy[i][j].setGreen(maxColorValue - pixels[i][j].getGreen());
                pixelsCopy[i][j].setBlue(maxColorValue - pixels[i][j].getBlue());
            }
        }
    }

    private void emboss(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++) {
                int value;
                int redDiff;
                int greenDiff;
                int blueDiff;

                //edge case... literally
                if(i == 0 || j == 0){
                    value = 128;
                }
                //handles normal case
                else{
                    //finds diff value for each pixel.
                    redDiff = pixels[i][j].getRed() - pixels[i - 1][j - 1].getRed();
                    greenDiff = pixels[i][j].getGreen() - pixels[i - 1][j - 1].getGreen();
                    blueDiff = pixels[i][j].getBlue() - pixels[i -1][j - 1].getBlue();
                    //finds largest diff and then sets the value of value.
                    if(Math.abs(redDiff) >= Math.abs(greenDiff) && Math.abs(redDiff) >= Math.abs(blueDiff)){
                        value = 128 + redDiff;
                        if(value > 255){
                            value = 255;
                        }
                        else if(value < 0){
                            value = 0;
                        }
                    }
                    else if(Math.abs(greenDiff) >= Math.abs(blueDiff)){
                        value = 128 + greenDiff;
                        if(value > 255){
                            value = 255;
                        }
                        else if(value < 0){
                            value = 0;
                        }
                    }
                    else{
                        value = 128 + blueDiff;
                        if(value > 255){
                            value = 255;
                        }
                        else if(value < 0){
                            value = 0;
                        }
                    }
                }
                pixelsCopy[i][j].setRed(value);
                pixelsCopy[i][j].setGreen(value);
                pixelsCopy[i][j].setBlue(value);
            }
        }

    }

    private void motionBlur(String[] args) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                int n = Integer.parseInt(args[3]);
                int rAvg = 0;
                int gAvg = 0;
                int bAvg = 0;
                //checks if n is too big.
                if((n + j) > width){
                    n = width - j;
                }
                for(int k = 0; k < n; k++){
                    rAvg += pixels[i][j+k].getRed();
                    gAvg += pixels[i][j+k].getGreen();
                    bAvg += pixels[i][j+k].getBlue();
                }
                rAvg = rAvg/n;
                gAvg = gAvg/n;
                bAvg = bAvg/n;
                pixelsCopy[i][j].setRed(rAvg);
                pixelsCopy[i][j].setGreen(gAvg);
                pixelsCopy[i][j].setBlue(bAvg);
            }
        }
    }

    private void run(Scanner myScanner, String[] args){
        //build first part of the .ppm file
        build(myScanner);
        //does image editing

        //noinspection IfCanBeSwitch
        if(args[2].equals("grayscale")){
            greyscale();
        }
        else if (args[2].equals("invert")){
            invert();
        }
        else if(args[2].equals("emboss")){
            emboss();
        }
        else if(args[2].equals("motionblur")){
            if(Integer.parseInt(args[3]) < 1){
                System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)");
            }
            else{
                motionBlur(args);
            }
        }
        else{
            System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)");
        }
        //print out header to the ppm file.
        print(args);

    }

    public static void main(String[] args){

        if(args.length < 3 || args.length > 4){
            System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)");
        }
        else{
            File inFile = new File(args[0]);
            try{
                Scanner myScanner = new Scanner(inFile).useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
                new ImageEditor().run(myScanner, args);
            } catch(Exception e){
                System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)");
            }
        }
    }
}
