package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit;
        this.y = yUnit;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public int getGridX() {
        return x / Sprite.SCALED_SIZE;
    }

    public int getGridY() {
        return y / Sprite.SCALED_SIZE;
    }

    public int[][] calculateDistMap() {
        char[][] map = BombermanGame.map;
        int posX = this.getGridX();
        int posY = this.getGridY();

        int[][] distMap = new int[map.length][map[0].length];

        int[] xNext = {1, -1, 0, 0};
        int[] yNext = {0, 0, 1, -1};

        for (int i = 0 ; i < distMap.length ; i++) {
            for (int j = 0 ; j < distMap[0].length ; j++)
                distMap[i][j] = -1;
        }

        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        q.add(new Pair(posX, posY));
        distMap[posY][posX] = 0;

        while (!q.isEmpty()) {
            Pair<Integer, Integer> currentPos = q.poll();
            int currentPosX = currentPos.getKey();
            int currentPosY = currentPos.getValue();

            for (int nextIdx = 0 ; nextIdx < 4 ; nextIdx++) {
                int nextPosX = currentPosX + xNext[nextIdx];
                int nextPosY = currentPosY + yNext[nextIdx];

                if (nextPosX <= 0 || nextPosX >= map[0].length-1 || nextPosY <= 0 || nextPosY >= map.length-1) {
                    continue;
                }

                if (map[nextPosY][nextPosX] == '#' || map[nextPosY][nextPosX] == '*') {
                    continue;
                }

                if (distMap[nextPosY][nextPosX] == -1 || distMap[nextPosY][nextPosX] > distMap[currentPosY][currentPosX]+1) {
                    distMap[nextPosY][nextPosX] = distMap[currentPosY][currentPosX]+1;
                    q.add(new Pair(nextPosX, nextPosY));
                }
            }
        }

        return distMap;
    }
}
