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

    // pegando a peça por localização row-col
    public Piece piece(int row, int col) {
        return pieces[row][col];
    }

    // pegando a peça por position
    public Piece piece(Position pos) {
        return pieces[pos.getRow()][pos.getCol()];
    }

    public void placePiece(Piece piece, Position pos) {
        // atribuir a peça à uma posição
        pieces[pos.getRow()][pos.getCol()] = piece;
        piece.position = pos;
        // PS: pode ser acessada diretamente pois está em boardgame, isto é,
        // no mesmo pacote que Board, respeitando sua classificação protected
    }
}
