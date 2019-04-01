package cn.lovezsm.bjcj.entity;

import java.io.Serializable;

public class Pos implements Serializable {
    private float x;
    private float y;

    public Pos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Pos() {
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
