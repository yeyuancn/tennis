package com.yuan.tennis.misc;

import com.yuan.tennis.dao.LeagueDAO;
import com.yuan.tennis.dao.MatchResultDAO;
import com.yuan.tennis.dao.PlayerDAO;
import com.yuan.tennis.ws.util.email.MailUtil;
import org.apache.log4j.Logger;

import java.util.TimerTask;

/**
 * Created by v644593 on 5/20/2016.
 */
public class LeagueTimerTask extends TimerTask
{
    private static final Logger logger = Logger.getLogger(LeagueTimerTask.class.getName());
    private LeagueDAO leagueDao = new LeagueDAO();
    private PlayerDAO playerDao = new PlayerDAO();
    private MatchResultDAO matchResultDao = new MatchResultDAO();

    @Override
    public void run()
    {
        logger.info("League timer task kicking off");
        long numberLeague = leagueDao.getLeagueNumber();
        long numberPlayer = playerDao.getPlayerNumber();
        long numberMatch = matchResultDao.getMatchResultNumber();
        MailUtil.sendCommentEmail("internal_league", "Current # of leagues:  " + numberLeague
                + "\n # of players:  " + numberPlayer
                + "\n # of matches:  " + numberMatch);
    }
}
