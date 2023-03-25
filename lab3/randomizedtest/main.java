package randomizedtest;

/**
 * @author zhouhaoran
 * @date 2023/3/25
 * @project skeleton-sp21-master
 */
public class main {
    public static void main(String[] args) {
        BuggyAList<Integer> broken = new BuggyAList<>();
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);

        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.addLast(5);
        broken.removeLast();
        broken.removeLast();
        broken.removeLast();
        broken.removeLast();

    }
}
