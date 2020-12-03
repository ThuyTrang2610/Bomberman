package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public  abstract class Mob extends Entity{

    protected boolean throughBomb = false;
    protected  int speed = 1;
    protected int fat = Sprite.SCALED_SIZE;

    public Mob(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);

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
        int y1 = (y + Sprite.SCALED_SIZE / 4) / Sprite.SCALED_SIZE;
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
