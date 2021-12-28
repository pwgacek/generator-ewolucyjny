package agh.ics.oop.maps;

import agh.ics.oop.map_elements.Vector2d;
import agh.ics.oop.conditions.MapConditions;

public class TorusGrassField extends AbstractWorldMap{
    public TorusGrassField(MapConditions mapConditions) {
        super(mapConditions);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }
}
