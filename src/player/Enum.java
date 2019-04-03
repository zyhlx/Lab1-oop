package player;

import game.*;

public class Enum {
    public enum Colour {
        BLACK,WHITE,NULLCHESSMAN
    }
    public enum Direction{
        UP(){
            public boolean isMove(int[] point){
                boolean canMove = false;
                if (point[0] > 0) {
                    point[0]--;
                    canMove = true;
                }
                return canMove;
            }

            @Override
            public int calScore(int[] point,int[] origin) {
                return origin[0] - point[0] - 1;
            }

            public void changeColor(int[] point, Chessboard chessboard, Colour colour){
                point[0]--;
                chessboard.getChessBoard()[point[0]][point[1]] = colour;
            }


        },DOWN(){
            public boolean isMove(int[] point){
                boolean canMove = false;
                if (point[0] < Chessboard.getSize() - 1) {
                    point[0]++;
                    canMove = true;
                }
                return canMove;
            }

            @Override
            public int calScore(int[] point,int[] origin) {
                return point[0] - origin[0] - 1;
            }

            public void changeColor(int[] point, Chessboard chessboard, Colour colour){
                point[0]++;
                chessboard.getChessBoard()[point[0]][point[1]] = colour;
            }
        },RIGHT(){
            public boolean isMove(int[] point){
                boolean canMove = false;
                if (point[1] < Chessboard.getSize() - 1) {
                    point[1]++;
                    canMove = true;
                }
                return canMove;
            }

            @Override
            public int calScore(int[] point,int[] origin) {
                return point[1] - origin[1] - 1;
            }

            public void changeColor(int[] point, Chessboard chessboard, Colour colour){
                point[1]++;
                chessboard.getChessBoard()[point[0]][point[1]] = colour;
            }
        },LEFT(){
            public boolean isMove(int[] point){
                boolean canMove = false;
                if (point[1] > 0) {
                   point[1]--;
                    canMove = true;
                }
                return canMove;
            }

            public int calScore(int[] point,int[] origin) {
                return origin[1] - point[1] - 1;
            }

            public void changeColor(int[] point, Chessboard chessboard, Colour colour){
                point[1]--;
                chessboard.getChessBoard()[point[0]][point[1]] = colour;
            }

        },LEFT_UP(){
            public boolean isMove(int[] point){
               return LEFT.isMove(point)&&UP.isMove(point);
            }

            public int calScore(int[] point,int[] origin) {
                return Math.abs(origin[1] - point[1]) - 1;
            }

            public void changeColor(int[] point, Chessboard chessboard, Colour colour){
                point[1]--;
                point[0]--;
                chessboard.getChessBoard()[point[0]][point[1]] = colour;
            }


        },LEFT_DOWN(){
            public boolean isMove(int[] point){
                return LEFT.isMove(point)&&DOWN.isMove(point);
            }
            public int calScore(int[] point,int[] origin) {
                return Math.abs(origin[1] - point[1]) - 1;
            }
            public void changeColor(int[] point, Chessboard chessboard, Colour colour){
                point[1]--;
                point[0]++;
                chessboard.getChessBoard()[point[0]][point[1]] = colour;
            }
        },RIGHT_UP(){
            public boolean isMove(int[] point){
                return RIGHT.isMove(point)&&UP.isMove(point);
            }
            public int calScore(int[] point,int[] origin) {
                return Math.abs(origin[1] - point[1]) - 1;
            }
            public void changeColor(int[] point, Chessboard chessboard, Colour colour){
                point[1]++;
                point[0]--;
                chessboard.getChessBoard()[point[0]][point[1]] = colour;
            }
        },RIGHT_DOWN(){
            public boolean isMove(int[] point){
                return RIGHT.isMove(point)&&DOWN.isMove(point);
            }
            public int calScore(int[] point,int[] origin) {
                return Math.abs(origin[1] - point[1]) - 1;
            }
            public void changeColor(int[] point, Chessboard chessboard, Colour colour){
                point[1]++;
                point[0]++;
                chessboard.getChessBoard()[point[0]][point[1]] = colour;
            }
        };



        public boolean isMove(int[] point){
            return false;
        }

        public int calScore(int[] point,int[] origin){
            return 0;
        }
        public void changeColor(int[] point, Chessboard chessboard, Colour colour){
        }

    }


}
