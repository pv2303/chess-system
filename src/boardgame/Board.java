package boardgame;

public class Board {

    // um board tem que ter sua quantidade de linhas e colunas
    private final int rows;
    private final int cols;

    // Matriz de peças
    private Piece[][] pieces;

    public Board(int cols, int rows) {

        if (rows < 1 || cols < 1) {
            throw new BoardException("Error creating board: Invalid rows or columns number (at least 1 for each)");
        }
        this.cols = cols;
        this.rows = rows;
        pieces = new Piece[rows][cols];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    // pegando a peça por localização row-col
    public Piece piece(int row, int col) {
        if (!positionExists(row, col)) {
            throw new BoardException("Position is not on the board");
        }
        return pieces[row][col];
    }

    // pegando a peça por position
    public Piece piece(Position pos) {
        if (!positionExist(pos)) {
            throw new BoardException("Position is not on the board");
        }
        return pieces[pos.getRow()][pos.getCol()];
    }

    public void placePiece(Piece piece, Position pos) {
        if (thereIsAPiece(pos)) {
            throw new BoardException("There is already a piece on position " + pos);
        }
        // atribuir a peça à uma posição
        pieces[pos.getRow()][pos.getCol()] = piece;
        piece.position = pos;
        // PS: pode ser acessada diretamente pois está em boardgame, isto é,
        // no mesmo pacote que Board, respeitando sua classificação protected
    }

    public Piece removePiece(Position pos) {
        if (!positionExist(pos)) {
            throw new BoardException("Position is not on the board");
        }

        if (piece(pos) == null) {
            return null;
        }
        Piece aux = piece(pos);
        aux.position = null; // ou seja ela foi retirada do tabuleiro
        pieces[pos.getRow()][pos.getCol()] = null;
        
        return aux;
    }

    private boolean positionExists(int row, int col) {
        return row >= 0 && row <= rows && col >= 0 && col < cols;
    }

    public boolean positionExist(Position pos) {
        return positionExists(pos.getRow(), pos.getCol());
    }

    public boolean thereIsAPiece(Position pos) {
        if (!positionExist(pos)) {
            throw new BoardException("Position is not on the board");
        }
        return piece(pos) != null;
    }

}
