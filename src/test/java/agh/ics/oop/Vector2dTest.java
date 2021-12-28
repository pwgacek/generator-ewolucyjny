package agh.ics.oop;
import agh.ics.oop.map_elements.Vector2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Vector2dTest {
    @Test
    void equalsTest(){
        Vector2d v1 = new Vector2d(2,1);
        Vector2d v2 = new Vector2d(2,1);
        assertEquals(v1, v2);
    }
    @Test
    void toStringTest(){
        Vector2d v = new Vector2d(1,1);
        assertEquals(v.toString(),"(1,1)");
    }
    @Test
    void followsTest(){
        Vector2d v1 = new Vector2d(2,2);
        Vector2d v2 = new Vector2d(1,1);
        assertTrue(v1.follows(v2));
    }
    @Test
    void precedesTest(){
        Vector2d v1 = new Vector2d(2,1);
        Vector2d v2 = new Vector2d(2,2);
        assertTrue(v1.precedes(v2));
    }
    @Test
    void upperRightTest(){
        Vector2d v1 = new Vector2d(2,1);
        Vector2d v2 = new Vector2d(0,2);
        assertEquals(v1.upperRight(v2),new Vector2d(2,2));
    }
    @Test
    void lowerLeftTest(){
        Vector2d v1 = new Vector2d(2,1);
        Vector2d v2 = new Vector2d(0,2);
        assertEquals(v1.lowerLeft(v2),new Vector2d(0,1));
    }
    @Test
    void addTest(){
        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(-1,-1);
        assertEquals(v1.add(v2),new Vector2d(0,0));
    }
    @Test
    void subtractTest(){
        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(-1,-1);
        assertEquals(v1.subtract(v2),new Vector2d(2,2));
    }
    @Test
    void oppositeTest(){
        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(-1,-1);

        assertEquals(v1.opposite(),v2);
    }
}
