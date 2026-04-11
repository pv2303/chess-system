package boardgame;

public class Piece {

    protected Position position; // é uma matriz e não deve ser visível na camada do xadrez
    private Board board;

    public Piece(Board board) {
        this.board = board; // automaticamente aplica nulo em position
    }

    // somente classes no pacote e subclasses podem acessar o board
    protected Board getBoard() {
        return board;
    }
}
