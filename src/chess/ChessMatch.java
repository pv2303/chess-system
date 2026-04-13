package chess;

import boardgame.Board;

// coração do sistema de xadrez com as regras e afins
public class ChessMatch {

    // partida de xadrez tem que ter um tabuleiro
    private final Board board;

    // uma partida de xadrez tem que saber quantas linhas e colunas
    public ChessMatch() {
        board = new Board(8, 8);
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
}
