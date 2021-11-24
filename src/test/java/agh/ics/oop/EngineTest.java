package agh.ics.oop;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class EngineTest {
    @Test
    public void engineTest1(){ //sprawdza czy nie stawia zwierząt na tej samej pozycji i czy nie powoduje to błędow
        ArrayList<MoveDirection> moves = (new OptionParser().parse(new String[]{"r", "r","f","f","f"}));
        IWorldMap map = new RectangularMap(5,5);
        ArrayList<Vector2d> positions = new ArrayList<>();
        for(int i=0;i<5;i++){
            positions.add(new Vector2d(0,0));
        }
        positions.add(new Vector2d(4,4));
        positions.add(new Vector2d(0,5));

        SimulationEngine engine = new SimulationEngine(moves,map,positions);
        engine.run();

        assertTrue(map.isOccupied(new Vector2d(1,0)));
        assertTrue(map.isOccupied(new Vector2d(5,4)));
        assertTrue(map.isOccupied(new Vector2d(0,5)));
    }
    @Test
    public void engineTest2(){ //sprawdza czy zwierze nie wchodzi na  zajęte pole,pole poza mapą i czy nie powoduje to błędow
        ArrayList<MoveDirection> moves = (new OptionParser().parse(new String[]{"f","r","f","r","r","f"}));
        IWorldMap map = new RectangularMap(5,5);
        ArrayList<Vector2d> positions = new ArrayList<>();
        positions.add(new Vector2d(1,1));
        positions.add(new Vector2d(1,2));

        SimulationEngine engine = new SimulationEngine(moves,map,positions);
        engine.run();


        assertFalse(map.canMoveTo(new Vector2d(0,-1)));
        assertFalse(map.canMoveTo(new Vector2d(-1,0)));
        assertFalse(map.canMoveTo(new Vector2d(6,0)));
        assertFalse(map.canMoveTo(new Vector2d(0,6)));


        assertTrue(map.isOccupied(new Vector2d(1,1)));
        assertTrue(map.isOccupied(new Vector2d(1,2)));

        assertFalse(map.canMoveTo(new Vector2d(1,2)));

    }
    @Test
    public void engineTest3(){ //sprawdza ogolnie działanie engine
        ArrayList<MoveDirection> moves = (new OptionParser().parse(new String[]{"f","r","f","r","r","f","b"}));
        IWorldMap map = new RectangularMap(5,5);
        ArrayList<Vector2d> positions = new ArrayList<>();
        positions.add(new Vector2d(1,1));
        positions.add(new Vector2d(1,1));
        positions.add(new Vector2d(1,2));
        positions.add(new Vector2d(1,3));


        SimulationEngine engine = new SimulationEngine(moves,map,positions);
        engine.run();

        assertTrue(map.isOccupied(new Vector2d(0,1)));
        assertTrue(map.isOccupied(new Vector2d(1,2)));
        assertTrue(map.isOccupied(new Vector2d(1,5)));

    }


}
