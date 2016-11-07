package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedList;

import algorithm.DijkstraAlgorithm;

/**
 * Created by Przemek on 03.05.2016.
 */
public class DijkstraAlgorithmView extends View {
    private DijkstraAlgorithm dijkstraAlgorithm;
    private Paint paint = new Paint();
    private ArrayList<Point> points;
    private LinkedList<Point> clickedPoints = new LinkedList<>();
    private int[] path;
    private Point source;
    private Point destination;
    private boolean isMiddleSource = false;
    private int[][] verticesPositions;

    private int floor = 1;

    int[][] graph = new int[][]{
            {0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {3, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {3, 2, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 4, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 7, 8, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 2, 7, 0, 4, 0, 3, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 8, 4, 0, 8, 7, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 2, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 3, 7, 2, 0, 0, 2, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 6, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 4, 0, 1, 0, 3, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 1, 0, 0, 0, 5},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 3},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 5, 4, 0, 2},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2, 0}};


    public DijkstraAlgorithmView(Context context) {
        super(context);
        onConstructor();
    }

    public DijkstraAlgorithmView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onConstructor();
    }

    public DijkstraAlgorithmView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onConstructor();
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    private void onConstructor() {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5f);
        paint.setAntiAlias(true);

        points = new ArrayList<>();
        verticesPositions = new int[][]{
                {50, 50},
                {50, 600},
                {250, 300},
                {300, 700},
                {500, 50},
                {500, 550},

                {50, 50},
                {50, 600},
                {250, 400},
                {500, 50},
                {500, 600},

                {70, 70},
                {70, 620},
                {270, 370},
                {350, 620},
                {550, 370},
                {570, 620}};

        createPointsArray();
        setDijkstraTemporaryPointsPositions(0,1);


        dijkstraAlgorithm = new DijkstraAlgorithm(source.getId(), destination.getId());
        dijkstraAlgorithm.dijkstra(graph);
        path = dijkstraAlgorithm.getSolutionPath();
    }

    public void createPointsArray(){
        for (int i = 0; i < verticesPositions.length; i++) {
            int tempFloor = 1;
            if (i > 5) tempFloor = 2;
            if(i>10) tempFloor = 3;
                points.add(new Point(getContext(), i, verticesPositions[i][0], verticesPositions[i][1], tempFloor, isMiddleSource));
        }
    }

    public void setDijkstraTemporaryPointsPositions(int tempSource, int tempDestination ){

        if (source == null && destination == null) {
            source = points.get(tempSource);
            destination = points.get(tempDestination);
        }
    }
    public void drawPoints(Canvas canvas) {
        for (Point point : points) {
            if (point.getFloor() == floor)
                point.draw(canvas);

        }

    }

    public void drawConnections(Canvas canvas, int[] shortestPath) {
        for (int i = 0; i < shortestPath.length; i++) {
            for (Point point : points) {
                if (shortestPath[i] == point.getId()) {
                    if (i < shortestPath.length - 1 && point.getFloor() == floor) // sprawdź
                        canvas.drawLine(point.getX(), point.getY(), points.get(shortestPath[i + 1]).getX(), points.get(shortestPath[i + 1]).getY(), paint);
                }
            }
        }
    }

    public void detectTouchedPoint(float x, float y) {
        Point closestPoint = null;
        float distance = getWidth() * getHeight();

        for (Point point : points) {
            float tempDistance = (float) Math.hypot(x - point.getX(), y - point.getY());
            if ((tempDistance < distance && point.getFloor() == floor)) {
                closestPoint = point;
                distance = tempDistance;
            }
        }

        if (closestPoint != null /*&& closestPoint.getFloor() == floor*/) { // sprawdź
            clickedPoints.add(closestPoint);
            if (clickedPoints.size() > 2) {
                clickedPoints.get(0).setSelected(false);
                clickedPoints.remove(0);
                destination = clickedPoints.get(1);
            }
            source = clickedPoints.get(0);
            System.out.println("Source: " + source.getId() + "   destination: " + destination.getId());
            closestPoint.setSelected(true);
            onConstructor();

        }
        invalidate();
    }

    public void showArrayToDrawConnections(float[] array) {
        System.out.println("Tablica do połączeń");
        for (int i = 0; i < array.length; i++) {
            System.out.println("pts  " + array[i]);
        }
    }

    public void setSource(Point source) {
        this.source = source;
    }


    @Override
    public void onDraw(Canvas canvas) {
        drawPoints(canvas);
        //drawConnectionsBetweenPoints(canvas);
        drawConnections(canvas, path);
        // showArrayToDrawConnections(createArrayToDrawConnections(path));
    }
}
