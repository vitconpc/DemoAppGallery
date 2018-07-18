package vn.com.example.demoappgallery.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import vn.com.example.demoappgallery.R;
import vn.com.example.demoappgallery.adapter.ImageAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    private static final String PATH = Environment.getExternalStorageDirectory().getPath().toString();
    private static final int NUM_COLUMN = 2;
    private static final String DOTJPG = ".jpg";
    private static final String DOTPNG = ".png";
    private static final String DOTJPEG = ".jpeg";
    private RecyclerView mRecyclerView;
    private ImageAdapter mImageAdapter;
    private List<File> mListImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkpermission();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void checkpermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                initData();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }
    }

    private void initData() {
        getfile(new File(PATH));
        mImageAdapter.notifyDataSetChanged();
    }

    public void getfile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    getfile(listFile[i]);
                } else {
                    if (listFile[i].getName().endsWith(DOTPNG)
                            || listFile[i].getName().endsWith(DOTJPG)
                            || listFile[i].getName().endsWith(DOTJPEG)
                            ) {
                        mListImage.add(listFile[i]);
                    }
                }
            }
        }
    }

    private void initView() {
        mListImage = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recycler_view);
        mImageAdapter = new ImageAdapter(MainActivity.this, mListImage);
        GridLayoutManager manager = new GridLayoutManager(this, NUM_COLUMN);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mImageAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initData();
        } else
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
    }
}
