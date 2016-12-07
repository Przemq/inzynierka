package view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    private List<Integer> solutionPath;
    private Point source;
    private Point destination;
    private boolean isMiddleSource = false;
    private int[][] verticesPositions;
    private int[][] verticesPositions2;
    private String name;
    private int floor = 1;
    private boolean shouldShowWarning = true;

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


    int[][] graph2 = new int[][]{
            {0, 6, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {6, 0, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 4, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {4, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 3, 0, 3, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 4, 0, 0, 3, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 3, 0, 0, 3, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 6, 0, 6, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 8, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 5, 0, 6, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 3, 3, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 3, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 10},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0},};

    public ViewResolver(Context context) {
        super(context);
        onConstructor();
    }


    public ViewResolver(Context context, AttributeSet attrs) {
        super(context, attrs);
        onConstructor();
    }

    public ViewResolver(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onConstructor();
    }

    public void onConstructor() {

readJSONFromFIle("testowyJSON");
        points = new ArrayList<>();

        verticesPositions2 = new int[][]{
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

        verticesPositions = new int[][]{
                {32, 45},
                {32, 465},
                {130, 465},
                {32, 900},
                {365, 45},
                {365, 250},
                {365, 600},
                {365, 900},
                {560, 130},
                {560, 250},
                {662, 900},

                {27, 40},
                {27, 140},
                {27, 386},
                {602, 140},
                {602, 255},
                {562, 386},
                {360, 386},
                {562, 605},
                {27, 894},
                {662, 894},
        };


        createPointsArray();
        setSourceAndDestinationIfNull(0, 1);

        dijkstraAlgorithm = new DijkstraAlgorithm(source.getId(), destination.getId());
        dijkstraAlgorithm.dijkstra(graph2);
        solutionPath = dijkstraAlgorithm.getSolutionPath();

        if (source.getId() == destination.getId() && source.getFloor() == floor && shouldShowWarning) {
            showWarning("Source and destination should be different points");
            setShouldShowWarning(false);
        }

    }


    public void setFloor(int floor) {
        this.floor = floor;
    }

    // Tworzy listę punktów i nadaje im pozycje do wyświetlenia
    public void createPointsArray() {
        for (int i = 0; i < verticesPositions.length; i++) {
            name = "A" + String.valueOf(i);
            int tempFloor = 1;
            if (i > 10) tempFloor = 2;
            if (i == 10 || i == 11) isMiddleSource = true;
            else isMiddleSource = false;
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

    // Tworzy listę punktów z otrzymanej ścieżki, które są na danym piętrze
    public ArrayList<Point> selectPointsToDrawConnections(List<Integer> solutionPath) {
        ArrayList<Point> pointsToConnectOnFloor = new ArrayList<Point>();
        for (Integer pointOnPath : solutionPath) {
            for (Point p : points) {
                if (pointOnPath == p.getId() && p.getFloor() == floor) {
                    pointsToConnectOnFloor.add(p);
                }
            }
        }
        return pointsToConnectOnFloor;
    }


    public void drawConnections(Canvas canvas, ArrayList<Point> list) {
        for (int i = 0; i < list.size(); i++) {
            if (i < list.size() - 1) {
                canvas.drawLine(list.get(i).getX(), list.get(i).getY(), list.get(i + 1).getX(), list.get(i + 1).getY(), paint);
            }
        }
    }

    // wykrywa kliknięcie i oblicza najkrótszą odległość od punktów i wyznacza który jest kliknięty
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

    // dodaje punkty do koleji source-destination
    public void addPointsToQueue(Point closestPoint) {
        if (closestPoint != null) {
            clickedPoints.add(closestPoint);
            if (clickedPoints.size() > 2) {
                clickedPoints.get(0).setSelected(false);
                clickedPoints.remove(0);
                destination = clickedPoints.get(1);
                System.out.println("I am i the loop");
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

    // pobiera listę Id punktów potrzebną do listView
    public List<Integer> getPointsId() {
        List<Integer> pointsId = new ArrayList<>();
        for (Point point : points) {
            pointsId.add(point.getId());
        }
        return pointsId;
    }

    //wyznaczanie żródła i celu oraz ich kolorowanie
    public void markSourceAndDestination() {
        for (Point p : points) {
            if (p.getId() == destination.getId() || p.getId() == source.getId())// czy są aktualnmie wybrane
                p.setSelected(true);
            else p.setSelected(false);

            if (p.getId() == source.getId()) {
                p.setSource(true);
            } else p.setSource(false);
            if (p.getId() == destination.getId()) {
                p.setDestination(true);
            } else p.setDestination(false);

            if (p.isSource()) p.setSource(true);
            if (p.isDestination()) p.setDestination(true);
        }
    }

    // kolorowanie pośrednich punktów
    public void colorMiddle() {
        for (Point p : selectPointsToDrawConnections(solutionPath)){
            if(p.isMiddleSource())
                p.setMiddleSource(true);
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

    // inicializuje s i d
    public void setSourceAndDestinationIfNull(int source, int destination) {
        if (this.source == null || this.destination == null) {
            this.source = points.get(source);
            this.destination = points.get(destination);
            clickedPoints.add(0, points.get(source));
            clickedPoints.add(1, points.get(destination));
        }
    }

    public void showWarning(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getContext());
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public boolean isShouldShowWarning() {
        return shouldShowWarning;
    }

    public void setShouldShowWarning(boolean shouldShowWarning) {
        this.shouldShowWarning = shouldShowWarning;
    }

    public void parseJSON(){

    }

   public String readJSONFromFIle(String fileName) {
       File root = Environment.getExternalStorageDirectory();
       File file = new File(root,fileName + ".txt");
       StringBuilder text = new StringBuilder();

       try {
           BufferedReader br = new BufferedReader(new FileReader(file));
           String line;
           while ((line = br.readLine()) != null) {
               text.append(line);
           }
           br.close();
       }
       catch (IOException e) {
          e.printStackTrace();
       }
       System.out.println(text.toString());
       return  text.toString();
    }

    @Override
    public void onDraw(Canvas canvas) {
        markSourceAndDestination();
        colorMiddle();
        drawPoints(canvas);
        setLinesProperties();
        onConstructor();
        drawConnections(canvas, selectPointsToDrawConnections(solutionPath));

    }
}
