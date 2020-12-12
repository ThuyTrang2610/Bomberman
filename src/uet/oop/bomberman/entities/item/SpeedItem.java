package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.character.Bomber;

public class SpeedItem extends Item{
    public SpeedItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    long appearTime = 0;
    @Override
    public void update() {
        super.update();

        if(isMeet()) {
            Bomber tmp = (Bomber) BombermanGame.getEntities().get(0);
                    tmp.setSpeed(tmp.getSpeed() * 2);
        }
    }
}
