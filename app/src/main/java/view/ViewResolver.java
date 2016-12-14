package view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private String name;
    private int floor = 1;
    private boolean shouldShowWarning = true;
    private String dataJSON;
    public static final int X_CORRECTION = 3;


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
        points = new ArrayList<>();
parseJSON("{\"metaData\":{\"idName\":9},\"pointsArray\":[{\"xPosition\":50.943397521972656,\"yPosition\":77.75916290283203,\"name\":\"A0\",\"id\":0,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":57.7358512878418,\"yPosition\":112.50261688232422,\"name\":\"A1\",\"id\":1,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":55.47169876098633,\"yPosition\":167.09947204589844,\"name\":\"A2\",\"id\":2,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":48.67924499511719,\"yPosition\":193.5706787109375,\"name\":\"A3\",\"id\":3,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":54.339622497558594,\"yPosition\":229.9685821533203,\"name\":\"A4\",\"id\":4,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":305.6603698730469,\"yPosition\":79.41361236572266,\"name\":\"A5\",\"id\":5,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":193.58489990234375,\"yPosition\":865.2774658203125,\"name\":\"A6\",\"id\":6,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":19.245283126831055,\"yPosition\":911.6021118164062,\"name\":\"A7\",\"id\":7,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":365.6603698730469,\"yPosition\":913.2565307617188,\"name\":\"A8\",\"id\":8,\"floor\":1,\"isMiddleSource\":false}],\"connectionsArray\":[]}");

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
        // System.out.println(dataJSON);
        //parseJSON(dataJSON);
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
                //System.out.println("I am i the loop");
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

    public void colorMiddle() {
        for (Point p : selectPointsToDrawConnections(solutionPath)) {
            if (p.isMiddleSource())
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

    public void parseJSON(String json) {
        try {
            System.out.println(json);
            JSONObject receivedData = new JSONObject(json);
            JSONArray pointsArray = receivedData.getJSONArray("pointsArray");
            JSONArray connectionsArr = receivedData.getJSONArray("connectionsArray");
            int[][] dijkstraGraph = new int[pointsArray.length()][pointsArray.length()];
            zerosTab(dijkstraGraph);
            for (int i = 0; i < pointsArray.length(); i++) {
                JSONObject p = pointsArray.getJSONObject(i);
                int id = p.getInt("id");
                String name = p.getString("name");
                float xPosition = (float) p.getDouble("xPosition");
                float yPosition = (float) p.getDouble("yPosition");
                int floor = p.getInt("floor");
                boolean isMiddleSource = p.getBoolean("isMiddleSource");
                Point pt = new Point(this.getContext(), id, name, xPosition + X_CORRECTION, yPosition, floor, isMiddleSource);
                points.add(pt);
            }

            for (int i = 0; i < connectionsArr.length(); i++) {
                JSONObject c = connectionsArr.getJSONObject(i);
                int from = c.getInt("source");
                int to = c.getInt("destination");
                int distance = c.getInt("distance");
                dijkstraGraph[from][to] = distance;
                dijkstraGraph[to][from] = distance;
            }

            setSourceAndDestinationIfNull(0, 1);
            dijkstraAlgorithm = new DijkstraAlgorithm(source.getId(), destination.getId(),pointsArray.length());
            dijkstraAlgorithm.dijkstra(dijkstraGraph);
            solutionPath = dijkstraAlgorithm.getSolutionPath();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void zerosTab(int[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab.length; j++) {
                tab[i][j] = 0;
            }
        }
    }

    public String getDataJSON() {
        return dataJSON;
    }

    public void setDataJSON(String dataJSON) {
        this.dataJSON = dataJSON;
    }

    @Override
    public void onDraw(Canvas canvas) {
        markSourceAndDestination();
        colorMiddle();
        drawPoints(canvas);
        setLinesProperties();
        onConstructor();
        drawConnections(canvas, selectPointsToDrawConnections(solutionPath));
        for (Point p : points)
            System.out.println(p.getId() + ":" + p.getName() + ":" + p.getX() + ":" + p.getY() + ":" + p.getFloor() + ":" + p.isMiddleSource());
    }
}
