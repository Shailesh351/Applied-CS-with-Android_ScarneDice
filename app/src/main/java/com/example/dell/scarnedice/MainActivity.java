package com.example.dell.scarnedice;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvPlayerTurnScore, tvPlayerOverallScore, tvComputerOverallScore;
    private ImageView ivDiseFace;
    private Button btHold, btRoll, btReset;

    private static int playerTurnScore = 0, playerOverallScore = 0, computerTurnScore = 0, computerOverallScore = 0;
    private int[] diceFaces = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

    final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPlayerTurnScore = (TextView) findViewById(R.id.tvTurnScore);
        tvPlayerOverallScore = (TextView) findViewById(R.id.tvPlayerOverallScore);
        tvComputerOverallScore = (TextView) findViewById(R.id.tvComputerOverallScore);

        btHold = (Button) findViewById(R.id.btHold);
        btRoll = (Button) findViewById(R.id.btRoll);
        btReset = (Button) findViewById(R.id.btReset);
        btHold.setEnabled(false);

        btHold.setOnClickListener(this);
        btRoll.setOnClickListener(this);
        btReset.setOnClickListener(this);

        ivDiseFace = (ImageView) findViewById(R.id.ivDiceFace);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btRoll) {
            roll();
        }
        else if (id == R.id.btHold) {
            hold();
        }
        else if (id == R.id.btReset) {
            reset();
        }
    }

    private void roll(){
        int roll = getDiceFaceNumber();
        ivDiseFace.setImageResource(diceFaces[roll - 1]);
        if(roll != 1){
            playerTurnScore += roll;
            tvPlayerTurnScore.setVisibility(View.VISIBLE);
            tvPlayerTurnScore.setText("Your Turn Score : " +playerTurnScore );
            btHold.setEnabled(true);
        }else {
            playerTurnScore = 0;
            isWin();
            tvPlayerTurnScore.setText("Your Turn Score : " +playerTurnScore );
            tvPlayerOverallScore.setText("Your Overall Score : " + playerOverallScore);
            computerTurn();
        }
    }

    private void computerTurn(){
        tvPlayerTurnScore.setVisibility(View.VISIBLE);
        disableButtons();
        while (true){
            int roll = getDiceFaceNumber();
            boolean hold = random.nextBoolean();
            if (hold) {
                computerOverallScore += computerTurnScore;
                tvComputerOverallScore.setText("Computers Overall Score : " + computerOverallScore);
                isWin();
                btHold.setEnabled(false);
                btRoll.setEnabled(true);
                Toast.makeText(MainActivity.this, "Your Turn" ,Toast.LENGTH_SHORT).show();
                break;
            }else{
                computerTurnScore += roll;
            }
        }
    }

    private void hold(){
        playerOverallScore += playerTurnScore;
        playerTurnScore = 0;
        tvPlayerTurnScore.setText("Your Turn Score : " +playerTurnScore );
        tvPlayerOverallScore.setText("Your Overall Score : " + playerOverallScore);
        computerTurn();
    }

    private void reset(){
        playerTurnScore = 0;
        playerOverallScore = 0;
        computerOverallScore = 0;
        computerTurnScore = 0;

        btHold.setEnabled(false);
        tvPlayerTurnScore.setVisibility(View.INVISIBLE);
        ivDiseFace.setImageResource(diceFaces[0]);

        tvPlayerTurnScore.setText("Your Turn Score : " + playerTurnScore );
        tvPlayerOverallScore.setText("Your Overall Score : " + playerOverallScore);
        tvComputerOverallScore.setText("Computer's Overall Score : " + computerOverallScore);
    }

    private int getDiceFaceNumber(){
        int diceNumber = (int) ((int)6 * Math.random());
        return diceNumber + 1;
    }

    private void disableButtons(){
        btHold.setEnabled(false);
        btRoll.setEnabled(false);
    }

    private void isWin(){
        if(playerOverallScore >= 100){
            Toast.makeText(MainActivity.this, "You Won",Toast.LENGTH_SHORT).show();
            reset();
        }
        if(computerOverallScore >= 100){
            Toast.makeText(MainActivity.this, "Computer Won", Toast.LENGTH_SHORT).show();
            reset();
        }
    }
}

