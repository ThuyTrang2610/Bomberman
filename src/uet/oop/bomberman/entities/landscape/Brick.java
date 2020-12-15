package uet.oop.bomberman.entities.landscape;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    private boolean exploding;
    private boolean exploded;
    private int animate = 0;
    private long explodeStartTime;
    private Item hiddenItem;


    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.exploded = false;
        this.exploding = false;
    }

    public void setHiddenItem(Item hiddenItem) {
        this.hiddenItem = hiddenItem;
    }

    public Item getHiddenItem() {
        return hiddenItem;
    }

    public void setExploding(boolean exploding) {
        this.exploding = exploding;
        this.explodeStartTime = System.currentTimeMillis();
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public boolean isExploding() {
        return exploding;
    }

    public boolean isExploded() {
        return exploded;
    }

    @Override
    public void update() {
        animate++;
        if (isExploding()) {
            img = Sprite.movingSprite(Sprite.brick_exploded1, Sprite.brick_exploded2, animate, 10).getFxImage();
            if (System.currentTimeMillis() - explodeStartTime > 300) {
                setExploded(true);
            }
        }
    }
}
