package com.example.jimfo.connectfour;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.GridLayout;

// This game is a variation of a game on Udemy from Rob Percival's
// The Complete Android Developer Course - Build 14 Apps
// His game was Connect 3 or TicTacToe. In his game he stored all
// winning combinations in a 2-d array. In my version I downloaded
// an image of the Connect 4 game board and used for-loops to find
// winning combinations. Future variations may include an undo last
// move button (in case player hits wrong position) and functionality
// to play over the internet.

public class MainActivity extends AppCompatActivity
{
    //declare variables
    int row;
    int col;
    int colSize = 6;
    int rowSize = 5;
    int activePlayer = 0;
    boolean winner = false;
    boolean gameIsActive = true;

    //declare widget variables
    Button playAgainBtn;
    TextView winStatusTV;
    GridLayout gridLayout;

    int[][] gameState =
                   {{2,2,2,2,2,2,2},
                    {2,2,2,2,2,2,2},
                    {2,2,2,2,2,2,2},
                    {2,2,2,2,2,2,2},
                    {2,2,2,2,2,2,2},
                    {2,2,2,2,2,2,2}};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set orientation to portrait mode only
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //initialize widget variables
        playAgainBtn = (Button) findViewById(R.id.playAgainBtn);
        winStatusTV = (TextView) findViewById(R.id.winStatus);
        gridLayout = (GridLayout)findViewById(R.id.gridlayout);
    }

    public void dropIn(View v)
    {
        ImageView counter = (ImageView) v;
        String tappedCounter = counter.getTag().toString();
        row = Character.getNumericValue(tappedCounter.charAt(0));
        col = Character.getNumericValue(tappedCounter.charAt(1));

        //game play procedure if position = 2 then position is unplayed
        if (gameState[row][col] == 2 && gameIsActive)
        {
            counter.setTranslationY(-1000f);
            gameState[row][col] = activePlayer;

            if (activePlayer == 0)
            {
                counter.setImageResource(R.drawable.yellow);
                winner = checkForWin(activePlayer, row, col);
                activePlayer = 1;
            }
            else
            {
                counter.setImageResource(R.drawable.red);
                winner = checkForWin(activePlayer, row, col);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).rotation(360).setDuration(500);

            if (winner)
            {
                String winningPlayer = "Red";

                gameIsActive = false;

                if (activePlayer == 1)
                {
                    winningPlayer = "Yellow";
                }

                winStatusTV.setText(winningPlayer + " has won!");
                winStatusTV.setVisibility(View.VISIBLE);
            }
            else
            {
                boolean gameIsOver = true;
                int counterState = 2;

                //check to see if game is a draw
                for (int i = 0; i < rowSize; i++)
                {
                    for (int j = 0; j < colSize; j++)
                    {
                        counterState = gameState[i][j];

                        if(counterState == 2)
                        {
                            gameIsOver = false;
                        }
                    }
                }

                if (gameIsOver)
                {
                    winStatusTV.setText("It's a draw");
                }
            }
        }
    }

    public void playAgainPressed(View view)
    {
        activePlayer = 0;
        gameIsActive = true;
        winStatusTV.setVisibility(View.INVISIBLE);

        //reset gameState positions to 2 (unplayed)
        for (int i = 0; i <= rowSize; i++)
        {
            for(int j = 0; j <= colSize; j++)
            {
                gameState[i][j] = 2;
            }
        }

        //remove pieces from the board
        for (int i = 0; i< gridLayout.getChildCount(); i++)
        {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

    }

    public boolean checkForWin(int p, int r, int c)
    {
        int count = 0;

        // check rows
        for (int i = 0; i <= colSize; i++)
        {
            if (gameState[r][i] == p)
            {
                count++;
            }
            else
            {
                count = 0;
            }

            if (count >= 4)
            {
                return true;
            }
        }

        count = 0;

        // check columns
        for (int i = 0; i  <= rowSize; i++)
        {
            if (gameState[i][c] == p)
            {
                count++;
            }
            else
            {
                count = 0;
            }

            if (count >= 4)
            {
                return true;
            }
        }

        //check diagonals by rows down to the right
        for(int i = 0; i <= rowSize - 2; i++)
        {
            count = 0;
            int row, col;

            for( row = i, col = 0; row <= rowSize && col <= colSize; row++, col++ )
            {
                if(gameState[row][col] == p)
                {
                    count++;
                    if(count >= 4) return true;
                }
                else
                {
                    count = 0;
                }
            }
        }

        //check diagonals by columns down to the right
        for (int i = 0; i <= colSize - 2; i++)
        {
            count = 0;
            int row, col;

            for (row = 0, col = i; row <= rowSize && col <= colSize; row++, col++)
            {
                if (gameState[row][col] == p)
                {
                    count++;
                    if (count >= 4)
                    {
                        return true;
                    }
                }
                else
                {
                    count = 0;
                }
            }
        }

        //check diagonals by rows up to the left
        for (int i = rowSize; i >= 2; i--)
        {
            count = 0;
            int row, col;

            for (row = i, col = 0; row >= 0 && col < colSize; row--, col++)
            {
                if (gameState[row][col] == p)
                {
                    count++;
                    if (count >= 4)
                    {
                        return true;
                    }
                }
                else
                {
                    count = 0;
                }
            }
        }

        //check diagonals by columns up to the left
        for (int i = 1; i <= 4; i++)
        {
            count = 0;
            int row, col;

            for (row = rowSize, col = i; row >= 0 && col <= colSize; row--, col++)
            {
                if (gameState[row][col] == p)
                {
                    count++;
                    if (count >= 4)
                    {
                        return true;
                    }
                }
                else
                {
                    count = 0;
                }
            }
        }
        return false;
    }
}
