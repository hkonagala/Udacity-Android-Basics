package com.example.harikakonagala.booklisting;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Harika Konagala on 6/28/2017.
 */

public class BookAdapter extends ArrayAdapter<books> {


    public BookAdapter(@NonNull Context context,  @NonNull List<books> data) {
        super(context, 0,  data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        //create object for books class
        books book = getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.book_title);
        title.setText(book.getTitle());

        TextView authors = (TextView) listItemView.findViewById(R.id.book_author);
        authors.setText("Author(s): " +TextUtils.join(", ", book.getAuthors()));

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.book_image);
        Picasso.with(getContext()).load(Uri.parse(book.getImageURL())).error(R.drawable.no_book_cover).into(imageView);

        return listItemView;
    }
}
