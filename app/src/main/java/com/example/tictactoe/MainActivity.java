package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    static final String Player_1 = "X";
    static final String Player_2 = "O";
    boolean player1_Turn = true;
    byte [][] board = new byte[3][3];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TableLayout table = findViewById(R.id.Board);
        for(int i=0; i<3;i++){
            TableRow row = (TableRow)table.getChildAt(i);
            for(int j = 0;j<3;j++){
                Button btn = (Button) row.getChildAt(j);
                btn.setOnClickListener(new CellListener(i,j));
            }
        }
    }


    class CellListener implements View.OnClickListener{

        int row,col;

        public CellListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void onClick(View view) {
            if(!isValidMethod(row,col)){
                Toast.makeText(MainActivity.this,"Cell is already occupied. ", Toast.LENGTH_LONG).show();
                return;
            }
            if(player1_Turn ){
                ((Button)view).setText(Player_1);
                board[row][col] =1;
            }else{
                ((Button)view).setText(Player_2);
                board[row][col] =2;
            }

            if(gameEnded(row,col)== -1){
                player1_Turn=! player1_Turn;
            }else if(gameEnded(row,col)== 0){
                Toast.makeText(MainActivity.this,"It is a Draw ", Toast.LENGTH_LONG).show();
            }else if(gameEnded(row,col)== 1){
                Toast.makeText(MainActivity.this,"Player 1 wins ", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(MainActivity.this,"Player 2 wins ", Toast.LENGTH_LONG).show();
            }


        }
    }

    public boolean isValidMethod(int row,int column){
        return board[row][column] == 0;
    }

    public int gameEnded(int row, int col){

        int symbol = board[row][col];
        boolean win =true;
        for (int i = 0;i<3;i++){
            if(board[i][col] != symbol  ){
                win =false;

                break;
            }

        }

        if (win) {

            return symbol;
        }
        return -1;



        //check rows
        //check diagonals

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("player1Turn",player1_Turn);
        byte [] boardSingle = new byte[9];
        for (int i = 0; i<3;i++){
            for (int j =0;j<3;j++){
                boardSingle[3*i+j] = board[i][j];
            }
        }
        outState.putByteArray("board",boardSingle);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        player1_Turn = savedInstanceState.getBoolean("player1Turn");
        byte [] boardSingle = savedInstanceState.getByteArray("board");
        for ( int i = 0; i<9;i++){
            board[i/3][i%3] = boardSingle[i];
        }
        TableLayout table = findViewById(R.id.Board);
        for(int i=0; i<3;i++){
            TableRow row = (TableRow)table.getChildAt(i);
            for(int j = 0;j<3;j++){
                Button button = (Button) row.getChildAt(j);
                if(board[i][j] == 1){
                    button.setText("X");
                }else if(board[i][j] == 2){
                    button.setText("O");
                }
            }
        }


        super.onRestoreInstanceState(savedInstanceState);
    }
}