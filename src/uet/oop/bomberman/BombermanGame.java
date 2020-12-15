package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.effects.sounds.SoundPlayer;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloom;
import uet.oop.bomberman.entities.character.enemy.Doll;
import uet.oop.bomberman.entities.character.Mob;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.character.enemy.Kondoria;
import uet.oop.bomberman.entities.character.enemy.Minvo;
import uet.oop.bomberman.entities.item.BombItem;
import uet.oop.bomberman.entities.item.FlameItem;
import uet.oop.bomberman.entities.item.SpeedItem;
import uet.oop.bomberman.entities.landscape.Brick;
import uet.oop.bomberman.entities.landscape.Flame;
import uet.oop.bomberman.entities.landscape.Grass;
import uet.oop.bomberman.entities.landscape.Wall;
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
    private static List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private static List<Entity> bombs = new ArrayList<>();
    private static List<Entity> flames = new ArrayList<>();

    public static List<Entity> getFlames() {
        return flames;
    }

    public static void setFlames(List<Entity> flames) {
        BombermanGame.flames = flames;
    }

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

    public static Bomber getBomber() {
        if (entities.get(0) instanceof Bomber) {
            return (Bomber) entities.get(0);
        } else {
            return null;
        }
    }

    public static List<Entity> getBombs() {
        return bombs;
    }

    public static void setBombs(List<Entity> bombs) {
        BombermanGame.bombs = bombs;
    }

    public static void main(String[] args) {
        SoundPlayer.play("main_game", true);
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
                        entities.add(new Balloom(j * Sprite.SCALED_SIZE
                                , i * Sprite.SCALED_SIZE, Sprite.balloom_right1.getFxImage()));
                        o = new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage());

                        break;
                    }
                    case '2':
                    {
                        entities.add(new Doll(j * Sprite.SCALED_SIZE
                                , i * Sprite.SCALED_SIZE, Sprite.doll_right1.getFxImage()));
                        o = new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage());

                        break;
                    }
                    case '3':
                    {
                        entities.add(new Minvo(j * Sprite.SCALED_SIZE
                                , i * Sprite.SCALED_SIZE, Sprite.minvo_right1.getFxImage()));
                        o = new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage());

                        break;
                    }
                    case '4':
                    {
                        entities.add(new Kondoria(j * Sprite.SCALED_SIZE
                                , i * Sprite.SCALED_SIZE, Sprite.kondoria_right1.getFxImage()));
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
                        map[i][j] = ' ';
                        items.add(new BombItem(j * Sprite.SCALED_SIZE
                                , i * Sprite.SCALED_SIZE, Sprite.powerup_bombs.getFxImage()));
                        o = new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage());

                        break;
                    }
                    case 'f' : {
                        map[i][j] = ' ';
                        items.add(new FlameItem(j * Sprite.SCALED_SIZE
                                , i * Sprite.SCALED_SIZE, Sprite.powerup_flames.getFxImage()));
                        o = new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage());

                        break;
                    }
                    case 's': {
                        map[i][j] = ' ';
                        items.add(new SpeedItem(j * Sprite.SCALED_SIZE
                                , i * Sprite.SCALED_SIZE, Sprite.powerup_speed.getFxImage()));
                        o = new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                    }
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
            if (entities.get(i) instanceof Mob) {
                Mob m = (Mob) entities.get(i);
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
                    } else {
                        entities.remove(i);
                    }
                }
            }
        }

        for(int i = 0; i < bombs.size(); i ++) {
            Bomb b = (Bomb) bombs.get(i);
            b.update();
            if (b.isExploded()) {
                bombs.remove(b);
            }
        }

        for(int i = 0; i < flames.size(); i ++) {
            Flame f = (Flame) flames.get(i);
            f.update();
            if (f.isDead()) {
                flames.remove(f);
            }
        }

        for (int i = 0 ; i < stillObjects.size() ; i++) {
            Entity stillObject = stillObjects.get(i);
            stillObject.update();

            if (stillObject instanceof Brick) {
                Brick b = (Brick) stillObject;
                if (b.isExploded()) {
                    map[b.getGridY()][b.getGridX()] = ' ';
                    stillObjects.set(i, new Grass(b.getX(), b.getY(), Sprite.grass.getFxImage()));
                } else {
                    for (int j = 0; j < flames.size(); j++) {
                        Flame f = (Flame) flames.get(j);
                        if (f.getGridX() == stillObject.getGridX() && f.getGridY() == stillObject.getGridY()) {
                            b.setExploding(true);
                        }
                    }
                }
            }
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        flames.forEach(g -> g.render(gc));
        items.forEach(g -> g.render(gc));
    }
}
