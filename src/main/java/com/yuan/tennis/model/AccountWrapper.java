package com.yuan.tennis.model;

import com.yuan.tennis.model.persistent.Account;
import com.yuan.tennis.model.persistent.Player;

import java.io.Serializable;

/**
 * Created by V644593 on 8/3/2016.
 */
public class AccountWrapper implements Serializable
{
    Account account;
    Player player;

    public AccountWrapper()
    {
    }

    public AccountWrapper(Account account, Player player)
    {
        this.account = account;
        this.player = player;
    }

    public Account getAccount()
    {
        return account;
    }

    public void setAccount(Account account)
    {
        this.account = account;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }
}
