package com.example.natalia.quadraticfun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Natalia on 4/20/16.
 */
public class DrawFunction extends View {
    private ArrayList<Double> lista_x, lista_y;
    private double delta, ekstremum,a,b,c;
    double x_max, x_min, y_max, y_min;
    double skala_x, skala_y;
    private double x1, x2;
    private Paint p_axis, p_background, p_plot, p_root;
    private Path mPath;
    private boolean isLinear = false;

    public DrawFunction(Context context) {
        super(context);
        Init();
    }

    public DrawFunction(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    public DrawFunction(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float h,w,avrgH,avrgW;
        h = canvas.getHeight();
        w = canvas.getWidth();
        canvas.drawRect(0,0,w,h, p_background);
        drawPlot(canvas);




    }

    private void Init()
    {
        //isLinear = false;
        this.setDrawingCacheEnabled(true);
        p_root = new Paint();
        p_root.setColor(Color.YELLOW);
        p_root.setStrokeWidth(4f);

        p_axis = new Paint(Paint.ANTI_ALIAS_FLAG);
        p_axis.setColor(Color.BLACK);
        p_axis.setStyle(Paint.Style.STROKE);

        p_plot = new Paint(Paint.ANTI_ALIAS_FLAG);
        p_plot.setColor(Color.BLUE);
        p_plot.setStrokeWidth(1f);
        p_plot.setStyle(Paint.Style.STROKE);

        p_background = new Paint();
        p_background.setColor(Color.WHITE);



        setFunctionParameters(1, 1, 1);

    }

    public void setFunctionParameters(double a, double b, double c)
    {
        this.a = a;
        this.b = b;
        this.c = c;

    }

    private void calculateX_axisLimits()
    {
        if(delta<0) {

            x_max = ekstremum - 1;
            x_min = ekstremum + 1;
        }
        else if(delta==0)
        {
            x_max = ekstremum - 1;
            x_min = ekstremum + 1;
        }
        else
        {
            x1 =((-b+Math.sqrt(delta))/(2*a));
            x2 =((-b-Math.sqrt(delta))/(2*a));
            if(x1 < x2) {
                x_max = x1 - (x2 - x1);
                x_min = x2 + (x2 - x1);
            }
            else
            {
                x_max = x2 - (x1 - x2);
                x_min = x1 + (x1 - x2);
            }
        }
    }
    private double evaluateExtremum()
    {
        return -b / (2*a);
    }

    private double evaluateDelta()
    {
        return (b*b) - (4*a*c);
    }

    private double evaluateValues(double x)
    {
        return a*x*x + b*x + c;
    }

    private void getCoordinates(int width)
    {
        this.delta = evaluateDelta();
        this.ekstremum = evaluateExtremum();
        calculateX_axisLimits();
        double step = (x_min - x_max)/width;
        skala_x = width/ (x_min - x_max);
        lista_x = new ArrayList<>();
        lista_y = new ArrayList<>();

        for(int i=0 ; i<width; i++)
        {
            double x = x_max +i*step;
            lista_x.add(x);
            lista_y.add(evaluateValues(x));
        }
        y_max = Collections.min(lista_y);
        y_min = Collections.max(lista_y);

    }

    private void drawPlot(Canvas canvas)
    {
        getCoordinates(canvas.getWidth());
        skala_y = canvas.getHeight()/(y_min - y_max) ;
        Path mPath = new Path();
        for(int i = 0; i< lista_x.size(); i++)
        {
            if(i==0)
                mPath.moveTo(rescaleX(lista_x.get(i)), rescaleY(lista_y.get(i)));
            else
                mPath.lineTo(rescaleX(lista_x.get(i)), rescaleY(lista_y.get(i)));
        }
        canvas.drawPath(mPath, p_plot);
        Xlinedraw(canvas);
        Ylinedraw(canvas);
        rootsDraw(canvas);

    }

    private float rescaleX(double x)
    {
        return (float)((x - x_max)* skala_x);
    }

    private float rescaleY(double y)
    {
        return (float)((-y + y_min)* skala_y);
    }



    private void rootsDraw(Canvas canvas)
    {
        if(delta>0)
        {
            canvas.drawPoint(rescaleX(x1), rescaleY(0), p_root);
            canvas.drawPoint(rescaleX(x2), rescaleY(0), p_root);
            canvas.drawText("("+ x1 +" ,0)", rescaleX(x1)-15, rescaleY(0)-3, p_axis);
            canvas.drawText("("+ x2 +" ,0)", rescaleX(x2)-15, rescaleY(0)-3, p_axis);
            if(a>0)
            {
                canvas.drawPoint(rescaleX(ekstremum), rescaleY(evaluateValues(ekstremum)), p_root);
                canvas.drawText("("+ ekstremum +","+evaluateValues(ekstremum)+")", rescaleX(ekstremum), rescaleY(evaluateValues(ekstremum))-3, p_axis);
            }
            else
            {
                canvas.drawPoint(rescaleX(ekstremum), rescaleY(evaluateValues(ekstremum)), p_root);
                canvas.drawText("("+ ekstremum +", "+evaluateValues(ekstremum)+")", rescaleX(ekstremum), rescaleY(evaluateValues(ekstremum))+3, p_axis);
            }
        }
        else
        {

            canvas.drawPoint(rescaleX(ekstremum), rescaleY(0), p_root);
            canvas.drawText("("+ ekstremum +", "+evaluateValues(ekstremum)+")", rescaleX(ekstremum)+20, rescaleY(0), p_axis);
        }
    }

    private void Xlinedraw(Canvas canvas)
    {
        if(delta>=0)
            canvas.drawLine(0, rescaleY(0),canvas.getWidth(), rescaleY(0), p_axis);
    }

    private void Ylinedraw(Canvas canvas)
    {
        if(x_max * x_min <=0)
            canvas.drawLine(rescaleX(0),0, rescaleX(0),canvas.getHeight(), p_axis);

    }
}
