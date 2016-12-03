package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    VBox root;

    @FXML
    HBox buttonBox;

    GameImpl game;

    int[][] fields = new int[7][6];
    Pane[][] panes = new Pane[7][6];
    Button[] buttons = new Button[7];
    String[] styles = new String[3];
    int playerId = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        styles[1] = "-fx-border-color: #444; -fx-background-color: #bb0000";
        styles[2] = "-fx-border-color: #444; -fx-background-color: #0000bb";
        for (int y = 0; y < 6; y++) {
            HBox hbox = new HBox();
            for (int x = 0; x < 7; x++) {
                Pane p = new Pane();
                p.setPrefSize(50, 50);
                panes[x][y] = p;
                hbox.getChildren().addAll(p);
                fields[x][y] = 0;
                p.setStyle("-fx-border-color: #444; -fx-background-color: #222");
            }
            root.getChildren().add(hbox);
        }

        for (int i = 0; i < 7; i++) {
            Button b = new Button("+");
            buttonBox.getChildren().add(b);
            b.setPrefWidth(50);
            int finalI = i;
            b.setOnAction(event -> dropPiece(finalI));
        }

        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        startMXBeanServer();
    }

    private void startMXBeanServer() {
        MBeanServer mbs =
                ManagementFactory.getPlatformMBeanServer();

        try {
            ObjectName mxbeanName = new ObjectName("sample:type=GameImpl");
            game = new GameImpl();
            game.setController(this);
            mbs.registerMBean(game, mxbeanName);
        } catch (MalformedObjectNameException | NotCompliantMBeanException | InstanceAlreadyExistsException | MBeanRegistrationException e) {
            e.printStackTrace();
        }
    }

    void dropPiece(int x) {
        System.out.println("Piece dropped on x: " + x);
        for (int y = 5; y >= 0; y--) {
            if (fields[x][y] == 0) {
                fields[x][y] = playerId;
                panes[x][y].setStyle(styles[playerId]);
                playerId = playerId == 1 ? 2 : 1;
                game.notify(x, y);
                break;
            }

        }
    }


    public String getBoardAsString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++)
                sb.append(fields[x][y]).append(" ");
            sb.append("\n");
        }
        return sb.toString();
    }
}
