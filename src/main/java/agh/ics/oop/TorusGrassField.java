package agh.ics.oop;

public class TorusGrassField extends AbstractWorldMap{
    public TorusGrassField(int width, int height,double jungleRatio) {
        super(width, height,jungleRatio);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }
}
