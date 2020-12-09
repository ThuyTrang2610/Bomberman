package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.landscapes.graphics.Sprite;
import uet.oop.bomberman.utilities.MyTimer;

public class Bomb extends Entity{
    int animate = 0;
    private Image move;

    private MyTimer myTimer;
    private boolean exploded;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        BombermanGame.map[yUnit / Sprite.SCALED_SIZE][xUnit / Sprite.SCALED_SIZE] = '.';

        myTimer = new MyTimer(BombermanGame.BOMB_DETONATION_TIME);
        exploded = false;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    @Override
    public void update() {
        animate++;
        if (myTimer.getTimeLeft() == 0) {
            setExploded(true);
        }
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public boolean isExploded() {
        return this.exploded;
    }

    public void setAnimate() {
        move = Sprite.movingSprite(Sprite.bomb,
                Sprite.bomb_1, Sprite.bomb_2, animate, 10).getFxImage();
    }
}
