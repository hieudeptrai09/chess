package chess;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller {

    protected int h, c;
    protected static boolean luot = true;
    private static int xYellow[] = new int[30];
    private static int yYellow[] = new int[30];
    protected static int soBuocDi = 0;
    private static boolean belowPawn[] = new boolean[8];
    private static boolean abovePawn[] = new boolean[8];
    private static int xDangerous[] = new int[64];
    private static int yDangerous[] = new int[64];
    private static int soVTNguyHiem;
    private static int eatenLine = 0;
    private static int blackKing[] = DLCoThg.getBlackKing();
    private static int whiteKing[] = DLCoThg.getWhiteKing();

    public Controller(int h, int c) {
        this.h = h;
        this.c = c;
    }

    private boolean nguyHiem(int h, int c) {
        for (int i = 0; i < soVTNguyHiem; i++) {
            if (xDangerous[i] == h && yDangerous[i] == c) {
                return true;
            }
        }
        if (soVTNguyHiem == 0) {
            return true;
        }
        return false;
    }

    protected void dondep() {
        for (int i = 1; i <= soBuocDi; i++) {
            if ((xYellow[i] + yYellow[i]) % 2 == 0) {
                Data.chessboard[xYellow[i]][yYellow[i]].setStyle("-fx-color: white");
            } else {
                Data.chessboard[xYellow[i]][yYellow[i]].setStyle("-fx-color: lime");
            }
            Data.square[xYellow[i]][yYellow[i]].setYellow(false);
        }
        soBuocDi = 0;

    }

    protected void ketThucLuot(int h, int c) {
        boolean vua = false;
        boolean phongCap = false;
        if (Data.square[xYellow[0]][yYellow[0]].getPiece() == Data.KING) {
            vua = true;
        }
        //bat tot qua duong
        if (Data.square[xYellow[0]][yYellow[0]].getPiece() == Data.PAWN && Data.square[h][c].isYellow() && Data.square[h][c].getPiece() == Data.NOTHING && c - yYellow[0] != 0) {
            Data.square[xYellow[0]][c].setPiece(Data.NOTHING);
            Data.imageView[xYellow[0]][c].setImage(ChessImage.getIv(Data.square[xYellow[0]][c].getPiece(), Data.square[xYellow[0]][c].isWhite()));
        }
        //nuoc thong thuong
        diQuan(xYellow[0], yYellow[0], h, c);
        dondep();
        //cac tot co the bat qua duong
        if (Data.square[h][c].getPiece() == Data.PAWN && Data.square[h][c].isBelow() && h - xYellow[0] == -2) {
            belowPawn[c] = true;
        }
        if (Data.square[h][c].getPiece() == Data.PAWN && !Data.square[h][c].isBelow() && h - xYellow[0] == 2) {
            abovePawn[c] = true;
        }
        //nhap thanh
        if (Data.square[h][c].getPiece() == Data.KING && c - yYellow[0] == 2) {
            diQuan(h, 7, h, 5);
        }
        if (Data.square[h][c].getPiece() == Data.KING && c - yYellow[0] == -2) {
            diQuan(h, 0, h, 3);
        }
        //phong cap
        if (Data.square[h][c].getPiece() == Data.PAWN) {
            if ((h == 0 && Data.square[h][c].isBelow()) || (h == 7 && !Data.square[h][c].isBelow())) {
                phongCap = true;
                Stage newWindow = new Stage();
                Pane pane = new Pane();

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

                Label label = new Label("Phong cấp");
                label.setFont(new Font("Constantia", 40));
                label.setPrefSize(500, 120);
                label.setAlignment(Pos.CENTER);
                pane.getChildren().add(label);

                Button button[] = new Button[5];
                for (int i = 0; i < 5; i++) {
                    button[i] = new Button();
                    button[i].setFont(new Font("Constantia", 20));
                    button[i].setTranslateX(100);
                    button[i].setTranslateY(100 * i + 130);
                    button[i].setPrefSize(300, 40);
                    button[i].setShape(taoHinh);
                    pane.getChildren().add(button[i]);
                }

                button[0].setText("Quân Hậu");
                button[0].setStyle("-fx-color:green");
                button[0].setOnAction((ActionEvent event) -> {
                    Data.square[h][c].setPiece(Data.QUEEN);
                    Data.imageView[h][c].setImage(ChessImage.getIv(Data.square[h][c].getPiece(), Data.square[h][c].isWhite()));
                    newWindow.close();
                    if (luot) {
                        checkDangerousPosition(blackKing[0], blackKing[1]);
                    } else {
                        checkDangerousPosition(whiteKing[0], whiteKing[1]);
                    }
                    luot = !luot;
                });

                button[1].setText("Quân Mã");
                button[1].setStyle("-fx-color:brown");
                button[1].setOnAction((ActionEvent event) -> {
                    Data.square[h][c].setPiece(Data.KNIGHT);
                    Data.imageView[h][c].setImage(ChessImage.getIv(Data.square[h][c].getPiece(), Data.square[h][c].isWhite()));
                    newWindow.close();
                    if (luot) {
                        checkDangerousPosition(blackKing[0], blackKing[1]);
                    } else {
                        checkDangerousPosition(whiteKing[0], whiteKing[1]);
                    }
                    luot = !luot;
                });

                button[2].setText("Quân Tượng");
                button[2].setStyle("-fx-color:orange");
                button[2].setOnAction((ActionEvent event) -> {
                    Data.square[h][c].setPiece(Data.BISHOP);
                    Data.imageView[h][c].setImage(ChessImage.getIv(Data.square[h][c].getPiece(), Data.square[h][c].isWhite()));
                    newWindow.close();
                    if (luot) {
                        checkDangerousPosition(blackKing[0], blackKing[1]);
                    } else {
                        checkDangerousPosition(whiteKing[0], whiteKing[1]);
                    }
                    luot = !luot;
                });

                button[3].setText("Quân Xe");
                button[3].setStyle("-fx-color:blue");
                button[3].setOnAction((ActionEvent event) -> {
                    Data.square[h][c].setPiece(Data.ROOK);
                    Data.imageView[h][c].setImage(ChessImage.getIv(Data.square[h][c].getPiece(), Data.square[h][c].isWhite()));
                    newWindow.close();
                    if (luot) {
                        checkDangerousPosition(blackKing[0], blackKing[1]);
                    } else {
                        checkDangerousPosition(whiteKing[0], whiteKing[1]);
                    }
                    luot = !luot;
                });

                Scene scene = new Scene(pane, 500, 500);
                newWindow.setScene(scene);
                newWindow.initStyle(StageStyle.UNDECORATED);
                newWindow.initModality(Modality.WINDOW_MODAL);
                newWindow.initOwner(Data.stage);
                newWindow.show();
                newWindow.setX(Math.max(Data.stage.getX() - 500, 0));
                newWindow.setY(Data.stage.getY());
            }
        }

        if (vua) {
            if (!luot) {
                blackKing[0] = h;
                blackKing[1] = c;
            } else {
                whiteKing[0] = h;
                whiteKing[1] = c;
            }
        }
        if (!phongCap) {
            if (luot) {
                checkDangerousPosition(blackKing[0], blackKing[1]);
            } else {
                checkDangerousPosition(whiteKing[0], whiteKing[1]);
            }
            luot = !luot;
        }
    }

    private void diQuan(int hBegin, int cBegin, int hFinish, int cFinish) {
        Data.square[hFinish][cFinish].setPiece(Data.square[hBegin][cBegin].getPiece());
        Data.square[hFinish][cFinish].setBelow(Data.square[hBegin][cBegin].isBelow());
        Data.square[hFinish][cFinish].setWhite(Data.square[hBegin][cBegin].isWhite());
        Data.square[hFinish][cFinish].setMove(true);
        Data.imageView[hFinish][cFinish].setImage(ChessImage.getIv(Data.square[hBegin][cBegin].getPiece(), Data.square[hBegin][cBegin].isWhite()));
        Data.square[hBegin][cBegin].setPiece(Data.NOTHING);
        Data.imageView[hBegin][cBegin].setImage(ChessImage.getIv(Data.square[hBegin][cBegin].getPiece(), Data.square[hBegin][cBegin].isWhite()));
    }

    private void doimau(int h, int c) {
        if (Data.square[xYellow[0]][yYellow[0]].getPiece() == Data.KING
                || (eatenLine < 2 && ((Data.square[xYellow[0]][yYellow[0]].getShield() != 1 && nguyHiem(h, c))
                || (Data.square[xYellow[0]][yYellow[0]].getShield() == 1
                && ((Data.square[xYellow[0]][yYellow[0]].getShieldPiece() == 1 && xYellow[0] == h)
                || (Data.square[xYellow[0]][yYellow[0]].getShieldPiece() == 2 && yYellow[0] == c)
                || (Data.square[xYellow[0]][yYellow[0]].getShieldPiece() == 3 && xYellow[0] + yYellow[0] == h + c)
                || (Data.square[xYellow[0]][yYellow[0]].getShieldPiece() == 4 && xYellow[0] - yYellow[0] == h - c)))))) {
            xYellow[soBuocDi + 1] = h;
            yYellow[soBuocDi + 1] = c;
            soBuocDi++;
            Data.chessboard[h][c].setStyle("-fx-color:yellow");
            Data.square[h][c].setYellow(true);
        }
    }

    protected void decoding(int h, int c) {
        switch (Data.square[h][c].getPiece()) {
            case Data.ROOK:
                xeTuongHau(h, c);
                break;
            case Data.KNIGHT:
                knight(h, c);
                break;
            case Data.BISHOP:
                xeTuongHau(h, c);
                break;
            case Data.QUEEN:
                xeTuongHau(h, c);
                break;
            case Data.KING:
                king(h, c);
                break;
            case Data.PAWN:
                pawn(h, c);
        }
    }

    private void pawn(int h, int c) {
        soBuocDi = 0;
        xYellow[0] = h;
        yYellow[0] = c;

        if (Data.square[h][c].isBelow()) {
            if (h == 6) {
                for (int i = 5; i >= 4; i--) {
                    if (Data.square[i][c].getPiece() != 0) {
                        break;
                    }
                    doimau(i, c);
                }
            } else {
                if (h >= 1 && Data.square[h - 1][c].getPiece() == Data.NOTHING) {
                    doimau(h - 1, c);
                }
            }
            if (h > 0 && c > 0 && Data.square[h - 1][c - 1].getPiece() != 0 && Data.square[h - 1][c - 1].isBelow() != Data.square[h][c].isBelow()) {
                doimau(h - 1, c - 1);
            }
            if (h > 0 && c < 7 && Data.square[h - 1][c + 1].getPiece() != 0 && Data.square[h - 1][c + 1].isBelow() != Data.square[h][c].isBelow()) {
                doimau(h - 1, c + 1);
            }
            if (h == 3 && c < 7 && Data.square[3][c + 1].getPiece() == Data.PAWN && !Data.square[3][c + 1].isBelow() && abovePawn[c + 1]) {
                doimau(2, c + 1);
            }
            if (h == 3 && c > 0 && Data.square[3][c - 1].getPiece() == Data.PAWN && !Data.square[3][c - 1].isBelow() && abovePawn[c - 1]) {
                doimau(2, c - 1);
            }
            for (int i = 0; i < 8; i++) {
                belowPawn[i] = false;
            }
        } else {
            if (h == 1) {
                for (int i = 2; i <= 3; i++) {
                    if (Data.square[i][c].getPiece() != 0) {
                        break;
                    }
                    doimau(i, c);
                }
            } else {
                if (h <= 6 && Data.square[h + 1][c].getPiece() == Data.NOTHING) {
                    doimau(h + 1, c);
                }
            }
            if (h < 7 && c > 0 && Data.square[h + 1][c - 1].getPiece() != 0 && Data.square[h + 1][c - 1].isBelow() != Data.square[h][c].isBelow()) {
                doimau(h + 1, c - 1);
            }
            if (h < 7 && c < 7 && Data.square[h + 1][c + 1].getPiece() != 0 && Data.square[h + 1][c + 1].isBelow() != Data.square[h][c].isBelow()) {
                doimau(h + 1, c + 1);
            }
            if (h == 4 && c < 7 && Data.square[4][c + 1].getPiece() == Data.PAWN && Data.square[4][c + 1].isBelow() && belowPawn[c + 1]) {
                doimau(5, c + 1);
            }
            if (h == 4 && c > 0 && Data.square[4][c - 1].getPiece() == Data.PAWN && Data.square[4][c - 1].isBelow() && belowPawn[c - 1]) {
                doimau(5, c - 1);
            }
            for (int i = 0; i < 8; i++) {
                abovePawn[i] = false;
            }
        }
    }

    private void knight(int h, int c) {
        soBuocDi = 0;
        xYellow[0] = h;
        yYellow[0] = c;

        int x[] = {2, 1, -1, -2, -2, -1, 1, 2};
        int y[] = {1, 2, 2, 1, -1, -2, -2, -1};
        for (int i = 0; i < 8; i++) {
            if (h + x[i] >= 0 && h + x[i] < 8) {
                if (c + y[i] >= 0 && c + y[i] < 8) {
                    if (Data.square[h + x[i]][c + y[i]].getPiece() == Data.NOTHING || (Data.square[h][c].isBelow() != Data.square[h + x[i]][c + y[i]].isBelow() && Data.square[h + x[i]][c + y[i]].getPiece() != 0)) {
                        doimau(h + x[i], c + y[i]);
                    }
                }
            }
        }
    }

    private void xeTuongHau(int h, int c) {
        soBuocDi = 0;
        xYellow[0] = h;
        yYellow[0] = c;

        if (Data.square[h][c].getPiece() == Data.ROOK || Data.square[h][c].getPiece() == Data.QUEEN) {
            for (int i = h + 1; i <= 7; i++) {
                if (Data.square[i][c].getPiece() != 0) {
                    if (Data.square[i][c].isBelow() == Data.square[h][c].isBelow()) {
                        break;
                    } else {
                        doimau(i, c);
                        break;
                    }
                }
                doimau(i, c);
            }

            for (int i = h - 1; i >= 0; i--) {
                if (Data.square[i][c].getPiece() != 0) {
                    if (Data.square[i][c].isBelow() == Data.square[h][c].isBelow()) {
                        break;
                    } else {
                        doimau(i, c);
                        break;
                    }
                }
                doimau(i, c);
            }

            for (int i = c + 1; i <= 7; i++) {
                if (Data.square[h][i].getPiece() != 0) {
                    if (Data.square[h][i].isBelow() == Data.square[h][c].isBelow()) {
                        break;
                    } else {
                        doimau(h, i);
                        break;
                    }
                }
                doimau(h, i);
            }

            for (int i = c - 1; i >= 0; i--) {
                if (Data.square[h][i].getPiece() != 0) {
                    if (Data.square[h][i].isBelow() == Data.square[h][c].isBelow()) {
                        break;
                    } else {
                        doimau(h, i);
                        break;
                    }
                }
                doimau(h, i);
            }
        }

        if (Data.square[h][c].getPiece() == Data.BISHOP || Data.square[h][c].getPiece() == Data.QUEEN) {
            for (int i = h + 1; i <= 7 && h + c - i >= 0; i++) {
                if (Data.square[i][h + c - i].getPiece() != 0) {
                    if (Data.square[i][h + c - i].isBelow() == Data.square[h][c].isBelow()) {
                        break;
                    } else {
                        doimau(i, h + c - i);
                        break;
                    }
                }
                doimau(i, h + c - i);
            }

            for (int i = h - 1; i >= 0 && h + c - i <= 7; i--) {
                if (Data.square[i][h + c - i].getPiece() != 0) {
                    if (Data.square[i][h + c - i].isBelow() == Data.square[h][c].isBelow()) {
                        break;
                    } else {
                        doimau(i, h + c - i);
                        break;
                    }
                }
                doimau(i, h + c - i);
            }

            for (int i = h + 1; i <= 7 && c + i - h <= 7; i++) {
                if (Data.square[i][c + i - h].getPiece() != 0) {
                    if (Data.square[i][c + i - h].isBelow() == Data.square[h][c].isBelow()) {
                        break;
                    } else {
                        doimau(i, c + i - h);
                        break;
                    }
                }
                doimau(i, c + i - h);
            }

            for (int i = h - 1; i >= 0 && c + i - h >= 0; i--) {
                if (Data.square[i][c + i - h].getPiece() != 0) {
                    if (Data.square[i][c + i - h].isBelow() == Data.square[h][c].isBelow()) {
                        break;
                    } else {
                        doimau(i, c + i - h);
                        break;
                    }
                }
                doimau(i, c + i - h);
            }
        }
    }

    private void king(int h, int c) {
        soBuocDi = 0;
        xYellow[0] = h;
        yYellow[0] = c;
        int xVua[] = {1, 1, 1, 0, -1, -1, -1, 0};
        int yVua[] = {-1, 0, 1, 1, 1, 0, -1, -1};
        int hTemp, cTemp;
        boolean ok = true;
        for (int i = 0; i < 8; i++) {
            hTemp = h + xVua[i];
            cTemp = c + yVua[i];
            if (hTemp < 0 || hTemp > 7 || cTemp < 0 || cTemp > 7) {
                continue;
            } else {
                if (Data.square[hTemp][cTemp].getPiece() != 0 && Data.square[hTemp][cTemp].isBelow() == Data.square[h][c].isBelow()) {
                    continue;
                }
                if (!checkEatKing(hTemp, cTemp, h, c)) {
                    doimau(hTemp, cTemp);
                }
            }
        }
        if (h == 0 || h == 7) {
            if (!checkEatKing(h, c, h, c)) {
                ok = true;
                if (!Data.square[h][c].isMove() && !Data.square[h][0].isMove()) {
                    for (int i = c - 1; i > 0; i--) {
                        if (Data.square[h][i].getPiece() != 0) {
                            ok = false;
                            break;
                        }
                    }
                    if (ok) {
                        for (int i = c - 1; i >= c - 2; i--) {
                            if (checkEatKing(h, i, h, c)) {
                                ok = false;
                                break;
                            }
                        }
                    }
                    if (ok) {
                        doimau(h, c - 2);
                    }
                }
                ok = true;
                if (!Data.square[h][c].isMove() && !Data.square[h][7].isMove()) {
                    for (int i = c + 1; i < 7; i++) {
                        if (Data.square[h][i].getPiece() != 0) {
                            ok = false;
                            break;
                        }
                    }
                    if (ok) {
                        for (int i = c + 1; i <= c + 2; i++) {
                            if (checkEatKing(h, i, h, c)) {
                                ok = false;
                                break;
                            }
                        }
                    }
                    if (ok) {
                        doimau(h, c + 2);
                    }
                }
            }
        }
    }

    private boolean checkEatKing(int h, int c, int hBegin, int cBegin) {

        int xVua[] = {1, 1, 1, 0, -1, -1, -1, 0};
        int yVua[] = {-1, 0, 1, 1, 1, 0, -1, -1};
        int xMa[] = {2, 1, -1, -2, -2, -1, 1, 2};
        int yMa[] = {1, 2, 2, 1, -1, -2, -2, -1};
        int piece = Data.square[hBegin][cBegin].getPiece();
        Data.square[hBegin][cBegin].setPiece(Data.NOTHING);

        for (int j = c + 1; j < 8; j++) {
            if ((Data.square[h][j].getPiece() == Data.ROOK || Data.square[h][j].getPiece() == Data.QUEEN) && Data.square[h][j].isBelow() != Data.square[hBegin][cBegin].isBelow()) {
                Data.square[hBegin][cBegin].setPiece(piece);
                return true;
            } else if (Data.square[h][j].getPiece() != 0) {
                break;
            }
        }
        for (int j = c - 1; j >= 0; j--) {
            if ((Data.square[h][j].getPiece() == Data.ROOK || Data.square[h][j].getPiece() == Data.QUEEN) && Data.square[h][j].isBelow() != Data.square[hBegin][cBegin].isBelow()) {
                Data.square[hBegin][cBegin].setPiece(piece);
                return true;
            } else if (Data.square[h][j].getPiece() != 0) {
                break;
            }
        }

        for (int j = h + 1; j < 8; j++) {
            if ((Data.square[j][c].getPiece() == Data.ROOK || Data.square[j][c].getPiece() == Data.QUEEN) && Data.square[j][c].isBelow() != Data.square[hBegin][cBegin].isBelow()) {
                Data.square[hBegin][cBegin].setPiece(piece);
                return true;
            } else if (Data.square[j][c].getPiece() != 0) {
                break;
            }
        }
        for (int j = h - 1; j >= 0; j--) {
            if ((Data.square[j][c].getPiece() == Data.ROOK || Data.square[j][c].getPiece() == Data.QUEEN) && Data.square[j][c].isBelow() != Data.square[hBegin][cBegin].isBelow()) {
                Data.square[hBegin][cBegin].setPiece(piece);
                return true;
            } else if (Data.square[j][c].getPiece() != 0) {
                break;
            }
        }

        for (int j = 0; j < 8; j++) {
            if (h + xMa[j] >= 0 && h + xMa[j] <= 7) {
                if (c + yMa[j] >= 0 && c + yMa[j] <= 7) {
                    if (Data.square[h + xMa[j]][c + yMa[j]].getPiece() == Data.KNIGHT && Data.square[h + xMa[j]][c + yMa[j]].isBelow() != Data.square[hBegin][cBegin].isBelow()) {
                        Data.square[hBegin][cBegin].setPiece(piece);
                        return true;
                    }
                }
            }
            if (h + xVua[j] >= 0 && h + xVua[j] <= 7) {
                if (c + yVua[j] >= 0 && c + yVua[j] <= 7) {
                    if (Data.square[h + xVua[j]][c + yVua[j]].getPiece() == Data.KING && Data.square[h + xVua[j]][c + yVua[j]].isBelow() != Data.square[hBegin][cBegin].isBelow()) {
                        Data.square[hBegin][cBegin].setPiece(piece);
                        return true;
                    }
                }
            }
        }

        for (int j = h + 1; j < 8; j++) {
            if (h + c - j >= 0 && h + c - j <= 7) {
                if ((Data.square[j][h + c - j].getPiece() == Data.BISHOP || Data.square[j][h + c - j].getPiece() == Data.QUEEN) && Data.square[j][h + c - j].isBelow() != Data.square[hBegin][cBegin].isBelow()) {
                    Data.square[hBegin][cBegin].setPiece(piece);
                    return true;
                } else if (Data.square[j][h + c - j].getPiece() != 0) {
                    break;
                }
            }
        }
        for (int j = h + 1; j < 8; j++) {
            if (c - h + j >= 0 && c - h + j <= 7) {
                if ((Data.square[j][c - h + j].getPiece() == Data.BISHOP || Data.square[j][c - h + j].getPiece() == Data.QUEEN) && Data.square[j][c - h + j].isBelow() != Data.square[hBegin][cBegin].isBelow()) {
                    Data.square[hBegin][cBegin].setPiece(piece);
                    return true;
                } else if (Data.square[j][c - h + j].getPiece() != 0) {
                    break;
                }
            }
        }

        for (int j = h - 1; j >= 0; j--) {
            if (h + c - j >= 0 && h + c - j <= 7) {
                if ((Data.square[j][h + c - j].getPiece() == Data.BISHOP || Data.square[j][h + c - j].getPiece() == Data.QUEEN) && Data.square[j][h + c - j].isBelow() != Data.square[hBegin][cBegin].isBelow()) {
                    Data.square[hBegin][cBegin].setPiece(piece);
                    return true;
                } else if (Data.square[j][h + c - j].getPiece() != 0) {
                    break;
                }
            }
        }
        for (int j = h - 1; j >= 0; j--) {
            if (c - h + j >= 0 && c - h + j <= 7) {
                if ((Data.square[j][c - h + j].getPiece() == Data.BISHOP || Data.square[j][c - h + j].getPiece() == Data.QUEEN) && Data.square[j][c - h + j].isBelow() != Data.square[hBegin][cBegin].isBelow()) {
                    Data.square[hBegin][cBegin].setPiece(piece);
                    return true;
                } else if (Data.square[j][c - h + j].getPiece() != 0) {
                    break;
                }
            }
        }

        if (Data.square[hBegin][cBegin].isBelow()) {
            if (h - 1 >= 0 && h - 1 <= 7) {
                if (c + 1 >= 0 && c + 1 <= 7) {
                    if (Data.square[h - 1][c + 1].getPiece() == Data.PAWN && !Data.square[h - 1][c + 1].isBelow()) {
                        Data.square[hBegin][cBegin].setPiece(piece);
                        return true;
                    }
                }
                if (c - 1 >= 0 && c - 1 <= 7) {
                    if (Data.square[h - 1][c - 1].getPiece() == Data.PAWN && !Data.square[h - 1][c - 1].isBelow()) {
                        Data.square[hBegin][cBegin].setPiece(piece);
                        return true;
                    }
                }
            }
        } else {
            if (h + 1 >= 0 && h + 1 <= 7) {
                if (c + 1 >= 0 && c + 1 <= 7) {
                    if (Data.square[h + 1][c + 1].getPiece() == Data.PAWN && Data.square[h + 1][c + 1].isBelow()) {
                        Data.square[hBegin][cBegin].setPiece(piece);
                        return true;
                    }
                }
                if (c - 1 >= 0 && c - 1 <= 7) {
                    if (Data.square[h + 1][c - 1].getPiece() == Data.PAWN && Data.square[h + 1][c - 1].isBelow()) {
                        Data.square[hBegin][cBegin].setPiece(piece);
                        return true;
                    }
                }
            }
        }
        Data.square[hBegin][cBegin].setPiece(piece);
        return false;
    }

    private void checkDangerousPosition(int h, int c) {
        soVTNguyHiem = 0;
        eatenLine = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Data.square[i][j].setShield(0);
            }
        }

        int xVua[] = {1, 1, 1, 0, -1, -1, -1, 0};
        int yVua[] = {-1, 0, 1, 1, 1, 0, -1, -1};
        int xMa[] = {2, 1, -1, -2, -2, -1, 1, 2};
        int yMa[] = {1, 2, 2, 1, -1, -2, -2, -1};
        int shield;
        int xShield[] = new int[8];
        int yShield[] = new int[8];
        boolean flag = false;

        boolean btqd[] = new boolean[8];
        for (int i = 0; i < 8; i++) {
            if (Data.square[h][c].isBelow()) {
                if (i > 0 && Data.square[3][i].getPiece() == Data.PAWN && Data.square[3][i - 1].getPiece() == Data.PAWN && Data.square[3][i].isBelow() != Data.square[3][i - 1].isBelow() && abovePawn[i]) {
                    btqd[i] = true;
                }
                if (i < 7 && Data.square[3][i].getPiece() == Data.PAWN && Data.square[3][i + 1].getPiece() == Data.PAWN && Data.square[3][i].isBelow() != Data.square[3][i + 1].isBelow() && abovePawn[i]) {
                    btqd[i] = true;
                }
            } else {
                if (i > 0 && Data.square[4][i].getPiece() == Data.PAWN && Data.square[4][i - 1].getPiece() == Data.PAWN && Data.square[4][i].isBelow() != Data.square[4][i - 1].isBelow() && belowPawn[i]) {
                    btqd[i] = true;
                }
                if (i < 7 && Data.square[4][i].getPiece() == Data.PAWN && Data.square[4][i + 1].getPiece() == Data.PAWN && Data.square[4][i].isBelow() != Data.square[4][i + 1].isBelow() && belowPawn[i]) {
                    btqd[i] = true;
                }
            }
        }

        int vtnhOld = soVTNguyHiem;
        shield = 0;
        for (int j = c + 1; j < 8; j++) {
            ++soVTNguyHiem;
            xDangerous[soVTNguyHiem - 1] = h;
            yDangerous[soVTNguyHiem - 1] = j;
            if ((Data.square[h][j].getPiece() == Data.ROOK || Data.square[h][j].getPiece() == Data.QUEEN) && Data.square[h][j].isBelow() != Data.square[h][c].isBelow()) {
                if (shield == 1) {
                    if (Data.square[xShield[0]][yShield[0]].getPiece() == Data.ROOK
                            || Data.square[xShield[0]][yShield[0]].getPiece() == Data.QUEEN
                            || (Data.square[xShield[0]][yShield[0]].getPiece() == Data.PAWN && Data.square[xShield[0]][yShield[0]].isBelow() == Data.square[h][c].isBelow())) {
                        Data.square[xShield[0]][yShield[0]].setShieldPiece(1);
                    } else {
                        Data.square[xShield[0]][yShield[0]].setShieldPiece(0);
                    }
                    Data.square[xShield[0]][yShield[0]].setShield(shield);
                }
                if (shield == 2) {
                    for (int i = 0; i < 2; i++) {
                        if (Data.square[xShield[i]][yShield[i]].getPiece() == Data.PAWN && Data.square[xShield[i]][yShield[i]].isBelow() != Data.square[h][c].isBelow()) {
                            if (!Data.square[xShield[i]][yShield[i]].isBelow()) {
                                abovePawn[yShield[i]] = false;
                            } else {
                                belowPawn[yShield[i]] = false;
                            }
                        }
                    }
                }
                if (shield >= 1) {
                    soVTNguyHiem = vtnhOld;
                } else {
                    ++eatenLine;
                }
                flag = true;
                break;
            } else if (Data.square[h][j].getPiece() != Data.NOTHING) {
                if (Data.square[h][j].isBelow() == Data.square[h][c].isBelow()) {
                    ++shield;
                    xShield[shield - 1] = h;
                    yShield[shield - 1] = j;
                } else if (Data.square[h][j].getPiece() == Data.PAWN
                        && ((h == 3 && Data.square[h][c].isBelow() && btqd[j])
                        || (h == 4 && !Data.square[h][c].isBelow() && btqd[j]))) {
                    ++shield;
                    xShield[shield - 1] = h;
                    yShield[shield - 1] = j;
                } else {
                    soVTNguyHiem = vtnhOld;
                    break;
                }
            }
        }
        if (!flag) {
            soVTNguyHiem = vtnhOld;
        } else {
            flag = false;
        }

        vtnhOld = soVTNguyHiem;
        shield = 0;
        for (int j = c - 1; j >= 0; j--) {
            ++soVTNguyHiem;
            xDangerous[soVTNguyHiem - 1] = h;
            yDangerous[soVTNguyHiem - 1] = j;
            if ((Data.square[h][j].getPiece() == Data.ROOK || Data.square[h][j].getPiece() == Data.QUEEN) && Data.square[h][j].isBelow() != Data.square[h][c].isBelow()) {
                if (shield == 1) {
                    if (Data.square[xShield[0]][yShield[0]].getPiece() == Data.ROOK
                            || Data.square[xShield[0]][yShield[0]].getPiece() == Data.QUEEN
                            || (Data.square[xShield[0]][yShield[0]].getPiece() == Data.PAWN && Data.square[xShield[0]][yShield[0]].isBelow() == Data.square[h][c].isBelow())) {
                        Data.square[xShield[0]][yShield[0]].setShieldPiece(1);
                    } else {
                        Data.square[xShield[0]][yShield[0]].setShieldPiece(0);
                    }
                    Data.square[xShield[0]][yShield[0]].setShield(shield);
                }
                if (shield == 2) {
                    for (int i = 0; i < 2; i++) {
                        if (Data.square[xShield[i]][yShield[i]].getPiece() == Data.PAWN && Data.square[xShield[i]][yShield[i]].isBelow() != Data.square[h][c].isBelow()) {
                            if (!Data.square[xShield[i]][yShield[i]].isBelow()) {
                                abovePawn[yShield[i]] = false;
                            } else {
                                belowPawn[yShield[i]] = false;
                            }
                        }
                    }
                }
                if (shield >= 1) {
                    soVTNguyHiem = vtnhOld;
                } else {
                    ++eatenLine;
                }
                flag = true;
                break;
            } else if (Data.square[h][j].getPiece() != 0) {
                if (Data.square[h][j].isBelow() == Data.square[h][c].isBelow()) {
                    ++shield;
                    xShield[shield - 1] = h;
                    yShield[shield - 1] = j;
                } else if (Data.square[h][j].getPiece() == Data.PAWN
                        && ((h == 3 && Data.square[h][c].isBelow() && btqd[j])
                        || (h == 4 && !Data.square[h][c].isBelow() && btqd[j]))) {
                    ++shield;
                    xShield[shield - 1] = h;
                    yShield[shield - 1] = j;
                } else {
                    soVTNguyHiem = vtnhOld;
                    break;
                }
            }
        }
        if (!flag) {
            soVTNguyHiem = vtnhOld;
        } else {
            flag = false;
        }

        vtnhOld = soVTNguyHiem;
        shield = 0;
        for (int j = h + 1; j < 8; j++) {
            ++soVTNguyHiem;
            xDangerous[soVTNguyHiem - 1] = j;
            yDangerous[soVTNguyHiem - 1] = c;
            if ((Data.square[j][c].getPiece() == Data.ROOK || Data.square[j][c].getPiece() == Data.QUEEN) && Data.square[j][c].isBelow() != Data.square[h][c].isBelow()) {
                if (shield == 1) {
                    if (Data.square[xShield[0]][yShield[0]].getPiece() == Data.ROOK
                            || Data.square[xShield[0]][yShield[0]].getPiece() == Data.QUEEN
                            || (Data.square[xShield[0]][yShield[0]].getPiece() == Data.PAWN && Data.square[xShield[0]][yShield[0]].isBelow() == Data.square[h][c].isBelow())) {
                        Data.square[xShield[0]][yShield[0]].setShieldPiece(2);
                    } else {
                        Data.square[xShield[0]][yShield[0]].setShieldPiece(0);
                    }
                    Data.square[xShield[0]][yShield[0]].setShield(shield);
                }
                if (shield >= 1) {
                    soVTNguyHiem = vtnhOld;
                } else {
                    ++eatenLine;
                }
                flag = true;
                break;
            } else if (Data.square[j][c].getPiece() != 0) {
                if (Data.square[j][c].isBelow() == Data.square[h][c].isBelow()) {
                    ++shield;
                    xShield[shield - 1] = j;
                    yShield[shield - 1] = c;
                } else {
                    soVTNguyHiem = vtnhOld;
                    break;
                }
            }
        }
        if (!flag) {
            soVTNguyHiem = vtnhOld;
        } else {
            flag = false;
        }

        vtnhOld = soVTNguyHiem;
        shield = 0;
        for (int j = h - 1; j >= 0; j--) {
            ++soVTNguyHiem;
            xDangerous[soVTNguyHiem - 1] = j;
            yDangerous[soVTNguyHiem - 1] = c;
            if ((Data.square[j][c].getPiece() == Data.ROOK || Data.square[j][c].getPiece() == Data.QUEEN) && Data.square[j][c].isBelow() != Data.square[h][c].isBelow()) {
                if (shield == 1) {
                    if (Data.square[xShield[0]][yShield[0]].getPiece() == Data.ROOK
                            || Data.square[xShield[0]][yShield[0]].getPiece() == Data.QUEEN
                            || (Data.square[xShield[0]][yShield[0]].getPiece() == Data.PAWN && Data.square[xShield[0]][yShield[0]].isBelow() == Data.square[h][c].isBelow())) {
                        Data.square[xShield[0]][yShield[0]].setShieldPiece(2);
                    } else {
                        Data.square[xShield[0]][yShield[0]].setShieldPiece(0);
                    }
                    Data.square[xShield[0]][yShield[0]].setShield(shield);
                }
                if (shield >= 1) {
                    soVTNguyHiem = vtnhOld;
                } else {
                    ++eatenLine;
                }
                flag = true;
                break;
            } else if (Data.square[j][c].getPiece() != 0) {
                if (Data.square[j][c].isBelow() == Data.square[h][c].isBelow()) {
                    ++shield;
                    xShield[shield - 1] = j;
                    yShield[shield - 1] = c;
                } else {
                    soVTNguyHiem = vtnhOld;
                    break;
                }
            }
        }
        if (!flag) {
            soVTNguyHiem = vtnhOld;
        } else {
            flag = false;
        }

        for (int j = 0; j < 8; j++) {
            if (h + xMa[j] >= 0 && h + xMa[j] <= 7) {
                if (c + yMa[j] >= 0 && c + yMa[j] <= 7) {
                    if (Data.square[h + xMa[j]][c + yMa[j]].getPiece() == Data.KNIGHT && Data.square[h + xMa[j]][c + yMa[j]].isBelow() != Data.square[h][c].isBelow()) {
                        ++soVTNguyHiem;
                        xDangerous[soVTNguyHiem - 1] = h + xMa[j];
                        yDangerous[soVTNguyHiem - 1] = c + yMa[j];
                        ++eatenLine;
                    }
                }
            }
        }

        vtnhOld = soVTNguyHiem;
        shield = 0;
        for (int j = h + 1; j < 8; j++) {
            if (h + c - j >= 0 && h + c - j <= 7) {
                ++soVTNguyHiem;
                xDangerous[soVTNguyHiem - 1] = j;
                yDangerous[soVTNguyHiem - 1] = h + c - j;
                if ((Data.square[j][h + c - j].getPiece() == Data.BISHOP || Data.square[j][h + c - j].getPiece() == Data.QUEEN) && Data.square[j][h + c - j].isBelow() != Data.square[h][c].isBelow()) {
                    if (shield == 1) {
                        if (Data.square[xShield[0]][yShield[0]].getPiece() == Data.BISHOP
                                || Data.square[xShield[0]][yShield[0]].getPiece() == Data.QUEEN
                                || (Data.square[xShield[0]][yShield[0]].getPiece() == Data.PAWN && Data.square[xShield[0]][yShield[0]].isBelow() == Data.square[h][c].isBelow())) {
                            Data.square[xShield[0]][yShield[0]].setShieldPiece(3);
                        } else {
                            Data.square[xShield[0]][yShield[0]].setShieldPiece(0);
                        }
                        Data.square[xShield[0]][yShield[0]].setShield(shield);
                    }
                    if (shield >= 1) {
                        soVTNguyHiem = vtnhOld;
                    } else {
                        ++eatenLine;
                    }
                    flag = true;
                    break;
                } else if (Data.square[j][h + c - j].getPiece() != 0) {
                    if (Data.square[j][h + c - j].isBelow() == Data.square[h][c].isBelow()) {
                        ++shield;
                        xShield[shield - 1] = j;
                        yShield[shield - 1] = h + c - j;
                    } else {
                        soVTNguyHiem = vtnhOld;
                        break;
                    }
                }
            }
        }
        if (!flag) {
            soVTNguyHiem = vtnhOld;
        } else {
            flag = false;
        }

        vtnhOld = soVTNguyHiem;
        shield = 0;
        for (int j = h + 1; j < 8; j++) {
            if (c - h + j >= 0 && c - h + j <= 7) {
                ++soVTNguyHiem;
                xDangerous[soVTNguyHiem - 1] = j;
                yDangerous[soVTNguyHiem - 1] = c - h + j;
                if ((Data.square[j][c - h + j].getPiece() == Data.BISHOP || Data.square[j][c - h + j].getPiece() == Data.QUEEN) && Data.square[j][c - h + j].isBelow() != Data.square[h][c].isBelow()) {
                    if (shield == 1) {
                        if (Data.square[xShield[0]][yShield[0]].getPiece() == Data.BISHOP
                                || Data.square[xShield[0]][yShield[0]].getPiece() == Data.QUEEN
                                || (Data.square[xShield[0]][yShield[0]].getPiece() == Data.PAWN && Data.square[xShield[0]][yShield[0]].isBelow() == Data.square[h][c].isBelow())) {
                            Data.square[xShield[0]][yShield[0]].setShieldPiece(4);
                        } else {
                            Data.square[xShield[0]][yShield[0]].setShieldPiece(0);
                        }
                        Data.square[xShield[0]][yShield[0]].setShield(shield);
                    }
                    if (shield >= 1) {
                        soVTNguyHiem = vtnhOld;
                    } else {
                        ++eatenLine;
                    }
                    flag = true;
                    break;
                } else if (Data.square[j][c - h + j].getPiece() != 0) {
                    if (Data.square[j][c - h + j].isBelow() == Data.square[h][c].isBelow()) {
                        ++shield;
                        xShield[shield - 1] = j;
                        yShield[shield - 1] = c - h + j;
                    } else {
                        soVTNguyHiem = vtnhOld;
                        break;
                    }
                }
            }

        }
        if (!flag) {
            soVTNguyHiem = vtnhOld;
        } else {
            flag = false;
        }

        vtnhOld = soVTNguyHiem;
        shield = 0;
        for (int j = h - 1; j >= 0; j--) {
            if (h + c - j >= 0 && h + c - j <= 7) {
                ++soVTNguyHiem;
                xDangerous[soVTNguyHiem - 1] = j;
                yDangerous[soVTNguyHiem - 1] = h + c - j;
                if ((Data.square[j][h + c - j].getPiece() == Data.BISHOP || Data.square[j][h + c - j].getPiece() == Data.QUEEN) && Data.square[j][h + c - j].isBelow() != Data.square[h][c].isBelow()) {
                    if (shield == 1) {
                        if (Data.square[xShield[0]][yShield[0]].getPiece() == Data.BISHOP
                                || Data.square[xShield[0]][yShield[0]].getPiece() == Data.QUEEN
                                || (Data.square[xShield[0]][yShield[0]].getPiece() == Data.PAWN && Data.square[xShield[0]][yShield[0]].isBelow() == Data.square[h][c].isBelow())) {
                            Data.square[xShield[0]][yShield[0]].setShieldPiece(3);
                        } else {
                            Data.square[xShield[0]][yShield[0]].setShieldPiece(0);
                        }
                        Data.square[xShield[0]][yShield[0]].setShield(shield);
                    }
                    if (shield >= 1) {
                        soVTNguyHiem = vtnhOld;
                    } else {
                        ++eatenLine;
                    }
                    flag = true;
                    break;
                } else if (Data.square[j][h + c - j].getPiece() != 0) {
                    if (Data.square[j][h + c - j].isBelow() == Data.square[h][c].isBelow()) {
                        ++shield;
                        xShield[shield - 1] = j;
                        yShield[shield - 1] = h + c - j;
                    } else {
                        soVTNguyHiem = vtnhOld;
                        break;
                    }
                }
            }
        }
        if (!flag) {
            soVTNguyHiem = vtnhOld;
        } else {
            flag = false;
        }

        vtnhOld = soVTNguyHiem;
        shield = 0;
        for (int j = h - 1; j >= 0; j--) {
            if (c - h + j >= 0 && c - h + j <= 7) {
                ++soVTNguyHiem;
                xDangerous[soVTNguyHiem - 1] = j;
                yDangerous[soVTNguyHiem - 1] = c - h + j;
                if ((Data.square[j][c - h + j].getPiece() == Data.BISHOP || Data.square[j][c - h + j].getPiece() == Data.QUEEN) && Data.square[j][c - h + j].isBelow() != Data.square[h][c].isBelow()) {
                    if (shield == 1) {
                        if (Data.square[xShield[0]][yShield[0]].getPiece() == Data.BISHOP
                                || Data.square[xShield[0]][yShield[0]].getPiece() == Data.QUEEN
                                || (Data.square[xShield[0]][yShield[0]].getPiece() == Data.PAWN && Data.square[xShield[0]][yShield[0]].isBelow() == Data.square[h][c].isBelow())) {
                            Data.square[xShield[0]][yShield[0]].setShieldPiece(4);
                        } else {
                            Data.square[xShield[0]][yShield[0]].setShieldPiece(0);
                        }
                        Data.square[xShield[0]][yShield[0]].setShield(shield);
                    }
                    if (shield >= 1) {
                        soVTNguyHiem = vtnhOld;
                    } else {
                        ++eatenLine;
                    }
                    flag = true;
                    break;
                } else if (Data.square[j][c - h + j].getPiece() != 0) {
                    if (Data.square[j][c - h + j].isBelow() == Data.square[h][c].isBelow()) {
                        ++shield;
                        xShield[shield - 1] = j;
                        yShield[shield - 1] = c - h + j;
                    } else {
                        soVTNguyHiem = vtnhOld;
                        break;
                    }
                }
            }
        }
        if (!flag) {
            soVTNguyHiem = vtnhOld;
        } else {
            flag = false;
        }

        if (Data.square[h][c].isBelow()) {
            if (h - 1 >= 0 && h - 1 <= 7) {
                if (c + 1 >= 0 && c + 1 <= 7) {
                    if (Data.square[h - 1][c + 1].getPiece() == Data.PAWN && !Data.square[h - 1][c + 1].isBelow()) {
                        soVTNguyHiem++;
                        xDangerous[soVTNguyHiem - 1] = h - 1;
                        yDangerous[soVTNguyHiem - 1] = c + 1;
                        ++eatenLine;
                    }
                }
                if (c - 1 >= 0 && c - 1 <= 7) {
                    if (Data.square[h - 1][c - 1].getPiece() == Data.PAWN && !Data.square[h - 1][c - 1].isBelow()) {
                        soVTNguyHiem++;
                        xDangerous[soVTNguyHiem - 1] = h - 1;
                        yDangerous[soVTNguyHiem - 1] = c - 1;
                        ++eatenLine;
                    }
                }
            }
        } else {
            if (h + 1 >= 0 && h + 1 <= 7) {
                if (c + 1 >= 0 && c + 1 <= 7) {
                    if (Data.square[h + 1][c + 1].getPiece() == Data.PAWN && Data.square[h + 1][c + 1].isBelow()) {
                        soVTNguyHiem++;
                        xDangerous[soVTNguyHiem - 1] = h + 1;
                        yDangerous[soVTNguyHiem - 1] = c + 1;
                        ++eatenLine;
                    }
                }
                if (c - 1 >= 0 && c - 1 <= 7) {
                    if (Data.square[h + 1][c - 1].getPiece() == Data.PAWN && Data.square[h + 1][c - 1].isBelow()) {
                        soVTNguyHiem++;
                        xDangerous[soVTNguyHiem - 1] = h + 1;
                        yDangerous[soVTNguyHiem - 1] = c - 1;
                        ++eatenLine;
                    }
                }
            }
        }

    }

}
