package player;

import game.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class Human extends Player {

    public int[] chessMove(Chessboard chessboard, Player competitor){
        Chessman chessman = new Chessman(chessInput(),this.getColour());
        //判断这个位置是否可以落子
        //是否有其它棋子
        if (!chessboard.isCoincided(chessman)){
            ArrayList<Enum.Direction> directions =  chessman.isChessmanAround(chessboard);
            //其余位置是否有白色棋子，没有的话返回null
            if (directions!=null){
                //返回的是方向,int[]里分别为结束的位置坐标x,y以及当时的得分
                LinkedHashMap<Enum.Direction,int[]> score = chessman.score(directions,chessboard);
                //是否有分可得
                if (getTail(score)!=null){
                    int totalScore = getTail(score).getValue()[2];
                    if (totalScore>0){
                        //设置得分
                        this.setScore(totalScore+getScore()+1);
                        competitor.setScore(competitor.getScore()-totalScore);
                        System.out.println("您的得分现在为"+this.getScore());
                        System.out.println("对手的得分现在为"+competitor.getScore());
                        //删除棋子
                        chessboard.removeChessmen(score,chessman.getPoint(),this.getColour());
                        return chessman.getPoint();
                    }
                }

            }
        }
        return null;
    }



    //输入用户下一个落子点，将字母转化为坐标，但还没有判断
    private int[] chessInput() {
        int[] nextMove = {-1,-1};
        System.out.println("请输入");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.toLowerCase();
        byte[] move = input.getBytes(StandardCharsets.UTF_8);
        if (move.length!=2||!input.matches("^[A-Za-z]+$")){
            System.out.println("非法输入");
            System.out.println("player.Human gave up");
            System.exit(1);
        }else {

                nextMove[0] = (int)move[0] - 97;
                nextMove[1] = (int)move[1] - 97;
                if (nextMove[0]>= Chessboard.getSize()||nextMove[0]<0||nextMove[1]>= Chessboard.getSize()||nextMove[1]<0){
                    System.out.println("非法输入");
                    System.out.println("player.Human gave up");
                    System.exit(1);
                }
        }
        return nextMove;
    }




    public static Map.Entry<Enum.Direction,int[]> getTail(LinkedHashMap<Enum.Direction,int[]> map) {
        try {
            Iterator<Map.Entry<Enum.Direction,int[]>> iterator = map.entrySet().iterator();
            Map.Entry<Enum.Direction,int[]> tail = null;
            while (iterator.hasNext()) {
                tail = iterator.next();
            }
            return tail;
        }catch(NullPointerException e){
            return null;
        }

    }


}


