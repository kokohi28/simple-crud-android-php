package com.divt.todos;

import android.text.TextUtils;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class Todo {
  private int id;
  private String itemName;
  private boolean done;
  private long createdAt;
  private long updatedAt;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }

  public long getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(long updatedAt) {
    this.updatedAt = updatedAt;
  }

  public static Todo getFromJson(JSONObject json) {
    try {
      Todo todo = new Todo();
      if (json.has("id")) todo.id = json.getInt("id");
      if (json.has("name")) todo.itemName = json.getString("name");
      if (json.has("done")) todo.done = json.getInt("done") > 0;
//      if (json.has("created_at")) todo.createdAt = json.getString("created_at");
//      if (json.has("updated_at")) todo.updatedAt = json.getString("created_at");

      return todo;
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return null;
  }
}
