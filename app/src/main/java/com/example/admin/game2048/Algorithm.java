package com.example.admin.game2048;

import android.graphics.drawable.GradientDrawable;

import java.util.Random;

public class Algorithm {
    //public  int[][] mass={{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    public final static int size = 4;
    public final static int newTwo = 2000;
    public final static int newFour = 2000;

    public int[][] mass = {{2, 2, 4, 8}, {2, 32, 64, 128}, {4, 0, 0, 0}, {8, 0, 0, 0}};

    public int score = 0;

    public Random rand;

    public void clear() {
        score = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                mass[i][j] = 0;
    }

    public void up() {
        int x = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = j + 1; k < size; k++) {
                    if (mass[i][k] != 0) {
                        if (mass[i][j] == 0) {
                            mass[i][j] = mass[i][k];
                            mass[i][k] = 0;
                            x = 1;
                        } else {
                            if (mass[i][j] == mass[i][k]) {
                                mass[i][j] += mass[i][k];
                                mass[i][k] = 0;
                                score += mass[i][j];
                                x = 1;
                            }
                            break;
                        }
                    }
                }
            }
        }
        random(x);
    }

    public void down() {
        int x = 0;
        for (int i = 0; i < size; i++) {
            for (int j = size - 1; j >= 0; j--) {
                for (int k = j - 1; k >= 0; k--) {
                    if (mass[i][k] != 0) {
                        if (mass[i][j] == 0) {
                            mass[i][j] = mass[i][k];
                            mass[i][k] = 0;
                            x = 1;
                        } else {
                            if (mass[i][j] == mass[i][k]) {
                                mass[i][j] += mass[i][k];
                                mass[i][k] = 0;
                                score += mass[i][j];
                                x = 1;
                            }
                            break;
                        }
                    }
                }
            }
        }
        random(x);
    }

    public void left() {
        int x = 0;
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                for (int k = i + 1; k < size; k++) {
                    if (mass[k][j] != 0) {
                        if (mass[i][j] == 0) {
                            mass[i][j] = mass[k][j];
                            mass[k][j] = 0;
                            x = 1;
                        } else {
                            if (mass[i][j] == mass[k][j]) {
                                mass[i][j] += mass[k][j];
                                mass[k][j] = 0;
                                score += mass[i][j];
                                x = 1;
                            }
                            break;
                        }
                    }
                }
            }
        }
        random(x);
    }

    public void right() {
        int x = 0;
        for (int j = 0; j < size; j++) {
            for (int i = size - 1; i >= 0; i--) {
                for (int k = i - 1; k >= 0; k--) {
                    if (mass[k][j] != 0) {
                        if (mass[i][j] == 0) {
                            mass[i][j] = mass[k][j];
                            mass[k][j] = 0;
                            x = 1;
                        } else {
                            if (mass[i][j] == mass[k][j]) {
                                mass[i][j] += mass[k][j];
                                mass[k][j] = 0;
                                score += mass[i][j];
                                x = 1;
                            }
                            break;
                        }
                    }
                }
            }
        }
        random(x);
    }

    public void start() {
        rand = new Random();
        random(1);
        random(1);
    }

    public int randomCell() {
        return rand.nextInt(11) == 10 ? newFour : newTwo;
    }

    public int randomIndex() {
        return rand.nextInt(4);
    }

    public void random(int x) {
        if (x != 0) {
            int i = randomIndex();
            int j = randomIndex();
            if (mass[i][j] == 0)
                mass[i][j] = randomCell();
            else
                random(x);
        }
    }

    public boolean isGameOver() {
        int w, a, s, d;
        for (int n = 0; n < size; n++)
            for (int m = 0; m < size; m++)
                if (mass[n][m] == 0)
                    return false;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                w = i - 1;
                a = j - 1;
                s = i + 1;
                d = j - 1;

                if (w >= 0 && w < size && mass[i][j] == mass[w][j])
                    return false;

                if (a >= 0 && a < size && mass[i][j] == mass[i][a])
                    return false;

                if (s >= 0 && s < size && mass[i][j] == mass[s][j])
                    return false;

                if (d >= 0 && d < size && mass[i][j] == mass[i][d])
                    return false;
            }
        }
        return true;
    }

    public GradientDrawable color(int var) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(8);
        int color = 0;
        switch (var) {
            case 0:
                color = R.color.c0;
                break;
            case 2:
                color = R.color.c2;
                break;
            case 4:
                color = R.color.c4;
                break;
            case 8:
                color = R.color.c8;
                break;
            case 16:
                color = R.color.c16;
                break;
            case 32:
                color = R.color.c32;
                break;
            case 64:
                color = R.color.c64;
                break;
            case 128:
                color = R.color.c128;
                break;
            case 256:
                color = R.color.c256;
                break;
            case 512:
                color = R.color.c512;
                break;
            case 1024:
                color = R.color.c1024;
                break;
            case 2048:
                color = R.color.c2048;
                break;
            case 4096:
                color = R.color.c4096;
                break;
            case 8192:
                color = R.color.c8192;
                break;
            case 16384:
                color = R.color.c16384;
                break;
            case 32768:
                color = R.color.c32768;
                break;
            case 65536:
                color = R.color.c65536;
                break;
            case 131072:
                color = R.color.c131072;
                break;
        }
        gd.setColor(App.getContext().getResources().getColor(color));
        return gd;
    }
}
