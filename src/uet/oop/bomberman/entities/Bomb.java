package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity{
    int birthTime;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        BombermanGame.map[yUnit / Sprite.SCALED_SIZE][xUnit / Sprite.SCALED_SIZE] = '.';
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }



    @Override
    public void update() {

    }
}
