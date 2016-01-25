import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiFunction;


public class Board<T> implements Iterable<T> {
    private ArrayList<ArrayList<T>> board = new ArrayList<>();
    private int rows, columns;

    // Constructors
    public Board() { this(1, 1); }

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        for (int i = 0; i < rows; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < columns; j++)
                board.get(i).add(null);
        }
    }

    public Board(int rows, int columns, T defaultValue) {
        this(rows, columns);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++)
                board.get(i).set(j, defaultValue);
        }
    }

    public Board(Board<T> originBord) {
        this(originBord.rows(), originBord.columns());
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board.get(i).set(j, originBord.at(i, j));
            }
        }
    }

    // Public methods
    public int totalSize() {
        return columns * rows;
    }

    // return number of rows
    public int rows() {
        return rows;
    }
    // return number of columns
    public int columns() {
        return columns;
    }

    // get ith row
    public ArrayList<T> getRow(int i) {
        return board.get(i);
    }

    // get value from position [i][j]
    public T at(int i, int j) {
        return board.get(i).get(j);
    }

    // assigned value to position [i][j]
    public void setAt(int i, int j, T value) {
        board.get(i).set(j, value);
    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                System.out.printf("%-10.10s",at(i,j) != null ? at(i, j).toString() : "null");
            System.out.println();
        }
    }

    public void combineRows(int rowA, int rowB, BiFunction<T, T, T> sum) {
        for (int j = 0; j < columns; j++) {
            board.get(rowB).set(j,
                    sum.apply(at(rowA, j), at(rowB, j)));
        }
    }

    public void combineColumns(int columnA, int columnB, BiFunction<T, T, T> sum) {
        for (int i = 0; i < rows; i++) {
            board.get(i).set(columnB,
                    sum.apply(at(i, columnA), at(i, columnB)));
        }
    }

    public void appendHorizontally(Board<T> rightBoard) {
        if (rightBoard.rows() > rows) {
            for (int i = rows; i <= rightBoard.rows(); i++) {
                board.add(new ArrayList<>());
                for (int j = 0; j < columns; j++)
                    setAt(i, j, null);
            }

        } else {
            for (int i = rightBoard.rows(); i < rows; i++)
                for (int j = 0; j < rightBoard.columns(); j++)
                    board.get(i).add(null);
        }

        for (int i = 0; i < rightBoard.rows(); i++) {
            for (int j = 0; j < rightBoard.columns(); j++) {
                board.get(i).add(rightBoard.at(i, j));
            }
        }
        updateSize();
    }

    public void appendVertically(Board<T> bottomBoard) {
        for (int i = 0; i < bottomBoard.rows(); i++) {
            board.add(bottomBoard.getRow(i));
        }

        if (columns < bottomBoard.columns()) {
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < bottomBoard.columns() - columns; j++)
                    board.get(i).add(null);
        } else {
            for (int i = rows; i < rows + bottomBoard.rows(); i++) {
                for (int j = columns; j < columns - bottomBoard.columns(); j++)
                    board.get(i).add(null);
            }
        }
        updateSize();
    }


    // PRIVATE method
    void updateSize() {
        rows = board.size();
        columns = board.get(0).size();
    }


    // Iterators
    class MatrixIterator<T> implements Iterator<T> {
        ArrayList<ArrayList<T>> matrix;
        int posi = 0;
        int posj = 0;
        public MatrixIterator(ArrayList<ArrayList<T>> array) {
            matrix = array;
        }
        @Override
        public boolean hasNext() {
            if (posj >= matrix.size()) { posi++; posj = 0; }
            return posi < matrix.size();
        }

        @Override
        public T next() {
           T res = matrix.get(posi).get(posj);
            posj++;
            return res;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new MatrixIterator<>(board);
    }
}
