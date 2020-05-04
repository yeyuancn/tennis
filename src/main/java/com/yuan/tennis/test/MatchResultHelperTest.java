package com.yuan.tennis.test;


import com.yuan.tennis.model.persistent.MatchResult;
import com.yuan.tennis.ws.exception.AppValidationException;
import com.yuan.tennis.ws.util.MatchResultHelper;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by V644593 on 2/17/2016.
 */
public class MatchResultHelperTest
{
    @Test
    public void testMatchResultHelper()
    {
        MatchResult origResult = new MatchResult();
        origResult.setWinnerId(1);
        origResult.setLoserId(2);
        origResult.setSet1Score("1:6");
        origResult.setSet2Score("7:6");
        origResult.setSet3Score("2:6");

        MatchResult result = null;
        try
        {
            result = MatchResultHelper.decorateMatchResult(origResult);
        } catch (AppValidationException e)
        {
            e.printStackTrace();
        }

        assertEquals(result.getWinnerId(), 2);
        assertEquals(result.getLoserId(), 1);
        assertEquals(result.getSet1Score(), "6:1");
        assertEquals(result.getSet2Score(), "6:7");
        assertEquals(result.getSet3Score(), "6:2");
        assertEquals(result.getGameWon(), 18);
        assertEquals(result.getGameLost(), 10);
    }

    @Test
    public void testMatchResultHelper2()
    {
        MatchResult origResult = new MatchResult();
        origResult.setWinnerId(1000);
        origResult.setLoserId(2000);
        origResult.setSet1Score("1:6");
        origResult.setSet2Score("0:6");
        origResult.setSet3Score(":");

        MatchResult result = null;
        try
        {
            result = MatchResultHelper.decorateMatchResult(origResult);
        } catch (AppValidationException e)
        {
            e.printStackTrace();
        }

        assertEquals(result.getWinnerId(), 2000);
        assertEquals(result.getLoserId(), 1000);
        assertEquals(result.getSet1Score(), "6:1");
        assertEquals(result.getSet2Score(), "6:0");
        assertEquals(result.getSet3Score(), "");
        assertEquals(result.getGameWon(), 12);
        assertEquals(result.getGameLost(), 1);
    }

    @Test
    public void testMatchResultHelper3()
    {
        MatchResult origResult = new MatchResult();
        origResult.setWinnerId(1000);
        origResult.setLoserId(2000);
        origResult.setSet1Score("6:1");
        origResult.setSet2Score(":");
        origResult.setSet3Score("");

        MatchResult result = null;
        try
        {
            result = MatchResultHelper.decorateMatchResult(origResult);
        } catch (AppValidationException e)
        {
            e.printStackTrace();
        }

        assertEquals(result.getWinnerId(), 1000);
        assertEquals(result.getLoserId(), 2000);
        assertEquals(result.getSet1Score(), "6:1");
        assertEquals(result.getSet2Score(), "");
        assertEquals(result.getSet3Score(), "");
        assertEquals(result.getGameWon(), 6);
        assertEquals(result.getGameLost(), 1);
    }
}
