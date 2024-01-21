package at.ac.fhcampuswien.penguinrun.game;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.Collections;

    public class MazeManager {
        private final ArrayList<Integer> directions = new ArrayList<>();
        private final int size;
        private final int height = 10 * GameSettings.SCALE;
        private final int width = 10 * GameSettings.SCALE;
        private int[][] gameBoard;
        private final TilePane tilePane;
        private boolean won;
        Entities items;
        TileManager tiles;

        /**
         * Constructor for the class.
         * @param size describes the number of tiles horizontal and vertical.
         * @param tilePane the object where the tiles are put into.
         */
        public MazeManager(int size, TilePane tilePane, Pane itemPane) {
            this.gameBoard = new int[size][size];
            this.size = size;
            this.tilePane = tilePane;
            this.tilePane.setLayoutX(0);
            this.tilePane.setLayoutY(0);
            items = new Entities(gameBoard, itemPane);
            tiles = new TileManager();
        }

        public Entities getItems(){
            return items;
        }

        public void setWon(){
            won = true;
        }
        /**
         * Four directions are created and added to the ArrayList directions.
         */
        public void generateDirections() {
            for (int i = 1; i <= 4; i++) {
                directions.add(i);
            }
        }

        /**
         * The game board is filled with walls (1) except for the start position which is 1/1.
         */
        public void fillGameboard() {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    gameBoard[i][j] = 1;
                }
            }
            gameBoard[1][1] = 0;
        }

        /**
         * The maze is being generated shuffling through all directions and filling the whole game board. This is achieved with a Depth first search algorithm.
         * Inspiration: https://www.migapro.com/depth-first-search/
         * @param y describes the vertical axis in the game board.
         * @param x describes the horizontal axis in the game board.
         */
        public void recursion(int y, int x) {
            Collections.shuffle(directions);
            for (int i = 0; i < directions.size(); i++) {
                //North
                if (directions.get(i) == 1 && y - 2 <= 0) continue;
                if (directions.get(i) == 1 && gameBoard[y - 2][x] != 0) {
                    gameBoard[y - 1][x] = 0;
                    gameBoard[y - 2][x] = 0;
                    recursion(y - 2, x);
                }
                //South
                if (directions.get(i) == 2 && y + 2 >= size - 1) continue;
                if (directions.get(i) == 2 && gameBoard[y + 2][x] != 0) {
                    gameBoard[y + 1][x] = 0;
                    gameBoard[y + 2][x] = 0;
                    recursion(y + 2, x);
                }
                //East
                if (directions.get(i) == 3 && x + 2 >= size - 1) continue;
                if (directions.get(i) == 3 && gameBoard[y][x + 2] != 0) {
                    gameBoard[y][x + 1] = 0;
                    gameBoard[y][x + 2] = 0;
                    recursion(y, x + 2);
                }
                //West
                if (directions.get(i) == 4 && x - 2 <= 0) continue;
                if (directions.get(i) == 4 && gameBoard[y][x - 2] != 0) {
                    gameBoard[y][x - 1] = 0;
                    gameBoard[y][x - 2] = 0;
                    recursion(y, x - 2);
                }
            }
        }

        /**
         * The tile pane is set to its correct rows and columns and each tile is filled with the help of the tiles class its setTile method.
         */
        public void drawTiles(){
            tilePane.setPrefColumns(size);
            tilePane.setPrefRows(size);
            tilePane.setPrefTileHeight(height);
            tilePane.setPrefTileWidth(width);
            gameBoard = tiles.setWalls(gameBoard, size);

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    ImageView image = new ImageView();
                    image.setFitHeight(height);
                    image.setFitWidth(width);
                    if (i == size - 2 && j == size - 1) image.setImage(tiles.getFinishClosed1());
                    else image.setImage(tiles.setTile(gameBoard,i,j));
                    tilePane.getChildren().add(image);
                }
            }
        }

        public void finishTile(){
            ImageView finish = new ImageView();
            finish.setFitHeight(height);
            finish.setFitWidth(width);
            finish.setImage(tiles.getFinish());
            tilePane.getChildren().set((tilePane.getPrefRows() * (tilePane.getPrefColumns() - 1)) - 1, finish);

            ImageView aboveFinish = new ImageView();
            aboveFinish.setFitHeight(height);
            aboveFinish.setFitWidth(width);
            aboveFinish.setImage(tiles.getW());
            tilePane.getChildren().set((tilePane.getPrefRows() * (tilePane.getPrefColumns())) - 1, aboveFinish);

            ImageView belowFinish = new ImageView();
            belowFinish.setFitHeight(height);
            belowFinish.setFitWidth(width);
            belowFinish.setImage(tiles.getN());
            tilePane.getChildren().set((tilePane.getPrefRows() * (tilePane.getPrefColumns() - 2)) - 1, belowFinish);
        }

        /**
         * Print the maze in the console. For development purpose only.
         * @param gameBoard the game board is given as an argument for this method.
         */
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

        /**
         * This method executes all methods in the correct order and also adds the entrance and exit.
         */
        public void generateMaze() {
            generateDirections();
            fillGameboard();
            recursion(1, 1);
            gameBoard[1][0] = 0;
            gameBoard[size - 2][size - 1] = 0;
            drawTiles();
            items.generateItems();
            //printMaze(gameBoard);
        }

        /**
         * Returns the tile in relation to the coordinates of the character.
         * @param y the vertical axis of the coordinates.
         * @param x the horizontal axis of the coordinates.
         * @return returns the Integer value of the corresponding game board tile.
         */
        public int getTileType(int y, int x) throws NullPointerException{
            int indexY = (int) Math.floor(Math.abs(y)/(double) width);
            int indexX = (int) Math.floor(Math.abs(x)/(double) height);
            if (!won && indexY != size - 1 && indexX != size) return gameBoard[indexX][indexY];
            else if (won) return gameBoard[indexX][indexY];
            else return 1;
        }
    }
