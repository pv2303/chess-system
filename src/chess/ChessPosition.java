package chess;

import boardgame.Position;

public class ChessPosition {

    private char col; // de a à h
    private int row; // 1 a 8. Subtrair 8 por 8 da zero que é a posição inicial

    public ChessPosition(char col, int row) {
        if (col < 'a' || col > 'h' || row < 1 || row > 8) {
            throw new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8");
        }
        this.col = col;
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public char getCol() {
        return col;
    }

    // matrix_row = 8 - chess_row
    // matrix_column = chess_column - 'a'
    protected Position toPosition() {
        int row = 8 - this.row;
        int col = this.col - 'a';
        return new Position(row, col);
    }
    protected static ChessPosition fromPosition(Position pos) {
        char col = (char) ('a' + pos.getCol());
        int row = 8 - pos.getRow();
        return new ChessPosition(col, row);
    }

    @Override
    public String toString() {
        return "" + col + row; // o "" no início força o compilador a entender que é concat.
    }
}
