package edu.csuci.appaca;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.utils.ShearUtils;
import edu.csuci.appaca.utils.TimeUtils;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("edu.csuci.appaca", appContext.getPackageName());
    }

    @Test
    public void getFullWoolSeconds() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        int expected = 86400;
        int actual = appContext.getResources().getInteger(R.integer.time_to_full_wool);
        assertEquals(expected, actual);
    }

    @Test
    public void testAlpacaThatHasJustBeenSheared() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", 1);
            jsonObject.put("name", "");
            jsonObject.put("path", "");
            jsonObject.put("foodStat", 1);
            jsonObject.put("hygieneStat", 1);
            jsonObject.put("happinessStat", 1);
            jsonObject.put("lastShearTime", TimeUtils.getCurrentTime());
            int expected = 0;
            int actual = ShearUtils.getShearValue(Alpaca.ofJSON(jsonObject), appContext);
            assertEquals(expected, actual);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAlpacaSheared12HoursAgo() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", 1);
            jsonObject.put("name", "");
            jsonObject.put("path", "");
            jsonObject.put("foodStat", 1);
            jsonObject.put("hygieneStat", 1);
            jsonObject.put("happinessStat", 1);
            jsonObject.put("lastShearTime", TimeUtils.getCurrentTime() - (12 * 60 * 60));
            int expected = 25;
            int actual = ShearUtils.getShearValue(Alpaca.ofJSON(jsonObject), appContext);
            assertEquals(expected, actual);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAlpacaSheared24HoursAgo() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", 1);
            jsonObject.put("name", "");
            jsonObject.put("path", "");
            jsonObject.put("foodStat", 1);
            jsonObject.put("hygieneStat", 1);
            jsonObject.put("happinessStat", 1);
            jsonObject.put("lastShearTime", TimeUtils.getCurrentTime() - (24 * 60 * 60));
            int expected = 50;
            int actual = ShearUtils.getShearValue(Alpaca.ofJSON(jsonObject), appContext);
            assertEquals(expected, actual);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAlpacaShearedSeveralYearsAgo() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", 1);
            jsonObject.put("name", "");
            jsonObject.put("path", "");
            jsonObject.put("foodStat", 1);
            jsonObject.put("hygieneStat", 1);
            jsonObject.put("happinessStat", 1);
            jsonObject.put("lastShearTime", 0);
            int expected = 50;
            int actual = ShearUtils.getShearValue(Alpaca.ofJSON(jsonObject), appContext);
            assertEquals(expected, actual);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
