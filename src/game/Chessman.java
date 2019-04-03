package game;
import player.Enum;

import java.util.*;

public class Chessman {
    //x,y
    private int[] point;
    private Enum.Colour colour;

    public static boolean move(int[] point,Enum.Direction direction) {
        return direction.isMove(point);
    }

    public Chessman(int[] point,Enum.Colour colour){

        this.setPoint(point);
        this.setColour(colour);
    }

    public Chessman(int i,int j,Enum.Colour colour){

        this.setPoint(i, j);
        this.setColour(colour);
    }

    //判断是否该棋子周围有异色棋子可以移动，未判断该棋子所在点是否有东西
    public ArrayList<Enum.Direction> isChessmanAround(Chessboard chessboard){
        int x = this.getPoint()[0];
        int y = this.getPoint()[1];
        ArrayList<Enum.Direction> directions = new ArrayList<>();
        for (Enum.Direction direction : Enum.Direction.values()) {
            int[] point = new int[]{x,y};
           if (move(point,direction)){
               if (chessboard.isAnotherColorChessman(point,this.getColour())){
                   directions.add(direction);
               }
           }
        }
        return directions;
    }

    //方向,int[]里分别为结束的位置坐标x,y以及当时的得分
    public LinkedHashMap<Enum.Direction,int[]> score(ArrayList<Enum.Direction> directions, Chessboard chessboard){
        LinkedHashMap<Enum.Direction,int[]> returnValue = new LinkedHashMap<>();
        int score = 0;
        int[] point = {this.getPoint()[0],this.getPoint()[1]};
        //对周围有异色棋子的方向
        for (Enum.Direction direction : directions){
            //不可继续移动条件是遇到边界或者没有棋子了
            while (move(point,direction)&&chessboard.isCoincided(point)){
                //当第一次遇见同色棋子
                if (!chessboard.isAnotherColorChessman(point,this.getColour())){
                    //计算分数
                    score += direction.calScore(point,this.getPoint());
                    if (score>0){
                        returnValue.put(direction,new int[]{point[0],point[1],score});
                        break;
                    }
                }
            }
            point[0] = this.getPoint()[0];point[1] = this.getPoint()[1];
        }
        return returnValue;
    }



    public void setColour(Enum.Colour colour) {
        this.colour = colour;
    }

    public Enum.Colour getColour() {
        return colour;
    }

    public int[] getPoint() {
        return point;
    }

    public void setPoint(int[] point) {
        this.point = point;
    }

    public void setPoint(int x, int y) {
        this.point = new int[]{x,y};
    }







}
