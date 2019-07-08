package test.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ChildViewHolder extends RecyclerView.ViewHolder {
    private TextView txt_title;

    public ChildViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_title = itemView.findViewById(R.id.txt_title_child);
    }

    public void onBind(Context context, ChildItem model) {
        txt_title.setText(model.getTitle());
    }
}
