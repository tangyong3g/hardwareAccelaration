/**
 * Created by Simon on 27/04/2016.
 */
package fr.smarquis.hardwareacceleration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;

public class CustomView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG) {{
        setStyle(Style.STROKE);
        setStrokeWidth(10);
        setStrokeJoin(Join.ROUND);
        setStrokeCap(Cap.ROUND);
        // NOTE: This path effect is partly responsible of the defect
        setPathEffect(new CornerPathEffect(5));
    }};

    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG) {{
        setTextSize(50);
    }};

    Point point = new Point();

    Circle circle = new Circle();

    Rect rect = new Rect();

    RoundRect roundRect = new RoundRect();

    Oval oval = new Oval();


    public CustomView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        offsetTo(event.getX(), event.getY());
        invalidate();
        return true;
    }


    private void offsetTo(float x, float y) {
        point.offsetTo(x, y);
        circle.offsetTo(x, y);
        rect.offsetTo(x, y);
        roundRect.offsetTo(x, y);
        oval.offsetTo(x, y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        point.draw(canvas, paint);
        circle.draw(canvas, paint);
        rect.draw(canvas, paint);
        roundRect.draw(canvas, paint);
        oval.draw(canvas, paint);

        textPaint.setColor(willFail(canvas) ? Color.RED : Color.GREEN);
        canvas.drawText("Hardware Accelerated: " + canvas.isHardwareAccelerated(), 50, 50, textPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        offsetTo(w * 0.5F, h * 0.5F);
    }


    public interface Drawing {

        void offsetTo(float x, float y);

        void draw(Canvas canvas, Paint paint);
    }

    public static class Point implements Drawing {

        private float x, y;

        @Override
        public void offsetTo(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            paint.setColor(Color.GREEN);
            canvas.drawPoint(x, y, paint);
        }
    }

    public static class Circle implements Drawing {

        private float x, y;

        private static final int SIZE = 200;

        @Override
        public void offsetTo(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            paint.setColor(willFail(canvas) ? Color.RED : Color.GREEN);
            canvas.drawCircle(x, y, SIZE, paint);
        }
    }

    public static class Rect implements Drawing {

        private static final int SIZE = 200;

        private RectF rect = new RectF(-SIZE, -SIZE, SIZE, SIZE);

        @Override
        public void offsetTo(float x, float y) {
            rect.offsetTo(x - SIZE, y - SIZE);
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            paint.setColor(Color.GREEN);
            canvas.drawRect(rect, paint);
        }
    }

    public static class RoundRect implements Drawing {

        private static final int SIZE = 200;


        private RectF rect = new RectF(-SIZE, -SIZE, SIZE, SIZE);

        @Override
        public void offsetTo(float x, float y) {
            rect.offsetTo(x - SIZE, y - SIZE);
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            paint.setColor(Color.GREEN);
            canvas.drawRoundRect(rect, 5, 5, paint);
        }
    }

    public static class Oval implements Drawing {

        private static final int SIZE = 200;

        private RectF rect = new RectF(-SIZE, -SIZE * 0.5F, SIZE, SIZE * 0.5F);

        @Override
        public void offsetTo(float x, float y) {
            rect.offsetTo(x - SIZE, y - SIZE * 0.5F);
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            paint.setColor(willFail(canvas) ? Color.RED : Color.GREEN);
            canvas.drawOval(rect, paint);
        }
    }

    private static boolean willFail(Canvas canvas) {
        return canvas.isHardwareAccelerated() && "N".equals(Build.VERSION.RELEASE);
    }

}
