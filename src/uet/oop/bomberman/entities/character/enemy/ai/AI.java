package uet.oop.bomberman.entities.character.enemy.ai;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.character.Mob;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

abstract public class AI extends Mob {
    int dir;
    int animate = 0;

    Sprite rightMove1, rightMove2, rightMove3;
    Sprite leftMove1, leftMove2, leftMove3;

    public AI(int xUnit, int yUnit, Image img, Sprite rightMove1, Sprite rightMove2, Sprite rightMove3, Sprite leftMove1, Sprite leftMove2, Sprite leftMove3) {
        super(xUnit, yUnit, img);
        fat =  Sprite.SCALED_SIZE;
        height = 0;
        this.rightMove1 = rightMove1;
        this.rightMove2 = rightMove2;
        this.rightMove3 = rightMove3;
        this.leftMove1 = leftMove1;
        this.leftMove2 = leftMove2;
        this.leftMove3 = leftMove3;
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    @Override
    public void update() {
        if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
            dir = direction();
            animate = 0;
        }
        run();
    }

    public void run() {
        animate++;
        if(dir == 1) {
            y -= speed;
        }
        if(dir == 2) {
            y += speed;
        }
        if(dir == 3) {
            x += speed;
            img = Sprite.movingSprite(this.rightMove1,
                    this.rightMove2, this.rightMove3, animate, 50).getFxImage();
        }
        if(dir == 4) {
            x -= speed;
            img = Sprite.movingSprite(this.leftMove1,
                    this.leftMove2, this.leftMove3, animate, 50).getFxImage();
        }
    }

    abstract public int direction();
}
