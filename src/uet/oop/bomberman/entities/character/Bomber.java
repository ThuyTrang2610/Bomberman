package uet.oop.bomberman.entities.character;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.HashSet;
import java.util.List;

public class Bomber extends Mob {
    private Image stop = Sprite.player_down.getFxImage();
    private Image move;
    private int animate = 0;
    private Bomb lastBomb;
    private int countBomb = 1;

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    private int power = 0;
    public int getCountBomb() {
        return countBomb;
    }

    public void setCountBomb(int countBomb) {
        this.countBomb = countBomb;
    }

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        fat = Sprite.SCALED_SIZE * 3 / 4;
        speed = 2;
    }

    @Override
    public void update() {
        animate++;
            run();
        creatBomb();
        if (lastBomb != null) {
            int x1 = x / Sprite.SCALED_SIZE;
            int y1 = y / Sprite.SCALED_SIZE;
            int x2 = (x + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            int y2 = (y + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            int x3 = lastBomb.getX() / Sprite.SCALED_SIZE;
            int y3 = lastBomb.getY() / Sprite.SCALED_SIZE;
            if (
                    (x3 != x1 || y3 != y1)
                    &&(x3 != x1 || y3 != y2)
                    &&(x3 != x2 || y3 != y1)
                    &&(x3 != x2 || y3 != y2)

            ) throughBomb = false;
            //System.out.println(throughBomb);
        }
    }

    public void run() {
        HashSet<String> keys = BombermanGame.getCurrentlyActiveKeys();
        if (!isDead()) {
            if (keys.contains("LEFT") && canMove(x - speed, y)) {
                x -= speed;
                stop = Sprite.player_left.getFxImage();
                move = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 10).getFxImage();
            }
            if (keys.contains("RIGHT") && canMove(x + speed, y)) {
                x += speed;
                stop = Sprite.player_right.getFxImage();
                move = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 10).getFxImage();

            }
            if (keys.contains("UP") && canMove(x, y - speed)) {
                y -= speed;
                stop = Sprite.player_up.getFxImage();
                move = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 10).getFxImage();

            }
            if (keys.contains("DOWN") && canMove(x, y + speed)) {
                y += speed;
                stop = Sprite.player_down.getFxImage();
                move = Sprite.movingSprite(Sprite.player_down_1,
                        Sprite.player_down_2, animate, 10).getFxImage();

            }
        } else {
            return;
        }
        isMeet();
        if (keys.isEmpty()) {
            img = stop;
            animate = 0;
        } else {
            img = move;
        }
    }

    public void creatBomb() {
        HashSet<String> keys = BombermanGame.getCurrentlyActiveKeys();
        if (keys.contains("SPACE")) {
            if (countBomb > 0) {
                if (BombermanGame.getBombs().isEmpty() ||
                    !meet(lastBomb)) {
                    List<Bomb> bomb = BombermanGame.getBombs();
                    lastBomb = new Bomb(x / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE,
                            (y + Sprite.SCALED_SIZE / 4) / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE, Sprite.bomb.getFxImage());
                    bomb.add(lastBomb);
                    BombermanGame.setBombs(bomb);
                    throughBomb = true;
                    countBomb--;
                }
            }

        }
    }

    public void isMeet() {

        for (int i = 1; i < BombermanGame.getEntities().size(); i++) {
            if (meet(BombermanGame.getEntities().get(i))) {
                dead = true;
                break;
            }
        }
    }
}
