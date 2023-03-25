package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            //微妙
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE

        SLList<Integer> N = new SLList<>();

        // 问题出在这里 不是 AList<Integer> N = new AList<>()
        // 我真无语了 这个题测试SLList，你还定义一个Alist，纯纯啥篮子
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        int tick = 0;

        for(int i = 1;i <= 128000;i += 1 )
        {
            N.addLast(i);
            if(N.size() == Math.pow(2,tick) * 1000)
            {
                Stopwatch watch = new Stopwatch();
                for(int j = 0;j < 10000;j += 1)
                {
                    N.getLast();
                }
                Ns.addLast(N.size());
                times.addLast(watch.elapsedTime());
                opCounts.addLast(10000);
                tick += 1;
            }
        }
        printTimingTable(Ns,times,opCounts);



    }

}
