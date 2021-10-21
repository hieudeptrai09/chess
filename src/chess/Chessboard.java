package chess;

import java.io.File;
import java.net.MalformedURLException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Chessboard {

    private final ButtonEvent buttonEvent[][] = new ButtonEvent[8][8];
    private final ImageEvent imageEvent[][] = new ImageEvent[8][8];
    private final Label x[] = new Label[8];
    private final Label y[] = new Label[8];
    private final File file = new File("");
    private final String url;

    public Chessboard() throws MalformedURLException {
        this.url = file.toURI().toURL().toString();
    }

    public void paint(Stage stage) {
        Pane playingZone = new Pane();
        Pane xZone = new Pane();
        Pane yZone = new Pane();
        Pane bigPane = new Pane();
        Font fontChuan = new Font("Constantia", 3*Data.canh/10);
        Rectangle taoHinh = new Rectangle(1000, 1000);

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                Data.chessboard[i][j] = new Button();
                Data.chessboard[i][j].setTranslateX(Data.canh * j + Data.canh/2);
                Data.chessboard[i][j].setTranslateY(Data.canh * i);
                Data.chessboard[i][j].setPrefSize(Data.canh, Data.canh);
                Data.chessboard[i][j].setShape(taoHinh);
                if ((i + j) % 2 == 0) {
                    Data.chessboard[i][j].setStyle("-fx-color: white");
                } else {
                    Data.chessboard[i][j].setStyle("-fx-color: lime");
                }
                Data.imageView[i][j] = new ImageView(new Image(url));
                Data.imageView[i][j].setTranslateX(Data.canh * j + 13 * Data.canh / 20);
                Data.imageView[i][j].setTranslateY(Data.canh * i + 3 * Data.canh / 20);
                bigPane.getChildren().add(Data.chessboard[i][j]);
                bigPane.getChildren().add(Data.imageView[i][j]);
                buttonEvent[i][j] = new ButtonEvent(i, j);
                Data.chessboard[i][j].setOnAction(buttonEvent[i][j]);
                imageEvent[i][j] = new ImageEvent(i, j);
                Data.imageView[i][j].setOnMousePressed(imageEvent[i][j]);
                
            }
        }

        for (int i = 0; i < 8; i++) {
            x[i] = new Label();
            x[i].setTranslateX(i * Data.canh + Data.canh/2);
            x[i].setTranslateY(8 * Data.canh);
            x[i].setPrefSize(Data.canh, Data.canh / 2);
            x[i].setText("" + (char) (i + 65));
            x[i].setFont(fontChuan);
            x[i].setAlignment(Pos.CENTER);
            bigPane.getChildren().add(x[i]);

            y[i] = new Label();
            y[i].setTranslateY(i * Data.canh);
            y[i].setPrefSize(Data.canh / 2, Data.canh);
            y[i].setText("" + (8 - i));
            y[i].setFont(fontChuan);
            y[i].setAlignment(Pos.CENTER);
            bigPane.getChildren().add(y[i]);
        }

        Scene scene = new Scene(bigPane, 8*Data.canh+Data.canh/2, 8*Data.canh+Data.canh/2);
        stage.setScene(scene);
        Data.stage = stage;
        stage.show();
    }

}
