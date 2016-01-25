import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.stream.Collector;

public class Tests {
    @Test
    public void defaultConstructorTest() {
        Board<Integer> board = new Board<>();
        Assert.assertEquals(board.totalSize(), 1);
        System.out.println(board.totalSize());
        Assert.assertEquals(board.at(0, 0), null);
        Assert.assertEquals(board.rows(), 1);
        Assert.assertEquals(board.columns(), 1);

        board.setAt(0, 0, 5);
        Assert.assertEquals(board.at(0, 0), new Integer(5));
    }

    @Test
    public void checkTwoRowsCombine() {
        Board<Integer> board = new Board<>(2, 2);

        Assert.assertEquals(board.at(0, 0), null);
        Assert.assertEquals(board.at(0, 1), null);
        Assert.assertEquals(board.at(1, 0), null);
        Assert.assertEquals(board.at(1, 1), null);

        board.setAt(0, 0, 1);
        board.setAt(0, 1, 2);
        board.setAt(1, 0, 3);
        board.setAt(1, 1, 4);

        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 2; i++)
            arrayList.add(board.at(0, i) + board.at(1, i));
        // Combine rows
        // [1, 2]
        // [3, 4]
        //combineRows(0, 1, ...) =>
        // [1, 2]
        // [4, 6]
        board.combineRows(0, 1,

                //lambda function - rule how we combine two numbers
                (Integer x, Integer y) -> {
                    if (x == null && y == null) return null;
                    if (x == null) return y;
                    if (y == null) return x;
                    return x + y;
                });

        Assert.assertEquals(arrayList, board.getRow(1));
    }

    @Test
    public void columnsCombineTest() {
        Board<Double> board = new Board<>(2, 2, 1.2);

        // Add second column to first column
        board.combineColumns(1, 0, (Double x, Double y) -> {
            if (x == null && y == null) return null;
            if (x == null) return y;
            if (y == null) return x;
            return x + y;
        });
        System.out.println("adding second column to first column");
        board.print();

        // checking first column
        for (int i = 0; i < board.rows(); i++)
            Assert.assertEquals(board.at(i, 0), (Double)2.4);

        // checking second column
        for (int i = 0; i < board.rows(); i++)
            Assert.assertEquals(board.at(i, 1), (Double)1.2);
    }

    @Test
    public void appendTest() {

        Board<Character> originboard = new Board<>(2, 2, 'a');
        Board<Character> appendedBoard = new Board<>(1,3,'b');

        Board<Character> b1 = new Board<>(originboard);
        b1.appendHorizontally(appendedBoard);

        System.out.println("Horizontal append");
        originboard.print();
        System.out.println("+");
        appendedBoard.print();
        System.out.println("==");
        b1.print();

        Board<Character> b2 = new Board<>(originboard);
        b2.appendVertically(appendedBoard);
        System.out.println("Vertical apppend");
        originboard.print();
        System.out.println("+");
        appendedBoard.print();
        System.out.println("==");
        b2.print();

    }

    @Test
    public void iteratorTest() {
        Board<Double> board = new Board<>(3, 3, 1.5);

        for (Double x : board)
            Assert.assertEquals(x, (Double)1.5);
    }




}
