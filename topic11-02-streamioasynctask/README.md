# Stream I/O via an AsyncTask
Uses AsyncTask for stream I/O from /sdcard


Note if you have not followed the setup instructions you will see:
```bash
Error file not found: /sdcard/ascii.txt: open failed: ENOENT (No such file or directory)
```

## Logcat
```
adb logcat -s MTHREAD
```
## Setup

This code is an example of doing I/O in a background thread using AsyncTask  remember AsyncTask is good for fairly short running task anything really long running it is better to consider implementing your own Java threads.

The I/O done here is to read a file from the Android SD card
path: /sdcard/

To run this code:
Run with a small file, create a small file:
1. shell into the device  adb shell
2. ls / >  /sdcard/ascii.txt
3. run the app
4. watch the log adb logcat -s MTHREAD

Note that the I/O will probably finish before you can page through any of it

Run with a large file, create a large file:
1. shell into the device  adb shell
2. ls -lR / >  /sdcard/ascii.txt
3. run the app
4. watch the log adb logcat -s MTHREAD

Note that the I/O will probably continue in the background as you are paging through the file
