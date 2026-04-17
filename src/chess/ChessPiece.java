package chess;

import boardgame.Board;
import boardgame.Piece;

public abstract class ChessPiece extends Piece {
    // torná-la abstrata nos previne de adicionar os métodos
    private final Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
