package test.myapplication.ui.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import test.myapplication.R;
import test.myapplication.support.model.ChildModel;
import test.myapplication.support.model.ParentModel;
import test.myapplication.support.utils.Adapter;

public class ParentViewHolder extends RecyclerView.ViewHolder {
    private ArrayList<ChildModel> dataChild = new ArrayList<>();
    private TextView txt_title;
    private RecyclerView rv_child;
    private Adapter<ChildModel, ChildViewHolder> adapter = new Adapter<ChildModel, ChildViewHolder>
            (R.layout.item_child, ChildViewHolder.class, dataChild) {
        @Override
        public void bindView(ChildViewHolder holder, final ChildModel model, int pos) {
            final Context ctx = holder.itemView.getContext();
            holder.onBind(ctx, model);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ctx, "Click on " + model.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    public ParentViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_title = itemView.findViewById(R.id.txt_title);
        rv_child = itemView.findViewById(R.id.rv_child);
        for (int i = 1; i <= 5; i++) {
            dataChild.add(new ChildModel("Child " + i));
        }
    }

    public void onBind(Context context, ParentModel model) {
        txt_title.setText(model.getTitle());
        rv_child.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rv_child.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
