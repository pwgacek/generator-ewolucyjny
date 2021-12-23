package agh.ics.oop;

public class MapConditions {


    private final int width;
    private final int height;
    private final double jungleRatio;

    public MapConditions(int width, int height, double jungleRatio) {
        this.width = width;
        this.height = height;
        this.jungleRatio = jungleRatio;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getJungleRatio() {
        return jungleRatio;
    }
}
