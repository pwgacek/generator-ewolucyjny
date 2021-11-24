package agh.ics.oop;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptionParserTest {
    @Test
    public void parseTest(){

        OptionParser parser = new OptionParser();
        ArrayList<MoveDirection> directions = new ArrayList<>();
        directions.add(MoveDirection.RIGHT);
        directions.add(MoveDirection.LEFT);
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.BACKWARD);
        directions.add(MoveDirection.BACKWARD);


        assertEquals(parser.parse(new String[]{"ala","r","left","t","forward","f","b","d","back"}) ,directions);

    }
}
