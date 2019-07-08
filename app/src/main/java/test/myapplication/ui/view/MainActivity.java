package test.myapplication.ui.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.co.nlab.nframework.base.ViewState;
import test.myapplication.R;
import test.myapplication.module.OneModule;
import test.myapplication.support.model.ParentModel;
import test.myapplication.support.utils.Adapter;
import test.myapplication.ui.viewholder.ParentViewHolder;

public class MainActivity extends AppCompatActivity implements ViewState {
    private static final String TAG_GET_DATA = "";
    OneModule module = new OneModule(this, this);
    ArrayList<ParentModel> dataParent = new ArrayList<>();
    Adapter<ParentModel, ParentViewHolder> adapter = new Adapter<ParentModel, ParentViewHolder>
            (R.layout.item_parent, ParentViewHolder.class, dataParent) {
        @Override
        public void bindView(ParentViewHolder holder, final ParentModel model, int pos) {
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
    SwipeRefreshLayout swipe_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_parent = findViewById(R.id.rv_parent);
        swipe_main = findViewById(R.id.swipe_main);

        setupView();
    }

    @Override
    public void onFailure(Object data, String tag, String message) {

    }

    @Override
    public void onLoading(final boolean status, String tag, String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipe_main.setRefreshing(status);
            }
        });
    }

    @Override
    public void onSuccess(final Object data, String tag, String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (data instanceof JSONObject) {
                    try {
                        List<ParentModel> parentList = module.toObjectList(((JSONObject) data).getJSONArray("data"), ParentModel.class);
                        dataParent.clear();
                        dataParent.addAll(parentList);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onUpdate(Object data, String tag, String message) {

    }

    @Override
    public void setupView() {
        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_parent.setLayoutManager(llm);
        rv_parent.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        module.doRequest("5d22e2a6b6c40c3ba03c605a", TAG_GET_DATA);
        swipe_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                module.doRequest("5d22e2a6b6c40c3ba03c605a", TAG_GET_DATA);
            }
        });
    }
}
