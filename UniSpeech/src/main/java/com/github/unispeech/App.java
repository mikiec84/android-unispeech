package com.github.unispeech;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import com.nuance.nmdp.speechkit.SpeechKit;

/**
 * Created by javier.romero on 2/5/14.
 */
public class App extends Application {

    private static final byte[] NUANCE_APPLICATION_KEY = {(byte)0xbb, (byte)0xe5, (byte)0xe0, (byte)0x44, (byte)0x6e, (byte)0xa9, (byte)0xfc, (byte)0x03, (byte)0x67, (byte)0x21, (byte)0x8c, (byte)0xd0, (byte)0x83, (byte)0x50, (byte)0x50, (byte)0x03, (byte)0x46, (byte)0x22, (byte)0x0e, (byte)0xa6, (byte)0x07, (byte)0xf0, (byte)0xc4, (byte)0x30, (byte)0xca, (byte)0xbb, (byte)0x9b, (byte)0xc1, (byte)0x66, (byte)0x58, (byte)0xa6, (byte)0x21, (byte)0x3d, (byte)0x86, (byte)0x65, (byte)0xab, (byte)0x2f, (byte)0x62, (byte)0xaf, (byte)0x96, (byte)0x83, (byte)0x3b, (byte)0x66, (byte)0x06, (byte)0x8c, (byte)0xb6, (byte)0x09, (byte)0xd2, (byte)0xd9, (byte)0x18, (byte)0x79, (byte)0xe0, (byte)0x08, (byte)0x3d, (byte)0x11, (byte)0xd2, (byte)0xf8, (byte)0xac, (byte)0x93, (byte)0x5e, (byte)0x04, (byte)0x39, (byte)0x80, (byte)0x41};

    private static final String NUANCE_API_ID = "NMDPTRIAL_StevenChanMD20141009153442";
    private static final String NUANCE_HOST = "sandbox.nmdp.nuancemobility.net";
    public static final String GOOGLE_TRANSLATE_API_KEY = "AIzaSyCt2QCG2uBee_7eVL-4ewCffNvit4NtOW4";

    private SpeechKit mSpeechKit;

    @Override
    public void onCreate() {
        super.onCreate();

        mSpeechKit = SpeechKit.initialize(
                this.getApplicationContext(),
                NUANCE_API_ID,
                NUANCE_HOST,
                443, false, NUANCE_APPLICATION_KEY);

        mSpeechKit.connect();
    }

    public SpeechKit getSpeechKit() {
        return mSpeechKit;
    }

    public static boolean runningOnGoogleGlass() {
        Log.v("@@@", "DEVICE: " + Build.DEVICE);
        Log.v("@@@", "MODEL: " + Build.MODEL);
        Log.v("@@@", "PRODUCT: " + Build.PRODUCT);
        return Build.DEVICE.contains("glass");
    }
}
