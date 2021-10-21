package chess;

public class Square {

    private boolean below;
    private int piece;
    private boolean yellow;
    private boolean white;
    private boolean move;
    private int shield;
    private int shieldPiece;

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public boolean isBelow() {
        return below;
    }

    public void setBelow(boolean below) {
        this.below = below;
    }

    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }

    public boolean isYellow() {
        return yellow;
    }

    public void setYellow(boolean yellow) {
        this.yellow = yellow;
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getShieldPiece() {
        return shieldPiece;
    }

    public void setShieldPiece(int shieldPiece) {
        this.shieldPiece = shieldPiece;
    }
}
