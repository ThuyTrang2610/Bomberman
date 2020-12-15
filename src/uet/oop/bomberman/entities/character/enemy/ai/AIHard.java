package uet.oop.bomberman.entities.character.enemy.ai;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.landscape.Flame;
import uet.oop.bomberman.graphics.Sprite;

public class AIHard extends AIMedium {
    public AIHard(int xUnit, int yUnit, Image img, Sprite rightMove1, Sprite rightMove2, Sprite rightMove3, Sprite leftMove1, Sprite leftMove2, Sprite leftMove3) {
        super(xUnit, yUnit, img, rightMove1, rightMove2, rightMove3, leftMove1, leftMove2, leftMove3);
    }

    @Override
    public int direction() {
        return super.direction();
    }

    @Override
    public boolean canMove(int x, int y) {
        // check for upcoming flames
        for (Bomb bomb : BombermanGame.getBombs()) {
            for (Flame flame : bomb.getFlames()) {
                if (flame.getGridX() == x / Sprite.SCALED_SIZE && flame.getGridY() == y / Sprite.SCALED_SIZE) {
                    return false;
                }
            }
        }

        // check for current flames
        for (Flame flame : BombermanGame.getFlames()) {
            if (flame.getGridX() == x / Sprite.SCALED_SIZE && flame.getGridY() == y / Sprite.SCALED_SIZE) {
                return false;
            }
        }

        return super.canMove(x, y);
    }
}
