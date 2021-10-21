package chess;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ImageEvent extends Controller implements EventHandler<MouseEvent> {

    public ImageEvent(int h, int c) {
        super(h, c);
    }
    
    @Override
    public void handle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            if (Data.square[h][c].isYellow()) {
                ketThucLuot(h, c);
            } else if (Data.square[h][c].getPiece() != 0 && Data.square[h][c].isWhite() == luot) {
                if (soBuocDi > 0) {
                    dondep();
                }
                decoding(h, c);
            }
        }
    }
}
