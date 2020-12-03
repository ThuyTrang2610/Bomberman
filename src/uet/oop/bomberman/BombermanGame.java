package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {

    public static char map[][] = new char[13][31];

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private  static List<Entity> bombs = new ArrayList<>();
    private static HashSet<String> currentlyActiveKeys = new HashSet<>();

    public static HashSet<String> getCurrentlyActiveKeys() {
        return currentlyActiveKeys;
    }

    public static void setCurrentlyActiveKeys(HashSet<String> currentlyActiveKeys) {
        BombermanGame.currentlyActiveKeys = currentlyActiveKeys;
    }

    public static List<Entity> getBombs() {
        return bombs;
    }

    public static void setBombs(List<Entity> bombs) {
        BombermanGame.bombs = bombs;
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();


        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);


        scene.setOnKeyPressed(keyEvent -> {
            currentlyActiveKeys.add(keyEvent.getCode().toString());
        });

        scene.setOnKeyReleased(keyEvent -> {
            currentlyActiveKeys.remove(keyEvent.getCode().toString());
        });
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();


        Entity bomberman = new Bomber(1 * Sprite.SCALED_SIZE, 1 * Sprite.SCALED_SIZE, Sprite.player_down.getFxImage());
        entities.add(bomberman);
        Bomb b = new Bomb(2 * Sprite.SCALED_SIZE, 2 * Sprite.SCALED_SIZE, Sprite.bomb.getFxImage());
        //bombs.add(b);
        createMap();
    }

    public void createMap() {
        String url = "res/levels/Level1.txt";// oke

        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(url);
            Scanner sc = new Scanner(fileInputStream);
            sc.nextLine();

            int curLine = 0;// oke
            while (sc.hasNextLine() && curLine < HEIGHT) {
                String line = sc.nextLine();
                for (int i = 0; i < line.length(); i++)
                    map[curLine][i] = line.charAt(i);
                curLine++;
            }
        } catch (FileNotFoundException e) {

        }

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity o = null;
                switch (map[i][j]) {
                    case '#': {
                        o = new Wall(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.wall.getFxImage());
                        break;
                    }
                    case  '*': {
                        o = new Brick(j * Sprite.SCALED_SIZE
                                , i * Sprite.SCALED_SIZE, Sprite.brick.getFxImage());
                        break;
                    }
                    default: {
                        o = new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                    }
                }


                stillObjects.add(o);
            }
        }
        System.out.println(stillObjects);
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));

    }
}
