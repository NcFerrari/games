package lp.frontend.shape;


public interface IBlock {

    void paintShape();

    void setLocation(int x, int y);

    void rotate();

    int getBlockWidth();

    int getBlockHeight();

    int getX();

    int getY();
}
