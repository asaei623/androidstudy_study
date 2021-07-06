package com.example.gamebasic;

public class Planet {
    int x, y;
    int planetSpeed=15;
    int dir=0;//0이면 행성이 오른쪽에서 왼쪽으로 이동
            //1이면 행성이 왼쪽에서 오른쪽으로 이동

    Planet(int x, int y, int dir){
        this.x=x;
        this.y=y;
        this.dir=dir;
    }
    Planet(int x, int y){
        this.x=x;
        this.y=y;
    }
    public void move(){
        if(dir==0){
            x-=planetSpeed;
        }
        else x+=planetSpeed;
    }
}
