public class Pixel {
    private int red;
    private int green;
    private int blue;

    /**
     *
     * @param Red
     * @param Green
     * @param Blue
     */
    Pixel(int Red, int Green, int Blue){
        red = Red;
        blue = Blue;
        green = Green;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}
