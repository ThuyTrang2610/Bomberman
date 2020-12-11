package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

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

    public boolean meet(Entity mob) {
        int x1 = x;
        int y1 = y;
        int x2 = (x + Sprite.SCALED_SIZE - 1);
        int y2 = (y + Sprite.SCALED_SIZE - 1);
        int x3 = mob.getX();
        int y3 = mob.getY();
        int x4 = mob.getX() + Sprite.SCALED_SIZE - 1;
        int y4 = mob.getY() + Sprite.SCALED_SIZE - 1;
        if (
                (x2 >= x3 && x2 <= x4 && y1 >= y3 && y1 <= y4)
                        || (x2 >= x3 && x2 <= x4 && y2 >= y3 && y2 <= y4)
                        || (x1 >= x3 && x1 <= x4 && y1 >= y3 && y1 <= y4)
                        || (x1 >= x3 && x1 <= x4 && y2 >= y3 && y2 <= y4)

        ) return true;
        return false;
    }
}
