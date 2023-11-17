package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dao.TODODAO;
import com.example.myapplication.interFaceRecycle;
import com.example.myapplication.model.TODO;

import java.util.ArrayList;

public class TODOADapter extends RecyclerView.Adapter<TODOADapter.ViewHolder> {
    Context context;
    ArrayList<TODO> arrayList;
    TODODAO tododao ;
    interFaceRecycle interFaceRecycle ;

    public TODOADapter(Context context, ArrayList<TODO> arrayList, TODODAO tododao) {
        this.context = context;
        this.arrayList = arrayList;
        this.tododao = tododao;
    }

    public void setOnclickRecycle(interFaceRecycle interFaceRecycle) {
        this.interFaceRecycle = interFaceRecycle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TODO todo = arrayList.get(position);
       holder.txtTitle.setText(todo.getTitle());
       holder.txtDate.setText(todo.getDate());
        holder.cb.setOnCheckedChangeListener(null); // Bỏ bắt sự kiện trước khi thiết lập trạng thái
        holder.cb.setChecked(todo.getStatus() == 1);

        if (todo.getStatus() == 1) {
            holder.txtTitle.setPaintFlags(holder.txtTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.txtTitle.setPaintFlags(holder.txtTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.cb.setOnCheckedChangeListener((compoundButton, isChecked) -> {

            int status = isChecked ? 1 : 0;
            todo.setStatus(status);
            tododao.update(todo);
            notifyItemChanged(position);
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(tododao.delete(todo.getId())>0){
                   Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                   arrayList.remove(todo);
                   notifyDataSetChanged();
               }
           }
       });
       holder.imgEdit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
             if(interFaceRecycle!= null){
                 interFaceRecycle.onItemClick(position);
             }
           }
       });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
         ImageView imgEdit,imgDelete;
         CheckBox cb ;
         TextView txtTitle,txtDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            cb = itemView.findViewById(R.id.cb);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
