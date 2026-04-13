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

    private void initialSetup() {
        // Testando o placePiece
        board.placePiece(new Rook(board, Color.WHITE), new Position(2, 1));
        board.placePiece(new King(board, Color.WHITE), new Position(0, 4));
        board.placePiece(new King(board, Color.WHITE), new Position(7, 4));
    }
}
