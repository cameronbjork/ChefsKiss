package com.example.chefskiss2;

import static android.net.Uri.parse;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends ArrayAdapter<Recipe> {

    private Context context;
    int resource;

    public RecipeListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Recipe> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        setUpImageLoader();

        String title = getItem(position).getTitle();
        String directions = getItem(position).getDirections();
        String ingredients = getItem(position).getDirections();
        int id = getItem(position).getId();
        String imageURI = getItem(position).getImageURI();



        Recipe r = new Recipe(id, title, ingredients, directions);
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView recipeDirections = (TextView) convertView.findViewById(R.id.recipeName);
        ImageView image = (ImageView) convertView.findViewById(R.id.recipeImage);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(imageURI, image);

        recipeDirections.setText(title);

        return convertView;
    }

    private void setUpImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true).imageScaleType(ImageScaleType.NONE)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache()).discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
    }



}
