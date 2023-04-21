import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.media.AudioClip;

public class MeteorMassacreApp extends Application
{
    Game game;
    GraphicsContext gc;
    
    AudioClip pew;

    public void start(Stage stage)
    {
        game = new Game();

        Canvas canvas = new Canvas(game.width, game.height);
        gc = canvas.getGraphicsContext2D();

        VBox vbox = new VBox(canvas);

        Scene scene = new Scene(vbox);

        scene.setOnKeyPressed(this::handleKeyPress);
        scene.setOnKeyReleased(this::handleKeyRelease);

        stage.setScene(scene);
        stage.show();
        

        GameTimer timer = new GameTimer();
        timer.start();

    }

    void handleKeyPress(KeyEvent e)
    {
        if (e.getCode() == KeyCode.W)
        {
            game.controls.up = true;
        }

        if (e.getCode() == KeyCode.S)
        {
            game.controls.down = true;
        }

        if (e.getCode() == KeyCode.A)
        {
            game.controls.left = true;
        }

        if (e.getCode() == KeyCode.D)
        {
            game.controls.right = true;
        }

        if (e.getCode() == KeyCode.SPACE)
        {
            game.controls.shoot = true;
        }
        
        if (e.getCode() == KeyCode.ENTER && game.isGameOver)
        {
            game = new Game();
        }
    }

    void handleKeyRelease(KeyEvent e)
    {
        if (e.getCode() == KeyCode.W)
        {
            game.controls.up = false;
        }

        if (e.getCode() == KeyCode.S)
        {
            game.controls.down = false;
        }

        if (e.getCode() == KeyCode.A)
        {
            game.controls.left = false;
        }

        if (e.getCode() == KeyCode.D)
        {
            game.controls.right = false;
        }

        if (e.getCode() == KeyCode.SPACE)
        {
            game.controls.shoot = false;
        }
    }

    void drawLasers()
    {
        gc.setFill(Color.YELLOW);

        for (Laser laser : game.player.lasers)
        {
            gc.fillOval(laser.x, laser.y, laser.width, laser.height);
        }
    }

    void drawShip()
    {
        gc.setFill(Color.RED);
        gc.fillRect(game.player.x, game.player.y, game.player.width, game.player.height);

        double offset = game.player.width/3;
        double turretX = game.player.x + offset;
        double turretY = game.player.y + offset;

        Ship.Direction dir = game.player.direction;

        if (dir == Ship.Direction.UP)
        {
            turretY -= 2 * offset;
        }

        if (dir == Ship.Direction.DOWN)
        {
            turretY += 2 * offset;
        }

        if (dir == Ship.Direction.LEFT)
        {
            turretX -= 2 * offset;
        }

        if (dir == Ship.Direction.RIGHT)
        {
            turretX += 2 * offset;
        }

        gc.fillRect(turretX, turretY, offset, offset);
    }

    void drawMeteors()
    {
        gc.setFill(Color.GRAY);

        for (Meteor m : game.meteors)
        {
            gc.fillOval(m.x, m.y, m.width, m.height);

        }
    }

    void drawGameOver()
    {
        gc.setFill(Color.FIREBRICK);
        gc.fillRect(game.width/3, game.height/3, game.width/3, game.height/3);
        
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(34));
        gc.fillText("GAME OVER", game.width/3 + 55, game.height/3 + 70);
        
        gc.fillText("SCORE: " + (int)game.calculateAccuracy() * game.points, game.width/3 + 40, game.height/3 + 150);
        
        gc.setFont(new Font(14));
        gc.fillText("Press ENTER to play again.", game.width/3 + 50, game.height/3 + 180);
    }
    
    void drawInfo()
    {
        String info = "Level: " + game.level + "    Points: " + game.points + 
        "     Accuracy: " + (int)game.calculateAccuracy() + "%" + 
        "                HP: " + game.player.hp;
        
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(14));
        gc.fillText(info, 15, 15);
    }

    void drawGame()
    {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, game.width, game.height);

        if (game.isGameOver == false)
        {
            drawLasers();
            drawShip();
        }

        drawMeteors();
        drawInfo();

        if (game.isGameOver == true)
        {
            drawGameOver();
        }
    }

    class GameTimer extends AnimationTimer
    {
        public void handle(long t)
        {
            // Write the code that runs every frame
            game.update();
            drawGame();
        }
    }
}