package view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedList;

import algorithm.DijkstraAlgorithm;
import domain.Point;

import static domain.Point.LINE_WIDTH;

/**
 * Created by Przemek on 03.05.2016.
 */
public class ViewResolver extends View {
    private DijkstraAlgorithm dijkstraAlgorithm;
    private Paint paint = new Paint();
    private static ArrayList<Point> points;
    private LinkedList<Point> clickedPoints = new LinkedList<>();
    private int[] path;
    private Point source;
    private Point destination;
    private boolean isMiddleSource = false;
    private int[][] verticesPositions;
    private String name;
    private int s = 0;
    private int d = 1;

    private int floor = 1;

    int[][] graph = new int[][]{
            {0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {3, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {3, 2, 0, 5, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 4, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 7, 8, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 2, 7, 0, 4, 0, 3, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 8, 4, 0, 8, 7, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 2, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 3, 7, 2, 0, 0, 2, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 6, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 4, 0, 1, 0, 3, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 1, 0, 0, 5, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 3},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 5, 4, 0, 2},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2, 0}};


  /* public ViewResolver(Context context) {
        super(context);
        onConstructor();
    }*/


    public ViewResolver(Context context, AttributeSet attrs) {
        super(context, attrs);
        onConstructor();
    }

   /* public ViewResolver(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onConstructor();
    }*/

    public void onConstructor() {
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
        setSourceAndDestinationIfNull(0, 1);
        //setSource(2);
        //setDestination(4);

        dijkstraAlgorithm = new DijkstraAlgorithm(source.getId(), destination.getId());
        dijkstraAlgorithm.dijkstra(graph);
        path = dijkstraAlgorithm.getSolutionPath();
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }


    public void createPointsArray() {
        for (int i = 0; i < verticesPositions.length; i++) {
            name = "A" + String.valueOf(i);
            int tempFloor = 1;
            if (i > 5) tempFloor = 2;
            if (i > 10) tempFloor = 3;
            points.add(new Point(getContext(), i, name, verticesPositions[i][0], verticesPositions[i][1], tempFloor, isMiddleSource));
        }
    }

    public void drawPoints(Canvas canvas) {
        for (Point point : points) {
            if (point.getFloor() == floor)
                point.draw(canvas);
        }
    }

    public void isSymmetric(int A[][]) {
        for (int row = 0; row < A.length; row++) {
            for (int col = 0; col < row; col++) {
                if (A[row][col] != A[col][row]) {
                    System.out.println("NIE SYMETRYCZNA");
                }
            }
        }
        System.out.println("SYMETRYCZNA");
    }

    public void drawConnections(Canvas canvas, int[] shortestPath) {
        for (Point point : points) {
            for (int i = 0; i < shortestPath.length; i++) {
                if (shortestPath[i] == point.getId()) {
                    if (i < shortestPath.length - 1 && point.getFloor() == floor)
                        canvas.drawLine(point.getX(), point.getY(), points.get(shortestPath[i + 1]).getX(), points.get(shortestPath[i + 1]).getY(), paint);
                }
            }
        }
    }

    public void drawConnections2(Canvas canvas, int[] shortestPath) {
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).getFloor() == this.floor)
                for (int j = 0; j < shortestPath.length; j++) {
                    if (points.get(i).getId() == shortestPath[j] && j < shortestPath.length - 1)
                        canvas.drawLine(points.get(i).getX(), points.get(i).getY(), points.get(shortestPath[j + 1]).getX(), points.get(shortestPath[j + 1]).getY(), paint);
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
        addPointsToQueue(closestPoint);
        invalidate();
    }

    public void addPointsToQueue(Point closestPoint) {
        if (closestPoint != null) {
            clickedPoints.add(closestPoint);
            if (clickedPoints.size() > 2) {
                clickedPoints.get(0).setSelected(false);
                clickedPoints.remove(0);
                destination = clickedPoints.get(1);
            }
            source = clickedPoints.get(0);
            source.setSelected(true);
            destination.setSelected(true);

            System.out.println("S: " + source.getId() + " D: " + destination.getId());
        }
    }

    public void setSource(final int sourceId) {
        if (source != null) {
            source.setSelected(false);
        }

        source = points.get(sourceId);
        source.setSelected(true);

        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }

    public void setDestination(final int destinationId) {
        if (destination != null) {
            destination.setSelected(false);
        }

        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                destination = points.get(destinationId);
                destination.setSelected(true);
                invalidate();
            }
        });
    }

    public ArrayList<Integer> getPointsId() {
        ArrayList<Integer> pointsId = new ArrayList<>();
        for (Point point : points) {
            pointsId.add(point.getId());
        }
        return pointsId;
    }

    public void colorSourceAndDestination() {
        for (Point p : points) {
            if (p.getId() == source.getId() || p.getId() == destination.getId())
                p.setSelected(true);
            else p.setSelected(false);
        }
    }

    public void setLinesProperties() {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(LINE_WIDTH);
        paint.setAntiAlias(true);
    }

    public void showFloors() {
        for (Point p : points)
            System.out.print(p.getId() + ":" + p.getFloor() + " ");
        System.out.println(" ");
    }

    public void setSourceAndDestinationIfNull(int source, int destination) {
        if (this.source == null || this.destination == null) {
            this.source = points.get(source);
            this.destination = points.get(destination);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        colorSourceAndDestination();
        drawPoints(canvas);
        setLinesProperties();
        onConstructor();
        drawConnections(canvas, path);
        //testPointDrawtylkoTeCoPOdane(canvas,path2);
        for (Point p : points)
            for (int pt : path)
                if (pt == p.getId())
                    System.out.println(p.getX() + " : " + p.getY());

    }

}
