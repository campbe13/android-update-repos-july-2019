package ca.campbell.optionsmenuprog;
/**
 * This illustrates dynamic  creation of an Option Menu
 * It also illustrates creating a Pop Up Menu
 *
 * The final type of menu in android, Context Menu is not implemented here
 * but will be shown in later code samples.
 *
 * Also I am playing fast and loose with the mediaplayer so it may crash
 * Check here for proper use
 * http://developer.android.com/reference/android/media/MediaPlayer.html
 *
 * @author Tricia
 *
 * Muppet sounds from http://www.soundboard.com/sb/The_Muppets_Sounds
 *
 */

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends Activity {
    private MediaPlayer mp = null;
    private static final String TAG = "MENUOPTPOP";

    private static final int MENU_CRICKETS = 0;
    private static final int MENU_DOGS = 1;
    private static final int MENU_BIGDOGS = 2;
    private static final int MENU_LITTLEDOGS = 3;
    private static final int MENU_CATS = 4;
    private static final int MENU_CHICKENS = 5;
    private static final int MENU_PIGS = 6;
    private static final int MENU_STOP = 7;
    private static final int MENU_CATHISS = 8;
    private static final int MENU_TIGER1 = 9;
    private static final int MENU_TIGER2 = 10;
    private static final int MENU_LION = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set default
        mp = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // public abstract MenuItem add (int groupId, int itemId, int order, CharSequence title)
        SubMenu dogMenu = menu.addSubMenu(R.string.dogs);
        dogMenu.add(Menu.NONE, MENU_BIGDOGS, Menu.NONE, R.string.bigdogs);
        dogMenu.add(Menu.NONE, MENU_LITTLEDOGS, Menu.NONE, R.string.littledogs);
        menu.add(Menu.NONE, MENU_CRICKETS, Menu.NONE, R.string.crickets);
        SubMenu catMenu = menu.addSubMenu(R.string.cats);
        catMenu.add(Menu.NONE, MENU_CATS, Menu.NONE, R.string.cat);
        catMenu.add(Menu.NONE, MENU_LION, Menu.NONE, R.string.lion);
        catMenu.add(Menu.NONE, MENU_TIGER1, Menu.NONE, R.string.tiger1);
        catMenu.add(Menu.NONE, MENU_TIGER2, Menu.NONE, R.string.tiger2);
        catMenu.add(Menu.NONE, MENU_CATHISS, Menu.NONE, R.string.cathiss);
        menu.add(Menu.NONE, MENU_CHICKENS, Menu.NONE, R.string.chickens);
        menu.add(Menu.NONE, MENU_PIGS, Menu.NONE, R.string.pigs);
        menu.add(Menu.NONE, MENU_STOP, Menu.NONE, R.string.shutup);
        return super.onCreateOptionsMenu(menu);
        // not using showAsAction() so they do not show on the actionbar
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case MENU_CRICKETS:
                startMediaPlayer(R.raw.crickets);
                Toast.makeText(this, "crickets", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "crickets");
                return true;
            case MENU_BIGDOGS:
                startMediaPlayer(R.raw.bigdog);
                Toast.makeText(this, "big dogs", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "big dogs");
                return true;

            case MENU_LITTLEDOGS:
                startMediaPlayer(R.raw.littledog);
                Toast.makeText(this, "little dogs", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "little dogs");
                return true;

            case MENU_CATS:
                startMediaPlayer(R.raw.meow);
                Toast.makeText(this, "cats", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "cats");
                return true;

            case MENU_CATHISS:
                startMediaPlayer(R.raw.cathiss);
                Toast.makeText(this, "cats", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "cats");
                return true;
            case MENU_LION:
                startMediaPlayer(R.raw.lion);
                Toast.makeText(this, "cats", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "cats");
                return true;
            case MENU_TIGER1:
                startMediaPlayer(R.raw.tigergrowl);
                Toast.makeText(this, "cats", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "cats");
                return true;

            case MENU_CHICKENS:
                startMediaPlayer(R.raw.chicken);
                Toast.makeText(this, "chickens", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "chickens");
                return true;

            case MENU_PIGS:
                startMediaPlayer(R.raw.pig);
                Toast.makeText(this, "pigs", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "pigs");
                return true;

            case MENU_STOP:
                stopMediaPlayer();
                Toast.makeText(this, "Quiet down now!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "stop mediaplayer");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void newActivity(View view) {
        Intent i = new Intent(this, Activity2.class);
        startActivity(i);
    }

    /**
     * Implement popup menu
     *
     * @param view
     */
    public void popupGrover (View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(handleMenu);
        popup.inflate(R.menu.grovermenu);
        popup.show();
    }

    private PopupMenu.OnMenuItemClickListener handleMenu
            =  new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            // Handle pop up menu item clicks here.
            switch (item.getItemId() ) {
                case R.id.kermit:
                    startMediaPlayer(R.raw.kermitthemuppetshow);
                    Log.d(TAG, "kermit");
                    return true;
                case R.id.piggy:
                    startMediaPlayer(R.raw.piggiesorry);
                    Log.d(TAG, "piggy");
                    return true;
                case R.id.fozzie:
                    startMediaPlayer(R.raw.fozziesoembarassed);
                    Log.d(TAG, "fozzie");
                    return true;
                case R.id.cookie:
                    startMediaPlayer(R.raw.cookiemonstercanmehaveacookie);
                    Log.d(TAG, "cookie");
                    return true;

                default:
                    return false;
            } // switch
        }
    };
    /**
     * Stop the mediaplayer then start with sound
     * @param R.raw.name sound file
     *
     **/
    private void startMediaPlayer(int sound) {
        stopMediaPlayer();
        mp = MediaPlayer.create(this, sound);
        mp.setLooping(true);
        mp.start();
    } // startMediaPlayer()

    /**
     * Stop the mediaplayer if it is running
     **/

    public void stopMediaPlayer() {
        if (mp != null && mp.isPlaying()) {
            mp.stop();
            mp.release();
        }

    }
}  //MainActivity
