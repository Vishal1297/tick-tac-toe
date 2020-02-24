package com.vishal.ticktactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    //  Player Representation

    //  0 - X
    //  1 - O

    //  States

    //  0   -   X
    //  1   -   O
    //  2   -   Null

    boolean gameStatus = true;
    int activePlayer = 0, tapCount = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winPos = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    public void onTap(View view) {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());
        if (!gameStatus) {
            resetGame();
        }

        if (gameState[tappedImage] == 2 && gameStatus) {

            gameState[tappedImage] = activePlayer;
            img.setTranslationY(-1000f);

            if (activePlayer == 0) {
                img.setImageResource(R.drawable.cross);
                activePlayer = 1;
                TextView status = findViewById(R.id.status);
                status.setText(R.string.yturn);
            } else {
                img.setImageResource(R.drawable.digit_0);
                activePlayer = 0;
                TextView status = findViewById(R.id.status);
                status.setText(R.string.xturn);
            }
            img.animate().translationYBy(1000f).setDuration(200);
            tapCount++;
        }

        //  Player  Winning
        for (int[] winIndex : winPos) {
            if (gameState[winIndex[0]] == gameState[winIndex[1]] &&
                    gameState[winIndex[1]] == gameState[winIndex[2]] &&
                    gameState[winIndex[0]] != 2) {

                //  Who Win's
                gameStatus = false;
                TextView status = findViewById(R.id.status);
                if (gameState[winIndex[0]] == 0) {
                    status.setText(R.string.xwon);
                } else {
                    status.setText(R.string.ywon);
                }
            }
        }

        //  Draw Game
        if (tapCount == 9 && gameStatus) {
            TextView status = findViewById(R.id.status);
            status.setText(R.string.draw);
            resetGame();
        }
    }

    public void resetGame() {
        tapCount = 0;
        activePlayer = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        ((ImageView) findViewById(R.id.imageView)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView8)).setImageResource(0);

        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setTitle(R.string.app_name).setMessage(R.string.playAgain).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gameStatus = true;
                TextView status = findViewById(R.id.status);
                status.setText(R.string.xturn);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setTitle(R.string.exitTitle).setMessage("Are you sure?").setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GameActivity.super.onBackPressed();
            }
        }).setNegativeButton(R.string.cancel, null).setCancelable(false);
        builder.create().show();
    }
}
