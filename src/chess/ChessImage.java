package chess;

import java.io.File;
import javafx.scene.image.Image;

public class ChessImage {

    private String image[][] = new String[2][7];
    private File fileImage[][] = new File[2][7];
    private static final Image iv[][] = new Image[2][7];
    private double halfWidth[][] = new double[2][7];
    private double halfHeight[][] = new double[2][7];

    public ChessImage() {
        fileImage[0][0] = new File("");
        fileImage[1][0] = new File("");
        fileImage[0][1] = new File("chessPiecesIcon\\rookBlack.png");
        fileImage[1][1] = new File("chessPiecesIcon\\rookWhite.png");
        fileImage[0][2] = new File("chessPiecesIcon\\knightBlack.png");
        fileImage[1][2] = new File("chessPiecesIcon\\knightWhite.png");
        fileImage[0][3] = new File("chessPiecesIcon\\bishopBlack.png");
        fileImage[1][3] = new File("chessPiecesIcon\\bishopWhite.png");
        fileImage[0][4] = new File("chessPiecesIcon\\queenBlack.png");
        fileImage[1][4] = new File("chessPiecesIcon\\queenWhite.png");
        fileImage[0][5] = new File("chessPiecesIcon\\kingBlack.png");
        fileImage[1][5] = new File("chessPiecesIcon\\kingWhite.png");
        fileImage[0][6] = new File("chessPiecesIcon\\pawnBlack.png");
        fileImage[1][6] = new File("chessPiecesIcon\\pawnWhite.png");
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 6; j++) {
                try {
                    image[i][j] = fileImage[i][j].toURI().toURL().toString();
                    iv[i][j] = new Image(image[i][j], 7 * Data.canh / 10, 7 * Data.canh / 10, true, true);
                } catch (Exception ex) {

                }
            }
        }
    }

    public static Image getIv(int i, boolean j) {
        if (j) {
            return iv[1][i];
        } else {
            return iv[0][i];
        }
    }
}
