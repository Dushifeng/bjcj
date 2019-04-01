package cn.lovezsm.bjcj.entity;

public class CalculateDeviceDensityReturnVal {
    private double[] newDensity;
    private double[] deviceDensity;
    private double[] posX;
    private double[] posY;

    public double[] getPosX() {
        return posX;
    }

    public void setPosX(double[] posX) {
        this.posX = posX;
    }

    public double[] getPosY() {
        return posY;
    }

    public void setPosY(double[] posY) {
        this.posY = posY;
    }

    public double[] getNewDensity() {
        return newDensity;
    }

    public void setNewDensity(double[] newDensity) {
        this.newDensity = newDensity;
    }

    public double[] getDeviceDensity() {
        return deviceDensity;
    }

    public void setDeviceDensity(double[] deviceDensity) {
        this.deviceDensity = deviceDensity;
    }
}
