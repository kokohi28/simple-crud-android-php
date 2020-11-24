package com.divt.todos;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = MainActivity.class.getSimpleName();

  private static final String END_POINT = "http://192.168.1.98:8000";

  private RecyclerView mRecyclerView;
  private TodoAdapter mAdapter = null;
  private List<Todo> mTodo;

  private static Handler mHandler = new Handler();

  public static Handler getHandler() {
    return mHandler;
  }

  public interface AdapterAct {
    void markDone(int id);
    void delete(int id);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mTodo = new ArrayList<>();

    mRecyclerView = findViewById(R.id.list_todo);

    // width recyclerView tidak berubah-ubah
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    mAdapter = new TodoAdapter(this, mTodo, new AdapterAct() {
      @Override
      public void markDone(int id) {
        markTodoDone(MainActivity.this, id);
      }

      @Override
      public void delete(int id) {
        deleteTodo(MainActivity.this, id);
      }
    });
    mRecyclerView.setAdapter(mAdapter);

    EditText etTodo = findViewById(R.id.et_todo);
    Button btnAdd = findViewById(R.id.btn_add_todo);
    btnAdd.setOnClickListener(v -> {
      String todo = etTodo.getText().toString();
      addTodo(MainActivity.this, todo);
    });

    getHandler().postDelayed(() -> getTodo(MainActivity.this), 500);
  }

  private void pushLocalTodo(Todo todo) {
    mTodo.add(todo);
    mAdapter.refresh();
  }

  private void getTodo(
      @NonNull MainActivity ctx
  ) {
    mTodo.clear();

    RequestQueue queue = Volley.newRequestQueue(ctx);
    String url = END_POINT + "/getTodos";
    queue.getCache().clear();

    StringRequest stringReq = new StringRequest(Request.Method.GET, url,
        response -> {
          Log.d(TAG, "getTodo Response: " + response);
          try {
            JSONObject respJSON = new JSONObject(response);
            JSONArray dataJSON = respJSON.getJSONArray("data");
            for (int i = 0; i < dataJSON.length(); i++) {
              // Log.d(TAG, "" + dataJSON.getJSONObject(i).toString());
              Todo todo = Todo.getFromJson(dataJSON.getJSONObject(i));
              ctx.pushLocalTodo(todo);
            }
          } catch (JSONException e) {
            e.printStackTrace();
          }
        },
        error -> Log.e(TAG, "Volley Error : " + error.getMessage())
    ){
      @Override
      public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return headers;
      }
    };

    queue.add(stringReq);
  }

  private void addTodo(
      @NonNull MainActivity ctx,
      String itemName
  ) {
    RequestQueue queue = Volley.newRequestQueue(ctx);
    String url = END_POINT + "/addTodo";

    JSONObject obj = new JSONObject();
    try {
      obj.put("name", itemName);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    StringRequest stringReq = new StringRequest(Request.Method.POST, url,
        response -> {
          Log.d(TAG, "addTodo Response: " + response);
          try {
            JSONObject respJSON = new JSONObject(response);
            if (respJSON.has("code")) {
              int code = respJSON.getInt("code");
              if (code == 0) {
                ctx.getTodo(ctx);
              }
            }
          } catch (JSONException e) {
            e.printStackTrace();
          }
        },
        error -> Log.e(TAG, "Volley Error : " + error.getMessage())
    ){
      @Override
      public byte[] getBody() {
        return obj.toString().getBytes();
      }

      @Override
      public String getBodyContentType() {
        return "application/json";
      }

      @Override
      public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return headers;
      }
    };

    queue.add(stringReq);
  }

  private void markTodoDone(
      @NonNull MainActivity ctx,
      int id
  ) {
    RequestQueue queue = Volley.newRequestQueue(ctx);
    String url = END_POINT + "/markTodo";

    JSONObject obj = new JSONObject();
    try {
      obj.put("id", id);
      obj.put("done", 1);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    StringRequest stringReq = new StringRequest(Request.Method.POST, url,
        response -> {
          Log.d(TAG, "markTodo Response: " + response);
          try {
            JSONObject respJSON = new JSONObject(response);
            if (respJSON.has("code")) {
              int code = respJSON.getInt("code");
              if (code == 0) {
                ctx.getTodo(ctx);
              }
            }
          } catch (JSONException e) {
            e.printStackTrace();
          }
        },
        error -> Log.e(TAG, "Volley Error : " + error.getMessage())
    ){
      @Override
      public byte[] getBody() {
        return obj.toString().getBytes();
      }

      @Override
      public String getBodyContentType() {
        return "application/json";
      }

      @Override
      public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return headers;
      }
    };

    queue.add(stringReq);
  }

  private void deleteTodo(
      @NonNull MainActivity ctx,
      int id
  ) {
    RequestQueue queue = Volley.newRequestQueue(ctx);
    String url = END_POINT + "/deleteTodo";

    JSONObject obj = new JSONObject();
    try {
      obj.put("id", id);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    StringRequest stringReq = new StringRequest(Request.Method.POST, url,
        response -> {
          Log.d(TAG, "markTodo Response: " + response);
          try {
            JSONObject respJSON = new JSONObject(response);
            if (respJSON.has("code")) {
              int code = respJSON.getInt("code");
              if (code == 0) {
                ctx.getTodo(ctx);
              }
            }
          } catch (JSONException e) {
            e.printStackTrace();
          }
        },
        error -> Log.e(TAG, "Volley Error : " + error.getMessage())
    ){
      @Override
      public byte[] getBody() {
        return obj.toString().getBytes();
      }

      @Override
      public String getBodyContentType() {
        return "application/json";
      }

      @Override
      public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return headers;
      }
    };

    queue.add(stringReq);
  }
}