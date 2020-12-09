package uet.oop.bomberman.entities.mobs;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.landscapes.graphics.Sprite;

public abstract class Mob extends Entity {

    protected boolean throughBomb = false;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    protected int speed = 1;
    protected int fat = Sprite.SCALED_SIZE;
    protected int height = Sprite.SCALED_SIZE / 4;
    protected boolean dead = false;

    public Mob(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);

    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }



    @Override
    public void update() {

    }

    public  boolean canMove(int x, int y) {
        int x1 = x / Sprite.SCALED_SIZE;
        int y1 = (y + height) / Sprite.SCALED_SIZE;
        int x2 = (x + fat - 1) / Sprite.SCALED_SIZE;
        int y2 = (y + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
        return (BombermanGame.map[y1][x1] != '#'
                && BombermanGame.map[y2][x1] != '#'
                && BombermanGame.map[y1][x2] != '#'
                && BombermanGame.map[y2][x2] != '#'
                && BombermanGame.map[y1][x1] != '*'
                && BombermanGame.map[y2][x1] != '*'
                && BombermanGame.map[y1][x2] != '*'
                && BombermanGame.map[y2][x2] != '*'
                && (BombermanGame.map[y1][x1] != '.' || (BombermanGame.map[y1][x1] == '.' && throughBomb == true))
                && (BombermanGame.map[y2][x1] != '.' || (BombermanGame.map[y2][x1] == '.' && throughBomb == true))
                && (BombermanGame.map[y1][x2] != '.' || (BombermanGame.map[y1][x2] == '.' && throughBomb == true))
                && (BombermanGame.map[y2][x2] != '.' || (BombermanGame.map[y2][x2] == '.' && throughBomb == true))
        );
    };
}
