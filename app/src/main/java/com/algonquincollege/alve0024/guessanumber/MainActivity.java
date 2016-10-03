/**
 * {Guess A Number is an app where the player need to guess the right number between 1 and 1000}
 *
 * @author {alve0024@algonquinlive.com}
 */

package com.algonquincollege.alve0024.guessanumber;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String ABOUT_DIALOG_TAG;
    private static int minGuessNumber = 1;  // Define the minimum value to be guessed
    private static int maxGuessNumber = 1000; // Define the maximum value to be guessed

    static {
        ABOUT_DIALOG_TAG = "About Dialog";
    }

    private int theNumber = 0;
    private int timesTried = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // As soon as the app is opened the game is started
        startGame();

        Button guessButton = (Button) findViewById(R.id.btnGuess);

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reference the txtGuess in the layout.
                EditText userGuessTxt = (EditText) findViewById(R.id.txtGuess);

                // The user can only enter a whole number.
                int userGuess;
                try {
                    userGuess = Integer.parseInt(userGuessTxt.getText().toString());
                } catch (NumberFormatException e) {
                    userGuessTxt.setError(getResources().getString(R.string.integer_error_msg));
                    userGuessTxt.requestFocus();
                    return;
                }

                // Validate if the user guess is in between the min and max number
                if (userGuess < minGuessNumber || userGuess > maxGuessNumber) {
                    String rangeErrorMsg = String.format(getResources().getString(R.string.range_error_msg), minGuessNumber, maxGuessNumber);
                    userGuessTxt.setError(rangeErrorMsg);
                    userGuessTxt.requestFocus();
                    return;
                }

                // Manage the flow and the rules of the game
                String msg = "";
                timesTried++;
                if (timesTried > 10) {
                    msg = String.format(getResources().getString(R.string.game_over_msg), theNumber);
                    startGame();
                } else if (userGuess == theNumber) {
                    if (timesTried >= 1 && timesTried <= 5) {
                        msg = String.format(getResources().getString(R.string.superior_win_msg), timesTried);
                    } else {
                        msg = String.format(getResources().getString(R.string.excellent_win_msg), timesTried);
                    }
                    startGame();
                } else {
                    if (userGuess > theNumber) {
                        msg = String.format(getResources().getString(R.string.too_high_msg), timesTried);
                    } else {
                        msg = String.format(getResources().getString(R.string.too_low_msg), timesTried);
                    }
                }
                showToast(msg);
            }
        });

        /**
         * When the user click and hold the button the secret number is shown
         */
        guessButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String msg = String.format(getResources().getString(R.string.show_secret_number), theNumber);
                showToast(msg);
                return true;
            }
        });
    }

    /**
     * Show be called every time you need to show a toast message
     * @param msg - String
     */
    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * This function is called every time you need to start or reset the game
     */
    public void startGame() {
        RandomNumber randomNumber = new RandomNumber(minGuessNumber, maxGuessNumber);
        EditText userGuessTxt = (EditText) findViewById(R.id.txtGuess);
        userGuessTxt.setText("");
        theNumber = randomNumber.generateRandomNumber();
        timesTried = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Display the About Dialog
        if (id == R.id.about_dlg) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        // Reset the game
        if (id == R.id.reset_btn) {
            String msg = getResources().getString(R.string.reseting_game_msg);
            showToast(msg);
            startGame();
        }
        return super.onOptionsItemSelected(item);
    }
}

