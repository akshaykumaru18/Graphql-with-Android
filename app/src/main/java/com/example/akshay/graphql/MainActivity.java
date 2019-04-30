package com.example.akshay.graphql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText personalTodo;
    EditText publicTodo;

    ListView personal_todo;
    ListView public_todo;
    String Title;
    String Description;
    ArrayList<String> titleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUiViews();
        getPosts();
    }

    public void setupUiViews(){
        personalTodo = (EditText)findViewById(R.id.editPersonalTodo);
        publicTodo = (EditText)findViewById(R.id.editPublicTodo);
        personal_todo = (ListView)findViewById(R.id.personal_todo_list);
        public_todo = (ListView)findViewById(R.id.public_todo_list);
    }

    private void getPosts(){

        MyApolloclient.getMyApolloClient().query(
                AllPostsQuery.builder().build()
        ).enqueue(new ApolloCall.Callback<AllPostsQuery.Data>() {
            @Override
            public void onResponse(@NotNull final Response<AllPostsQuery.Data> response) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        titleList = new ArrayList<String>();
                        for(int i = 0; i < response.data().allPosts().size(); i++){
                            Log.i("MainActivity","On Response Title : " + response.data().allPosts().get(i).title());
                            Title = response.data().allPosts.get(i).title();
                            titleList.add(Title);
                        }
                        adapter();

                    }
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });
    }
    public void adapter(){

            PersonalTodoAdapter personalTodoAdapter = new PersonalTodoAdapter(getApplicationContext(),titleList);
            personal_todo.setAdapter(personalTodoAdapter);
            personal_todo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(),titleList.get(position),Toast.LENGTH_SHORT).show();
                }
            });

            PublicTodoAdapter publicTodoAdapter = new PublicTodoAdapter(getApplicationContext(),titleList,titleList);
            public_todo.setAdapter(publicTodoAdapter);
            setDynamicHeight(personal_todo);
            setDynamicHeight(public_todo);


    }

    public static void setDynamicHeight(ListView listView) {
        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }

        int height = 0;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();

        layoutParams.height = height + (listView.getDividerHeight() * (adapter.getCount() - 1));

        listView.setLayoutParams(layoutParams);

        listView.requestLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todo_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.all:
                Toast.makeText(getApplicationContext(),"All",Toast.LENGTH_SHORT).show();
                break;
            case R.id.active:
                Toast.makeText(getApplicationContext(),"Active",Toast.LENGTH_SHORT).show();
                break;
            case R.id.completed_item:
                Toast.makeText(getApplicationContext(),"Completed",Toast.LENGTH_SHORT).show();
                break;
            case R.id.clear_completed:
                Toast.makeText(getApplicationContext(),"Clear Completed",Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void publishTodo(View view) {
        final String todoTitle = personalTodo.getText().toString();
        MyApolloclient.getMyApolloClient().mutate(NewPostMutation.builder()
        .title(todoTitle)
         .description("done")
        .build()).enqueue(new ApolloCall.Callback<NewPostMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<NewPostMutation.Data> response) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
                            getPosts();
                        }
                    });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }


}


