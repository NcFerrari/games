package lp.frontend.shape;

import javafx.scene.layout.Region;
import lombok.Getter;

@Getter
public class TestBlock extends Region implements IBlock {

    private int x;
    private int y;
    private int blockWidth;
    private int blockHeight;
    private String a, b, c;

    public TestBlock(int x, int y, int blockWidth, int blockHeight, String a, String b, String c) {
        setLocation(x, y);
        setSize(blockWidth, blockHeight);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    //TODO dfojasdůof
    public void paintShape() {

    }

    /**
     * @param x represents number of row
     * @param y represents number of height
     */
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param blockWidth  width in points (not pixels)
     * @param blockHeight height in points (not pixels)
     */
    public void setSize(int blockWidth, int blockHeight) {
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
    }

    public void rotate() {

    }
}
