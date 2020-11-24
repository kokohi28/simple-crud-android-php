package com.divt.todos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final String TAG = TodoAdapter.class.getSimpleName();

  private Context mContext;
  private List<Todo> mTodo;

  public TodoAdapter(Context context, List<Todo> todo) {
    mContext = context;
    mTodo = todo;
  }

  @Override
  public int getItemCount() {
    return mTodo.size();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();

    final View viewItem = LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);

    return new ItemTodo(viewItem);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    final Todo todo = mTodo.get(position);

    ItemTodo vh = (ItemTodo) holder;

    vh.tvName.setText(todo.getItemName());

    vh.rlItem.setTag(position);
  }

  public void refresh() {
    notifyDataSetChanged();
  }

  private class ItemTodo extends RecyclerView.ViewHolder {
    private RelativeLayout rlItem;
    private TextView tvName;
    private TextView tvDate;

    public ItemTodo(View view) {
      super(view);

      rlItem = view.findViewById(R.id.v_touch);
      tvName = view.findViewById(R.id.tv_name);
      tvDate = view.findViewById(R.id.tv_date);
    }
  }
}
