package com.example.viewclass00;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {
    Paint p1= new Paint();
    Paint p2= new Paint(); //빨간색
    Paint p3= new Paint(); //파란색
    Paint p4= new Paint(); //노란색
    Paint p5= new Paint(); //녹색

    int myData_x[]= new int[30000];
    int myData_y[]= new int[30000];
    int myData_color[]= new int[30000];

    static int radius = 15;

    int dataNumber=0;
    int mx, my;
    static int whatColor=0;

    //생성자
    public MyView(Context context, AttributeSet attr){
        super(context);
        //색상 설정
        p1.setColor(Color.BLACK);
        p2.setColor(Color.RED);
        p3.setColor(Color.BLUE);
        p4.setColor(Color.YELLOW);
        p5.setColor(Color.GREEN);
        //좌표값 초기화
        myData_x[0]=0;
        myData_y[0]=0;
        myData_color[0]=0;
    }

    @Override
    public void onDraw(Canvas canvas){
        //터치한 개수만큼 반복한다
        for (int i=1; i <=dataNumber; i++){
            if(myData_color[i]==0)
                canvas.drawCircle(myData_x[i], myData_y[i], radius, p1);
            if(myData_color[i]==1)
                canvas.drawCircle(myData_x[i], myData_y[i], radius, p2);
            if(myData_color[i]==2)
                canvas.drawCircle(myData_x[i], myData_y[i], radius, p3);
            if(myData_color[i]==3)
                canvas.drawCircle(myData_x[i], myData_y[i], radius, p4);
            if(myData_color[i]==4)
                canvas.drawCircle(myData_x[i], myData_y[i], radius, p5);
        }
        invalidate(); //onDraw()메소드를 호출한다.
        // 이때 onDraw가 호출되면서 화면이 다시 그려진다. 그래서 배열에 좌표 및 색상값을 저장하는 것
    }
    //터치한 화면의 좌표와 색상을 저장한다
    public void saveData(){
        myData_x[dataNumber]=mx;
        myData_y[dataNumber]=my;
        myData_color[dataNumber]=whatColor;
    }
    public boolean onTouchEvent (MotionEvent event){
        //화면에 터치한 좌표값을 mx, my에 저장
        mx=(int)event.getX();
        my=(int)event.getY();
        //화면을 터치할 때마다 dataNumber값이 1씩 증가한다
        dataNumber+=1;
        saveData();
        return true;
    }
}
