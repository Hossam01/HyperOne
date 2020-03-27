package com.example.hyperone.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hyperone.Model.User;
import com.example.hyperone.R;

import java.util.ArrayList;

public class AdapterList extends BaseAdapter {
    ArrayList<User> arrayitems;
    LayoutInflater vi;
    int Resource;
    ViewHolder viewHolder;
    User listitem;
    Context c;

    AdapterList(){}
    public AdapterList(Context context, int resource, ArrayList<User> objects) {
        //super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        arrayitems = objects;
        c=context;
    }
    @Override
    public int getCount() {
        return arrayitems.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayitems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            viewHolder=new ViewHolder();
            v=vi.inflate(Resource,null);
            viewHolder.addresstxt=(TextView) v.findViewById(R.id.address);
            viewHolder.nametxt=(TextView) v.findViewById(R.id.name);
            viewHolder.emailtxt=(TextView) v.findViewById(R.id.email);
            viewHolder.passtxt=(TextView) v.findViewById(R.id.password);
            v.setTag(viewHolder);
        }else
        {
            viewHolder=(ViewHolder)v.getTag();
        }
        listitem=arrayitems.get(position);
        viewHolder.addresstxt.setText("Address : "+listitem.getAddress().trim());
        viewHolder.emailtxt.setText("Email :  "+listitem.getEmail());
        viewHolder.nametxt.setText("Name : "+listitem.getName());
        viewHolder.passtxt.setText("Password : "+listitem.getPassword());


        return v;
    }
    public class ViewHolder {
        public TextView addresstxt,nametxt,passtxt,emailtxt;

    }
}
