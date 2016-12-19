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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import algorithm.DijkstraAlgorithm;
import domain.Connection;
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
    private List<Connection> connections;
    private int numberOfFloor = 0;
    private boolean showAllConnections = false;


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
        connections = new ArrayList<>();

parseJSON(readJSONFromFIle("testowyJSON"));




reindex();



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

    public void drawAllConnections(Canvas canvas){
        if(showAllConnections) {
            Paint allPathPaint = new Paint();
            allPathPaint.setColor(Color.LTGRAY);
            allPathPaint.setStrokeWidth(LINE_WIDTH);
            allPathPaint.setAntiAlias(true);
            for (Connection c : connections) {

                if ((points.get(c.getFrom()).getFloor() == floor) && (points.get(c.getTo()).getFloor() == floor))
                    canvas.drawLine(points.get(c.getFrom()).getX(), points.get(c.getFrom()).getY()
                            , points.get(c.getTo()).getX(), points.get(c.getTo()).getY(), allPathPaint);

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

           // System.out.println("S: " + source.getId() + " D: " + destination.getId());
        }
    }

    public void setSource(String sourceId) {
        if (source != null) {
            source.setSelected(false);
        }
        source = points.get(findPoint(sourceId));
        source.setSelected(true);
        invalidate();

        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }

    private int findPoint(String pos){
        int positionn = 1;
        for (Point p : points){
            if (p.getName().equals(pos)) {
                positionn = p.getId();
               // System.out.println("znalazłem: " + p.getName() + " id: " + p.getId());
                break;
            }
        }
        return  positionn;
    }

    public void setDestination( final String destinationId) {
        if (destination != null) {
            destination.setSelected(false);
        }

        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                destination = points.get(findPoint(destinationId));
                destination.setSelected(true);
                invalidate();
            }
        });
    }

    public List<String> getPointsId() {
        List<String> pointsId = new ArrayList<>();
        for (Point point : points) {
            pointsId.add(point.getName());
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
          //  System.out.println(json);
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
                Connection tmp = new Connection(from,to);
                connections.add(tmp);
                int distance = c.getInt("distance");
                dijkstraGraph[from][to] = distance;
                dijkstraGraph[to][from] = distance;
            }

            JSONObject metaData = receivedData.getJSONObject("metaData");
            numberOfFloor = metaData.getInt("numberOfFloor");
            System.out.println("FLOOORS  " + numberOfFloor);

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

    public  ArrayList<Point> getPoints() {
        return points;
    }


    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        if(floor >= numberOfFloor)
            floor = numberOfFloor;
        if(floor <=1)
            floor = 1;
        this.floor = floor;
    }


    public boolean isShowAllConnections() {
        return showAllConnections;
    }

    public void setShowAllConnections(boolean showAllConnections) {
        this.showAllConnections = showAllConnections;
    }

    private void reindex()
    {    Collections.sort(points, new Comparator<Point>() {
        @Override
        public int compare(Point p1, Point p2) {
            if (p1.getId() > p2.getId())
                return 1;
            if (p1.getId() < p2.getId())
                return -1;
            return 0;
        }
    });
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
        return  text.toString();
    }

    @Override
    public void onDraw(Canvas canvas) {
        markSourceAndDestination();
        drawAllConnections(canvas);
        colorMiddle();
        drawPoints(canvas);
        setLinesProperties();
        onConstructor();
        drawConnections(canvas, selectPointsToDrawConnections(solutionPath));
    }
}
