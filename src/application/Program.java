package application;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;

import java.util.Scanner;

public class Program {
    void main() {

        // board de xadrez tem 8 linhas e 8 colunas
        ChessMatch chessMatch = new ChessMatch();
        UI.printBoard(chessMatch.getPieces());
    }
}
