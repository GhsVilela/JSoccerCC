package com.soccer.ghsvi.jsoccercc;
import junit.framework.Assert;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.support.test.filters.SmallTest;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.EditText;

import com.soccer.ghsvi.jsoccercc.R;

import com.robotium.solo.Solo;
import com.soccer.ghsvi.jsoccercc.rss.LatestNewsFragment;
import com.soccer.ghsvi.jsoccercc.rss.RssActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SearchTeam extends
        ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public SearchTeam() {
        super(MainActivity.class);
    }

    @Before
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @SmallTest
    public void testSearchTeam() throws InterruptedException {
        String resultado = "4 Results found for Arsenal";

        solo.assertCurrentActivity("wrong activity", MainActivity.class);
        solo.unlockScreen();
        Thread.sleep(2000);
        solo.clickOnImageButton(0);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_CENTER);
        solo.waitForActivity(InserirPesquisaTimes.class, 2000);
        EditText editTextTime = (EditText) solo.getView(R.id.editText);
        solo.enterText(editTextTime, "Arsenal");
        solo.clickOnButton("OK");
        solo.waitForDialogToClose();
        Thread.sleep(1000);
        solo.scrollDown();
        assertTrue(solo.searchText(resultado));
        Thread.sleep(5000);
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

}
