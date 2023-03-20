package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }
    @Test
    public void testSquarePrimesSimple1(){
        IntList lst = IntList.of(9, 7, 16, 17, 15);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("9 -> 49 -> 16 -> 289 -> 15", lst.toString());
        assertTrue(changed);
    }
    @Test
    public void testSquarePrimesSimple2(){
        IntList lst = IntList.of(9, 7, 16, 17, 15, 3);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("9 -> 49 -> 16 -> 289 -> 15 -> 9", lst.toString());
        assertTrue(changed);
    }
    @Test
    public void mytest(){
        IntList lst = IntList.of(1, 2, 3, 89, 5,7,9,17);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("1 -> 4 -> 9 -> 7921 -> 25 -> 49 -> 9 -> 289", lst.toString());
        assertTrue(changed);
    }
}
