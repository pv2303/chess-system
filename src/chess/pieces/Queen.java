package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {

    public Queen(Board board, Color color) {super(board, color);}

    @Override
    public String toString() {return "Q";}

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];

        Position p = new Position(0,0);

        // acima da peça
        p.setValues(position.getRow() - 1, position.getCol());
        // enquanto a posição p existir e não houver peças: marque como verdadeira
        while (canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
            p.setRow(p.getRow() - 1);
        }
        // Se chegar e tiver peça, marca como possível também
        if (canCapture(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // à esquerda da peça
        p.setValues(position.getRow(), position.getCol() - 1);
        // enquanto a posição p existir e não houver peças: marque como verdadeira
        while (canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
            p.setCol(p.getCol() - 1);
        }
        // Se chegar e tiver peça, marca como possível também
        if (canCapture(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // à direita da peça
        p.setValues(position.getRow(), position.getCol() + 1);
        // enquanto a posição p existir e não houver peças: marque como verdadeira
        while (canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
            p.setCol(p.getCol() + 1);
        }
        // Se chegar e tiver peça, marca como possível também
        if (getBoard().positionExist(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // abaixo da peça
        p.setValues(position.getRow() + 1, position.getCol());
        // enquanto a posição p existir e não houver peças: marque como verdadeira
        while (canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
            p.setRow(p.getRow() + 1);
        }
        // Se chegar e tiver peça, marca como possível também
        if (canCapture(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // Noroeste
        p.setValues(position.getRow() - 1, position.getCol() - 1);
        // enquanto a posição p existir e não houver peças: marque como verdadeira
        while (canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
            p.setValues(p.getRow() - 1, p.getCol() - 1);
        }
        // Se chegar e tiver peça, marca como possível também
        if (canCapture(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // Nordeste
        p.setValues(position.getRow() - 1, position.getCol() + 1);
        while (canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
            p.setValues(p.getRow() - 1, p.getCol() + 1);
        }
        if (canCapture(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // Sudoeste
        p.setValues(position.getRow() + 1, position.getCol() - 1);
        while (canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
            p.setValues(p.getRow() + 1, p.getCol() - 1);
        }
        if (canCapture(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }

        // Sudeste
        p.setValues(position.getRow() + 1, position.getCol() + 1);
        while (canMove(p)) {
            mat[p.getRow()][p.getCol()] = true;
            p.setValues(p.getRow() + 1, p.getCol() + 1);
        }

        if (canCapture(p)) {
            mat[p.getRow()][p.getCol()] = true;
        }
        return mat;
    }

    private boolean canMove(Position p) {
        return getBoard().positionExist(p) && !getBoard().thereIsAPiece(p);
    }

    private boolean canCapture(Position p) {
        return getBoard().positionExist(p) && isThereOpponentPiece(p);
    }
}
