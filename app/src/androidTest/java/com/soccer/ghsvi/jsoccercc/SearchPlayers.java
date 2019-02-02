package com.soccer.ghsvi.jsoccercc;
import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.EditText;
import com.robotium.solo.Solo;
import org.junit.After;
import org.junit.Before;

public class SearchPlayers extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public SearchPlayers() {
        super(MainActivity.class);
    }

    @Before
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @SmallTest
    public void testSearchPlayers() throws InterruptedException {
        String resultado = "1 Result found for Neymar";

        solo.assertCurrentActivity("wrong activity", MainActivity.class);
        solo.unlockScreen();
        Thread.sleep(2000);
        solo.clickOnImageButton(0);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_CENTER);
        solo.waitForActivity(InserirPesquisaTimes.class, 2000);
        EditText editTextTime = (EditText) solo.getView(R.id.editTextSinglePlayer);
        solo.enterText(editTextTime, "Neymar");
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
