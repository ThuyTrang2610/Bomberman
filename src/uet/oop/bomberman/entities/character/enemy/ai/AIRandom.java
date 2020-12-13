package uet.oop.bomberman.entities.character.enemy.ai;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.character.Mob;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class AIRandom extends AI {
    public AIRandom(int xUnit, int yUnit, Image img, Sprite rightMove1, Sprite rightMove2, Sprite rightMove3, Sprite leftMove1, Sprite leftMove2, Sprite leftMove3) {
        super(xUnit, yUnit, img, rightMove1, rightMove2, rightMove3, leftMove1, leftMove2, leftMove3);
    }

    public int direction() {
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
