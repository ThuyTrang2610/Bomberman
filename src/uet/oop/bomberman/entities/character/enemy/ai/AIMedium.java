package uet.oop.bomberman.entities.character.enemy.ai;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class AIMedium extends AIRandom {
    public AIMedium(int xUnit, int yUnit, Image img, Sprite rightMove1, Sprite rightMove2, Sprite rightMove3, Sprite leftMove1, Sprite leftMove2, Sprite leftMove3) {
        super(xUnit, yUnit, img, rightMove1, rightMove2, rightMove3, leftMove1, leftMove2, leftMove3);
    }

    public int direction() {
        Bomber bomber = BombermanGame.getBomber();

        int[][] distMap;

        if (bomber == null) {
            return super.direction();
        } else {
            distMap = bomber.calculateDistMap();
        }

        int[] xNext = {1, -1, 0, 0};
        int[] yNext = {0, 0, 1, -1};
        int bestX = getGridX();
        int bestY = getGridY();
        int bestDist = distMap[getGridY()][getGridX()];

        // return random move if cannot get to bomber
        if (bestDist == -1) {
            return super.direction();
        }

        for (int nextIdx = 0 ; nextIdx < 4 ; nextIdx++) {
            int nextPosX = getGridX() + xNext[nextIdx];
            int nextPosY = getGridY() + yNext[nextIdx];

            if (distMap[nextPosY][nextPosX] == -1) {
                continue;
            }

            if (bestDist > distMap[nextPosY][nextPosX] && canMove(nextPosX * Sprite.SCALED_SIZE, nextPosY * Sprite.SCALED_SIZE)) {
                bestX = nextPosX;
                bestY = nextPosY;
                bestDist = distMap[nextPosY][nextPosX];
            }
        }

        if (bestY == getGridY()-1) {
            return 1;
        } else if (bestY == getGridY()+1) {
            return 2;
        } else if (bestX == getGridX()-1) {
            return 4;
        } else if (bestX == getGridX()+1){
            return 3;
        } else {
            return super.direction();
        }
    }
}
