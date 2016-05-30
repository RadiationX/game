package com.example.admin.game2048v0_2;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.view.View.OnTouchListener;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import com.example.admin.game2048v0_2.OnSwipeTouchListener;





public class MainActivity extends ActionBarActivity  {

    TextView score;
    RelativeLayout RL;
    public Algoritm A2048;
    ImageView [][] KvadratMassiv;
    TextView score2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score=(TextView)findViewById(R.id.Score);
        RL = (RelativeLayout) findViewById(R.id.Main2);
        score2=(TextView)findViewById(R.id.Score2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void New_Game(View v)
    {
        score.setText("------>");
        /*int count=0;
        count++;

        ImageView imageView=new ImageView(this);
        imageView.setId(count);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(140,300));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding( 40, 200, 0, 0);

        imageView.setImageResource(RetText(8));
        RL.addView(imageView);*/
        Start();
    }



    private void Start()
    {
        A2048 = new Algoritm();
        A2048.Zero();//Обнуление игрового массива
        //A2048.Start();
        KvadratMassiv = new ImageView[4][4];
        int id=0;
        for (int i=0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                id++;
                KvadratMassiv[i][j] = new ImageView(this);
                KvadratMassiv[i][j].setId(id);
                KvadratMassiv[i][j].setLayoutParams(new RelativeLayout.LayoutParams(140+100*i,200+100*j));//***300->200
                KvadratMassiv[i][j].setScaleType(ImageView.ScaleType.CENTER_CROP);
                KvadratMassiv[i][j].setPadding(40+100*i, 100+100*j, 0, 0);//***200->100
                KvadratMassiv[i][j].setImageResource(A2048.RetText(A2048.mass[i][j]));
                RL.addView(KvadratMassiv[i][j]);
            }
        }
        A2048.Start();
        Draw();

    }

    private void Update()
    {

    }

    private void Draw()
    {
        score.setText(Integer.toString(A2048.Score));
        for (int i=0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                if(A2048.mass[i][j]!=2000&&A2048.mass[i][j]!=4000)
                {
                KvadratMassiv[i][j].setImageResource(A2048.RetText(A2048.mass[i][j]));
                }
                if(A2048.mass[i][j]==2000)
                {
                    A2048.mass[i][j]=2;
                    KvadratMassiv[i][j].setImageResource(R.drawable.new2);
                }
                if(A2048.mass[i][j]==4000)
                {
                    A2048.mass[i][j]=4;
                    KvadratMassiv[i][j].setImageResource(R.drawable.new4);
                }
            }
        }
        //KvadratMassiv[0][0].setImageResource(A2048.RetText(128));
    }



    public void Left(View v)
    {
        //A2048.Randomm(1);
        A2048.Up();
        Draw();
        if(A2048.GameOver()==true)
        {
            Game_over();
        }
    }

    public void Up(View v)
    {
        A2048.Left();
        Draw();
        //KvadratMassiv[1][3].setImageResource(R.drawable.new2);
        if(A2048.GameOver()==true)
        {
            Game_over();
        }
    }

    public void Down(View v)
    {
        A2048.Right();
        Draw();
        if(A2048.GameOver()==true)
        {
            Game_over();
        }
        //Game_over();
    }

    public void Right(View v)
    {
        /*if(A2048.GameOver()==true)
        {
            Game_over();
        }
        else
        {
            A2048.Down();
            Draw();
        }*/
        A2048.Down();
        Draw();
        if(A2048.GameOver()==true)
        {
            Game_over();
        }
    }

    public void Game_over()
    {
       setContentView(R.layout.game_over);
       //score2.setText("Баллы:"+Integer.toString(A2048.Score));
    }

    public void New_Game2(View v)
    {
        setContentView(R.layout.activity_main);//не работает новая игра
        Start();
    }

    public void Exit(View v)
    {
        System.exit(0);
    }
}
