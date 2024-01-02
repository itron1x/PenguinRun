package at.ac.fhcampuswien.penguinrun.game;

public class Camera{
    private double cameraX;
    private double cameraY;
    public double screenWidth;
    public double screenHeight;
    public double mapSize;

    public Camera(double screenWidth, double screenHeight, double mapSize){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.mapSize = mapSize;
        this.cameraX = 0;
        this.cameraY = 0;
    }
    public void updateCamPos(double penX, double penY){
        cameraX = penX -((screenWidth)/2 - GameSettings.scale * 10);
        cameraY = penY - ((screenHeight)/2 - GameSettings.scale * 20);
        cameraX = Math.max(0, Math.min(mapSize-screenWidth, cameraX));
        cameraY = Math.max(0,Math.min(mapSize-screenHeight,cameraY));
    }
    public double getCameraX(){
        return cameraX;
    }
    public double getCameraY(){
        return cameraY;
    }
}
