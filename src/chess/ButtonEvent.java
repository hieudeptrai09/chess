package chess;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ButtonEvent extends Controller implements EventHandler<ActionEvent> {

    public ButtonEvent(int h, int c) {
        super(h, c);
    }
    
    @Override
    public void handle(ActionEvent event) {
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
