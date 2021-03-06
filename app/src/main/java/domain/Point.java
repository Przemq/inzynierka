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
    private String name;
    private int id;
    private int floor;
    private boolean isMiddleSource;
    private boolean isSelected;
    private boolean isSource;
    private boolean isDestination;

    public static final float EDGE_WIDTH = 2.0f;
    public static final boolean ANTI_ALIAS = true;
    public static final float TEXT_SIZE = 22f;
    public static final float RADIUS = 7f;
    public static final float LINE_WIDTH = 2.0f;


    Paint pointStyle;
    Paint textStyle;
    Paint pointDestination;
    Paint pointStyleNormal;
    Paint pointIsMiddleSource;
    Paint pointSource;

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

        pointDestination = new Paint();
        pointDestination.setColor(Color.GREEN);
        pointDestination.setStrokeWidth(EDGE_WIDTH);
        pointDestination.setAntiAlias(ANTI_ALIAS);
        pointDestination.setStyle(Paint.Style.FILL_AND_STROKE);

        pointStyle = new Paint();
        pointStyle.setColor(Color.BLUE);
        pointStyle.setStrokeWidth(EDGE_WIDTH);
        pointStyle.setAntiAlias(ANTI_ALIAS);
        pointStyle.setStyle(Paint.Style.STROKE);

        pointIsMiddleSource = new Paint();
        pointIsMiddleSource.setColor(Color.GREEN);
        pointIsMiddleSource.setStrokeWidth(EDGE_WIDTH);
        pointIsMiddleSource.setAntiAlias(ANTI_ALIAS);
        pointIsMiddleSource.setStyle(Paint.Style.STROKE);

        pointSource = new Paint();
        pointSource.setColor(Color.RED);
        pointSource.setStrokeWidth(EDGE_WIDTH);
        pointSource.setAntiAlias(ANTI_ALIAS);
        pointSource.setStyle(Paint.Style.FILL_AND_STROKE);


    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
       this.isSelected = selected;
      /*  if(isSelected){
            pointStyle = pointDestination;
        }else{
            pointStyle = pointStyleNormal;
        }*/
    }

    public void setMiddleSource(boolean middleSource) {
        isMiddleSource = middleSource;
        if(isMiddleSource){
            pointStyle = pointIsMiddleSource;
        }else{
            pointStyle = pointStyleNormal;
        }
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

    public boolean isDestination() {
        return isDestination;

    }

    public void setDestination(boolean destination) {
        isDestination = destination;
        if(isDestination){
            pointStyle = pointDestination;
        }else{
            pointStyle = pointStyleNormal;
        }
    }

    public boolean isSource() {
        return isSource;
    }

    public void setSource(boolean source) {
        isSource = source;
        if(isSource){
            pointStyle = pointSource;
        }else{
            pointStyle = pointStyleNormal;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(name, getX() + 3.0f * RADIUS + name.length()*3, getY() + RADIUS, textStyle);
        canvas.drawCircle(getX(), getY() , RADIUS, pointStyle);
    }
}
