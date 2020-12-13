package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.landscape.Flame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    private long birthTime;
    private boolean exploded = false;
    private int animate = 0;
    private int power = 0;
    private Image move;
    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        BombermanGame.map[yUnit / Sprite.SCALED_SIZE][xUnit / Sprite.SCALED_SIZE] = '.';
        birthTime = System.currentTimeMillis();
    }

    public void explode() {
        BombermanGame.map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE] = ' ';
        Bomber p = BombermanGame.getBomber();

        if (p == null) {
            return;
        }

        p.setCountBomb(p.getCountBomb() + 1);
        new Flame(x, y, Sprite.bomb_exploded.getFxImage());

        int i = 0;
        while (i < power
                && BombermanGame.map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE + i + 1] == ' ') {
            new Flame(x + (i + 1) * Sprite.SCALED_SIZE, y, Sprite.explosion_horizontal.getFxImage());
            i ++;
        }

        if (BombermanGame.map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE + i + 1] == ' ') {
            new Flame(x + (i + 1) * Sprite.SCALED_SIZE, y, Sprite.explosion_horizontal_right_last.getFxImage());

        }

        i = 0;
        while (i < power
                && BombermanGame.map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - i - 1] == ' ') {
            new Flame(x - (i + 1) * Sprite.SCALED_SIZE, y, Sprite.explosion_horizontal.getFxImage());
            i ++;
        }

        if (BombermanGame.map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - i - 1] == ' ') {
            new Flame(x - (i + 1) * Sprite.SCALED_SIZE, y, Sprite.explosion_horizontal_left_last.getFxImage());

        }

        i = 0;
        while (i < power
                && BombermanGame.map[y / Sprite.SCALED_SIZE - i - 1][x / Sprite.SCALED_SIZE] == ' ') {
            new Flame(x, y - (i + 1) * Sprite.SCALED_SIZE, Sprite.explosion_vertical.getFxImage());
            i ++;
        }

        if (BombermanGame.map[y / Sprite.SCALED_SIZE  - i - 1][x / Sprite.SCALED_SIZE] == ' ') {
            new Flame(x, y  - (i + 1) * Sprite.SCALED_SIZE, Sprite.explosion_vertical_top_last.getFxImage());

        }

        i = 0;
        while (i < power
                && BombermanGame.map[y / Sprite.SCALED_SIZE + i + 1][x / Sprite.SCALED_SIZE] == ' ') {
            new Flame(x, y + (i + 1) * Sprite.SCALED_SIZE, Sprite.explosion_vertical.getFxImage());
            i ++;
        }

        if (BombermanGame.map[y / Sprite.SCALED_SIZE  + i + 1][x / Sprite.SCALED_SIZE] == ' ') {
            new Flame(x, y  + (i + 1) * Sprite.SCALED_SIZE, Sprite.explosion_vertical_down_last.getFxImage());

        }
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }


    @Override
    public void update() {
        animate ++;
        setAnimate();
        if (System.currentTimeMillis() - birthTime >= 2000)
        {
            explode();
            exploded = true;
        }
    }

    public void setAnimate() {
        img = Sprite.movingSprite(Sprite.bomb,
                Sprite.bomb_1, Sprite.bomb_2, (int) ((int) System.currentTimeMillis() - birthTime), 1000).getFxImage();
    }
}
