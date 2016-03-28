package xyz.imyeo.learnc.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public abstract class AbsFragment extends Fragment {

    public static final int FLAG_SINGLETON = 0x0001;

    private static Map<Class<? extends AbsFragment>, AbsFragment> mFragments = new HashMap<>();

    private int mContainerViewId;

    private String mTag;

    private AbsFragmentListener mFragmentListener;

    public interface AbsFragmentListener {
        void onChangeFragment(AbsFragment fragment, Flag flag);
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
                            int containerId, String tag, Bundle args, Flag flags) {
        AbsFragment ins;
        if (flags.singleton) {
            ins = mFragments.get(cls);
            if (ins == null) {
                try {
                    ins = cls.newInstance();
                } catch (java.lang.InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                    ins = null;
                }
                mFragments.put(cls, ins);
            }
        } else {
            try {
                ins = cls.newInstance();
            } catch (java.lang.InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                ins = null;
            }
        }
        if (ins != null) {
            if (ins.isAdded()) return;
            ins.setArguments(args);
            ins.mContainerViewId = containerId;
            ins.mTag = tag;
            manager.beginTransaction().replace(containerId, ins, tag).commit();
        }
    }

    protected void show(Class<? extends AbsFragment> cls, int containerId, String tag,
                        Bundle args, Flag flag) {
        AbsFragment ins;
        if (flag.singleton) {
            ins = mFragments.get(cls);
            if (ins == null) {
                try {
                    ins = cls.newInstance();
                } catch (java.lang.InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                    ins = null;
                }
                mFragments.put(cls, ins);
            }
        } else {
            try {
                ins = cls.newInstance();
            } catch (java.lang.InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                ins = null;
            }
        }
        if (ins != null) {
            if (ins.isAdded()) return;
            ins.setArguments(args);
            ins.mContainerViewId = containerId;
            ins.mTag = tag;
            mFragmentListener.onChangeFragment(ins, flag);
        }
    }

    public abstract String getTitle();

    public int getContainerId() {
        return mContainerViewId;
    }

    public String getFragmentTag() {
        return mTag;
    }

    public static class Flag {

        public final boolean singleton;
        public final boolean addToStack;

        public Flag(Builder builder) {
            this.singleton = builder.singleton;
            this.addToStack = builder.addToStack;
        }

        public static class Builder {

            boolean singleton;
            boolean addToStack;

            public Flag build() {
                return new Flag((this));
            }

            public Builder singleton(boolean v) {
                singleton = v;
                return this;
            }

            public Builder addToStack(boolean v) {
                addToStack = v;
                return this;
            }
        }
    }
}
