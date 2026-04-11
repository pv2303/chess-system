package boardgame;

public class Board {

    // um board tem que ter sua quantidade de linhas e colunas
    private int rows;
    private int cols;

    // Matriz de peças
    private Piece[][] pieces;

    public Board(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        pieces = new Piece[rows][cols];
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }
}
