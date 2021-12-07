package com.example.chefskiss2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MealScheduleListAdapter extends ArrayAdapter<String[]> {

    private Context context;
    int resource;

    public MealScheduleListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String[]> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String[] list = getItem(position);
        String title;
        String recipeName;
        if(list.length > 1) {
            title = list[0];
            recipeName = list[1];
        }
        else {
            title = list[0];
            recipeName = "";
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView timeOfDay = (TextView) convertView.findViewById(R.id.timeOfDay);
        timeOfDay.setText(title);

        TextView recipeTitle = (TextView) convertView.findViewById(R.id.recipeName);
        if(recipeName.equals("")) {
            recipeTitle.setText("+");
        } else {
            recipeTitle.setText(recipeName);
        }

        return convertView;
    }
}
