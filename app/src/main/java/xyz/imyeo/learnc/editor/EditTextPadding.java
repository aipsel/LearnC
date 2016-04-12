package xyz.imyeo.learnc.editor;

import android.content.Context;

import xyz.imyeo.learnc.core.PreferenceHelper;
import xyz.imyeo.learnc.core.Utils;

public class EditTextPadding {

    public static int getPaddingWithoutLineNumbers(Context context) {
        return (int) Utils.convertDpToPixel(5, context);
    }

    public static int getPaddingBottom(Context context) {
        boolean useAccessoryView = PreferenceHelper.getUseAccessoryView(context);
        return (int) Utils.convertDpToPixel(useAccessoryView ? 50 : 0, context);
    }

    public static int getPaddingWithLineNumbers(Context context, float fontSize) {
        return (int) Utils.convertDpToPixel(fontSize * 2f, context);
    }

    public static int getPaddingTop(Context context) {
        return getPaddingWithoutLineNumbers(context);
    }
}

