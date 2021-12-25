package agh.ics.oop;

import java.util.ArrayList;

public class Genotype extends ArrayList<Integer> {
    @Override
    public boolean equals(Object o) {

        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayList<Integer> other = (ArrayList<Integer>)o;

        for(int i=0;i<32;i++){
            if(!this.get(i).equals(other.get(i))){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
