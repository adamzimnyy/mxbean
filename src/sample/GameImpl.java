package sample;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationEmitter;
import javax.management.StandardEmitterMBean;

/**
 * Created by adamz on 29.11.2016.
 */
public class GameImpl extends NotificationBroadcasterSupport implements GameMXBean {
    Controller controller;

    static int seq =0;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public String getBoard() {
        return controller.getBoardAsString();
    }

    @Override
    public void dropPiece(int i) {
        controller.dropPiece(i);
    }

    @Override
    public void notify(int x, int y) {
        Notification n = new Notification("MOVE",this,seq++,"x="+x+" y="+y);
        sendNotification(n);
    }
}
