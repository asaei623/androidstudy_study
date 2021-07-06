package com.example.gamebasic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Bitmap spaceship;
    Bitmap leftkey, rightkey;
    Bitmap screen;
    int spaceship_x, spaceship_y;
    int leftkey_x, leftkey_y;
    int rightkey_x, rightkey_y;
    int Width, Height;
    int button_width;
    int score;
    int spaceshipWidth;

    Bitmap missileButton;
    int missileButton_x, missileButton_y;
    int missileWidth;
    int missile_middle;//미사일 크기 반
    Bitmap missile;
    Bitmap planetimg;

    int count=0;
    ArrayList<MyMissile> myM;
    ArrayList<Planet> planet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));

        //스크린 가로, 세로 크기 구하기
        Display display=((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Width=display.getWidth();
        Height=display.getHeight();
        //ArrayList 객체 생성
        myM=new ArrayList<MyMissile>();
        planet=new ArrayList<Planet>();
        //각 이미지 파일을 비트맵으로 만들어서 이미지 크기 조정하기
        spaceship= BitmapFactory.decodeResource(getResources(),R.drawable.spaceship);
        int x=Width/8;
        int y=Height/11;
        spaceship=Bitmap.createScaledBitmap(spaceship,x,y,true);
        spaceshipWidth=spaceship.getWidth();
        spaceship_x=Width*1/9;
        spaceship_y=Height*6/9;

        leftkey=BitmapFactory.decodeResource(getResources(), R.drawable.leftkey);
        leftkey_x=Width*5/9;
        leftkey_y=Height*7/9;
        button_width=Width/6;
        leftkey=Bitmap.createScaledBitmap(leftkey, button_width, button_width, true);

        rightkey=BitmapFactory.decodeResource(getResources(), R.drawable.rightkey);
        rightkey_x=Width*7/9;
        rightkey_y=Height*7/9;
        rightkey=Bitmap.createScaledBitmap(rightkey, button_width, button_width, true);

        missileButton=BitmapFactory.decodeResource(getResources(), R.drawable.missilebutton);
        missileButton=Bitmap.createScaledBitmap(missileButton, button_width, button_width, true);
        missileButton_x=Width*1/11;
        missileButton_y=Height*7/9;

        missile=BitmapFactory.decodeResource(getResources(), R.drawable.missile);
        missile=Bitmap.createScaledBitmap(missile, button_width/4, button_width/4, true);
        missileWidth=missile.getWidth();

        planetimg=BitmapFactory.decodeResource(getResources(), R.drawable.planetimg);
        planetimg=Bitmap.createScaledBitmap(planetimg, button_width, button_width, true);

        screen=BitmapFactory.decodeResource(getResources(), R.drawable.screen);
        screen=Bitmap.createScaledBitmap(screen, Width, Height, true);
    }

    class MyView extends View {
        //생성자
        MyView(Context context){
            super(context);//상위클래스의 생성자를 호출해야 한다
            setBackgroundColor(Color.BLUE);
            gHandler.sendEmptyMessageDelayed(0, 1000);//1초 딜레이 후 다음 문장 실행

        }

        @Override
        public void onDraw(Canvas canvas){
            //랜덤한 위치에 행성 생성
            Random r1=new Random();
            int x=r1.nextInt(Width);
            //행성의 arraylist를 보고 개수가 5개 미만이면 아까 정한 x값에 생성
            if(planet.size()<5)
                planet.add(new Planet(x, -100));
            //paint 설정
            Paint p1=new Paint();
            p1.setColor(Color.RED);
            p1.setTextSize(50);
            //text, 이미지 파일 추가
            canvas.drawBitmap(screen, 0, 0, p1);
            canvas.drawBitmap(spaceship, spaceship_x, spaceship_y, p1);
            canvas.drawBitmap(leftkey, leftkey_x, leftkey_y, p1);
            canvas.drawBitmap(rightkey, rightkey_x, rightkey_y, p1);
            canvas.drawBitmap(missileButton, missileButton_x, missileButton_y, p1);
                //미사일과 행성의 경우, arraylist에서 하나씩 빼와서 만듦
            for(MyMissile tmp : myM)
                canvas.drawBitmap(missile, tmp.x, tmp.y, p1);
            for(Planet tmp : planet)
                canvas.drawBitmap(planetimg, tmp.x, tmp.y, p1);
            canvas.drawText(Integer.toString(count), 0, 300, p1);
            canvas.drawText("점수 : "+Integer.toString(score), 0, 200, p1);

            moveMissile();
            movePlanet();
            checkCollision();
            count++;
        }

        public void moveMissile(){
            //arraylist의 미사일이 차례대로 move
            for(int i=myM.size()-1; i>=0; i--){
                myM.get(i).move();
            }
            for(int i=myM.size()-1; i>=0; i--){
                //미사일이 화면을 벗어나면 없앰
                if(myM.get(i).y<0)myM.remove(i);
            }
        }
        public void movePlanet(){
            //arraylist의 행성이 차례대로 move
            for(int i=planet.size()-1; i>=0; i--){
                planet.get(i).move();
            }
            for(int i=planet.size()-1; i>=0; i--){
                //화면 밖을 벗어나면 없앰
                if(planet.get(i).y>Height)planet.remove(i);
            }
        }
        public void checkCollision(){//충돌시
            for(int i=planet.size()-1;i>=0; i--){
                for(int j=myM.size()-1; j>=0; j--){
                    if((myM.get(j).x+missile_middle>planet.get(i).x)&&(myM.get(j).x+missile_middle<planet.get(i).x+button_width)
                    &&(myM.get(j).y>planet.get(i).y)&&(myM.get(j).y<planet.get(i).y+button_width)){
                        planet.remove(i);
                        myM.get(j).y-=30;
                        score+=10;
                    }
                }
            }
        }

        //핸들러
        Handler gHandler=new Handler(){
          public void handleMessage(Message msg){
              invalidate();
              gHandler.sendEmptyMessageDelayed(0, 30);
          }
        };
        @Override
        public boolean onTouchEvent (MotionEvent event){
            //사용자가 터치한 좌표
            int x=0,y=0;
            //화면을 터치했거나, 터치해서 움직였을 경우 작동하게 함
            if(event.getAction() == MotionEvent.ACTION_DOWN
                    || event.getAction()==MotionEvent.ACTION_MOVE){
                //터치한 좌표값을 변수에 저장
                x=(int)event.getX();
                y=(int)event.getY();
            }
            //leftkey 터치하면
            if((x>leftkey_x) && (x<(leftkey_x+button_width)) && (y>leftkey_y)&&(y<(leftkey_y+button_width))){
                //우주선을 왼쪽으로 20만큼 이동한다
                spaceship_x-=20;
            }
            if((x>rightkey_x) && (x<(rightkey_x+button_width)) && (y>rightkey_y)&&(y<(rightkey_y+button_width))){
                //우주선을 오른쪽으로 20만큼 이동한다
                spaceship_x+=20;
            }

            if(event.getAction()==MotionEvent.ACTION_DOWN)
                if((x>missileButton_x)&&(x<(missileButton_x+button_width))&&(y>missileButton_y)&&(y<(missileButton_y+button_width)))
                    if(myM.size()<1){
                        myM.add(new MyMissile(spaceship_x+spaceshipWidth/2-missileWidth/2, spaceship_y));
                    }
            invalidate();
            return true;
        }
    }
}