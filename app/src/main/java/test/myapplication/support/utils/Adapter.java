package test.myapplication.support.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.List;

public abstract class Adapter<DataClass, ViewHolder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<ViewHolder> {
    private int mLayout;
    private Class<ViewHolder> viewHolderClass;
    private List<DataClass> data;

    public Adapter(int mLayout, Class<ViewHolder> viewHolderClass, List<DataClass> data) {
        this.mLayout = mLayout;
        this.viewHolderClass = viewHolderClass;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(mLayout, viewGroup, false);
        try {
            Constructor<ViewHolder> constructor = viewHolderClass.getConstructor(View.class);
            return constructor.newInstance(view);
        } catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DataClass model = getItem(i);
        bindView(viewHolder, model, i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public abstract void bindView(ViewHolder holder, DataClass model, int pos);

    private DataClass getItem(int pos) {
        return data.get(pos);
    }
}
