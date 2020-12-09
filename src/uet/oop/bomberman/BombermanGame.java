package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.items.BombItem;
import uet.oop.bomberman.entities.items.SpeedItem;
import uet.oop.bomberman.entities.landscapes.Brick;
import uet.oop.bomberman.entities.landscapes.Flame;
import uet.oop.bomberman.entities.landscapes.Grass;
import uet.oop.bomberman.entities.landscapes.Wall;
import uet.oop.bomberman.entities.mobs.Bomber;
import uet.oop.bomberman.entities.mobs.Enemy1;
import uet.oop.bomberman.entities.mobs.Mob;
import uet.oop.bomberman.entities.landscapes.graphics.Sprite;

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
    public static final int BOMB_DETONATION_TIME = 4; // seconds
    public static final int FLAME_EXIST_TIME = 4;

    private GraphicsContext gc;
    private Canvas canvas;
    private static List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private  static List<Entity> bombs = new ArrayList<>();

    public static List<Entity> getItems() {
        return items;
    }

    public static void setItems(List<Entity> items) {
        BombermanGame.items = items;
    }

    private static List<Entity> items = new ArrayList<>();
    private static HashSet<String> currentlyActiveKeys = new HashSet<>();

    long deadTime = 0;

    public static HashSet<String> getCurrentlyActiveKeys() {
        return currentlyActiveKeys;
    }

    public static List<Entity> getEntities() {
        return entities;
    }

    public static void setEntities(List<Entity> entities) {
        BombermanGame.entities = entities;
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
                    case '1':
                    {

                        entities.add(new Enemy1(j * Sprite.SCALED_SIZE
                                , i * Sprite.SCALED_SIZE, Sprite.balloom_right1.getFxImage()));
                        o = new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage());

                        break;
                    }
                    case 'p':
                    {
                        map[i][j] = ' ';
                        o = new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage());

                        break;

                    }
                    case 'b' : {

                        items.add(new BombItem(j * Sprite.SCALED_SIZE
                                , i * Sprite.SCALED_SIZE, Sprite.powerup_bombs.getFxImage()));
                        o = new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage());

                        break;
                    }
                    case 's':
                        items.add(new SpeedItem(j * Sprite.SCALED_SIZE
                                , i * Sprite.SCALED_SIZE, Sprite.powerup_speed.getFxImage()));
                        o = new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage());

                        break;
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
        for(int i = 0; i < items.size(); i ++) {
            items.get(i).update();
        }

        for(int i = 0;i < entities.size(); i ++) {
            Entity currentEntity = entities.get(i);

            if (currentEntity instanceof Mob) {
                Mob m = (Mob) currentEntity;
                m.update();
                if (m.isDead()) {
                    if(m instanceof Bomber) {
                        if (deadTime == 0) {
                            deadTime = System.currentTimeMillis();
                        } else
                        {
                            if (System.currentTimeMillis() - deadTime <= 1500) {
                                m.setImg(Sprite.movingSprite(
                                        Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3,
                                        (int) (System.currentTimeMillis() - deadTime), 1500).getFxImage());
                            }
                            else {
                                entities.remove(i);
                            }
                        }
                    }  else {
                        entities.remove(i);
                    }
                }
            } else if (currentEntity instanceof Flame) {
                Flame f = (Flame) currentEntity;

                if (deadTime == 0) {
                    deadTime = System.currentTimeMillis();
                } else {
                    if (System.currentTimeMillis() - deadTime <= 1500) {
                        f.setImg(Sprite.movingSprite(
                                Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2,
                                (int) (System.currentTimeMillis() - deadTime), 1500).getFxImage());
                    }
                    else {
                        entities.remove(i);
                    }
                }
            }
        }

        for (int i = 0 ; i < bombs.size() ; i++) {
            Entity currentEntity = bombs.get(i);

            if (currentEntity instanceof Bomb) {
                Bomb currentBomb = (Bomb) currentEntity;
                currentBomb.update();

                if (currentBomb.isExploded()) {
                    System.out.println("EXPLODE");

                    int mapX = currentBomb.getX() / Sprite.SCALED_SIZE;
                    int mapY = currentBomb.getY() / Sprite.SCALED_SIZE;
                    System.out.println(currentBomb);
                    System.out.println(mapX);
                    System.out.println(mapY);



                    for (int direction = 0 ; direction < 4 ; direction++) {
                        int posX = mapX;
                        int posY = mapY;
                        int bombRange = ((Bomber) entities.get(0)).getBombRange();
                        for (int pos = 1 ; pos <= bombRange ; pos++) {
                            Image img;
                            if (direction == 0) {
                                posX = mapX + pos;
                                if (pos == bombRange)
                                    img = Sprite.explosion_horizontal_right_last.getFxImage();
                                else
                                    img = Sprite.explosion_horizontal.getFxImage();
                            } else if (direction == 1) {
                                posY = mapY + pos;
                                if (pos == bombRange)
                                    img = Sprite.explosion_vertical_down_last.getFxImage();
                                else
                                    img = Sprite.explosion_vertical.getFxImage();
                            } else if (direction == 2) {
                                posX = mapX - pos;
                                if (pos == bombRange)
                                    img = Sprite.explosion_horizontal_left_last.getFxImage();
                                else
                                    img = Sprite.explosion_horizontal.getFxImage();
                            } else {
                                posY = mapY - pos;
                                if (pos == bombRange)
                                    img = Sprite.explosion_vertical_top_last.getFxImage();
                                else
                                    img = Sprite.explosion_vertical.getFxImage();
                            }

                            if (map[posY][posX] == ' ') {
                                Entity flame = new Flame(posX * Sprite.SCALED_SIZE, posY * Sprite.SCALED_SIZE, img);
                                entities.add(flame);
                            }
                        }
                    }
                    entities.add(new Flame(mapX * Sprite.SCALED_SIZE, mapY * Sprite.SCALED_SIZE, Sprite.bomb_exploded.getFxImage()));

                    bombs.remove(i);

                    Bomber bomber = (Bomber) entities.get(0);
                    bomber.setCountBomb(bomber.getCountBomb()+1);
                }
            }
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        items.forEach(g -> g.render(gc));
    }
}
