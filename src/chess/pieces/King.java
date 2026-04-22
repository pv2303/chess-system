package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    private final ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
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

        // Castling
        if (getMoveCount() == 0 && !chessMatch.isCheck()) {
            // small rook (lado rei)
            Position posSmallRook = new Position(position.getRow(), position.getCol() + 3);
            if (testRookCastling(posSmallRook)) {
                Position posLeft1 = new Position(position.getRow(), position.getCol() + 1);
                Position posLeft2 = new Position(position.getRow(), position.getCol() + 2);
                if (getBoard().piece(posLeft1) == null && getBoard().piece(posLeft2) == null) {
                    mat[position.getRow()][position.getCol() + 2] = true;
                }
            }
            // large rook (lado rainha)
            Position posLargeRook = new Position(position.getRow(), position.getCol() - 4);
            if (testRookCastling(posLargeRook)) {
                Position posRight1 = new Position(position.getRow(), position.getCol() - 1);
                Position posRight2 = new Position(position.getRow(), position.getCol() - 2);
                Position posRight3 = new Position(position.getRow(), position.getCol() - 3);
                if (getBoard().piece(posRight1) == null && getBoard().piece(posRight2) == null && getBoard().piece(posRight3) == null) {
                    mat[position.getRow()][position.getCol() - 2] = true;
                }
            }
        }
        return mat; // por enquanto retorna todas as posições como falso
    }

    private boolean canMove(Position pos) {
        ChessPiece p = (ChessPiece) getBoard().piece(pos);
        return p == null || p.getColor() != getColor();
    }

    private boolean testRookCastling(Position pos) {
        ChessPiece p = (ChessPiece) getBoard().piece(pos);
        return p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
    }
}
