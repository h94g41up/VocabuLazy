package com.wishcan.www.vocabulazy.service;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by allencheng07 on 2016/1/27.
 */
public class WCTextToSpeech extends UtteranceProgressListener
        implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {

    public static final String TAG = WCTextToSpeech.class.getSimpleName();

    public static final String UTTERANCE_ID = "utterance-id";

    private Context mContext;
    private TextToSpeech wTextToSpeech;
    private OnUtteranceStatusListener wOnUtteranceStatusListener;

    private boolean ttsEngineInit;
    private String currentUtterance;
    private HashMap<String, String> utteranceParams;

    public WCTextToSpeech (Context context, OnUtteranceStatusListener listener) {
        mContext = context;
        wTextToSpeech = new TextToSpeech(context, this);
        wOnUtteranceStatusListener = listener;
        ttsEngineInit = false;
        setTTSListener();
        setTTSIDParams(UTTERANCE_ID);
        currentUtterance = "";
    }

    void speak(String text) {

        if (!ttsEngineInit)
            return;

//        if (!currentUtterance.equals(""))
//            return;

//        Log.d(TAG, "speak: " + text);
        currentUtterance = text;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//          Log.d(TAG, "sdk >= 21");
            wTextToSpeech.setSpeechRate(0.8f);
            wTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, text);
        } else {
//          Log.d(TAG, "sdk < 21");
            setTTSIDParams(text);
            wTextToSpeech.setSpeechRate(0.8f);
            wTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, utteranceParams);
        }

    }

    void pause() {
        stop();
    }

    void stop() {
        wTextToSpeech.stop();
    }

    void shutdown() {
        if (!wTextToSpeech.isSpeaking())
            wTextToSpeech.shutdown();
    }

    void setLanguage(Locale locale) {
        if (ttsEngineInit)
            wTextToSpeech.setLanguage(locale);
    }

    void setTTSListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wTextToSpeech.setOnUtteranceProgressListener(this);
        } else {
            wTextToSpeech.setOnUtteranceCompletedListener(this);
        }
    }

    void setTTSIDParams(String utteranceid) {
        utteranceParams = new HashMap<>();
        utteranceParams.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceid);
    }

    @Override
    public void onInit(int status) {
//        Log.d(TAG, "onInit");
        if(status == TextToSpeech.SUCCESS) {
            ttsEngineInit = true;
//            Log.d(TAG, "engine initialized");
//            Toast.makeText(mContext, "engine initialized", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart(String utteranceId) {
//        Log.d(TAG, "onStart: " + utteranceId);
    }

    @Override
    public void onDone(String utteranceId) {
//        Log.d(TAG, "onDone: " + utteranceId);
        if (utteranceId.equals(currentUtterance)) {
//            Log.d(TAG, "utterances match");
            currentUtterance = "";
            wOnUtteranceStatusListener.onUtteranceCompleted();
        } else {
//            Log.d(TAG, "error: utterance dosent match!");
        }
    }

    @Override
    public void onError(String utteranceId) {
//        Log.d(TAG, "onError: " + utteranceId);
    }

    @Override
    public void onStop(String utteranceId, boolean interrupted) {
        super.onStop(utteranceId, interrupted);
//        Log.d(TAG, "onStop, interrupted: " + utteranceId);
    }

    @Override
    public void onUtteranceCompleted(String utteranceId) {
//        Log.d(TAG, "onUtteranceCompleted: " + utteranceId);
        if (utteranceId.equals(currentUtterance)) {
            currentUtterance = "";
            wOnUtteranceStatusListener.onUtteranceCompleted();
        } else {
//            Log.d(TAG, "error: utterance dosent match!");
        }
    }

    protected interface OnUtteranceStatusListener {
        void onUtteranceCompleted();
    }
}
