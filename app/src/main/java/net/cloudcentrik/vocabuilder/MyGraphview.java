package net.cloudcentrik.vocabuilder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import java.util.Random;

/**
 * Created by ismail on 2016-02-16.
 */
public class MyGraphview extends View {
    RectF rectf = new RectF(20, 100, 380, 460);
    int c[] = new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.BLACK, Color.CYAN};
    float temp = 0;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float[] value_degree;

    public MyGraphview(Context context, float[] values) {
        super(context);
        value_degree = new float[values.length];
        //this.rectf=new RectF(100,100,this.getMeasuredWidth()-20,this.getMeasuredWidth() -20);
        for (int i = 0; i < values.length; i++) {
            value_degree[i] = values[i];
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Random r;
        for (int i = 0; i < value_degree.length; i++) {
            if (i == 0) {
                r = new Random();
                /*int color = Color.argb(100, r.nextInt(256), r.nextInt(256),
                        r.nextInt(256));*/

                //paint.setColor(color);
                paint.setColor(c[i]);
                canvas.drawArc(rectf, 0, value_degree[i], true, paint);
            } else {
                temp += value_degree[i - 1];
                r = new Random();
                /*int color = Color.argb(255, r.nextInt(256), r.nextInt(256),
                        r.nextInt(256));*/
                //paint.setColor(color);
                paint.setColor(c[i]);
                canvas.drawArc(rectf, temp, value_degree[i], true, paint);
            }
        }
    }
}