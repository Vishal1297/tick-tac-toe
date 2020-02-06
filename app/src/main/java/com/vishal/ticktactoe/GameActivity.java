package com.vishal.ticktactoe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    int activePlayer = 0;
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
            resetGame(view);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        if (gameState[tappedImage] == 2) {

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
            img.animate().translationYBy(1000f).setDuration(100);
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
        int count = 0;
        for (int i = 0; i < gameState.length; i++) {
            if (gameState[i] != 2) {
                count++;
            } else {
                count = 0;
                break;
            }
        }
        if (count == gameState.length) {
            TextView status = findViewById(R.id.status);
            status.setText(R.string.draw);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(GameActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }, 1000);
        }
    }

    public void resetGame(View view) {
        gameStatus = true;
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
    }
}
