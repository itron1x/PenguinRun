package at.ac.fhcampuswien.penguinrun.game;

import at.ac.fhcampuswien.penguinrun.GameManager;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.*;

public class Entities {
    private int[][] gameBoard;
    private List<Integer> itemTilesY;
    private List<Integer> itemTilesX;
    private Pane pane;
    private static int itemCount;
    private boolean temp;
    public final static Image keyImage = new Image(Objects.requireNonNull(Entities.class.getResource("icons/Key.gif")).toExternalForm(), true);

    public Entities(int[][] gameBoard, Pane pane) {
        itemCount = 0;
        this.pane = pane;
        itemTilesY = new ArrayList<>();
        itemTilesX = new ArrayList<>();
        this.gameBoard = gameBoard;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void generateItems() {
        List<Integer> tempListY = new ArrayList<>();
        List<Integer> tempListX = new ArrayList<>();
        Map<Integer, Integer> tempMap = new HashMap<>();

        for (int y = 1; y < (gameBoard.length - 2); y++) {
            for (int x = 1; x < gameBoard.length - 1; x++) {
                if (validTile(y, x) && y >= gameBoard.length / 2 || validTile(y, x) && x >= gameBoard.length / 2) {
                    tempListY.add(y);
                    tempListX.add(x);
                }
            }
        }
        List<Integer> randomNums = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            Random random = new Random();
            int index = random.nextInt(tempListX.size());
            if (i == 0) randomNums.add(index);
            else if(!randomNums.contains(index)) randomNums.add(index);
            else i--;
        }
        for (Integer nums : randomNums) {
            itemTilesY.add(tempListY.get(nums));
            itemTilesX.add(tempListX.get(nums));
        }
        drawItems(itemTilesY, itemTilesX);
    }

    public boolean validTile(int y, int x) {
        int directions = 0;
        if (gameBoard[y][x] == 0) {
            if (gameBoard[y + 1][x] != 0) directions++;
            if (gameBoard[y - 1][x] != 0) directions++;
            if (gameBoard[y][x + 1] != 0) directions++;
            if (gameBoard[y][x - 1] != 0) directions++;
        }
        return directions == 3;
    }

    public void drawItems(List<Integer> itemListY, List<Integer> itemListX) {
        for (int i = 0; i < itemListY.size(); i++) {
            ImageView image = new ImageView();
            image.setImage(keyImage);
            image.setFitWidth(GameSettings.SCALE * 10 - GameSettings.SCALE * 4);
            image.setFitHeight(GameSettings.SCALE * 10 - GameSettings.SCALE * 4);
            image.setLayoutY(itemListY.get(i) * GameSettings.SCALE * 10 + GameSettings.SCALE * 2);
            image.setLayoutX(itemListX.get(i) * GameSettings.SCALE * 10 + GameSettings.SCALE * 2);
            pane.getChildren().add(image);
        }
    }

    public void itemCollision(double y, double x) {
        for (Node image : pane.getChildren()) {
            if (image.getLayoutY() <= y && y <= (image.getLayoutY() + (GameSettings.SCALE * 10 - GameSettings.SCALE * 4)) && image.getLayoutX() <= x && (image.getLayoutX() + (GameSettings.SCALE * 10 - GameSettings.SCALE * 4)) >= x) {
                image.setVisible(false);
                if (!image.isDisabled()) temp = true;
                if (temp) {
                    itemCount ++;
                    temp = false;
                    System.out.println(itemCount);
                }
                image.setDisable(true);
            }
        }
    }
}
