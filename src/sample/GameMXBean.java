package sample;

/**
 * Created by adamz on 29.11.2016.
 */
public interface GameMXBean {

   String getBoard();

    void dropPiece(int i);

    void notify(int x, int y);
}
