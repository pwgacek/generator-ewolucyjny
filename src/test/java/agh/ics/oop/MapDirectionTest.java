package agh.ics.oop;
import agh.ics.oop.map_elements.MapDirection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {
    @Test
    public void nextTest(){

        assertEquals(MapDirection.EAST.next(),MapDirection.SOUTH);
        assertEquals(MapDirection.SOUTH.next(),MapDirection.WEST);
        assertEquals(MapDirection.WEST.next(),MapDirection.NORTH);
        assertEquals(MapDirection.NORTH.next(),MapDirection.EAST);

    }
    @Test
    public void previousTest(){
        assertEquals(MapDirection.EAST.previous(),MapDirection.NORTH);
        assertEquals(MapDirection.SOUTH.previous(),MapDirection.EAST);
        assertEquals(MapDirection.WEST.previous(),MapDirection.SOUTH);
        assertEquals(MapDirection.NORTH.previous(),MapDirection.WEST);
    }

}
