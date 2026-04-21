package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "N";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];

        Position p = new Position(0,0);

        // 2 cima 1 esq.
        p.setValues(position.getRow() - 2, position.getCol() - 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // 2 cima 1 dir.
        p.setValues(position.getRow() - 2, position.getCol() + 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // 1 cima 2 esq
        p.setValues(position.getRow() - 1, position.getCol() - 2);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // 1 cima 2 dir.
        p.setValues(position.getRow() - 1, position.getCol() + 2);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // 2 baixo 1 esq
        p.setValues(position.getRow() + 2, position.getCol() - 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // 2 baixo 1 dir
        p.setValues(position.getRow() + 2, position.getCol() + 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // 1 baixo 2 esq
        p.setValues(position.getRow() + 1, position.getCol() - 2);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // 1 baixo 2 dir
        p.setValues(position.getRow() + 1, position.getCol() + 2);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }
        return mat;
    }

    private boolean canMove(Position pos) {
        ChessPiece p = (ChessPiece) getBoard().piece(pos);
        return p == null || p.getColor() != getColor();
    }
}
