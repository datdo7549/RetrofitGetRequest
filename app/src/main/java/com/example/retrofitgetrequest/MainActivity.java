package com.example.retrofitgetrequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.text_view);

        Gson gson=new GsonBuilder().serializeNulls().create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        jsonPlaceHolderApi=retrofit.create(JsonPlaceHolderApi.class);
        //getPost();
        //getComment();
        //createPost();
        updatePost();

    }

    private void updatePost() {
        Post post=new Post(23,"Do Thanh Dat","Dat Dep Trai");
        Call<Post> call=jsonPlaceHolderApi.patchPost(5,post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful())
                {
                    textView.setText("Code "+response.code());
                    return;
                }
                Post postRespond=response.body();
                String content="";
                content+="Code: "+response.code()+"\n";
                content+="UserId: "+postRespond.getUserId()+"\n";
                content+="Title: "+postRespond.getTitle()+"\n";
                content+="Body: "+postRespond.getText()+"\n\n";
                textView.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void createPost() {
        Post post=new Post(23,"Dat Dep Trai Lam","Dat Rat La Dep Trai Nha Moi Nguoi");
        Call<Post> call=jsonPlaceHolderApi.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful())
                {
                    textView.setText("Code "+response.code());
                    return;
                }
                Post postRespond=response.body();
                String content="";
                content+="Code: "+response.code()+"\n";
                content+="ID: "+postRespond.getId()+"\n";
                content+="UserId: "+postRespond.getUserId()+"\n";
                content+="Title: "+postRespond.getTitle()+"\n";
                content+="Body: "+postRespond.getText()+"\n\n";
                textView.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void getComment() {
        Call<List<Comments>> call=jsonPlaceHolderApi.getComments(3);
        call.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                if(!response.isSuccessful())
                {
                    textView.setText("Code "+response.code());
                }
                List<Comments> comments=response.body();
                for(Comments comment:comments)
                {
                    String content="";
                    content+="ID: "+comment.getId()+"\n";
                    content+="Post ID: "+comment.getPostId()+"\n";
                    content+="Name: "+comment.getName()+"\n";
                    content+="Email: "+comment.getEmail()+"\n";
                    content+="Body: "+comment.getText()+"\n\n";
                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });

    }

    private void getPost() {
        Call<List<Post>> call=jsonPlaceHolderApi.getPost(new Integer[]{2,3,6},"id","desc");
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful())
                {
                    textView.setText("code "+response.code());
                }
                List<Post> posts=response.body();
                for(Post post:posts)
                {
                    String content="";
                    content+="UserId: "+post.getUserId()+"\n";
                    content+="Id: "+post.getId()+"\n";
                    content+="Title: "+post.getTitle()+"\n";
                    content+="Body: "+post.getText()+"\n";
                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}
