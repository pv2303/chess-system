package chess;

import boardgame.Board;
import boardgame.Piece;
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

    public ChessPiece performChessMove(ChessPosition sourcePos, ChessPosition targetPos) {
        Position source = sourcePos.toPosition();
        Position target = targetPos.toPosition();
        validateSourcePosition(source);
        Piece capturedPiece = makeMove(source, target);
        return (ChessPiece) capturedPiece;
    }

    private void validateSourcePosition(Position pos) {
        if (!board.thereIsAPiece(pos)) {
            throw new ChessException("There is no piece on source position");
        }
        // validação de movimentos possíveis
        if (!board.piece(pos).isThereAnyPossibleMove()){
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }

    private Piece makeMove(Position source, Position target) {
        // Quando remove, o programa "gera" o objeto da peça
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        // bota a peça no target
        board.placePiece(p, target);
        return capturedPiece; // Se tiver 
    }

    private void placeNewPiece(char col, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(col, row).toPosition());
    }

    private void initialSetup() {
        // Testando o placePiece
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }
}
