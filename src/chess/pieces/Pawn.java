package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {


    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getCols()];

        Position p = new Position(0,0);

        if (getColor() == Color.WHITE) {
            // Verificando se ele pode se mover para frente
            p.setValues(position.getRow() - 1,  position.getCol());
            boolean OneStepAhead = getBoard().positionExist(p) && !getBoard().thereIsAPiece(p);
            if (OneStepAhead) {
                possibleMoves[p.getRow()][p.getCol()] = true;
            }

            // Verificando se ele pode se mover 2 casas (Primeira jogada)
            p.setValues(position.getRow() - 2, position.getCol());
            // Verificar se tem algo na frente dele
            Position p2 = new Position(position.getRow() - 1, position.getCol());
            boolean twoStepsAhead = (
                    getBoard().positionExist(p) && !getBoard().thereIsAPiece(p)
                    && getBoard().positionExist(p2) && !getBoard().thereIsAPiece(p2)
                    && getMoveCount() == 0
            );
            if (twoStepsAhead) {
                possibleMoves[p.getRow()][p.getCol()] = true;
            }

            // Se da para atacar à esquerda
            p.setValues(position.getRow() - 1, position.getCol() - 1);
            boolean canCapturedLeft = getBoard().positionExist(p) && isThereOpponentPiece(p);
            if (canCapturedLeft) {
                possibleMoves[p.getRow()][p.getCol()] = true;
            }

            // Se da para atacar à direita
            p.setValues(position.getRow() - 1, position.getCol() + 1);
            boolean canCapturedRight = getBoard().positionExist(p) && isThereOpponentPiece(p);
            if (canCapturedRight) {
                possibleMoves[p.getRow()][p.getCol()] = true;
            }
        }
        else {
            // Verificando se ele pode se mover para frente
            p.setValues(position.getRow() + 1,  position.getCol());
            boolean OneStepAhead = getBoard().positionExist(p) && !getBoard().thereIsAPiece(p);
            if (OneStepAhead) {
                possibleMoves[p.getRow()][p.getCol()] = true;
            }

            // Verificando se ele pode se mover 2 casas (Primeira jogada)
            p.setValues(position.getRow() + 2, position.getCol());
            // Verificar se tem algo na frente dele
            Position p2 = new Position(position.getRow() + 1, position.getCol());
            boolean twoStepsAhead = (
                    getBoard().positionExist(p) && !getBoard().thereIsAPiece(p)
                            && getBoard().positionExist(p2) && !getBoard().thereIsAPiece(p2)
                            && getMoveCount() == 0
            );
            if (twoStepsAhead) {
                possibleMoves[p.getRow()][p.getCol()] = true;
            }

            // Se da para atacar à esquerda
            p.setValues(position.getRow() + 1, position.getCol() - 1);
            boolean canCapturedLeft = getBoard().positionExist(p) && isThereOpponentPiece(p);
            if (canCapturedLeft) {
                possibleMoves[p.getRow()][p.getCol()] = true;
            }

            // Se da para atacar à direita
            p.setValues(position.getRow() + 1, position.getCol() + 1);
            boolean canCapturedRight = getBoard().positionExist(p) && isThereOpponentPiece(p);
            if (canCapturedRight) {
                possibleMoves[p.getRow()][p.getCol()] = true;
            }
        }

        return possibleMoves;
    }
}
