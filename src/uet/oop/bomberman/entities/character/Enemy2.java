package uet.oop.bomberman.entities.character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class Enemy2 extends Mob{

    int dir;
    int animate = 0;
    public Enemy2(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        fat =  Sprite.SCALED_SIZE;
        height = 0;
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    @Override
    public void update() {
        if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
            dir = directtion();
            animate = 0;
        }
        run();
    }
    public void run() {
        animate ++;
        if(dir == 1) {
            y -= 1;
        }
        if(dir == 2) {
            y += 1;
        }
        if(dir == 3) {
            x += 1;
            img = Sprite.movingSprite(Sprite.doll_right1,
                    Sprite.doll_right2, Sprite.doll_right3, animate, 50).getFxImage();
        }
        if(dir == 4) {
            x -= 1;
            img = Sprite.movingSprite(Sprite.doll_left1,
                    Sprite.doll_left2, Sprite.doll_left3, animate, 50).getFxImage();
        }
    }
    public int directtion() {
        ArrayList<Integer> d = new ArrayList<>();
        if(canMove(x, y - 1)) d.add(1);
        if(canMove(x, y + Sprite.SCALED_SIZE)) d.add(2);
        if(canMove(x + Sprite.SCALED_SIZE, y)) d.add(3);
        if(canMove(x - 1, y)) d.add(4);
        double n = Math.random() * d.size();
        if(d.isEmpty()) {
            return -1;
        }
        return d.get((int) n);
    }
}
