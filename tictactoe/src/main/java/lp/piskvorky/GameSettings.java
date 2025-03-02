package lp.piskvorky;

import lp.piskvorky.conf.Props;

public class GameSettings {

    private final int countForWin;
    private final int recountedWidth;
    private final int recountedHeight;
    private final int fieldSize;

    public GameSettings(Props props) {
        fieldSize = props.getPropertyInt("field-size");
        countForWin = props.getPropertyInt("count-for-win");
        recountedWidth = (int) (Math.floor(props.getPropertyDouble("width") / fieldSize) * fieldSize);
        recountedHeight = (int) (Math.floor(props.getPropertyDouble("height") / fieldSize) * fieldSize);
    }

    public int getCountForWin() {
        return countForWin;
    }

    public int getRecountedWidth() {
        return recountedWidth;
    }

    public int getRecountedHeight() {
        return recountedHeight;
    }

    public int getFieldSize() {
        return fieldSize;
    }
}
