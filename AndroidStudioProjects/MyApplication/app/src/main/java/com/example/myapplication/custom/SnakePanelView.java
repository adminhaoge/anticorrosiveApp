package com.example.myapplication.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplication.activity_game;
import com.example.myapplication.activity_home;
import com.example.myapplication.bean.GameType;
import com.example.myapplication.bean.GridPosition;
import com.example.myapplication.bean.Panking_bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SnakePanelView extends View {

    private int mSnakeLength = 4;
    private long mSpeed = 150;
    private int mGridSize = 30;
    private int mSnakeDirection = GameType.TOP;
    private GridPosition mSnakeHeader;
    private GridPosition mFoodPosition;
    private int count = 0;
    private List<List<GridSquare>> mGridGroup = new ArrayList<>();
    private List<GridPosition> mSnakePositions = new ArrayList<>();
    private int mRectSize = dp2px(getContext(),15);//相当于dpVal的值X2
    private boolean mStartGame = false;
    private int mStartX,mStartY;
    private TimerTask timerTask;
    private Timer timer;
    private int foodX;
    private int foodY;

    public static List<Panking_bean> score_list = new ArrayList<>();
    private int dp2px(Context context, int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpVal,
                context.getResources().getDisplayMetrics());
    }

    public SnakePanelView(Context context) {
        this(context,null);
    }

    public SnakePanelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SnakePanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //绘制每个格子的颜色
        List<GridSquare> squares;
        for (int i = 0; i < mGridSize; i++) {
            squares = new ArrayList<>();
            for (int j = 0; j < mGridSize; j++) {
                squares.add(new GridSquare(GameType.GRID));
            }
            mGridGroup.add(squares);
        }
        mSnakeHeader  = new GridPosition(10,10);
        mSnakePositions.add(new GridPosition(mSnakeHeader.getX(),mSnakeHeader.getY()));
        mStartGame = true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartX = w/2 - mGridSize * mRectSize;
        mStartY = dp2px(getContext(),50);
    }
    int score_value = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();

        //绘制分数和消耗时间段
        paint.setTextSize(30);
        paint.setColor(Color.BLACK);
        score_value = mSnakeLength-4;
        canvas.drawText("Score:"+score_value+"分",1500,500,paint);
        canvas.drawText("Duration"+count+"秒",1500,550,paint);

        //格子画笔
        Paint mGridPaint = new Paint();
        mGridPaint.reset();
        mGridPaint.setStyle(Paint.Style.FILL);
        mGridPaint.setAntiAlias(true);

        //循环展示每个格子相加的结果然后利用getcolor获取颜色
        for (int i = 0; i < mGridSize; i++) {
            for (int j = 0; j < mGridSize; j++) {
                int left = mStartX + i * mRectSize;
                int top = mStartY + j * mRectSize;
                int right = left + mRectSize;
                int bottom = top + mRectSize;
                mGridPaint.setColor(mGridGroup.get(i).get(j).getColor());
                canvas.drawRect(left,top,right,bottom,mGridPaint);
            }
        }
    }

    public void reStartGame(){
        if (!mStartGame) return;
        for (List<GridSquare> squares : mGridGroup) {
            for (GridSquare square : squares) {
                square.setType(GameType.GRID);
            }
        }
        if (mSnakeHeader != null){
            mSnakeHeader.setX(10);
            mSnakeHeader.setY(10);
        }else {
            mSnakeHeader = new GridPosition(10,10);
        }
        if (timer == null && timerTask == null){
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    count++;
                }
            };
            timer.schedule(timerTask,1000,1000);
        }else {
            timerTask.cancel();
            timer.cancel();
            mSnakeLength = 4;
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    count++;
                }
            };
            timer.schedule(timerTask,1000,1000);
        }
        mSnakePositions.clear();
        mSnakePositions.add(new GridPosition(mSnakeHeader.getX(),mSnakeHeader.getY()));
        mSnakeDirection = GameType.RIGHT;
        if (mFoodPosition != null){
            mFoodPosition.setX(foodX);
            mFoodPosition.setY(foodY);
        }else {
            mFoodPosition = new GridPosition(foodX,foodY);
        }
        reFreshFood(mFoodPosition);
        mStartGame = false;
        GameMainThread gameMainThread = new GameMainThread();
        gameMainThread.start();
    }

    private void reFreshFood(GridPosition mFoodPosition) {
        mGridGroup.get(mFoodPosition.getX()).get(mFoodPosition.getY()).setType(GameType.FOOD);
    }

    private void reFreshGridSquare() {
        for (GridPosition mSnakePosition : mSnakePositions) {
            mGridGroup.get(mSnakePosition.getX()).get(mSnakePosition.getY()).setType(GameType.SNAKE);
        }
    }

    private void reFreshSnakeHead(){
        GridPosition headerPosition = mSnakePositions.get(mSnakePositions.size() -1);
        mGridGroup.get(headerPosition.getX()).get(headerPosition.getY()).setType(GameType.HEADSNAKE);
    }

    private void reFreshGrid(GridPosition gridPosition) {
        mGridGroup.get(gridPosition.getX()).get(gridPosition.getY()).setType(GameType.GRID);
    }

    private class GameMainThread extends Thread{
        @Override
        public void run() {
            while (!mStartGame){
                //刷新蛇的位置
                handleSnakeTail();
                //刷新网格
                reFreshGridSquare();
                //检测碰撞
                checkCollision();
                //蛇头碰到墙体就从对面的墙体出现
                moveSnake(mSnakeDirection);
                //重绘洁面/刷新
                postInvalidate();
                try {
                    sleep(mSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void moveSnake(int mSnakeDirection) {
            switch (mSnakeDirection){
                case GameType.LEFT:
                    if (mSnakeHeader.getX() -1 < 0){
                        mSnakeHeader.setX(mGridSize -1);
                    }else {
                        mSnakeHeader.setX(mSnakeHeader.getX() -1 );
                    }
                    mSnakePositions.add(new GridPosition(mSnakeHeader.getX(),mSnakeHeader.getY()));
                    break;
                case GameType.RIGHT:
                    if (mSnakeHeader.getX() +1 >= mGridSize){
                        mSnakeHeader.setX(0);
                    }else {
                        mSnakeHeader.setX(mSnakeHeader.getX() +1 );
                    }
                    mSnakePositions.add(new GridPosition(mSnakeHeader.getX(),mSnakeHeader.getY()));
                    break;
                case GameType.TOP:
                    if (mSnakeHeader.getY() -1 < 0){
                        mSnakeHeader.setY(mGridSize -1);
                    }else {
                        mSnakeHeader.setY(mSnakeHeader.getY() -1 );
                    }
                    mSnakePositions.add(new GridPosition(mSnakeHeader.getX(),mSnakeHeader.getY()));
                    break;
                case GameType.BOTTOM:
                    if (mSnakeHeader.getY() +1 >= mGridSize){
                        mSnakeHeader.setY(0);
                    }else {
                        mSnakeHeader.setY(mSnakeHeader.getY() +1 );
                    }
                    mSnakePositions.add(new GridPosition(mSnakeHeader.getX(),mSnakeHeader.getY()));
                    break;
            }
        }

        private void checkCollision() {
            reFreshSnakeHead();
            GridPosition headerPosition = mSnakePositions.get(mSnakePositions.size() -1);
            //判断是否碰撞到自己如果碰撞到自己就结束游戏然后提示dialog
            for (int i = 0; i < mSnakePositions.size()-2; i++) {
                GridPosition gridPosition = mSnakePositions.get(i);
                if (headerPosition.getX() == gridPosition.getX() && headerPosition.getY() == gridPosition.getY()){
                    mStartGame = true;
                    showMessageDialog();
                    score_list.add(new Panking_bean(score_value,count));
                    Collections.sort(score_list, new Comparator<Panking_bean>() {
                        @Override
                        public int compare(Panking_bean o1, Panking_bean o2) {
                            if (o2.getScore() == o1.getScore()){
                                return o1.getDuration() - o2.getDuration();
                            }else {
                                return o2.getScore() -o1.getScore();
                            }
                        }
                    });
                }
            }
            //吃到食物就增加长度
            if (headerPosition.getX() == mFoodPosition.getX()
                && headerPosition.getY() == mFoodPosition.getY()){
                mSnakeLength++;
                generateFood();
            }
        }




        private void showMessageDialog() {
            post(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(getContext())
                            .setMessage("Game OVer!")
                            .setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    reStartGame();
                                }
                            })
                            .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    getContext().startActivity(new Intent(getContext(), activity_home.class));
                                }
                            }).create()
                            .show();
                }
            });
        }



        private void handleSnakeTail() {
            int snakeLength = mSnakeLength;
            for (int i = mSnakePositions.size() -1 ; i>=0 ;i--){
                if (snakeLength >0){
                    snakeLength--;
                }else {
                    GridPosition gridPosition = mSnakePositions.get(i);
                    reFreshGrid(gridPosition);

                }
            }
            snakeLength = mSnakeLength;
            for (int i = mSnakePositions.size() -1; i>=0; i--) {
                if (snakeLength > 0){
                    snakeLength--;
                }else {
                    mSnakePositions.remove(i);
                }
            }
        }

    }

    private void generateFood() {
        Random random = new Random();
        foodX = random.nextInt(mGridSize - 1);
        foodY = random.nextInt(mGridSize - 1);
        for (int i = 0; i < mSnakePositions.size() -1 ; i++) {
            if (foodX == mSnakePositions.get(i).getX() && foodY == mSnakePositions.get(i).getY()){
                foodX = random.nextInt(mGridSize -1);
                foodY = random.nextInt(mGridSize -1);
                i = 0;
            }
        }
        mFoodPosition.setX(foodX);
        mFoodPosition.setY(foodY);
        reFreshFood(mFoodPosition);
    }

    int x,y;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int mSnakeDirection = 0;
        int action = event.getAction();
        if (action == KeyEvent.ACTION_DOWN){
            x = (int) event.getX();
            y = (int) event.getY();
        }
        if (action == KeyEvent.ACTION_UP){
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (Math.abs(x - this.x) > Math.abs(y - this.y)){
                if (x > this.x){
                    mSnakeDirection = GameType.RIGHT;
                }
                if (x < this.x){
                    mSnakeDirection = GameType.LEFT;
                }
            }else {
                if (y < this.y){
                    mSnakeDirection = GameType.TOP;
                }
                if (y > this.y){
                    mSnakeDirection = GameType.BOTTOM;
                }
                if (y == this.y){
                    if (this.mSnakeDirection == GameType.TOP){
                        mSnakeDirection = GameType.TOP;
                    }else if (this.mSnakeDirection == GameType.BOTTOM){
                        mSnakeDirection = GameType.BOTTOM;
                    }else if (this.mSnakeDirection == GameType.LEFT){
                        mSnakeDirection = GameType.LEFT;
                    }else if (this.mSnakeDirection == GameType.RIGHT){
                        mSnakeDirection = GameType.RIGHT;
                    }
                }
            }
            if (this.mSnakeDirection == GameType.TOP || this.mSnakeDirection == GameType.BOTTOM){
                if (mSnakeDirection == GameType.TOP || mSnakeDirection == GameType.BOTTOM){
                }else {
                    this.mSnakeDirection = mSnakeDirection;
                }
            }else if (this.mSnakeDirection == GameType.RIGHT || this.mSnakeDirection == GameType.LEFT){
                if (mSnakeDirection == GameType.RIGHT || mSnakeDirection == GameType.LEFT){
                }else {
                    this.mSnakeDirection = mSnakeDirection;
                }
            }

        }
        return true;
    }

}
