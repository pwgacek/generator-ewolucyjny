package agh.ics.oop;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {
    @Test
    public void next(){
        MapDirection m = MapDirection.NORTH;
        assertEquals(m.next(),MapDirection.EAST);
    }
    public void previous(){
        MapDirection m = MapDirection.NORTH;
        assertEquals(m.next(),MapDirection.WEST);
    }

}
