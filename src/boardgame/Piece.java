package boardgame;

public abstract class Piece {

    protected Position position; // é uma matriz e não deve ser visível na camada do xadrez
    private Board board;

    public Piece(Board board) {
        this.board = board; // automaticamente aplica nulo em position
    }

    // somente classes no pacote e subclasses podem acessar o board
    protected Board getBoard() {
        return board;
    }

    public abstract boolean[][] possibleMoves();

    // hook method: esse method é concreto e chama um abstrato.
    // o possibleMoves será concreto em uma subclasse
    // Então ele materializa a concretude do possibleMoves
    public boolean possibleMove(Position pos) {
        return possibleMoves()[pos.getRow()][pos.getCol()];
    }

    public boolean isThereAnyPossibleMove() {
        boolean[][] mat = possibleMoves();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if (mat[i][j]) {
                    return true;
                } // se em alguma posicao, há movimentos possiveis, já é o suficiente.
            }
        }
        return false;
    }
}
