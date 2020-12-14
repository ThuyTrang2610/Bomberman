package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.effects.sounds.SoundPlayer;
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
        BombermanGame.map[this.getGridY()][this.getGridX()] = ' ';
        Bomber p = BombermanGame.getBomber();

        if (p == null) {
            return;
        }

        p.setCountBomb(p.getCountBomb() + 1);
        new Flame(x, y, Sprite.bomb_exploded.getFxImage());

        int[] xNextDirection = {0, 0, 1, -1};
        int[] yNextDirection = {1, -1, 0, 0};

        for (int idx = 0 ; idx < 4 ; idx++) {
            Image spriteImage;

            for (int pos = 1 ; pos <= power ; pos++) {
                int nextGridX = this.getGridX() + (xNextDirection[idx]*pos);
                int nextGridY = this.getGridY() + (yNextDirection[idx]*pos);

                if (BombermanGame.map[nextGridX][nextGridY] == '#') {
                    break;
                }

                if (xNextDirection[idx]*pos != 0) {
                    spriteImage = Sprite.explosion_horizontal.getFxImage();
                } else {
                    spriteImage = Sprite.explosion_vertical.getFxImage();
                }

                new Flame(x + (xNextDirection[idx]*pos) * Sprite.SCALED_SIZE, y + (yNextDirection[idx]*pos), spriteImage);
            }

            int nextGridX = this.getGridX() + (xNextDirection[idx]*(power+1));
            int nextGridY = this.getGridY() + (yNextDirection[idx]*(power+1));

            if (BombermanGame.map[nextGridY][nextGridX] == '#') {
                continue;
            }

            if (xNextDirection[idx] * (power+1) > 0) {
                spriteImage = Sprite.explosion_horizontal_right_last.getFxImage();
            } else if (xNextDirection[idx] * (power+1) < 0) {
                spriteImage = Sprite.explosion_horizontal_left_last.getFxImage();
            } else if (yNextDirection[idx] * (power+1) > 0) {
                spriteImage = Sprite.explosion_vertical_down_last.getFxImage();
            } else if (yNextDirection[idx] * (power+1) < 0) {
                spriteImage = Sprite.explosion_vertical_top_last.getFxImage();
            } else {
                continue;
            }
            new Flame(nextGridX * Sprite.SCALED_SIZE, nextGridY * Sprite.SCALED_SIZE, spriteImage);
        }

        SoundPlayer.play("explosion", false);
        exploded = true;
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
        if (System.currentTimeMillis() - birthTime >= 2000) {
            explode();
        }
    }

    public void setAnimate() {
        img = Sprite.movingSprite(Sprite.bomb,
                Sprite.bomb_1, Sprite.bomb_2, (int) ((int) System.currentTimeMillis() - birthTime), 1000).getFxImage();
    }
}
