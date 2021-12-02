package agh.ics.oop;

import javax.swing.text.Position;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition);

}
