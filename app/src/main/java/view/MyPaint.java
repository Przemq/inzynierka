package view;

import android.graphics.Paint;

/**
 * Created by Przemek on 03.05.2016.
 */
public class MyPaint extends Paint {
    private int color;
    private float strokeWidth;
    private Cap shape;
    private boolean antiAlias;
    private Style style;

    public MyPaint(int color, float strokeWidth, Cap shape, boolean antiAlias, Style style) {
        super();
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.shape = shape;
        this.antiAlias = antiAlias;
        this.style = style;
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(shape);
        paint.setAntiAlias(antiAlias);
        paint.setStyle(style);
    }

    public Paint createPaint()
    {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(shape);
        paint.setAntiAlias(antiAlias);
        paint.setStyle(style);
        return paint;
    }
}
