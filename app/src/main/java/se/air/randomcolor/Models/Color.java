package se.air.randomcolor.Models;

public class Color {
    private String colorHEX;
    private int colorINT;
    private int[] rgb;

    public Color(String colorHEX){
        this.colorHEX = colorHEX;
    }

    public Color(Integer colorINT) {
        this.colorINT = colorINT;
    }

    public int[] getRGB() {
        return rgb;
    }

    @Override
    public String toString() {
        return "(r = " + rgb[0] + ", g = " + rgb[1] + ", b = " + rgb[2] + ")";
    }

    public int getColorINT() {
        return colorINT;
    }

    public String getColorHEX() {
        return colorHEX;
    }

    public void setColorHEX(String colorHEX) {
        this.colorHEX = colorHEX;
    }

    public void setColorINT(int colorINT) {
        this.colorINT = colorINT;
    }

    public int[] getRgb() {
        return rgb;
    }

    public void setRgb(int[] rgb) {
        this.rgb = rgb;
    }

    public String toStringRGB(){
        return "RGB (" + rgb[0] + ", " + rgb[1] + ", " + rgb[2] + ")";
    }
}
