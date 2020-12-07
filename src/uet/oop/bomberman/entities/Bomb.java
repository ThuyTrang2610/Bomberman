package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity{
    int birthTime;
    int animate = 0;
    private Image move;
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
        animate ++;

    }

    public void setAnimate() {
        move = Sprite.movingSprite(Sprite.bomb,
                Sprite.bomb_1, Sprite.bomb_2, animate, 10).getFxImage();
    }
}
