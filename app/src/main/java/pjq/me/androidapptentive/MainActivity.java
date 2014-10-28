package pjq.me.androidapptentive;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.apptentive.android.sdk.Apptentive;
import com.apptentive.android.sdk.module.messagecenter.UnreadMessagesListener;

import net.qiujuer.genius.material.MaterialButton;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private MaterialButton feedbackButton;
    private MaterialButton unreadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Apptentive.engage(this, "app_init");

        init();
    }

    private void init() {
        feedbackButton = (MaterialButton) findViewById(R.id.feedbackButton);
        unreadButton = (MaterialButton) findViewById(R.id.unreadButton);

        feedbackButton.setOnClickListener(this);
        unreadButton.setOnClickListener(this);

        Apptentive.setUnreadMessagesListener(unreadMessagesListener);
    }

    private UnreadMessagesListener unreadMessagesListener = new UnreadMessagesListener() {
        @Override
        public void onUnreadMessageCountChanged(int i) {
            if (i > 0) {
                unreadButton.setVisibility(View.VISIBLE);
            } else {
                unreadButton.setVisibility(View.VISIBLE);
            }

            unreadButton.setText("Got New Message " + i);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Apptentive.setUnreadMessagesListener(null);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // Engage a code point called "init".
            boolean shown = Apptentive.engage(this, "main_activity_focused");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.unreadButton:
                Utils.showMessageCenter(this);

                break;
            case R.id.feedbackButton:
                Utils.showMessageCenter(this);
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            Apptentive.engage(this, "app_exit");
        }

        return super.onKeyDown(keyCode, event);
    }

}
