package com.example.admin.game2048v0_2;

import java.util.Random;

/**
 * Created by Admin on 10.07.2015.
 */
public class Algoritm
{
    //public  int[][] mass={{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    public  int[][] mass={{2,2,4,8},{2,32,64,128},{4,0,0,0},{8,0,0,0}};

    public  int Score=0;

    Random Rand;

    public boolean Game_Over = false;
    public boolean Game_Win = false;

    //Обнуление игрового массива
    public  void Zero()
    {
        Score=0;
        for (int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                mass[i][j]=0;
            }
        }
    }
    //Сдвиг влево
    public void Left()
    {
        int x = 0;
        for(int i = 0; i < 4; i++ )
        {
            for(int j = 0; j < 4; j++ )
            {
                for(int k = j + 1; k < 4; k++ )
                {
                    if (mass[i] [k] != 0)
                    {
                        if (mass[i] [j] == 0)
                        {
                            mass[i] [j] = mass[i] [k];
                            mass[i] [k] = 0;
                            x = 1;
                        }
                        else
                        {
                            if (mass[i] [j] == mass[i] [k])
                            {
                                mass[i] [j] += mass[i] [k];
                                mass[i] [k] = 0;
                                Score += mass[i] [j];
                                x = 1;
                            }
                            break;
                        }
                    }
                }
            }
        }
        this.Randomm(x);
    }
    //Сдвиг вправо
    public void Right()
    {
        int x = 0;
        for (int i = 0; i < 4; i++)
        {
            for (int j = 3; j >-1; j--)
            {
                for (int k = j - 1; k >-1; k--)
                {
                    if (mass[i] [k] != 0)
                    {
                        if (mass[i] [j] == 0)
                        {
                            mass[i] [j] = mass[i] [k];
                            mass[i] [k] = 0;
                            x = 1;
                        }
                        else
                        {
                            if (mass[i] [j] == mass[i] [k])
                            {
                                mass[i] [j] += mass[i] [k];
                                mass[i] [k] = 0;
                                Score += mass[i] [j];
                                x = 1;
                            }
                            break;
                        }
                    }
                }
            }
        }
        this.Randomm(x);
    }
    //Сдвиг вверх
    public void Up()
    {
        int x=0;
        for (int j = 0; j < 4; j++)
        {
            for (int i = 0; i < 4; i++)
            {
                for (int k = i + 1; k < 4; k++)
                {
                    if (mass[k][j] != 0)
                    {
                        if (mass[i] [j] == 0)
                        {
                            mass[i] [j] = mass[k] [j];
                            mass[k] [j] = 0;
                            x=1;
                        }
                        else
                        {
                            if (mass[i] [j] == mass[k] [j])
                            {
                                mass[i] [j] += mass[k] [j];
                                mass[k] [j] = 0;
                                Score+=mass[i][j];
                                x=1;
                            }
                            break;
                        }
                    }
                }
            }
        }
        Randomm(x);
    }
    //Сдвиг вниз
    public void Down()
    {
        int x = 0;
        for (int j = 0; j < 4; j++)
        {
            for (int i = 3; i >-1; i--)
            {
                for (int k = i - 1; k >-1; k--)
                {
                    if (mass[k] [j] != 0)
                    {
                        if (mass[i] [j] == 0)
                        {
                            mass[i] [j] = mass[k] [j];
                            mass[k] [j] = 0;
                            x = 1;
                        }
                        else
                        {
                            if (mass[i] [j] == mass[k] [j])
                            {
                                mass[i][j] += mass[k] [j];
                                mass[k] [j] = 0;
                                Score += mass[i] [j];
                                x = 1;
                            }
                            break;
                        }
                    }
                }
            }
        }
        this.Randomm(x);
    }

    public void Start()
    {
        Rand=new Random();
        Randomm(1);
        Randomm(1);
    }

    public int Random_Cell()
    {
        Random r=new Random();
        int cell;
        cell=r.nextInt(10)+1;
        if (cell == 10)
            cell = 4000;//***
        else
            cell = 2000;//***
        return cell;
    }

    public int Random_index()
    {
        return Rand.nextInt(4);
    }

    public void Randomm(int x)
    {
        int var;
        if(x!=0)
        {
            int i;
            int j;
            i=Random_index();
            j=Random_index();
            if(mass[i][j]==0)
            {
                var = Random_Cell();
                mass[i][j]=var;
            }
            else
            {
                Randomm(x);
            }
        }
    }
    //Победа
    public boolean GameWin()
    {
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                if (mass[i] [j] == 131072)
                Game_Win = true;
            }
        }
        return Game_Win;
    }
    //Поражение
    public boolean GameOver()
    {
        boolean x1 = true;
        Game_Over = true;

        int w, a, s, d;

        for (int n = 0; n < 4; n++)
        {
            for (int m = 0; m < 4; m++)
            {
                if (mass[n] [m] == 0)
                {
                    x1 = false;
                    Game_Over = false;
                }

            }
        }

        if(x1==true)
        {
            for(int i=0;i<4;i++)
            {
                for(int j=0;j<4;j++)
                {
                    w = i - 1; a = j - 1; s = i + 1; d = j - 1;
                    if(w >= 0 && w <= 3 )
                    {
                        if (mass[i] [j] == mass[w] [j])
                        Game_Over = false;
                    }
                    if(a >= 0 && a <= 3 )
                    {
                        if (mass[i] [j] == mass[i] [a])
                        Game_Over = false;
                    }
                    if(s >= 0 && s <= 3 )
                    {
                        if (mass[i] [j] == mass[s] [j])
                        Game_Over = false;
                    }
                    if(d >= 0 && d <= 3 )
                    {
                        if (mass[i] [j] == mass[i] [d])
                        Game_Over = false;
                    }
                }
            }
        }
        return Game_Over;
    }

    //Возвращение адреса текстуры
    public int RetText(int var)
    {
        int TextureID=0;

        switch (var)
        {
            case 0:
                TextureID = R.drawable.k0;
                break;
            case 2:
                TextureID = R.drawable.k2;
                break;
            case 4:
                TextureID = R.drawable.k4;
                break;
            case 8:
                TextureID = R.drawable.k8;
                break;
            case 16:
                TextureID = R.drawable.k16;
                break;
            case 32:
                TextureID = R.drawable.k32;
                break;
            case 64:
                TextureID = R.drawable.k64;
                break;
            case 128:
                TextureID = R.drawable.k128;
                break;
            case 256:
                TextureID = R.drawable.k256;
                break;
            case 512:
                TextureID = R.drawable.k512;
                break;
            case 1024:
                TextureID = R.drawable.k1024;
                break;
            case 2048:
                TextureID = R.drawable.k2048;
                break;
            case 4096:
                TextureID = R.drawable.k4096;
                break;
            case 8192:
                TextureID = R.drawable.k8192;
                break;
            case 16384:
                TextureID = R.drawable.k16384;
                break;
            case 32768:
                TextureID = R.drawable.k32768;
                break;
            case 65536:
                TextureID = R.drawable.k65536;
                break;
            case 131072:
                TextureID = R.drawable.k131072;
                break;
        }

        return TextureID;
    }
}
