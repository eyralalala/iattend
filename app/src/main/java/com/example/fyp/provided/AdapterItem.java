package com.example.fyp.provided;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ItemViewHolder> {

    Context context;
    ArrayList<dataUser> dataUserArrayList;
    Locale id = new Locale ("in", "ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("dd-MMMM-yyyy", id);

    public AdapterItem(Context context, ArrayList<dataUser> dataUserArrayList) {
        this.context = context;
        this.dataUserArrayList = dataUserArrayList;
    }

    @NonNull
    @Override
    public AdapterItem.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from (parent.getContext ()).inflate (R.layout.exam_list_layout, parent, false);
        return new ItemViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItem.ItemViewHolder holder, int position) {
        holder.viewBind(dataUserArrayList.get (position));

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvcoursecode, tvcoursename, tvexamtype, tvexamdate;
        public ItemViewHolder(@NonNull View itemView) {
            super (itemView);
            /*tvcoursecode = itemView.findViewById (R.id.tvcoursecode);
            tvcoursename = itemView.findViewById (R.id.tvcoursename);
            tvexamtype = itemView.findViewById (R.id.tvexamtype);
            tvexamdate = itemView.findViewById (R.id.tvexamdate);*/
        }

        public void viewBind(dataUser dataUser) {
            tvcoursecode.setText (dataUser.getCoursecode());
            tvcoursename.setText (dataUser.getCoursename());
            tvexamtype.setText (dataUser.getExamtype());
            tvexamdate.setText (simpleDateFormat.format (dataUser.getExamdate ()));

        }
    }
}
