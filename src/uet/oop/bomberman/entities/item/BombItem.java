package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.character.Bomber;

public class BombItem extends Item{
    public BombItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if(isMeet()) {
            ((Bomber)BombermanGame.getEntities().get(0))
                    .setCountBomb(((Bomber)BombermanGame.getEntities().get(0)).getCountBomb() + 1);
        }
        super.update();
    }
}
