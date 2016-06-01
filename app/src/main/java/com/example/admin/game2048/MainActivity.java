package com.example.admin.game2048;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends Activity {
    private TextView score, topScore;
    private GridLayout container;
    private Algorithm algorithm;
    private TextView[][] views;
    private SharedPreferences preferences;
    private int currentTopScore = 0;
    private final static int size = Algorithm.size;
    private static float density = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        density = (float) getResources().getDisplayMetrics().densityDpi;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.Main2);
        score = (TextView) findViewById(R.id.Score);
        topScore = (TextView) findViewById(R.id.topScore);
        container = (GridLayout) findViewById(R.id.container);
        assert container != null;
        assert mainLayout != null;

        container.setColumnCount(4);
        container.setRowCount(4);
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(8);
        gd.setColor(getResources().getColor(R.color.grid_bg));
        container.setBackgroundDrawable(gd);
        currentTopScore = preferences.getInt("topScore", 0);

        topScore.setText("" + currentTopScore);

        mainLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public boolean onSwipeRight() {
                right();
                return true;
            }

            @Override
            public boolean onSwipeLeft() {
                left();
                return true;
            }

            @Override
            public boolean onSwipeTop() {
                up();
                return true;
            }

            @Override
            public boolean onSwipeBottom() {
                down();
                return true;
            }
        });
        container.post(new Runnable() {
            @Override
            public void run() {
                newGame();
            }
        });
    }

    public void newGame(View v) {
        if (algorithm != null) {
            if (algorithm.score > 0) {
                new AlertDialog.Builder(this)
                        .setMessage("Вы уверены что хотите начать новую игру?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                newGame();
                            }
                        }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
                return;
            }
        }
        newGame();
    }

    private void newGame() {
        container.removeAllViews();
        start();
    }

    public static float convertDpToPixel(float dp) {
        return (density / DisplayMetrics.DENSITY_DEFAULT) * dp;
    }

    public float textSize(String s) {
        switch (s.length()) {
            case 4:
                return convertDpToPixel(14);
            case 5:
                return convertDpToPixel(12);
            case 6:
                return convertDpToPixel(10);
            default:
                return convertDpToPixel(18);
        }
    }

    private void start() {
        algorithm = new Algorithm();
        algorithm.clear();
        views = new TextView[size][size];
        int padding = container.getPaddingLeft();
        int proportions = (container.getWidth() - padding * 6) / 4;
        int margin = padding / 2;

        int id = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                id++;
                views[i][j] = new TextView(this);
                views[i][j].setId(id);
                String text = "" + algorithm.mass[i][j];

                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.width = param.height = proportions;
                param.leftMargin = param.topMargin = param.rightMargin = param.bottomMargin = margin;
                param.setGravity(Gravity.CENTER);
                param.columnSpec = GridLayout.spec(i);
                param.rowSpec = GridLayout.spec(j);
                views[i][j].setLayoutParams(param);
                views[i][j].setGravity(Gravity.CENTER);


                if (algorithm.mass[i][j] != 0)
                    views[i][j].setText(text);

                views[i][j].setTextSize(textSize(text));

                views[i][j].setBackgroundDrawable(algorithm.color(algorithm.mass[i][j]));

                container.addView(views[i][j]);
            }
        }
        algorithm.start();
        draw();
    }

    private void draw() {
        score.setText(Integer.toString(algorithm.score));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (algorithm.mass[i][j] == Algorithm.newTwo)
                    algorithm.mass[i][j] = 2;

                if (algorithm.mass[i][j] == Algorithm.newFour)
                    algorithm.mass[i][j] = 4;
                String text = "" + algorithm.mass[i][j];
                views[i][j].setBackgroundDrawable(algorithm.color(algorithm.mass[i][j]));
                views[i][j].setText(algorithm.mass[i][j] == 0 ? "" : text);
                views[i][j].setTextSize(textSize(text));
            }
        }
    }

    public void left() {
        algorithm.left();
        draw();
        if (algorithm.isGameOver())
            gameOver();
    }

    public void up() {
        algorithm.up();
        draw();
        if (algorithm.isGameOver())
            gameOver();
    }

    public void down() {
        algorithm.down();
        draw();
        if (algorithm.isGameOver())
            gameOver();
    }

    public void right() {
        algorithm.right();
        draw();
        if (algorithm.isGameOver())
            gameOver();
    }

    public void gameOver() {
        if (algorithm.score > currentTopScore) {
            currentTopScore = algorithm.score;
            preferences.edit().putInt("topScore", currentTopScore).apply();
            topScore.setText("" + currentTopScore);
        }
        new AlertDialog.Builder(this)
                .setTitle("Поражение")
                .setMessage("Счет: " + Integer.toString(algorithm.score))
                .setPositiveButton("Новая игра", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        newGame();
                    }
                })
                .setNegativeButton("Выход", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                })
                .show();
    }
}
