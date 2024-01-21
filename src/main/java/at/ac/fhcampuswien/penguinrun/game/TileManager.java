package at.ac.fhcampuswien.penguinrun.game;

import javafx.scene.image.Image;

import java.util.Objects;

public class TileManager {
    public final static Image bg = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileBG.jpg")).toExternalForm(), true);
    public final static Image wallNSWE = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileNSWE.jpg")).toExternalForm(), true);
    public final static Image wallNSW = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileNSW.jpg")).toExternalForm(), true);
    public final static Image wallNSE = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileNSE.jpg")).toExternalForm(), true);
    public final static Image wallNWE = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileNWE.jpg")).toExternalForm(), true);
    public final static Image wallSWE = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileSWE.jpg")).toExternalForm(), true);
    public final static Image wallNW = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileNW.jpg")).toExternalForm(), true);
    public final static Image wallNE = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileNE.jpg")).toExternalForm(), true);
    public final static Image wallSW = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileSW.jpg")).toExternalForm(), true);
    public final static Image wallSE = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileSE.jpg")).toExternalForm(), true);
    public final static Image wallNS = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileNS.jpg")).toExternalForm(), true);
    public final static Image wallWE = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileWE.jpg")).toExternalForm(), true);
    public final static Image wallN = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileN.jpg")).toExternalForm(), true);
    public final static Image wallS = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileS.jpg")).toExternalForm(), true);
    public final static Image wallW = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileW.jpg")).toExternalForm(), true);
    public final static Image wallE = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileE.jpg")).toExternalForm(), true);
    public final static Image finish = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileGateOpen.jpg")).toExternalForm(), true);
    public final static Image finishClosed0 = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileGateClosed0.jpg")).toExternalForm(), true);
    public final static Image finishClosed1 = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileGateClosed1.jpg")).toExternalForm(), true);
    public final static Image finishClosed2 = new Image(Objects.requireNonNull(TileManager.class.getResource("tiles/tileGateClosed2.jpg")).toExternalForm(), true);

    public Image getFinish(){
        return finish;
    }
    public Image getFinishClosed1(){
        return finishClosed1;
    }
    public Image getW(){
        return wallW;
    }
    public Image getN(){
        return wallN;
    }

    /**
     * Changes the walls to an Integer value depending on where the neighbor walls are.
     * @param gameBoard the game board of the maze.
     * @param size the size of the maze.
     * @return the new game board is being returned.
     */
    public int[][] setWalls(int[][] gameBoard, int size){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //Wall tiles.
                if (i == 0 & j == 0) gameBoard[i][j] = 9; //ES
                else if (i == 0 & j == (size - 1)) gameBoard[i][j] = 10; //SW
                else if (i == size - 1 & j == 0) gameBoard[i][j] = 8; //NE
                else if (i == size - 1 & j == size - 1) gameBoard[i][j] = 11; //NW
                else if (i == 0) {
                    if (gameBoard[i + 1][j] == 0 && gameBoard[i][j - 1] != 0 && gameBoard[i][j + 1] != 0) gameBoard[i][j] = 3; //WE
                    else if (gameBoard[i + 1][j] != 0 && gameBoard[i][j - 1] != 0 && gameBoard[i][j + 1] != 0) gameBoard[i][j] = 6; //ESW
                    else if (gameBoard[i + 1][j] != 0 && gameBoard[i][j - 1] == 0 && gameBoard[i][j + 1] != 0) gameBoard[i][j] = 9; //ES
                    else if (gameBoard[i + 1][j] != 0 && gameBoard[i][j - 1] != 0 && gameBoard[i][j + 1] == 0) gameBoard[i][j] = 10;//SW
                }
                else if (i == size - 1){
                    if (gameBoard[i - 1][j] == 0 && gameBoard[i][j - 1] != 0 && gameBoard[i][j + 1] != 0) gameBoard[i][j] = 3; //WE
                    else if (gameBoard[i - 1][j] != 0 && gameBoard[i][j - 1] != 0 && gameBoard[i][j + 1] != 0) gameBoard[i][j] = 4; //NWE
                    else if (gameBoard[i - 1][j] != 0 && gameBoard[i][j - 1] == 0 && gameBoard[i][j + 1] != 0) gameBoard[i][j] = 8; //NE
                    else if (gameBoard[i - 1][j] != 0 && gameBoard[i][j - 1] != 0 && gameBoard[i][j + 1] == 0) gameBoard[i][j] = 11;//WN
                }
                else if (j == 0){
                    if (gameBoard[i - 1][j] != 0 && gameBoard[i + 1][j] != 0 && gameBoard[i][j + 1] == 0) gameBoard[i][j] = 2; //NS
                    else if (gameBoard[i - 1][j] != 0 && gameBoard[i + 1][j] != 0 && gameBoard[i][j + 1] != 0) gameBoard[i][j] = 5; //NES
                    else if (gameBoard[i - 1][j] == 0 && gameBoard[i + 1][j] != 0 && gameBoard[i][j + 1] == 0) gameBoard[i][j] = 9;//SE
                    else if (gameBoard[i - 1][j] != 0 && gameBoard[i + 1][j] == 0 && gameBoard[i][j + 1] == 0) gameBoard[i][j] = 8;//EN
                }
                else if (j == size - 1){
                    if (gameBoard[i - 1][j] != 0 && gameBoard[i + 1][j] != 0 && gameBoard[i][j - 1] == 0) gameBoard[i][j] = 2; //NS
                    else if (gameBoard[i - 1][j] != 0 && gameBoard[i + 1][j] != 0 && gameBoard[i][j - 1] != 0) gameBoard[i][j] = 7; //NWS
                    else if (gameBoard[i - 1][j] != 0 && gameBoard[i + 1][j] == 0 && gameBoard[i][j - 1] != 0) gameBoard[i][j] = 11; //NW
                    else if (gameBoard[i - 1][j] == 0 && gameBoard[i + 1][j] != 0 && gameBoard[i][j - 1] != 0) gameBoard[i][j] = 10; //WS
                }

                //Rest of the tiles
                //                 N                            S                              W                        E
                else if (gameBoard[i][j] != 0){
                    if (gameBoard[i - 1][j] != 0 && gameBoard[i + 1][j] != 0 && gameBoard[i][j - 1] != 0 && gameBoard[i][j + 1] != 0) gameBoard[i][j] = 1; //NSWE
                    else if (gameBoard[i - 1][j] != 0 && gameBoard[i + 1][j] != 0 && gameBoard[i][j - 1] == 0 && gameBoard[i][j + 1] == 0) gameBoard[i][j] = 2; //NS
                    else if (gameBoard[i - 1][j] == 0 && gameBoard[i + 1][j] == 0 && gameBoard[i][j - 1] != 0 && gameBoard[i][j + 1] != 0) gameBoard[i][j] = 3; //WE
                    else if (gameBoard[i - 1][j] != 0 && gameBoard[i + 1][j] == 0 && gameBoard[i][j - 1] != 0 && gameBoard[i][j + 1] != 0) gameBoard[i][j] = 4; //NWE
                    else if (gameBoard[i - 1][j] != 0 && gameBoard[i + 1][j] != 0 && gameBoard[i][j - 1] == 0 && gameBoard[i][j + 1] != 0) gameBoard[i][j] = 5; //NES
                    else if (gameBoard[i - 1][j] == 0 && gameBoard[i + 1][j] != 0 && gameBoard[i][j - 1] != 0 && gameBoard[i][j + 1] != 0) gameBoard[i][j] = 6; //ESW
                    else if (gameBoard[i - 1][j] != 0 && gameBoard[i + 1][j] != 0 && gameBoard[i][j - 1] != 0 && gameBoard[i][j + 1] == 0) gameBoard[i][j] = 7; //SWN
                    else if (gameBoard[i - 1][j] != 0 && gameBoard[i + 1][j] == 0 && gameBoard[i][j - 1] == 0 && gameBoard[i][j + 1] != 0) gameBoard[i][j] = 8; //NE
                    else if (gameBoard[i - 1][j] == 0 && gameBoard[i + 1][j] != 0 && gameBoard[i][j - 1] == 0 && gameBoard[i][j + 1] != 0) gameBoard[i][j] = 9; //ES
                    else if (gameBoard[i - 1][j] == 0 && gameBoard[i + 1][j] != 0 && gameBoard[i][j - 1] != 0 && gameBoard[i][j + 1] == 0) gameBoard[i][j] = 10;//SW
                    else if (gameBoard[i - 1][j] != 0 && gameBoard[i + 1][j] == 0 && gameBoard[i][j - 1] != 0 && gameBoard[i][j + 1] == 0) gameBoard[i][j] = 11;//WN
                    else if (gameBoard[i - 1][j] != 0 && gameBoard[i + 1][j] == 0 && gameBoard[i][j - 1] == 0 && gameBoard[i][j + 1] == 0) gameBoard[i][j] = 12;//N
                    else if (gameBoard[i - 1][j] == 0 && gameBoard[i + 1][j] == 0 && gameBoard[i][j - 1] == 0 && gameBoard[i][j + 1] != 0) gameBoard[i][j] = 13;//E
                    else if (gameBoard[i - 1][j] == 0 && gameBoard[i + 1][j] != 0 && gameBoard[i][j - 1] == 0 && gameBoard[i][j + 1] == 0) gameBoard[i][j] = 14;//S
                    else if (gameBoard[i - 1][j] == 0 && gameBoard[i + 1][j] == 0 && gameBoard[i][j - 1] != 0 && gameBoard[i][j + 1] == 0) gameBoard[i][j] = 15;//W
                }
            }
        }

        //Special set Tiles.
        gameBoard[0][0] = 13;
        gameBoard[1][0] = 0;
        if (gameBoard[2][1] == 0)gameBoard[2][0] = 14;
        else gameBoard[2][0] = 9;
        gameBoard[size - 3][size - 1] = 16;
        gameBoard[size - 2][size - 1] = 0;
        gameBoard[size - 1][size - 1] = 18;
        return gameBoard;
    }

    /**
     * Sets and returns the correct image according to the Integer value of the tile.
     * @param gameBoard the game board of the maze.
     * @param i the vertical axis of the game board.
     * @param j the horizontal axis of the game board.
     * @return the correct Image is being returned.
     */
    public Image setTile(int[][] gameBoard, int i, int j){
        if (gameBoard[i][j] == 1) return wallNSWE;
        else if (gameBoard[i][j] == 2) return wallNS;
        else if (gameBoard[i][j] == 3) return wallWE;
        else if (gameBoard[i][j] == 4) return wallNWE;
        else if (gameBoard[i][j] == 5) return wallNSE;
        else if (gameBoard[i][j] == 6) return wallSWE;
        else if (gameBoard[i][j] == 7) return wallNSW;
        else if (gameBoard[i][j] == 8) return wallNE;
        else if (gameBoard[i][j] == 9) return wallSE;
        else if (gameBoard[i][j] == 10) return wallSW;
        else if (gameBoard[i][j] == 11) return wallNW;
        else if (gameBoard[i][j] == 12) return wallN;
        else if (gameBoard[i][j] == 13) return wallE;
        else if (gameBoard[i][j] == 14) return wallS;
        else if (gameBoard[i][j] == 15) return wallW;
        else if (gameBoard[i][j] == 16) return finishClosed0;
        else if (gameBoard[i][j] == 17) return finishClosed1;
        else if (gameBoard[i][j] == 18) return finishClosed2;
        return bg;
    }

}
