package at.ac.fhcampuswien.penguinrun.game;

public class Camera{
    private double cameraX;
    private double cameraY;
    public double screenWidth;
    public double screenHeight;
    public double mapSize;

    /**
     * This method is responsible for the camera movement. It moves with the character, once the character is close
     * to an outer border of the map, the camera stops and the character is able to move around freely.
     * @param screenWidth this is needed for the character do be centered in the middle of the window
     * @param screenHeight also needed for the character to be continuously displayed in the centre of the window
     * @param mapSize needed for the camera to be able to stop once the character is close to an outer border
     */
    public Camera(double screenWidth, double screenHeight, double mapSize){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.mapSize = mapSize;
        this.cameraX = GameSettings.scale * 10;
        this.cameraY = GameSettings.scale * 10;
    }

    /**
     * This method is responsible for the camera movement. It is used in the continuousMovement class. The character
     * is fixated in the middle of the screen, this method also makes sure that the camera movement works as it should
     * regarding the outer bounds of the map.
     * @param penX this is the X coordinate of the camera position currently on the map
     * @param penY this is the Y coordinate of the camera position currently on the map
     */
    public void updateCamPos(double penX, double penY){
        cameraX = penX -((screenWidth)/2 - GameSettings.scale * 10);
        cameraY = penY - ((screenHeight)/2 - GameSettings.scale * 20);
        cameraX = Math.max(0, Math.min(mapSize-screenWidth, cameraX));
        cameraY = Math.max(0,Math.min(mapSize-screenHeight,cameraY));
    }

    /**
     * getter in order to get the X coordinate of the camera
     * @return returns X coordinate
     */
    public double getCameraX(){
        return cameraX;
    }

    /**
     * getter in order to get the Y coordinate of the camera
     * @return returns Y coordinate
     */
    public double getCameraY(){
        return cameraY;
    }
}
