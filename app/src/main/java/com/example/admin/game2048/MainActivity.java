package com.example.admin.game2048;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    TextView score;
    RelativeLayout RL;
    GridLayout container;
    public Algorithm algorithm;
    TextView[][] views;
    TextView score2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = (TextView) findViewById(R.id.Score);
        RL = (RelativeLayout) findViewById(R.id.Main2);
        score2 = (TextView) findViewById(R.id.Score2);
        container = (GridLayout) findViewById(R.id.container);
        container.removeAllViews();
        container.setColumnCount(4);
        container.setRowCount(4);
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(8);
        gd.setColor(0xff4f5a6e);
        container.setBackgroundDrawable(gd);

        RL.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public boolean onSwipeRight() {
                Right();
                return true;
            }

            @Override
            public boolean onSwipeLeft() {
                Left();
                return true;
            }

            @Override
            public boolean onSwipeTop() {
                Up();
                return true;
            }

            @Override
            public boolean onSwipeBottom() {
                Down();
                return true;
            }
        });
        container.post(new Runnable() {
            @Override
            public void run() {
                Start();
            }
        });

    }

    public void New_Game(View v) {
        score.setText("------>");
        Start();
    }

    public static float convertDpToPixel(float dp, Context context){
        return ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT) * dp;
    }

    public float textSize(String s){
        if(s.length()==4) {
            return convertDpToPixel(16, this);
        }else if(s.length()==5) {
            return convertDpToPixel(12, this);
        }else if(s.length()==6) {
            return convertDpToPixel(8, this);
        }
        return convertDpToPixel(18, this);
    }
    private void Start() {
        algorithm = new Algorithm();
        algorithm.Zero();//Обнуление игрового массива
        views = new TextView[4][4];
        int padding = container.getPaddingLeft();
        int size = (container.getWidth() - padding * 6)/4;
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(8);
        gd.setStroke((int)convertDpToPixel(8, this), 0xFF000000);
        int margin = padding/2;

        int id = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                id++;
                views[i][j] = new TextView(this);
                views[i][j].setId(id);
                String keks = ""+algorithm.mass[i][j]*10;

                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.width = param.height = size;
                param.leftMargin = param.topMargin = param.rightMargin = param.bottomMargin = margin;
                param.setGravity(Gravity.CENTER);
                param.columnSpec = GridLayout.spec(i);
                param.rowSpec = GridLayout.spec(j);
                views[i][j].setLayoutParams(param);
                views[i][j].setGravity(Gravity.CENTER);
                if (algorithm.mass[i][j] != 0)
                    views[i][j].setText(keks);
                views[i][j].setTextSize(textSize(keks));

                views[i][j].setBackgroundDrawable(algorithm.color(algorithm.mass[i][j]));

                container.addView(views[i][j]);
            }
        }
        algorithm.Start();
        Draw();
    }

    private void Draw() {
        score.setText(Integer.toString(algorithm.Score));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                if (algorithm.mass[i][j] != 2000 && algorithm.mass[i][j] != 4000) {
                }
                if (algorithm.mass[i][j] == 2000) {
                    algorithm.mass[i][j] = 2;
                }
                if (algorithm.mass[i][j] == 4000) {
                    algorithm.mass[i][j] = 4;

                }
                String keks = ""+algorithm.mass[i][j];
                views[i][j].setBackgroundDrawable(algorithm.color(algorithm.mass[i][j]));
                views[i][j].setText(algorithm.mass[i][j] == 0 ? "" : keks);
                views[i][j].setTextSize(textSize(keks));
            }
        }
    }


    public void Left() {
        algorithm.left();
        Draw();
        if (algorithm.GameOver()) {
            Game_over();
        }
    }

    public void Up() {
        algorithm.up();
        Draw();
        if (algorithm.GameOver()) {
            Game_over();
        }
    }

    public void Down() {
        algorithm.down();
        Draw();
        if (algorithm.GameOver()) {
            Game_over();
        }
    }

    public void Right() {
        algorithm.right();
        Draw();
        if (algorithm.GameOver()) {
            Game_over();
        }
    }

    public void Game_over() {
        setContentView(R.layout.game_over);
    }

    public void New_Game2(View v) {
        setContentView(R.layout.activity_main);//не работает новая игра
        Start();
    }

    public void Exit(View v) {
        System.exit(0);
    }
}
