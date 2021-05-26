/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Mahmoud
 */
public class Controller implements EventHandler<KeyEvent>{
     final private static double FRAMES_PER_SECOND = 5.0;
     static public KeyCode[] keyCodes={KeyCode.UP,KeyCode.DOWN,KeyCode.LEFT,KeyCode.RIGHT};

    @FXML private Label scoreLabel;
    @FXML private Label levelLabel;
    @FXML private Label gameOverLabel;
    @FXML private PacManView pacManView;
    private PacManModel pacManModel;
    private static final String[] levelFiles = {"src/levels/level1.txt", "src/levels/level2.txt", "src/levels/level3.txt"};

    private Timer timer;
    private static int ghostEatingModeCounter;
    private boolean paused;

    public Controller() {
        this.paused = false;
    }

    /**
     * Initialize and update the model and view from the first txt file and starts the timer.
     * @throws java.io.IOException
     */
    public void initialize() throws IOException {
        String file = Controller.getLevelFile(0);
        this.pacManModel = new PacManModel();
        this.update(PacManModel.Direction.NONE);
        ghostEatingModeCounter = 25;
        this.startTimer();
    }

    /**
     * Schedules the model to update based on the timer.
     */
    private void startTimer() {
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        update(PacManModel.getCurrentDirection());
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        };

        long frameTimeInMilliseconds = (long)(1000.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    /**
     * Steps the PacManModel, updates the view, updates score and level, displays Game Over/You Won, and instructions of how to play
     * @param direction the most recently inputted direction for PacMan to move in
     */
    private void update(PacManModel.Direction direction) throws IOException {
        this.pacManModel.step(direction);
        this.pacManView.update(pacManModel);
        this.scoreLabel.setText(String.format("Score: %d", this.pacManModel.getScore()));
        this.levelLabel.setText(String.format("Level: %d", this.pacManModel.getLevel()));
        if (PacManModel.isGameOver()) {
            try {
                int score= new Integer(scoreLabel.getText().split("Score: ")[1]);
                String date=LocalDate.now().toString();
                int levelNO=new Integer(levelLabel.getText().split("Level: ")[1]);
                try (FileWriter writer = new FileWriter(new File("src/res/score.txt"),true)) {
                    BufferedWriter bufferedWriter=new BufferedWriter(writer);
                    PrintWriter printWriter=new PrintWriter(bufferedWriter);
                    printWriter.println(levelNO+" "+date+" "+score+"");
                    printWriter.flush();
                }
            } catch (NumberFormatException e) {
            }
            this.gameOverLabel.setText(String.format("GAME OVER"));
            pause();
            mainPageController.mediaPlayer.stop();
            mainPageController.mediaPlayer= new MediaPlayer(new Media(getClass().getResource("/res/gameOver.mp3").toString()));
            mainPageController.mediaPlayer.play();
        }
        if (PacManModel.isYouWon()) {
              try {
                int score= new Integer(scoreLabel.getText().split("Score: ")[1]);
                String date=LocalDate.now().toString();
                int levelNO=new Integer(levelLabel.getText().split("Level: ")[1]);
                try (FileWriter writer = new FileWriter(new File("src/res/score.txt"),true)) {
                    BufferedWriter bufferedWriter=new BufferedWriter(writer);
                    PrintWriter printWriter=new PrintWriter(bufferedWriter);
                    printWriter.println(levelNO+" "+date+" "+score+"");
                    printWriter.flush();
                }
            } catch (NumberFormatException e) {
            }
            this.gameOverLabel.setText(String.format("YOU WON!"));
        }
        //when PacMan is in ghostEatingMode, count down the ghostEatingModeCounter to reset ghostEatingMode to false when the counter is 0
        if (PacManModel.isGhostEatingMode()) {
            ghostEatingModeCounter--;
        }
        if (ghostEatingModeCounter == 0 && PacManModel.isGhostEatingMode()) {
            PacManModel.setGhostEatingMode(false);
        }
    }
    @Override
    public void handle(KeyEvent keyEvent) {
        boolean keyRecognized = true;
        KeyCode code = keyEvent.getCode();
        PacManModel.Direction direction = PacManModel.Direction.NONE;
        if (code == keyCodes[0]) {
            direction = PacManModel.Direction.UP;
        } else if (code == keyCodes[1]) {
            direction = PacManModel.Direction.DOWN;
        } else if (code == keyCodes[2]) {
            direction = PacManModel.Direction.LEFT;
        } else if (code == keyCodes[3]) {
            direction = PacManModel.Direction.RIGHT;
        } else if (code == KeyCode.G) {
            pause();
            this.pacManModel.startNewGame();
            this.gameOverLabel.setText(String.format(""));
            paused = false;
            this.startTimer();
        }else if (code == KeyCode.ESCAPE) {
           Alert alert = new Alert(AlertType.CONFIRMATION);
           alert.setTitle("Confirmation Dialog");
           alert.setHeaderText("Are you sure you want Exit");
           Optional<ButtonType> result = alert.showAndWait();
           if (result.get() == ButtonType.OK){
               System.exit(0);
           } else {
               alert.close();
           }
        }  else {
            keyRecognized = false;
        }
        if (keyRecognized) {
            keyEvent.consume();
            pacManModel.setCurrentDirection(direction);
        }
    }
    public void pause() {
            this.timer.cancel();
            this.paused = true;
    }

    public double getBoardWidth() {
        return PacManView.CELL_WIDTH * this.pacManView.getColumnCount();
    }

    public double getBoardHeight() {
        return PacManView.CELL_WIDTH * this.pacManView.getRowCount();
    }

    public static void setGhostEatingModeCounter() {
        ghostEatingModeCounter = 25;
    }

    public static int getGhostEatingModeCounter() {
        return ghostEatingModeCounter;
    }

    public static String getLevelFile(int x)
    {
        return levelFiles[x];
    }

    public boolean getPaused() {
        return paused;
    }
    
}
