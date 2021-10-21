package chess;

import java.io.File;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PeelPage {
    
    public void start(final Stage primaryStage) throws Exception {
        Pane pane = new Pane();

        File file = new File("iconphay.png");
        String url = file.toURI().toURL().toString();
        ImageView iv = new ImageView(new Image(url));
        pane.getChildren().add(iv);

        Text text = new Text(100, 175, "Cờ vua");
        text.setFill(Color.ORANGE);
        text.setFont(new Font("Constantia", 100));
        pane.getChildren().add(text);

        Polygon taoHinh = new Polygon();
        ObservableList<Double> listButtonCoThuong = taoHinh.getPoints();
        listButtonCoThuong.add(100.0);
        listButtonCoThuong.add(20.0);
        listButtonCoThuong.add(125.0);
        listButtonCoThuong.add(0.0);
        listButtonCoThuong.add(375.0);
        listButtonCoThuong.add(0.0);
        listButtonCoThuong.add(400.0);
        listButtonCoThuong.add(20.0);
        listButtonCoThuong.add(375.0);
        listButtonCoThuong.add(40.0);
        listButtonCoThuong.add(125.0);
        listButtonCoThuong.add(40.0);

        Button feature[] = new Button[2];

        for (int i = 0; i <= 1; i++) {
            feature[i] = new Button();
            feature[i].setTranslateX(100);
            feature[i].setPrefSize(300, 40);
            feature[i].setFont(new Font("Constantia", 20));
            feature[i].setShape(taoHinh);
            pane.getChildren().add(feature[i]);
        }

        feature[0].setTranslateY(460);
        feature[0].setText("Cờ thường");
        feature[0].setOnAction((ActionEvent event) -> {
            try {
                DLCoThg data = new DLCoThg();
                data.preparing();
                Chessboard chessboard = new Chessboard();
                chessboard.paint(primaryStage);
                data.datQuan();
            } catch (Exception ex) {

            }
        });

        feature[1].setTranslateY(560);
        feature[1].setText("Cờ chớp");

        Scene scene = new Scene(pane, 500, 675);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chess game");
        primaryStage.setX(Math.max(Screen.getPrimary().getBounds().getWidth() - 8*Data.canh - 50, 0));
        primaryStage.setY(0);
        primaryStage.show();
    }
}
