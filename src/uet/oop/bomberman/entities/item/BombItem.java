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
            Bomber bomber = BombermanGame.getBomber();
            if (bomber == null) {
                return;
            }
            bomber.setCountBomb(bomber.getCountBomb() + 1);
        }
        super.update();
    }
}
