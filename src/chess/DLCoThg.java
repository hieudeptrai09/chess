package chess;

public class DLCoThg {
    
    private static int blackKing[] = {0, 4};
    private static int whiteKing[] = {7, 4};

    public void preparing() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Data.square[i][j] = new Square();
                Data.square[i][j].setYellow(false);
            }
        }
        
        Data.square[0][0].setPiece(Data.ROOK);
        Data.square[0][1].setPiece(Data.KNIGHT);
        Data.square[0][2].setPiece(Data.BISHOP);
        Data.square[0][3].setPiece(Data.QUEEN);
        Data.square[0][4].setPiece(Data.KING);
        Data.square[0][5].setPiece(Data.BISHOP);
        Data.square[0][6].setPiece(Data.KNIGHT);
        Data.square[0][7].setPiece(Data.ROOK);
        for (int i = 0; i < 8; i++) {
            Data.square[1][i].setPiece(Data.PAWN);
            Data.square[0][i].setBelow(false);
            Data.square[1][i].setBelow(false);
            Data.square[0][i].setMove(false);
            Data.square[1][i].setMove(false);
            Data.square[0][i].setWhite(false);
            Data.square[1][i].setWhite(false);
        }

        Data.square[7][0].setPiece(Data.ROOK);
        Data.square[7][1].setPiece(Data.KNIGHT);
        Data.square[7][2].setPiece(Data.BISHOP);
        Data.square[7][3].setPiece(Data.QUEEN);
        Data.square[7][4].setPiece(Data.KING);
        Data.square[7][5].setPiece(Data.BISHOP);
        Data.square[7][6].setPiece(Data.KNIGHT);
        Data.square[7][7].setPiece(Data.ROOK);
        for (int i = 0; i < 8; i++) {
            Data.square[6][i].setPiece(Data.PAWN);
            Data.square[7][i].setBelow(true);
            Data.square[6][i].setBelow(true);
            Data.square[7][i].setMove(false);
            Data.square[6][i].setMove(false);
            Data.square[7][i].setWhite(true);
            Data.square[6][i].setWhite(true);
        }

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                Data.square[i][j].setPiece(Data.NOTHING);
            }
        }
    }

    public void datQuan() throws Exception {
        ChessImage image = new ChessImage();
        for (int i = 0; i < 8; i++) {
            Data.imageView[0][i].setImage(ChessImage.getIv(Data.square[0][i].getPiece(), Data.square[0][i].isWhite()));
            Data.imageView[1][i].setImage(ChessImage.getIv(Data.square[1][i].getPiece(), Data.square[1][i].isWhite()));
            Data.imageView[6][i].setImage(ChessImage.getIv(Data.square[6][i].getPiece(), Data.square[6][i].isWhite()));
            Data.imageView[7][i].setImage(ChessImage.getIv(Data.square[7][i].getPiece(), Data.square[7][i].isWhite()));
        }
    }

    public static int[] getBlackKing() {
        return blackKing;
    }

    public static int[] getWhiteKing() {
        return whiteKing;
    }
    
    
}
