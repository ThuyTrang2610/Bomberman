package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.character.enemy.ai.AIHard;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends AIHard {
    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img,
                Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3,
                Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3
        );
    }

    @Override
    public void update() {
        setSpeed((int) (Math.random()*2));
        super.update();
    }
}
