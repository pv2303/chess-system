package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_DARK_PURPLE_BACKGROUND = "\u001b[48;5;53m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void clearScreen() {
        IO.print("\033[H\033[2J");
        System.out.flush();
    }

    public static ChessPosition readChessPosition(String s) {
        try {
            char col = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(col, row);
        }
        catch (RuntimeException e) {
            throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8");
        }
    }

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces());
        IO.println();
        printCapturedPieces(captured);
        IO.println("Turn: " + chessMatch.getTurn());
        if (!chessMatch.isCheckMate()) {
            IO.println("Waiting player: " + chessMatch.getCurrentPlayer());
            if (chessMatch.isCheck()) {
                IO.println("Player " + chessMatch.getCurrentPlayer() + " is in CHECK");
            }
        }
        else {
            IO.println("CHECKMATE!");
            IO.println("Winner: " + chessMatch.getCurrentPlayer());
        }
    }

    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            IO.print((8-i) + " ");
            // assume matriz quadrada (8x8)
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], false);
            }
            IO.println();
        }
        IO.println("  a b c d e f g h");
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][]  possibleMoves) {
        for (int i = 0; i < pieces.length; i++) {
            IO.print((8-i) + " ");
            // assume matriz quadrada (8x8)
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }
            IO.println();
        }
        IO.println("  a b c d e f g h");
    }

    // imprimi uma única peça
    private static void printPiece(ChessPiece piece, boolean background) {
        if (background) {
            System.out.print(ANSI_DARK_PURPLE_BACKGROUND);
        }
        if (piece == null) {
            IO.print("-" + ANSI_RESET);
        }
        else {
            if (piece.getColor() == Color.WHITE) {
                IO.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                IO.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        IO.print(" ");
    }

    private static void printCapturedPieces(List<ChessPiece> captured) {
        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).toList();
        List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).toList();
        IO.println("Captured pieces:");
        IO.println(ANSI_WHITE + "White: " + Arrays.toString(white.toArray()));
        IO.print(ANSI_RESET);
        IO.println(ANSI_YELLOW + "Black: " + Arrays.toString(black.toArray()));
        IO.print(ANSI_RESET);
    }
}
