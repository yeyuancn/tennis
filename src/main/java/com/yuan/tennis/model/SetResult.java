package com.yuan.tennis.model;

/**
 * Created by V644593 on 9/14/2015.
 */
public class SetResult
{
    private int gameWon;
    private int gameLost;
    private boolean isTieBreak;

    public SetResult()
    {

    }

    public SetResult(int gameWon, int gameLost)
    {
        this.gameWon = gameWon;
        this.gameLost = gameLost;
    }


    public int getGameWon()
    {
        return gameWon;
    }

    public void setGameWon(int gameWon)
    {
        this.gameWon = gameWon;
    }

    public int getGameLost()
    {
        return gameLost;
    }

    public void setGameLost(int gameLost)
    {
        this.gameLost = gameLost;
    }

    public boolean isTieBreak()
    {
        return isTieBreak;
    }

    public void setIsTieBreak(boolean isTieBreak)
    {
        this.isTieBreak = isTieBreak;
    }
}
