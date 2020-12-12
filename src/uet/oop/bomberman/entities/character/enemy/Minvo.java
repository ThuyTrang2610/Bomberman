package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Mob;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

public class Minvo extends Balloom {

    int dir;
    int animate = 0;
    public Minvo(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        fat =  Sprite.SCALED_SIZE;
        height = 0;

        setSpeed(1);
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    @Override
    public void update() {
        if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
            dir = direction();
            animate = 0;
        }
        run();
    }
    public void run() {
        animate ++;
        if(dir == 1) {
            y -= speed;
        }
        if(dir == 2) {
            y += speed;
        }
        if(dir == 3) {
            x += speed;
            img = Sprite.movingSprite(Sprite.minvo_left1,
                    Sprite.minvo_left2, Sprite.minvo_left3, animate, 50).getFxImage();
        }
        if(dir == 4) {
            x -= speed;
            img = Sprite.movingSprite(Sprite.minvo_left1,
                    Sprite.minvo_left2, Sprite.minvo_left3, animate, 50).getFxImage();
        }
    }

    private int[][] calculateDistMap(int posX, int posY, int bomberPosX, int bomberPosY, char[][] map) {
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

                if (nextPosX <= 0 || nextPosX >= map[0].length || nextPosY <= 0 || nextPosY >= map.length) {
                    continue;
                }

                if (map[nextPosY][nextPosX] == '#' && map[nextPosY][nextPosX] == '*') {
                    continue;
                }

                if (distMap[nextPosY][nextPosX] == -1 || distMap[nextPosY][nextPosX] > distMap[currentPosY][currentPosX]) {
                    distMap[nextPosY][nextPosX] = distMap[currentPosY][currentPosX]+1;
                    q.add(new Pair(nextPosX, nextPosY));
                }
            }
        }
        for(int i = 0 ; i < distMap.length ; i++) {
            for (int j = 0 ; j < distMap[0].length ; j++) {
                System.out.print(distMap[i][j]+ " ");
            }
            System.out.println();
        }
        System.out.println("\n\n");
        return distMap;
    }

    public int direction() {
        Bomber bomber = BombermanGame.getBomber();

        int[][] distMap = bomber.calculateDistMap();

        int[] xNext = {1, -1, 0, 0};
        int[] yNext = {0, 0, 1, -1};
        int bestX = getGridX();
        int bestY = getGridY();
        int bestDist = distMap[getGridY()][getGridX()];

        // return random move if cannot get to bomber
        if (bestDist == -1) {
            return super.direction();
        }

        System.out.println(bestX + " " + bestY);


        for (int nextIdx = 0 ; nextIdx < 4 ; nextIdx++) {
            int nextPosX = getGridX() + xNext[nextIdx];
            int nextPosY = getGridY() + yNext[nextIdx];

            if (distMap[nextPosY][nextPosX] == -1) {
                continue;
            }

            if (bestDist > distMap[nextPosY][nextPosX]) {
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
            return 0;
        }

//        for(int i = 0 ; i < distMap.length ; i++) {
//            for (int j = 0 ; j < distMap[0].length ; j++) {
//                System.out.print(distMap[i][j]);
//            }
//            System.out.println();
//        }
//        System.out.println("\n\n");
    }
}
