package shahin.android.exam.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class SplashActivityTest {

    @Rule
    public ActivityTestRule<SplashActivity> activityTestRule =
            new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void testSplashActivityAvailability() {
        Instrumentation instrumentation = getInstrumentation();
        Instrumentation.ActivityMonitor monitor = instrumentation
                .addMonitor(SplashActivity.class.getName(), null, false);

        Activity currentActivity = getInstrumentation().waitForMonitor(monitor);
        SplashActivity splashActivity = (SplashActivity) currentActivity;

        assertNotNull(splashActivity);
    }
}
