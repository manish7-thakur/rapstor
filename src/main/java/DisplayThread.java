import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

public class DisplayThread implements Runnable {
    public DisplayThread() {
        Preferences root = Preferences.userRoot();
        final Preferences node = root.node("/com/birlasoft/gui");
        intervalTime = node.getInt("IntervalTime", 10);
    }

    public void run() {
        ActionListener listener = new DisplayAction();
        Timer intervalTimer = new Timer(1000 * intervalTime, listener);
        intervalTimer.setInitialDelay(0);
        intervalTimer.start();
    }

    private int intervalTime;
}
