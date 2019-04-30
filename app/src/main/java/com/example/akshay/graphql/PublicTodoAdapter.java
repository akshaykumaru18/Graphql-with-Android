package com.example.akshay.graphql;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class PublicTodoAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private TextView person;
    private TextView todoTitle;
    private ArrayList<String> todoList;
    private ArrayList<String> personList;

    public PublicTodoAdapter(Context context,ArrayList<String> person, ArrayList<String> todoList){
        this.mContext = context;
        this.personList = person;
        this.todoList = todoList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return todoList.size();
    }

    @Override
    public Object getItem(int position) {
        return todoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.public_todo_item_card, null);
        }

        person = (TextView)convertView.findViewById(R.id.tvPerson);
        todoTitle = (TextView) convertView.findViewById(R.id.tvTodo);
        person.setText("@"+personList.get(position));
        todoTitle.setText(todoList.get(position));

        return convertView;
    }
}
