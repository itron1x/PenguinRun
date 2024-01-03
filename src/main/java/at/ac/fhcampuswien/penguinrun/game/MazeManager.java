package at.ac.fhcampuswien.penguinrun.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Collections;

    public class MazeManager {
        private final ArrayList<Integer> directions = new ArrayList<>();
        private final int size;
        private final int height = 10 * GameSettings.scale;
        private final int width = 10 * GameSettings.scale;
        private int[][] gameBoard;
        private final TilePane tilePane;

        public MazeManager(int size, TilePane tilePane) {
            this.gameBoard = new int[size][size];
            this.size = size;
            this.tilePane = tilePane;
            this.tilePane.setLayoutX(0);
            this.tilePane.setLayoutY(0);
        }

        public void generateDirections() {
            for (int i = 1; i <= 4; i++) {
                directions.add(i);
            }
        }

        public void fillGameboard() {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    gameBoard[i][j] = 1;
                }
            }
        }
        public void recursion(int x, int y) {
            Collections.shuffle(directions);
            for (int i = 0; i < directions.size(); i++) {
                //North
                if (directions.get(i) == 1 && x - 2 <= 0) continue;
                if (directions.get(i) == 1 && gameBoard[x - 2][y] != 0) {
                    gameBoard[x - 1][y] = 0;
                    gameBoard[x - 2][y] = 0;
                    recursion(x - 2, y);
                }
                //South
                if (directions.get(i) == 2 && x + 2 >= size - 1) continue;
                if (directions.get(i) == 2 && gameBoard[x + 2][y] != 0) {
                    gameBoard[x + 1][y] = 0;
                    gameBoard[x + 2][y] = 0;
                    recursion(x + 2, y);
                }
                //East
                if (directions.get(i) == 3 && y + 2 >= size - 1) continue;
                if (directions.get(i) == 3 && gameBoard[x][y + 2] != 0) {
                    gameBoard[x][y + 1] = 0;
                    gameBoard[x][y + 2] = 0;
                    recursion(x, y + 2);
                }
                //West
                if (directions.get(i) == 4 && y - 2 <= 0) continue;
                if (directions.get(i) == 4 && gameBoard[x][y - 2] != 0) {
                    gameBoard[x][y - 1] = 0;
                    gameBoard[x][y - 2] = 0;
                    recursion(x, y - 2);
                }
            }
        }
        public void drawTiles(){
            tilePane.setPrefColumns(size);
            tilePane.setPrefRows(size);
            tilePane.setPrefTileHeight(height);
            tilePane.setPrefTileWidth(width);
            TileManager tiles = new TileManager();
            gameBoard = tiles.setCorners(gameBoard, size);
            /*BackgroundImage bgImageOrange = new BackgroundImage(wall, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,new BackgroundSize(width,height,true,true,true,true));
            BackgroundImage bgImageDark = new BackgroundImage(path, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,new BackgroundSize(width,height,true,true,true,true));
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    Pane pane = new Pane();
                    pane.setPrefHeight(height);
                    pane.setPrefWidth(width);
                    if (gameBoard[i][j] == 1)pane.setBackground(new Background(bgImageOrange));
                    else pane.setBackground(new Background(bgImageDark));
                    tilePane.getChildren().add(pane);
                }
            }*/
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    ImageView image = new ImageView();
                    image.setFitHeight(height);
                    image.setFitWidth(width);
                    image.setImage(tiles.setTile(gameBoard,i,j));
                    tilePane.getChildren().add(image);
                }
            }
        }

        public void printMaze(int[][] gameBoard) {
            System.out.println();
            char space = (char) 32;
            char raute = (char) 217;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (gameBoard[i][j] == 0) {
                        System.out.printf("\u001B[36m %2s", space);
                    } else System.out.printf("\u001B[36m %2s", raute);
                }
                System.out.println();
            }
        }

        public void generateMaze() {
            generateDirections();
            fillGameboard();
            recursion(1, 1);
            gameBoard[1][0] = 0;
            gameBoard[size - 2][size - 1] = 0;
            drawTiles();
            //printMaze(gameBoard);
        }
        public int getTileType(int x, int y){
            int indexY = (int) Math.floor(Math.abs(x)/(double) width);
            int indexX = (int) Math.floor(Math.abs(y)/(double) height);
            return gameBoard[indexX][indexY];
        }
    }
