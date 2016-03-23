package xyz.imyeo.learnc.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public abstract class AbsFragment extends Fragment {

    private static Map<Class<? extends AbsFragment>, AbsFragment> mFragments = new HashMap<>();

    private int mContainerViewId;

    private String mTag;

    private AbsFragmentListener mFragmentListener;

    public interface AbsFragmentListener {
        void onChangeFragment(AbsFragment fragment, int flag);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AbsFragmentListener) {
            mFragmentListener = (AbsFragmentListener) activity;
        } else {
            throw new RuntimeException(String.format("Activity %s may not implement AbsFragmentListener.",
                    activity.getComponentName().getShortClassName()));
        }
    }

    public static void show(FragmentManager manager, Class<? extends AbsFragment> cls,
                            int containerId, String tag, Bundle args) {
        AbsFragment ins = mFragments.get(cls);
        if (ins == null) {
            try {
                ins = cls.newInstance();
            } catch (java.lang.InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                ins = null;
            }
            mFragments.put(cls, ins);
        }
        if (ins != null) {
            ins.setArguments(args);
            ins.mContainerViewId = containerId;
            ins.mTag = tag;
            manager.beginTransaction().replace(containerId, ins, tag).commit();
        }
    }

    protected void show(Class<? extends AbsFragment> cls, int containerId, String tag, Bundle args) {
        AbsFragment ins = mFragments.get(cls);
        if (ins == null) {
            throw new RuntimeException("Fragment haven't been instantiated, try the static version");
        }
        ins.setArguments(args);
        ins.mContainerViewId = containerId;
        ins.mTag = tag;
        mFragmentListener.onChangeFragment(ins, 0);
    }

    public int getContainerId() {
        return mContainerViewId;
    }

    public String getFragmentTag() {
        return mTag;
    }

}
