package com.app.lizhilives.utils;

import java.io.Serializable;

/**
 * Created by Jackson Fu on 15/7/23.
 */
public class Size implements Serializable {

    public int width;

    public int height;

    public Size() {

    }

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
