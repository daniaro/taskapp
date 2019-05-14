package com.daniar.taskapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daniar.taskapp.ui.main.SectionsPagerAdapter;

public class OnBoardActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private OnBoardAdapter onBoardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);

        viewPager = findViewById(R.id.viewPager);
        onBoardAdapter = new OnBoardAdapter(getSupportFragmentManager());
        viewPager.setAdapter(onBoardAdapter);
    }

    public static class MyFragment extends Fragment {

        public MyFragment() {
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_on_board, container, false);
            TextView textView = view.findViewById(R.id.textView);
            ImageView imageView = view.findViewById(R.id.imageView);
            Button button = view.findViewById(R.id.button);
            int pos = getArguments().getInt("pos");
            switch (pos) {
                case 0:
                    imageView.setImageResource(R.drawable.smile1);
                    textView.setText("Привет");
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.smile2);
                    textView.setText("Как дела?");
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.smile3);
                    textView.setText("Что делаешь?");
                    break;
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), MainActivity.class));
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
                    sharedPreferences.edit().putBoolean("isShown", true).apply();
                    getActivity().finish();
                }
            });
            return view;
        }
    }

    private class OnBoardAdapter extends FragmentPagerAdapter {

        public OnBoardAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            MyFragment myFragment = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("pos", pos);
            myFragment.setArguments(bundle);
            return myFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
