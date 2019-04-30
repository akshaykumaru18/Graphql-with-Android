package com.example.akshay.graphql;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PersonalTodoAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater layoutInflater;
    private TextView title;
    private Button cancelButton;

    private ArrayList<String> todoList;



    public PersonalTodoAdapter(Context context, ArrayList<String> todoList){
        this.mContext = context;
        this.todoList = todoList;
        layoutInflater = LayoutInflater.from(context);
    }




    @Override
    public int getCount() {
        // return subjectArray.length;
        return todoList.size();
    }

    @Override
    public Object getItem(int position) {
        // return subjectArray[position];
        return todoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.personal_todo_item_card, null);
        }
        title = (TextView) convertView.findViewById(R.id.tvTodo);
        cancelButton = (Button)convertView.findViewById(R.id.cancel_todo);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

            }
        });
        title.setText(todoList.get(position));
        return convertView;

    }


}
