package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

// coração do sistema de xadrez com as regras e afins
public class ChessMatch {


    private int turn;
    private Color currentPlayer;
    // partida de xadrez tem que ter um tabuleiro
    private final Board board;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;

    // As peças não devem ser reatribuídas
    private final List<Piece> piecesOnBoard;
    private final List<Piece> piecesCaptured;

    // uma partida de xadrez tem que saber quantas linhas e colunas
    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        piecesOnBoard = new ArrayList<>();
        piecesCaptured = new ArrayList<>();
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
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

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position pos = sourcePosition.toPosition();
        validateSourcePosition(pos);
        return board.piece(pos).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePos, ChessPosition targetPos) {
        Position source = sourcePos.toPosition();
        Position target = targetPos.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        if (testCheck(currentPlayer)){
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }

        ChessPiece movedPiece = (ChessPiece) board.piece(target);

        // Verificação para En Passant
        boolean isPawn = movedPiece instanceof Pawn;
        boolean isTwoSteps = target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2;
        if (isPawn && isTwoSteps) {
            enPassantVulnerable = movedPiece;
        }
        else {
            enPassantVulnerable = null;
        }

        // Verificação de Special Move
        promoted = null;
        boolean promoCondWhite = movedPiece.getColor() == Color.WHITE && target.getRow() == 0;
        boolean promoCondBlack = movedPiece.getColor() == Color.BLACK && target.getRow() == 7;
        if (isPawn && (promoCondWhite || promoCondBlack)) {
            promoted = (ChessPiece) board.piece(target);
            promoted = replacePromotedPiece("Q");
        }

        // Testar se a jogada gerou xeque
        check = testCheck(opponent(currentPlayer));

        // Testar se o jogo acabou com essa jogada
        checkMate = testCheckMate(opponent(currentPlayer));

        if (!checkMate) {
            nextTurn();
        }
        return (ChessPiece) capturedPiece;
    }

    public ChessPiece replacePromotedPiece(String type) {
        if (promoted == null) {
            throw new IllegalArgumentException("Promoted piece can't be null");
        }
        if (!type.equals("B")
            && !type.equals("N")
            && !type.equals("R")
            && !type.equals("Q")) {
            return promoted; // retorna a rainha se dá errado
        }

        Position pos = promoted.getChessPosition().toPosition();
        Piece p = board.removePiece(pos);
        piecesOnBoard.remove(p);

        ChessPiece newPiece = newPiece(type, promoted.getColor());
        board.placePiece(newPiece, pos);
        piecesOnBoard.add(newPiece);

        return newPiece;
    }

    private ChessPiece newPiece(String type, Color color) {
        return switch (type) {
            case "B" -> new Bishop(board, color);
            case "N" -> new Knight(board, color);
            case "Q" -> new Queen(board, color);
            default -> new Rook(board, color);
        };
    }

    private void validateSourcePosition(Position pos) {
        if (!board.thereIsAPiece(pos)) {
            throw new ChessException("There is no piece on source position");
        }
        // Validar se o jogador atual ta movendo a peça dele
        if (currentPlayer != ((ChessPiece)(board.piece(pos))).getColor()) {
            throw new ChessException("The chosen piece is not yours");
        }
        // validação de movimentos possíveis
        if (!board.piece(pos).isThereAnyPossibleMove()){
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        // Se para a peça de origem, o target não é um movimento possível, throw exception
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece can't move to target position");
        }
    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private Piece makeMove(Position source, Position target) {
        // Quando remove, o programa "gera" o objeto da peça
        ChessPiece p = (ChessPiece) board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        // se houve captura, realoca nas listas
        if (capturedPiece != null) {
            piecesOnBoard.remove(capturedPiece);
            piecesCaptured.add(capturedPiece);
        }
        // bota a peça no target
        board.placePiece(p, target);

        // Mov. de Castling
        //  (Kingside rook)
        if (p instanceof King && target.getCol() == source.getCol() + 2) {
            Position sourceR = new Position(source.getRow(), source.getCol() + 3);
            Position targetR = new Position(target.getRow(), target.getCol() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceR);
            board.placePiece(rook, targetR);
            rook.increaseMoveCount();

        }
        //  (Queen rook)
        if (p instanceof King && target.getCol() == source.getCol() - 2) {
            Position sourceR = new Position(source.getRow(), source.getCol() - 4);
            Position targetR = new Position(target.getRow(), target.getCol() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceR);
            board.placePiece(rook, targetR);
            rook.increaseMoveCount();
        }

        // Mov. En Passant
        //  A captura do En Passant ocorre quando o peão se move em diagonal, porém não há peça aonde ele parou
        boolean enPassantCapture = p instanceof Pawn && source.getCol() != target.getCol() && capturedPiece == null;
        if (enPassantCapture) {
            Position pawnPosition;
            // Se o peão que se moveu é branco, o peão que ele capturou estará abaixo dele
            if (p.getColor() == Color.WHITE) {
                pawnPosition = new Position(target.getRow() + 1, target.getCol());
            }
            // Se o peão que se moveu é preto, o peão que ele capturou estará acima dele
            else {
                pawnPosition = new Position(target.getRow() - 1, target.getCol());

            }
            capturedPiece = board.removePiece(pawnPosition);
            piecesCaptured.add(capturedPiece);
            piecesOnBoard.remove(capturedPiece);
        }
        return capturedPiece; // Se tiver 
    }

    // Em situ de checks, deve-se desfazer o movimento
    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p, source);

        // Desfazendo o Mov. De Castling
        //  (Kingside rook)
        if (p instanceof King && target.getCol() == source.getCol() + 2) {
            Position sourceR = new Position(source.getRow(), source.getCol() + 3);
            Position targetR = new Position(target.getRow(), target.getCol() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetR);
            board.placePiece(rook, sourceR);
            rook.decreaseMoveCount();

        }
        //  (Queen rook)
        if (p instanceof King && target.getCol() == source.getCol() - 2) {
            Position sourceR = new Position(source.getRow(), source.getCol() - 4);
            Position targetR = new Position(target.getRow(), target.getCol() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetR);
            board.placePiece(rook, sourceR);
            rook.decreaseMoveCount();
        }

        // Mov. En Passant
        //  A captura do En Passant ocorre quando o peão se move em diagonal, porém não há peça aonde ele parou
        if (p instanceof Pawn && source.getCol() != target.getCol() && capturedPiece == enPassantVulnerable) {
            ChessPiece pawn = (ChessPiece) capturedPiece;
            Position pawnPosition;
            // Se o peão que se moveu é branco, o peão que ele capturou estará abaixo dele
            if (p.getColor() == Color.WHITE) {
                pawnPosition = new Position(3, target.getCol());
            }
            // Se o peão que se moveu é preto, o peão que ele capturou estará acima dele
            else {
                pawnPosition = new Position(4, target.getCol());
            }
            board.placePiece(pawn, pawnPosition);
            piecesOnBoard.add(pawn);
            piecesCaptured.remove(pawn);
            capturedPiece = null;
        }

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            piecesOnBoard.add(capturedPiece);
            piecesCaptured.remove(capturedPiece);
        }
    }

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color) {
        List<Piece> list = piecesOnBoard.stream()
                .filter(x -> ((ChessPiece) x).getColor() == color)
                .toList();
        for (Piece piece : list) {
            if (piece instanceof King){
                return (ChessPiece) piece;
            }
        }
        // === isso NÃO DEVE ACONTECER ===
        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnBoard.stream()
                .filter(x -> ((ChessPiece) x).getColor() == opponent(color))
                .toList();
        for (Piece piece : opponentPieces) {
            boolean[][] possibleMoves = piece.possibleMoves();
            if (possibleMoves[kingPosition.getRow()][kingPosition.getCol()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }
        // verificar se algum o movimento de alguma peça tira do cheque
        List<Piece> list = piecesOnBoard.stream()
                .filter(x -> ((ChessPiece) x).getColor() == color)
                .toList();
        for (Piece p : list) {
            boolean[][] possibleMoves = p.possibleMoves();
            for (int i=0; i<board.getRows(); i++) {
                for (int j=0; j<board.getCols(); j++) {
                    if (possibleMoves[i][j]) {
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean stillCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!stillCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void placeNewPiece(char col, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(col, row).toPosition());
        piecesOnBoard.add(piece);
    }

    private void initialSetup() {
        // Placing pieces
        for (char c = 'a'; c <= 'h'; c++) {
            // Rooks
            if (c == 'a' || c == 'h') {
                placeNewPiece(c, 8, new Rook(board, Color.BLACK));
                placeNewPiece(c, 1, new Rook(board, Color.WHITE));
            }

            // Bishops
            if (c == 'c' || c == 'f') {
                placeNewPiece(c, 8, new Bishop(board, Color.BLACK));
                placeNewPiece(c, 1, new Bishop(board, Color.WHITE));
            }

            // Knights
            if (c == 'b' || c == 'g') {
                placeNewPiece(c, 8, new Knight(board, Color.BLACK));
                placeNewPiece(c, 1, new Knight(board, Color.WHITE));
            }

            // Queens
            // Kings
            if (c == 'd') {
                placeNewPiece(c, 8, new Queen(board, Color.BLACK));
                placeNewPiece(c, 1, new Queen(board, Color.WHITE));
            }

            // Kings
            if (c == 'e') {
                placeNewPiece(c, 8, new King(board, Color.BLACK, this));
                placeNewPiece(c, 1, new King(board, Color.WHITE, this));
            }

            // Pawns
            placeNewPiece(c, 7, new Pawn(board, Color.BLACK, this));
            placeNewPiece(c, 2, new Pawn(board, Color.WHITE, this));
        }
    }

    public boolean isCheck() {
        return check;
    }

    public boolean isCheckMate() {
        return checkMate;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromoted() {
        return promoted;
    }
}
