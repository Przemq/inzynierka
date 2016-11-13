package domain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Przemek on 31.05.2016.
 */
public class Point extends View {
    private int id;
    private int floor;
    private boolean isMiddleSource;
    private String name;
    public static final float EDGE_WIDTH = 3f;
    public static final boolean ANTI_ALIAS = true;
    public static final float TEXT_SIZE = 25f;
    public static final float RADIUS = 10f;
    public static final float SPACE_BETWEEN_POINT_AND_TEXT = 3.5f;
    public static final float LINE_WIDTH = 3f;

    private boolean isSelected;
    Paint pointStyle;
    Paint textStyle;
    Paint pointStyleSelected;
    Paint pointStyleNormal;

    public Point(Context context, int id, String name, float xPosition, float yPosition, int floor, boolean isMiddleSource) {
        super(context);
        setX(xPosition);
        setY(yPosition);
        this.id = id;
        this.floor = floor;
        this.isMiddleSource = isMiddleSource;
        this.name = name;

        initializePaintStyle();
    }

    public void initializePaintStyle(){


        pointStyleNormal = new Paint();
        pointStyleNormal.setColor(Color.BLUE);
        pointStyleNormal.setStrokeWidth(EDGE_WIDTH);
        pointStyleNormal.setAntiAlias(ANTI_ALIAS);
        pointStyleNormal.setStyle(Paint.Style.STROKE);

        textStyle = new Paint();
        textStyle.setColor(Color.DKGRAY);
        textStyle.setTextSize(TEXT_SIZE);
        textStyle.setTextAlign(Paint.Align.CENTER);

        pointStyleSelected = new Paint();
        pointStyleSelected.setColor(Color.RED);
        pointStyleSelected.setStrokeWidth(EDGE_WIDTH);
        pointStyleSelected.setAntiAlias(ANTI_ALIAS);
        pointStyleSelected.setStyle(Paint.Style.FILL_AND_STROKE);

        pointStyle = new Paint();
        pointStyle.setColor(Color.BLUE);
        pointStyle.setStrokeWidth(EDGE_WIDTH);
        pointStyle.setAntiAlias(ANTI_ALIAS);
        pointStyle.setStyle(Paint.Style.STROKE);

    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
       this.isSelected = selected;
        if(isSelected){
            pointStyle = pointStyleSelected;
        }else{
            pointStyle = pointStyleNormal;
        }
    }

    public void setMiddleSource(boolean middleSource) {
        isMiddleSource = middleSource;
    }

    public boolean isMiddleSource() {

        return isMiddleSource;
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(name, getX() + SPACE_BETWEEN_POINT_AND_TEXT * RADIUS, getY() + RADIUS, textStyle);
        canvas.drawCircle(getX(), getY(), RADIUS, pointStyle);
    }
}
