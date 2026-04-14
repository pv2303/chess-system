package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

// coração do sistema de xadrez com as regras e afins
public class ChessMatch {

    // partida de xadrez tem que ter um tabuleiro
    private final Board board;

    // uma partida de xadrez tem que saber quantas linhas e colunas
    public ChessMatch() {
        board = new Board(8, 8);
        initialSetup();
    }

    // Tem que retornar uma matriz de peças de xadrez
    // assim ele reconhece apenas a camada de xadrez
    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getCols()];
        for (int i=0; i<board.getRows(); i++) {
            for (int j=0; j<board.getCols(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }

    private void placeNewPiece(char col, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(col, row).toPosition());
    }

    private void initialSetup() {
        // Testando o placePiece
        placeNewPiece('b', 6, new Rook(board, Color.WHITE));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('e', 1, new King(board, Color.BLACK));
    }
}
