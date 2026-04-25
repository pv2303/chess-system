package application;

import boardgame.Board;
import boardgame.Position;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Program {
    void main() {

        // board de xadrez tem 8 linhas e 8 colunas
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<ChessPiece>();

        while (!chessMatch.isCheckMate()) {
            try {
                UI.clearScreen(); // limpa a tela a cada rodada
                UI.printMatch(chessMatch, captured);
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(IO.readln());

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                // sobrecarga para colocar também para onde podemos ir com a peça
                UI.printBoard(chessMatch.getPieces(), possibleMoves);

                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(IO.readln());

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
                if (capturedPiece != null) {
                    captured.add(capturedPiece);
                    System.out.println("Piece Captured: " + capturedPiece);
                }

                if (chessMatch.getPromoted() != null) {
                    IO.print("Enter piece for promotion (B/N/R/Q): ");
                    String type = IO.readln().toUpperCase();
                    while (!type.equals("B")
                            && !type.equals("N")
                            && !type.equals("R")
                            && !type.equals("Q")) {
                        IO.print("Invalid type! Enter piece for promotion (B/N/R/Q): ");
                        type = IO.readln().toUpperCase();
                    }
                    chessMatch.replacePromotedPiece(type);
                }
            }
            catch (ChessException | InputMismatchException e) {
                IO.println(e.getMessage());
                IO.readln(); // apertar a tecla enter
            }
        }
        UI.clearScreen();
        UI.printMatch(chessMatch, captured);

    }
}
