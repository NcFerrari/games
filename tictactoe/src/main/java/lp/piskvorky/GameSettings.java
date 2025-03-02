package lp.piskvorky;

import lombok.Getter;
import lp.piskvorky.conf.Props;

@Getter
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
}
