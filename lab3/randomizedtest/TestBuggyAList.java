package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;
import timingtest.AList;

import java.util.concurrent.BrokenBarrierException;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE

    @Test
    public void testThreeAddThreeRemove()
    {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();

        correct.addLast(5);
        broken.addLast(5);
        correct.addLast(6);
        broken.addLast(6);
        correct.addLast(10);
        broken.addLast(10);
        correct.addLast(16);
        broken.addLast(16);
        correct.addLast(16);
        broken.addLast(16);

        assertEquals(correct.size(),broken.size());
        assertEquals(correct.removeLast(),broken.removeLast());
        assertEquals(correct.removeLast(),broken.removeLast());
        assertEquals(correct.removeLast(),broken.removeLast());
        assertEquals(correct.removeLast(),broken.removeLast());
        assertEquals(correct.removeLast(),broken.removeLast());

    }
    @Test
    public void randomizedTest()
    {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            //在 [a， b] 中均匀返回一个随机整数。
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                // 在 [a， b] 中均匀返回一个随机整数。
                correct.addLast(randVal);
                broken.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size1 = correct.size();
                int size2 = broken.size();
                System.out.println("size: " + size1 +" size: " + size2);
                assertEquals(size1,size2);
            } else if (operationNumber == 2 && correct.size() > 0 && broken.size() > 0) {
               int a = correct.getLast();
               int b = broken.getLast();
               assertEquals(a,b);
               System.out.println("getLast(" + a + ")" + " getLast(" + b + ")");
            } else if (operationNumber == 3 && correct.size() > 0 && broken.size() > 0){
                int a = correct.removeLast();
                int b = broken.removeLast();
                System.out.println("removeLast(" + a + ")" + " removeLast(" + b + ")");

                assertEquals(a,b);
            }

        }
    }
}
