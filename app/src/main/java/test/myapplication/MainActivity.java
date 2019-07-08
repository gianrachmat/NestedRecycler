package test.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<ParentModel> dataParent = new ArrayList<>();
    Adapter<ParentModel, ParentViewHolder> adapter = new Adapter<ParentModel, ParentViewHolder>
            (R.layout.item_parent, ParentViewHolder.class, dataParent) {
        @Override
        void bindView(ParentViewHolder holder, final ParentModel model, int pos) {
            holder.onBind(MainActivity.this, model);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Click on " + model.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
    RecyclerView rv_parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_parent = findViewById(R.id.rv_parent);

        for (int i = 1; i <= 10; i++) {
            dataParent.add(new ParentModel("Judul ke-" + i));
        }

        setupView();
    }

    private void setupView() {
        rv_parent.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        rv_parent.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
