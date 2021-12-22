package agh.ics.oop;

public class TorusGrassField extends AbstractWorldMap{
    public TorusGrassField(int width, int height) {
        super(width, height);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }
}
