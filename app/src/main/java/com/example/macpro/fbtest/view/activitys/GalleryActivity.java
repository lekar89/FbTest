package com.example.macpro.fbtest.view.activitys;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.macpro.fbtest.R;
import com.example.macpro.fbtest.view.fragments.IBaseFragment;
import com.example.macpro.fbtest.view.fragments.listFragment.ListFragment;
import com.example.macpro.fbtest.view.fragments.redactProfileFragment.RedactProfileFragment;

public class GalleryActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private DialogFragment mDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        mFragmentManager = getSupportFragmentManager();

        mDialogFragment = new RedactProfileFragment();

        findViewById(R.id.fab).setOnClickListener(view -> {

            mDialogFragment.show(mFragmentManager, "dlg1");
        });

        ListFragment listFragment = ListFragment.newInstance();
        navigator(listFragment, listFragment.getCurrentTag());

    }

    public void onBackPressed() {
        IBaseFragment iBaseFragment = null;
        for (Fragment f : mFragmentManager.getFragments()) {
            if (f instanceof IBaseFragment) {
                iBaseFragment = (IBaseFragment) f;
                break;
            }
        }
        if (iBaseFragment != null) {
            iBaseFragment.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    public void navigator(Fragment fragment, String TAG) {

        Fragment f = mFragmentManager.findFragmentByTag(TAG);

        if (!(f != null && f.isVisible())) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation,
                    R.anim.pop_enter_animation, R.anim.pop_exit_animation);
            fragmentTransaction
                    .add(fragment, ((IBaseFragment) fragment).getCurrentTag())
                    .replace(R.id.container_fragment, fragment)
                    .commit();
        }
    }

    public void navigatorBackPressed(Fragment fragment) {

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.pop_enter_animation, R.anim.pop_exit_animation,
                R.anim.enter_animation, R.anim.exit_animation);
        fragmentTransaction
                .remove(fragment)
                .replace(R.id.container_fragment, ListFragment.newInstance())
                .commit();
    }
}
