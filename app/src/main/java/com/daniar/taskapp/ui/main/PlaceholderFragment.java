package com.daniar.taskapp.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.daniar.taskapp.App;
import com.daniar.taskapp.R;
import com.daniar.taskapp.TaskActivity;
import com.daniar.taskapp.TaskAdapter;
import com.daniar.taskapp.room.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> list;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        initList();
        return root;
    }

    private void initList() {
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TaskAdapter(list);
        recyclerView.setAdapter(adapter);
        App.getInstance().getDatabase().taskDao().getAll().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                list.clear();
                list.addAll(tasks);
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setClickListener(new TaskAdapter.ClickListener() {
            @Override
            public void onClick(int pos) {
                Task task = list.get(pos);
                Intent intent = new Intent(getContext(), TaskActivity.class);
                intent.putExtra("task", task);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int pos) {
                Task task = list.get(pos);
                showAlert(task);
            }
        });
    }

    private void showAlert(final Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Вы хотите удалить " + task.getTitle() + " ?");
        builder.setNegativeButton("Отмена", null);
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                App.getInstance().getDatabase().taskDao().delete(task);
            }
        });
        builder.create().show();
    }
}