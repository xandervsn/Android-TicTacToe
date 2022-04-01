/*
Xander Siruno-Nebel
Feb 27, 2022
Java TicTacToe: Plays a game of TicTacToe against an AI on Android
Galbraith Java Programming Per. 6
 */


package com.example.jtictactoe;

//imports
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements OnClickListener{

    Button[][] grid = new Button[3][3];
    int[][] board = new int[3][3];
    final int BLANK = 0;
    final int X_MOVE = 1;
    final int O_MOVE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //makes the buttons usable
        grid[0][0] = (Button)this.findViewById(R.id.button1);
        grid[0][1] = (Button)this.findViewById(R.id.button2);
        grid[0][2] = (Button)this.findViewById(R.id.button3);

        grid[1][0] = (Button)this.findViewById(R.id.button4);
        grid[1][1] = (Button)this.findViewById(R.id.button5);
        grid[1][2] = (Button)this.findViewById(R.id.button6);

        grid[2][0] = (Button)this.findViewById(R.id.button7);
        grid[2][1] = (Button)this.findViewById(R.id.button8);
        grid[2][2] = (Button)this.findViewById(R.id.button9);

        for (int x = 0; x<3; x++){
            for (int i = 0; i < 3; i++) {
                grid[x][i].setOnClickListener(this);
            }
        }

    }

    @Override
    public void onClick(View v){
        //plays the game once a button is clicked
        Button b = (Button)v;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (b.equals(grid[x][y])) {
                    if (board[x][y] == BLANK) {
                        //if the button is blank, continue
                        b.setText("X");
                        b.setEnabled(false);
                        board[x][y] = X_MOVE;

                        //sees if X's move resulted in a win or tie
                        if(checkWin(X_MOVE)){
                            clearBoard();
                            Toast.makeText(this, "X Wins!", Toast.LENGTH_SHORT).show();
                            return;
                        }else if(checkTie()){
                            //
                            clearBoard();
                            Toast.makeText(this, "Tie!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        aiMove();

                    }
                }
            }
        }
        //sees if O's turn resulted in a win or tie
        if(checkWin(O_MOVE)){
            clearBoard();
            Toast.makeText(this, "O Wins!", Toast.LENGTH_SHORT).show();
            return;
        }else if(checkTie()){
            clearBoard();
            Toast.makeText(this, "Tie!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void aiMove() {
        if (checkSingleBlank(O_MOVE)){
            //try to win
            return;
        }
        if(checkSingleBlank(X_MOVE)){
            //try to block
            return;
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int x = 0; x < 3; x++) {
            //play randomly
            for (int y = 0; y < 3; y++) {
                if (board[x][y] == BLANK){
                    list.add(x*10+y);
                }
            }
        }
        int choice = (int)(Math.random()*list.size());
        board[list.get(choice) / 10][list.get(choice) % 10] = O_MOVE;
        grid[list.get(choice) / 10][list.get(choice) % 10].setText("O");
        grid[list.get(choice) / 10][list.get(choice) % 10].setEnabled(false);
        Toast.makeText(this, "Played Randomly", Toast.LENGTH_SHORT).show();
    }

    public boolean checkSingleBlank(int player) {
        int oCount = 0;
        int blankCount = 0;
        int blankX = 0;
        int blankY = 0;
        for (int x = 0; x < 3; x++) {
            //check column
            oCount = 0;
            blankCount = 0;
            blankX = 0;
            blankY = 0;

            for (int y = 0; y < 3; y++) {
                //blocks potential win
                if (board[x][y] == BLANK) {
                    blankCount++;
                    blankX = x;
                    blankY = y;
                }
                if (board[x][y] == player) {
                    oCount++;
                }
            }
            if (oCount == 2 && blankCount == 1) {
                board[blankX][blankY] = O_MOVE;
                grid[blankX][blankY].setText("O");
                grid[blankX][blankY].setEnabled(false);
                return true;
            }
        }

        for (int y = 0; y < 3; y++) {
            //check row
            oCount = 0;
            blankCount = 0;
            blankX = 0;
            blankY = 0;

            for (int x = 0; x < 3; x++) {
                //blocks potential win
                if (board[x][y] == BLANK) {
                    blankCount++;
                    blankX = x;
                    blankY = y;
                }
                if (board[x][y] == player) {
                    oCount++;
                }
            }
            if (oCount == 2 && blankCount == 1) {
                board[blankX][blankY] = O_MOVE;
                grid[blankX][blankY].setText("O");
                grid[blankX][blankY].setEnabled(false);
                return true;
            }
        }

        //diagonal - starting bottom left
        oCount = 0;
        blankCount = 0;
        blankX = 0;
        blankY = 0;

        if (board[0][0] == BLANK) {
            blankCount++;
            blankX = 0;
            blankY = 0; }
        if (board[0][0] == player) {
            oCount++; }

        if (board[1][1] == BLANK) {
            blankCount++;
            blankX = 1;
            blankY = 1; }
        if (board[1][1] == player) {
            oCount++; }

        if (board[2][2] == BLANK) {
            blankCount++;
            blankX = 2;
            blankY = 2; }
        if (board[2][2] == player) {
            oCount++; }

        if (oCount == 2 && blankCount == 1) {
            //blocks potential win
            board[blankX][blankY] = O_MOVE;
            grid[blankX][blankY].setText("O");
            grid[blankX][blankY].setEnabled(false);
            return true;
        }

        oCount = 0;
        blankCount = 0;
        blankX = 0;
        blankY = 0;

        if (board[2][0] == BLANK) {
            blankCount++;
            blankX = 0;
            blankY = 0; }
        if (board[2][0] == player) {
            oCount++; }

        if (board[1][1] == BLANK) {
            blankCount++;
            blankX = 1;
            blankY = 1; }
        if (board[1][1] == player) {
            oCount++; }
        if (board[0][2] == BLANK) {
            blankCount++;
            blankX = 0;
            blankY = 2; }
        if (board[0][2] == player) {
            oCount++; }
        if (oCount == 2 && blankCount == 1) {
            //blocks potential win
            board[blankX][blankY] = O_MOVE;
            grid[blankX][blankY].setText("O");
            grid[blankX][blankY].setEnabled(false);
            return true;
        }

    return false;
    }

    public boolean checkWin(int player){
        //columns
        if(board[0][0] == player && board[0][1] == player && board[0][2] == player){//left
            return true; }
        if(board[1][0] == player && board[1][1] == player && board[1][2] == player){//mid
            return true; }
        if(board[2][0] == player && board[2][1] == player && board[2][2] == player){//right
            return true; }

        //rows
        if(board[0][0] == player && board[1][0] == player && board[2][0] == player){//top
            return true; }
        if(board[0][1] == player && board[1][1] == player && board[2][1] == player){//mid
            return true; }
        if(board[0][2] == player && board[1][2] == player && board[2][2] == player){//bottom
            return true; }

        //diagonals
        if(board[0][2] == player && board[1][1] == player && board[2][0] == player){//from bottom left
            return true; }
        if(board[0][0] == player && board[1][1] == player && board[2][2] == player){//from top left
            return true; }

        return false;
    }

    public boolean checkTie(){
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if(board[x][y] == BLANK){
                    return false;
                }
            }
        }
        return true;
    }

    public void clearBoard(){
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                board[x][y] = BLANK;
                grid[x][y].setText("");
                grid[x][y].setEnabled(true);
            }
        }
    }

}