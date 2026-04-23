package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
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

            // En Passant
            //  Peão branco só pode executar esse movimento na linha 3
            if (position.getRow() == 3) {
                Position left = new Position(position.getRow(), position.getCol() - 1);
                boolean canCaptureLeft = getBoard().positionExist(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable();
                 if (canCaptureLeft) {
                    possibleMoves[left.getRow() - 1][ left.getCol()] = true;
                }
                Position right = new Position(position.getRow(), position.getCol() + 1);
                boolean canCaptureRight = getBoard().positionExist(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable();
                if (canCaptureRight) {
                    possibleMoves[right.getRow() - 1][ right.getCol()] = true;
                }
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

            // En Passant
            //  Peão preto só pode executar esse movimento na linha 4
            if (position.getRow() == 4) {
                Position left = new Position(position.getRow(), position.getCol() - 1);
                boolean canCaptureLeft = getBoard().positionExist(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable();
                if (canCaptureLeft) {
                    possibleMoves[left.getRow() + 1][ left.getCol()] = true;
                }
                Position right = new Position(position.getRow(), position.getCol() + 1);
                boolean canCaptureRight = getBoard().positionExist(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable();
                if (canCaptureRight) {
                    possibleMoves[right.getRow() + 1][ right.getCol()] = true;
                }
            }
        }

        return possibleMoves;
    }
}
