package com.divt.todos;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final String TAG = TodoAdapter.class.getSimpleName();

  private Context mContext;
  private List<Todo> mTodo;
  private MainActivity.AdapterAct mBridge;

  public TodoAdapter(Context context, List<Todo> todo, MainActivity.AdapterAct bridge) {
    mContext = context;
    mTodo = todo;
    mBridge = bridge;
  }

  @Override
  public int getItemCount() {
    return mTodo.size();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();

    final View viewItem = LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    ItemTodo holder = new ItemTodo(viewItem);
    holder.ibDelete.setOnClickListener(v -> mBridge.delete((int )v.getTag()));
    holder.ibDone.setOnClickListener(v -> mBridge.markDone((int )v.getTag()));

    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    final Todo todo = mTodo.get(position);

    ItemTodo vh = (ItemTodo) holder;

    vh.tvName.setText(todo.getItemName());
    if (todo.isDone()) {
      vh.ibDone.setVisibility(View.GONE);
      vh.tvName.setPaintFlags(vh.tvName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    } else {
      vh.ibDone.setVisibility(View.VISIBLE);
    }

    vh.ibDone.setTag(todo.getId());
    vh.ibDelete.setTag(todo.getId());
    vh.rlItem.setTag(position);
  }

  public void refresh() {
    notifyDataSetChanged();
  }

  private class ItemTodo extends RecyclerView.ViewHolder {
    private RelativeLayout rlItem;
    private TextView tvName;
    private TextView tvDate;
    private ImageButton ibDone;
    private ImageButton ibDelete;

    public ItemTodo(View view) {
      super(view);

      rlItem = view.findViewById(R.id.v_touch);
      tvName = view.findViewById(R.id.tv_name);
      tvDate = view.findViewById(R.id.tv_date);

      ibDone = view.findViewById(R.id.ib_done);
      ibDelete = view.findViewById(R.id.ib_delete);
    }
  }
}
