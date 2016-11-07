package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.inz.przemek.dijkstra.R;

/**
 * Created by Przemek on 31.05.2016.
 */
public class Point extends View {
    private int id;
    private int floor;
    private boolean isMiddleSource;

    public void setMiddleSource(boolean middleSource) {
        isMiddleSource = middleSource;
    }

    public boolean isMiddleSource() {

        return isMiddleSource;
    }

    public static float radius = 10;
    private boolean isSelected;
    MyPaint paint;
    Paint textPaint;



    public Point(Context context, int id, float xPosition, float yPosition, int floor, boolean isMiddleSource) {
        super(context);
        setX(xPosition);
        setY(yPosition);
        this.id = id;
        this.floor = floor;
        this.isMiddleSource = isMiddleSource;
        paint = new MyPaint(Color.BLUE, 3, Paint.Cap.ROUND, true, Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setColor(Color.DKGRAY);
        textPaint.setTextSize(25);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        if(isSelected){
            paint = new MyPaint(getResources().getColor(R.color.pointSelected), 3, Paint.Cap.ROUND, true, Paint.Style.STROKE);
            System.out.println("Koloruję");
        }else{
            paint = new MyPaint(Color.BLUE, 3, Paint.Cap.ROUND, true, Paint.Style.STROKE);
        }
    }

    public int getId() {
        return id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {

        this.floor = floor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getX(), getY(), radius, paint.createPaint());
        canvas.drawText(String.valueOf(id), getX() + 2 * radius, getY() + radius, textPaint);
    }
}
