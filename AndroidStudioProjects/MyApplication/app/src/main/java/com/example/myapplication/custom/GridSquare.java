package com.example.myapplication.custom;

import android.graphics.Color;

import com.example.myapplication.bean.GameType;

/**
 * 设置三个元素的颜色（蛇，食物，格子）
 */

public class GridSquare {
  private int mType;//元素类型

  public GridSquare(int type) {
   this.mType = type;
  }



  public int getColor() {
    switch (mType) {
      case GameType.GRID://空格子
        return Color.GRAY;
      case GameType.FOOD://食物
        return Color.BLUE;
      case GameType.SNAKE://蛇
        return Color.WHITE;
      case GameType.HEADSNAKE://蛇头
        return Color.CYAN;
    }
    return Color.WHITE;
  }

  public void setType(int type) {
    this.mType = type;
  }



}
