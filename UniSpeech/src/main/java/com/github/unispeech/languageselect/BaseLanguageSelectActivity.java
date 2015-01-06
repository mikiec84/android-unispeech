package com.github.unispeech.languageselect;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.unispeech.R;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

public abstract class BaseLanguageSelectActivity extends Activity {

    private ListView mListView;

    private GestureDetector mGestureDetector;

    protected abstract String getPrompt();

    protected abstract void onLanguageClicked(SupportedSttLanguage language);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_select);

        ((TextView) findViewById(R.id.lbl_prompt)).setText(getPrompt());

        mGestureDetector = createGestureDetector(this);
        mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(new LanguagesAdapter(this, SupportedSttLanguage.values()));
        mListView.setOnItemClickListener(mOnItemClickListener);
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SupportedSttLanguage language = ((LanguagesAdapter) mListView.getAdapter()).getItem(position);
            onLanguageClicked(language);
        }
    };

    private static final class LanguagesAdapter extends ArrayAdapter<SupportedSttLanguage> {

        private final LayoutInflater mLayoutInflater;

        public LanguagesAdapter(Context context, SupportedSttLanguage[] languages) {
            super(context, R.layout.item_language, languages);
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.item_language, null);
                convertView.setTag(new ViewHolder(convertView));
            }

            SupportedSttLanguage language = getItem(position);

            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.language.setText(language.getLabel());
            if (!TextUtils.isEmpty(language.getSpecifier())) {
                viewHolder.specifier.setText("(" +language.getSpecifier() + ")");
                viewHolder.specifier.setVisibility(View.VISIBLE);
            } else {
                viewHolder.specifier.setVisibility(View.GONE);
            }

            return convertView;
        }
    }

    public static final class ViewHolder {
        public final TextView language;
        public final TextView specifier;

        public ViewHolder(View view) {
            language = (TextView) view.findViewById(R.id.lbl_language);
            specifier = (TextView) view.findViewById(R.id.lbl_language_specifier);
        }
    }

    private GestureDetector createGestureDetector(Context context) {
        GestureDetector gestureDetector = new GestureDetector(context);
        //Create a base listener for generic gestures
        gestureDetector.setBaseListener( new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
                if (gesture == Gesture.SWIPE_RIGHT) {
                    // do something on right (forward) swipe
                    mListView.setSelection(mListView.getSelectedItemPosition()+1);

                    return true;
                } else if (gesture == Gesture.SWIPE_LEFT) {
                    // do something on left (backwards) swipe
                    mListView.setSelection(mListView.getSelectedItemPosition()-1);
                    return true;
                }
                return false;
            }
        });
        gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
            @Override
            public void onFingerCountChanged(int previousCount, int currentCount) {
                // do something on finger count changes
            }
        });
        /*gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
            @Override
            public boolean onScroll(float displacement, float delta, float velocity) {
                // do something on scrolling
            }
        });*/
        return gestureDetector;
    }

    /*
     * Send generic motion events to the gesture detector
     */
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (mGestureDetector != null) {
            return mGestureDetector.onMotionEvent(event);
        }
        return false;
    }
}