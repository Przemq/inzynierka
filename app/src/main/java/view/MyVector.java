package view;

/**
 * Created by Przemek on 31.05.2016.
 */
public class MyVector {
    private float x;
    private float y;

    public MyVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {

        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {

        return x;
    }

    public float getY() {
        return y;
    }
}
