package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position pos) {
        ChessPiece p = (ChessPiece) getBoard().piece(pos);
        return p == null || p.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];

        Position p = new Position(0,0);

        // a cima da peça
        p.setValues(position.getRow() - 1, position.getCol());
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // abaixo da peça
        p.setValues(position.getRow() + 1, position.getCol());
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // à esquerda da peça
        p.setValues(position.getRow(), position.getCol() - 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // à direita da peça
        p.setValues(position.getRow(), position.getCol() + 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // noroeste
        p.setValues(position.getRow() - 1, position.getCol() - 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // nordeste
        p.setValues(position.getRow() - 1, position.getCol() + 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // sudoeste
        p.setValues(position.getRow() + 1, position.getCol() - 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // sudeste
        p.setValues(position.getRow() + 1, position.getCol() + 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }
        return mat; // por enquanto retorna todas as posições como falso
    }
}
