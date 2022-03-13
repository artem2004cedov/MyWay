package com.example.myway.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
    private int pos;
    private String task;

    public FragmentAdapter(@NonNull FragmentManager fm, int pos, String task) {
        super(fm);
        this.pos = pos;
        this.task = task;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // создаем новые фрагменты по позиции
        switch (position) {
            case 0:
                EatFragment eatFragment = new EatFragment();
                Bundle args = new Bundle();
                args.putInt("num", pos);
                args.putString("task", task);
                eatFragment.setArguments(args);
                return eatFragment;
            case 1:
                NecessaryFragment necessaryFragment = new NecessaryFragment();
                Bundle bun = new Bundle();
                bun.putString("task", task);
                bun.putInt("num", pos);
                necessaryFragment.setArguments(bun);
                return necessaryFragment;

            case 2:
                NoFragment noFragment = new NoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("task", task);
                bundle.putInt("num", pos);
                noFragment.setArguments(bundle);
                return noFragment;
            default:
                return new EatFragment();
        }
    }

    // количетво фрагментов
    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // изначальное значение
        String title = null;
        if (position == 0) {
            // присваевания названия
            title = "Имеется";
        }
        if (position == 1) {
            title = "Нужно";
        }
        if (position == 2) {
            title = "Отсутствует";
        }

        return title;
    }
}
