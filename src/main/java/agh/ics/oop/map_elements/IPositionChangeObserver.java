package agh.ics.oop.map_elements;

import agh.ics.oop.map_elements.Animal;
import agh.ics.oop.map_elements.Vector2d;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Animal animal);

}
