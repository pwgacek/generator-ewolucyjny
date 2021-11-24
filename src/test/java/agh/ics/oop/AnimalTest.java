package agh.ics.oop;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {
    @Test
    public void AnimalTester(){
        Animal pies = new Animal();
        for(int i=0;i<100;i++){
            pies.move(MoveDirection.RIGHT);
        }
        for(int i=0;i<100;i++){
            pies.move(MoveDirection.LEFT);
        }
        assertEquals(pies.getDirection(),MapDirection.NORTH);


        for(int i =0;i<5;i++){
            for(int j=0;j<10;j++){
                pies.move(MoveDirection.FORWARD);
            }
            pies.move(MoveDirection.RIGHT);
        }
        assertEquals(pies.getPosition(),new Vector2d(0,4));
        assertEquals(pies.getDirection(), MapDirection.EAST);


    }

}
