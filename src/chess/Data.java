package chess;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Data {
    
    public static final int NOTHING = 0;
    public static final int ROOK = 1;
    public static final int KNIGHT = 2;
    public static final int BISHOP = 3;
    public static final int QUEEN = 4;
    public static final int KING = 5;
    public static final int PAWN = 6;
    public static Square square[][] = new Square[8][8];
    public static Button chessboard[][] = new Button[8][8];
    public static ImageView imageView[][] = new ImageView[8][8];
    public static Stage stage;
    public static final int canh = 80;
    
}
