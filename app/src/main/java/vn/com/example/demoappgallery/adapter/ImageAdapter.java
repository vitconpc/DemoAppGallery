package vn.com.example.demoappgallery.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import vn.com.example.demoappgallery.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<File> mListImage;

    public ImageAdapter(Context context, List<File> listImage) {
        this.mContext = context;
        this.mListImage = listImage;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_image, null);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Picasso.with(mContext).load(mListImage.get(position))
                .fit().centerCrop().into(holder.mImageGallery);
    }

    @Override
    public int getItemCount() {
        return mListImage == null ? 0 : mListImage.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageGallery;

        private ImageViewHolder(View itemView) {
            super(itemView);
            mImageGallery = itemView.findViewById(R.id.image_gallery);
        }
    }
}
