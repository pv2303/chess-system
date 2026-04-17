package application;

import boardgame.Board;
import boardgame.Position;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.InputMismatchException;

public class Program {
    void main() {

        // board de xadrez tem 8 linhas e 8 colunas
        ChessMatch chessMatch = new ChessMatch();

        while (true) {
            try {
                UI.clearScreen(); // limpa a tela a cada rodada
                UI.printBoard(chessMatch.getPieces());
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(IO.readln());

                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(IO.readln());

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
                if (capturedPiece != null) {
                    System.out.println("Piece Captured: " + capturedPiece);
                }
            }
            catch (ChessException | InputMismatchException e) {
                IO.println(e.getMessage());
                IO.readln(); // apertar a tecla enter
            }
        }

    }
}
