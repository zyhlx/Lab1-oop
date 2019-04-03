package main;

import game.Chessboard;
import player.Computer;
import player.Enum;
import player.Human;
import player.Player;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Reversi {
    public static String[] object = new String[5];
    public static void main(String[] args) {
        Chessboard chessboard = new Chessboard(Reversi.initialSize());

        Player computer = new Computer();
        Player player = new Human();

        Enum.Colour[] colours = initialColor();
        player.setColour(colours[0]);
        computer.setColour(colours[1]);
        printChessboard(chessboard.getChessBoard());


        play(player,computer,chessboard);


        printWin(player,computer);

    }

    private static void play(Player player,Player computer,Chessboard chessboard){
        while (!player.isWin() && !computer.isWin()) {
            if (player.getColour() == Enum.Colour.BLACK) {
                if (checkPlace(chessboard, player, computer)) {
                    if (turn(player, computer, chessboard)) {
                        break;
                    }
                }
                if (player.isWin() || computer.isWin()) {
                    break;
                }
                if (checkPlace(chessboard, computer, player)) {
                    if (turn(computer, player, chessboard)) {
                        break;
                    }
                }

            } else {
                if (checkPlace(chessboard, computer, player)) {
                    if (turn(computer, player, chessboard)) {
                        break;
                    }
                }
                if (player.isWin() || computer.isWin()) {
                    break;
                }
                if (checkPlace(chessboard, player, computer)) {
                    if (turn(player, computer, chessboard)) {
                        break;
                    }
                }

            }
        }
    }


    private static void printWin(Player player,Player computer){
        if (player.isWin() && computer.isWin()) {
            System.out.println("Drawn");
            setResult(player,computer);
        } else if (player.isWin()) {
            System.out.println(player.getClass().toString() + " player wins!");
            setResult(player,computer);
        } else {
            System.out.println(computer.getClass().toString() + " player wins!");
            if (object[4]==null){
                setResult(player,computer);
            }
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        object[0] = df.format(new Date());// new Date()为获取当前系统时间
        writeFile(object);
    }




    private static void setResult(Player player,Player player1){
        if (player.getColour() == Enum.Colour.BLACK){
            object[4] = player.getScore() +" to " +player1.getScore();
        }else {
            object[4] = player1.getScore() +" to " +player.getScore();
        }
    }

    private static boolean checkPlace(Chessboard chessboard, Player player, Player computer) {
        if (!chessboard.checkIsPlaceToChess(player)) {
            System.out.println(player.getColour() + " player has no valid move");
            if (!chessboard.checkIsPlaceToChess(computer)) {
                System.out.println(computer.getColour() + " player has no valid move");
                System.out.println("game over.");
                chessboard.setWinner(player, computer);
            }
            return false;
        }
        return true;
    }

    private static boolean turn(Player playerFirst, Player playerSecond, Chessboard chessboard) {
        //如果有位置可以走
        boolean isEnd = chessboard.isEnd(playerFirst, playerSecond, playerFirst.chessMove(chessboard, playerSecond));
        chessboard.setNumOfTurn(chessboard.getNumOfTurn() + 1);
        printChessboard(chessboard.getChessBoard());
        return isEnd;
    }


    private static int initialSize() {
        int size;
        while (true) {
            System.out.print("Enter the board dimension:");
            Scanner input = new Scanner(System.in);
            try {
                size = input.nextInt();
                String s = "" + size;
                if (!s.matches("^[0-9]*$") || size < 4 || size > 26) throw new Exception();
            } catch (Exception e) {
                System.out.println("您输入的不是4~26之间的数字！");
                continue;
            }
            break;
        }
        System.out.println("Board dimension:" + size);
        object[1] = size+"*"+size;
        return size;
    }

    private static Enum.Colour[] initialColor() {
        Enum.Colour[] colours = new Enum.Colour[2];
        while (true) {
            System.out.print(" player.Computer plays (X/O):");
            try {
                Scanner input = new Scanner(System.in);
                String playSign = input.next();
                playSign = playSign.toUpperCase();
                switch (playSign) {
                    case "O": {
                        colours[0] = Enum.Colour.BLACK;
                        colours[1] = Enum.Colour.WHITE;
                        object[2] = "player.Human";
                        object[3] = "player.Computer";
                        System.out.println("player.Human plays first.");
                        break;
                    }
                    case "X": {
                        colours[0] = Enum.Colour.WHITE;
                        colours[1] = Enum.Colour.BLACK;
                        object[3] = "player.Human";
                        object[2] = "player.Computer";
                        System.out.println("player.Computer plays first.");
                        break;
                    }

                    default:
                        throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("您输入的不是X/O！");
                continue;
            }
            break;
        }
        System.out.println(" player.Computer plays :" + colours[1]);
        return colours;
    }


    private static void printChessboard(Enum.Colour[][] chessBoard) {
        char lineNum = 97;
        System.out.print(" ");
        for (int i = 0; i < chessBoard.length; i++) {
            System.out.print("" + (char) (lineNum + i));
        }
        System.out.println();
        for (Enum.Colour[] line : chessBoard) {
            System.out.print("" + (char) lineNum);
            for (Enum.Colour chessman : line) {
                switch (chessman) {
                    case NULLCHESSMAN:
                        System.out.print("·");
                        break;
                    case BLACK:
                        System.out.print("X");
                        break;
                    case WHITE:
                        System.out.print("O");
                        break;
                }
            }
            lineNum++;
            System.out.println();
        }
    }


    private static void writeFile(String[] object) {
        try {
            File writeName = new File("src/data/main.Reversi.csv"); // 相对路径，如果没有则要建立一个新的文件
            try (FileWriter writer = new FileWriter(writeName,true);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                StringBuilder buffer = new StringBuilder();
                for(int i = 0;i<5;i++) {
                    buffer.append(object[i]);
                    buffer.append(",");
                }
                buffer.deleteCharAt(buffer.length()-1);
                buffer.append("\r\n");
                out.write(buffer.toString());
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
