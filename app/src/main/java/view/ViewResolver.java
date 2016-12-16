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

parseJSON("{\"metaData\":{\"idName\":50,\"numberOfFloor\":2},\"pointsArray\":[{\"xPosition\":28.30188751220703,\"yPosition\":52.94240951538086,\"name\":\"A0\",\"id\":0,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":30.566038131713867,\"yPosition\":461.59161376953125,\"name\":\"A1\",\"id\":1,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":30.566038131713867,\"yPosition\":896.7120361328125,\"name\":\"A2\",\"id\":2,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":362.26416015625,\"yPosition\":903.329833984375,\"name\":\"A3\",\"id\":3,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":361.132080078125,\"yPosition\":632,\"name\":\"A5\",\"id\":4,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":370.1886901855469,\"yPosition\":322.6177978515625,\"name\":\"A6\",\"id\":5,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":369.0566101074219,\"yPosition\":249.82199096679688,\"name\":\"A7\",\"id\":6,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":366.7924499511719,\"yPosition\":51.287960052490234,\"name\":\"A8\",\"id\":7,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":552.4528198242188,\"yPosition\":249.82199096679688,\"name\":\"A9\",\"id\":8,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":554.7169799804688,\"yPosition\":137.31936645507812,\"name\":\"A10\",\"id\":9,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":660,\"yPosition\":76.1047134399414,\"name\":\"A11\",\"id\":10,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":484.5283203125,\"yPosition\":67.83245849609375,\"name\":\"A12\",\"id\":11,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":272.8302001953125,\"yPosition\":211.76963806152344,\"name\":\"A14\",\"id\":12,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":109.81132507324219,\"yPosition\":117.4659652709961,\"name\":\"A15\",\"id\":13,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":115.4717025756836,\"yPosition\":253.13088989257812,\"name\":\"A16\",\"id\":14,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":149.4339599609375,\"yPosition\":463.2460632324219,\"name\":\"A17\",\"id\":15,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":81.50943756103516,\"yPosition\":823.916259765625,\"name\":\"A18\",\"id\":16,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":577.3585205078125,\"yPosition\":898.3665161132812,\"name\":\"A20\",\"id\":18,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":570.5660400390625,\"yPosition\":613.801025390625,\"name\":\"A21\",\"id\":19,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":645.2830200195312,\"yPosition\":420.2303771972656,\"name\":\"A22\",\"id\":20,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":458.4905700683594,\"yPosition\":420.2303771972656,\"name\":\"A23\",\"id\":21,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":229.8113250732422,\"yPosition\":362.3246154785156,\"name\":\"A25\",\"id\":23,\"floor\":1,\"isMiddleSource\":false},{\"xPosition\":28.30188751220703,\"yPosition\":137.31936645507812,\"name\":\"B27\",\"id\":25,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":24.90566062927246,\"yPosition\":388.7958068847656,\"name\":\"B28\",\"id\":26,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":27.169811248779297,\"yPosition\":898.3665161132812,\"name\":\"B29\",\"id\":27,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":682.6415405273438,\"yPosition\":893.4031372070312,\"name\":\"B30\",\"id\":28,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":558.1132202148438,\"yPosition\":603.8743286132812,\"name\":\"B31\",\"id\":29,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":552.4528198242188,\"yPosition\":742.84814453125,\"name\":\"B32\",\"id\":30,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":654.339599609375,\"yPosition\":751.1204223632812,\"name\":\"B33\",\"id\":31,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":362.26416015625,\"yPosition\":741.1937255859375,\"name\":\"B34\",\"id\":32,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":105.28302001953125,\"yPosition\":547.623046875,\"name\":\"B37\",\"id\":35,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":306.7924499511719,\"yPosition\":557.5497436523438,\"name\":\"B38\",\"id\":36,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":319.24530029296875,\"yPosition\":291.1832580566406,\"name\":\"B39\",\"id\":37,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":99.62264251708984,\"yPosition\":297.8010559082031,\"name\":\"B40\",\"id\":38,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":358.867919921875,\"yPosition\":383.83245849609375,\"name\":\"B41\",\"id\":39,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":593.2075805664062,\"yPosition\":397.06805419921875,\"name\":\"B42\",\"id\":40,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":412.0754699707031,\"yPosition\":547.623046875,\"name\":\"B43\",\"id\":41,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":671.3207397460938,\"yPosition\":560.858642578125,\"name\":\"B44\",\"id\":42,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":600,\"yPosition\":256.4397888183594,\"name\":\"B45\",\"id\":43,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":594.339599609375,\"yPosition\":142.28273010253906,\"name\":\"B46\",\"id\":44,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":654.339599609375,\"yPosition\":59.560211181640625,\"name\":\"B47\",\"id\":45,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":390.5660400390625,\"yPosition\":129.047119140625,\"name\":\"B48\",\"id\":46,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":159.62265014648438,\"yPosition\":122.4293212890625,\"name\":\"B49\",\"id\":47,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":26.037736892700195,\"yPosition\":31.434555053710938,\"name\":\"B26\",\"id\":24,\"floor\":2,\"isMiddleSource\":true},{\"xPosition\":683.7736206054688,\"yPosition\":900.0209350585938,\"name\":\"A24\",\"id\":22,\"floor\":1,\"isMiddleSource\":true},{\"xPosition\":124.52830505371094,\"yPosition\":744.5026245117188,\"name\":\"Test\",\"id\":33,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":198.1132049560547,\"yPosition\":392.1047058105469,\"name\":\"Olek\",\"id\":34,\"floor\":2,\"isMiddleSource\":false},{\"xPosition\":219.62265014648438,\"yPosition\":823.916259765625,\"name\":\"Biuro\",\"id\":17,\"floor\":1,\"isMiddleSource\":false}],\"connectionsArray\":[{\"distance\":247,\"destination\":1,\"from\":\"A0\",\"source\":0,\"to\":\"A1\"},{\"distance\":263,\"destination\":2,\"from\":\"A1\",\"source\":1,\"to\":\"A2\"},{\"distance\":293,\"destination\":3,\"from\":\"A2\",\"source\":2,\"to\":\"A3\"},{\"distance\":190,\"destination\":18,\"from\":\"A3\",\"source\":3,\"to\":\"A20\"},{\"distance\":94,\"destination\":22,\"from\":\"A20\",\"source\":18,\"to\":\"A24\"},{\"distance\":172,\"destination\":19,\"from\":\"A20\",\"source\":18,\"to\":\"A21\"},{\"distance\":134,\"destination\":20,\"from\":\"A21\",\"source\":19,\"to\":\"A22\"},{\"distance\":153,\"destination\":21,\"from\":\"A21\",\"source\":19,\"to\":\"A23\"},{\"distance\":220,\"destination\":8,\"from\":\"A21\",\"source\":19,\"to\":\"A9\"},{\"distance\":132,\"destination\":21,\"from\":\"A9\",\"source\":8,\"to\":\"A23\"},{\"distance\":131,\"destination\":20,\"from\":\"A9\",\"source\":8,\"to\":\"A22\"},{\"distance\":68,\"destination\":9,\"from\":\"A9\",\"source\":8,\"to\":\"A10\"},{\"distance\":100,\"destination\":10,\"from\":\"A10\",\"source\":9,\"to\":\"A11\"},{\"distance\":74,\"destination\":11,\"from\":\"A10\",\"source\":9,\"to\":\"A12\"},{\"distance\":155,\"destination\":10,\"from\":\"A12\",\"source\":11,\"to\":\"A11\"},{\"distance\":162,\"destination\":6,\"from\":\"A9\",\"source\":8,\"to\":\"A7\"},{\"distance\":44,\"destination\":5,\"from\":\"A7\",\"source\":6,\"to\":\"A6\"},{\"distance\":187,\"destination\":4,\"from\":\"A6\",\"source\":5,\"to\":\"A5\"},{\"distance\":164,\"destination\":3,\"from\":\"A5\",\"source\":4,\"to\":\"A3\"},{\"distance\":105,\"destination\":15,\"from\":\"A1\",\"source\":1,\"to\":\"A17\"},{\"distance\":226,\"destination\":16,\"from\":\"A17\",\"source\":15,\"to\":\"A18\"},{\"distance\":226,\"destination\":17,\"from\":\"A17\",\"source\":15,\"to\":\"Biuro\"},{\"distance\":122,\"destination\":16,\"from\":\"Biuro\",\"source\":17,\"to\":\"A18\"},{\"distance\":93,\"destination\":15,\"from\":\"A25\",\"source\":23,\"to\":\"A17\"},{\"distance\":88,\"destination\":6,\"from\":\"A14\",\"source\":12,\"to\":\"A7\"},{\"distance\":154,\"destination\":13,\"from\":\"A14\",\"source\":12,\"to\":\"A15\"},{\"distance\":82,\"destination\":14,\"from\":\"A15\",\"source\":13,\"to\":\"A16\"},{\"distance\":141,\"destination\":12,\"from\":\"A16\",\"source\":14,\"to\":\"A14\"},{\"distance\":120,\"destination\":7,\"from\":\"A7\",\"source\":6,\"to\":\"A8\"},{\"distance\":299,\"destination\":0,\"from\":\"A8\",\"source\":7,\"to\":\"A0\"},{\"distance\":64,\"destination\":25,\"from\":\"B26\",\"source\":24,\"to\":\"B27\"},{\"distance\":152,\"destination\":26,\"from\":\"B27\",\"source\":25,\"to\":\"B28\"},{\"distance\":308,\"destination\":27,\"from\":\"B28\",\"source\":26,\"to\":\"B29\"},{\"distance\":579,\"destination\":28,\"from\":\"B29\",\"source\":27,\"to\":\"B30\"},{\"distance\":90,\"destination\":30,\"from\":\"B33\",\"source\":31,\"to\":\"B32\"},{\"distance\":168,\"destination\":32,\"from\":\"B32\",\"source\":30,\"to\":\"B34\"},{\"distance\":210,\"destination\":33,\"from\":\"B34\",\"source\":32,\"to\":\"Test\"},{\"distance\":84,\"destination\":29,\"from\":\"B32\",\"source\":30,\"to\":\"B31\"},{\"distance\":128,\"destination\":40,\"from\":\"B31\",\"source\":29,\"to\":\"B42\"},{\"distance\":85,\"destination\":43,\"from\":\"B42\",\"source\":40,\"to\":\"B45\"},{\"distance\":69,\"destination\":44,\"from\":\"B45\",\"source\":43,\"to\":\"B46\"},{\"distance\":72,\"destination\":45,\"from\":\"B46\",\"source\":44,\"to\":\"B47\"},{\"distance\":180,\"destination\":46,\"from\":\"B46\",\"source\":44,\"to\":\"B48\"},{\"distance\":204,\"destination\":47,\"from\":\"B48\",\"source\":46,\"to\":\"B49\"},{\"distance\":116,\"destination\":25,\"from\":\"B49\",\"source\":47,\"to\":\"B27\"},{\"distance\":85,\"destination\":38,\"from\":\"B28\",\"source\":26,\"to\":\"B40\"},{\"distance\":153,\"destination\":34,\"from\":\"B28\",\"source\":26,\"to\":\"Olek\"},{\"distance\":142,\"destination\":39,\"from\":\"Olek\",\"source\":34,\"to\":\"B41\"},{\"distance\":123,\"destination\":37,\"from\":\"Olek\",\"source\":34,\"to\":\"B39\"},{\"distance\":66,\"destination\":37,\"from\":\"B41\",\"source\":39,\"to\":\"B39\"},{\"distance\":114,\"destination\":36,\"from\":\"B41\",\"source\":39,\"to\":\"B38\"},{\"distance\":138,\"destination\":34,\"from\":\"B38\",\"source\":36,\"to\":\"Olek\"},{\"distance\":178,\"destination\":35,\"from\":\"B38\",\"source\":36,\"to\":\"B37\"},{\"distance\":124,\"destination\":34,\"from\":\"B37\",\"source\":35,\"to\":\"Olek\"},{\"distance\":104,\"destination\":34,\"from\":\"B40\",\"source\":38,\"to\":\"Olek\"},{\"distance\":194,\"destination\":37,\"from\":\"B40\",\"source\":38,\"to\":\"B39\"},{\"distance\":207,\"destination\":40,\"from\":\"B41\",\"source\":39,\"to\":\"B42\"},{\"distance\":184,\"destination\":41,\"from\":\"B42\",\"source\":40,\"to\":\"B43\"},{\"distance\":120,\"destination\":42,\"from\":\"B42\",\"source\":40,\"to\":\"B44\"},{\"distance\":123,\"destination\":31,\"from\":\"B31\",\"source\":29,\"to\":\"B33\"},{\"distance\":783,\"destination\":24,\"from\":\"A24\",\"source\":22,\"to\":\"B26\"}]}");




reindex();



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

            System.out.println("S: " + source.getId() + " D: " + destination.getId());
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
                System.out.println("znalazłem: " + p.getName() + " id: " + p.getId());
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
                Connection tmp = new Connection(from,to);
                connections.add(tmp);
                int distance = c.getInt("distance");
                dijkstraGraph[from][to] = distance;
                dijkstraGraph[to][from] = distance;
            }

            JSONObject metaData = receivedData.getJSONObject("metaData");
            numberOfFloor = metaData.getInt("numberOfFloor");

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

    @Override
    public void onDraw(Canvas canvas) {
        markSourceAndDestination();
        drawAllConnections(canvas);
        colorMiddle();
        drawPoints(canvas);
        setLinesProperties();
        onConstructor();
        drawConnections(canvas, selectPointsToDrawConnections(solutionPath));
       for (Point p : points)
            System.out.println(p.getId() + ":" + p.getName());
        /*int j = 0;
        for (Connection c : connections){ System.out.println(j + "    " + c.getFrom() + " : " + c.getTo());j++;}*/

    }
}
