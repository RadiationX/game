package com.example.admin.game2048;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    TextView score;
    RelativeLayout RL;
    RelativeLayout container;
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
        container = (RelativeLayout) findViewById(R.id.container);
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(2, 0xFF000000);
        gd.setColor(0xff597da3);
        container.setBackgroundDrawable(gd);
        RL.setOnTouchListener(new OnSwipeTouchListener(this){
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
    }

    public void New_Game(View v) {
        score.setText("------>");
        Start();
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT) * dp;
    }
    private void Start() {
        algorithm = new Algorithm();
        algorithm.Zero();//Обнуление игрового массива
        //algorithm.Start();
        views = new TextView[4][4];
        int size;
        size = container.getWidth();
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(1, 0xFF000000);
        int padding = container.getPaddingLeft();
        int margin = container.getPaddingLeft();
        size = size-padding*2;
        int huina = (int) convertDpToPixel(8, this);
        int id = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                id++;
                views[i][j] = new TextView(this);
                views[i][j].setId(id);
                //views[i][j].setLayoutParams(new LinearLayout.LayoutParams(70 + 50 * i, 100 + 50 * j));//***300->200
                ViewGroup.MarginLayoutParams kek = new ViewGroup.MarginLayoutParams((size-huina*2)/4, (size-huina*2)/4);
                kek.leftMargin =  (size+huina)/4 * i;
                kek.topMargin =  (size+huina)/4 * j;

                views[i][j].setLayoutParams(kek);//***300->200
                views[i][j].setGravity(Gravity.CENTER);
                //views[i][j].setWidth(70 + 50 * i);
                //views[i][j].setHeight(100 + 50 * j);
                //views[i][j].setScaleType(ImageView.ScaleType.CENTER_CROP);
                //views[i][j].setPadding(20 + 50 * i, 50 + 50 * j, 0, 0);//***200->100
                //views[i][j].setPadding(20 + 50 * i, 50 + 50 * j, 0, 0);//***200->100
                //views[i][j].setImageResource(algorithm.RetText(algorithm.mass[i][j]));
                //views[i][j].setBackgroundColor(algorithm.color(algorithm.mass[i][j]));
                if(algorithm.mass[i][j]!=0)
                    views[i][j].setText(""+algorithm.mass[i][j]);

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
                    views[i][j].setBackgroundDrawable(algorithm.color(algorithm.mass[i][j]));
                    views[i][j].setText(algorithm.mass[i][j]==0?"":""+algorithm.mass[i][j]);
                }
                if (algorithm.mass[i][j] == 2000) {
                    algorithm.mass[i][j] = 2;
                    views[i][j].setBackgroundDrawable(algorithm.color(algorithm.mass[i][j]));
                    views[i][j].setText(algorithm.mass[i][j]==0?"":""+algorithm.mass[i][j]);
                }
                if (algorithm.mass[i][j] == 4000) {
                    algorithm.mass[i][j] = 4;
                    views[i][j].setBackgroundDrawable(algorithm.color(algorithm.mass[i][j]));
                    views[i][j].setText(algorithm.mass[i][j]==0?"":""+algorithm.mass[i][j]);
                }
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
