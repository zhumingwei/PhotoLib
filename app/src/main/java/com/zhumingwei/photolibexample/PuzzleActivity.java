package com.zhumingwei.photolibexample;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zhumingwei.photolib.PuzzleLayout;
import com.zhumingwei.photolib.PuzzleView;

import java.util.ArrayList;
import java.util.List;

public class PuzzleActivity extends AppCompatActivity {

    private PuzzleView mPuzzleView;
    private PuzzleLayout mPuzzleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        int size = getIntent().getIntExtra("piece_size",5);
        int theme =  getIntent().getIntExtra("theme_id",1);
        mPuzzleLayout = PuzzleUtil.getPuzzleLayout(size, theme);
        initView();

        loadBitmap();
    }

    private void loadBitmap() {

        final List<Bitmap> pieces = new ArrayList<>();

        final int[] resIds = new int[] {
                R.drawable.demo1, R.drawable.demo2, R.drawable.demo3, R.drawable.demo4, R.drawable.demo5,
                R.drawable.demo6, R.drawable.demo7, R.drawable.demo8, R.drawable.demo9,
        };
        final int count = resIds.length > mPuzzleLayout.getBorderSize() ? mPuzzleLayout.getBorderSize()
                : resIds.length;

        for (int i = 0; i < count; i++) {
            final Target target = new Target() {
                @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    pieces.add(bitmap);
                    if (pieces.size() == count) {
                        if (resIds.length < mPuzzleLayout.getBorderSize()) {
                            for (int i = 0; i < mPuzzleLayout.getBorderSize(); i++) {
                                mPuzzleView.addPiece(pieces.get(i % count));
                            }
                        } else {
                            mPuzzleView.addPieces(pieces);
                        }
                    }
                }

                @Override public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };

            Picasso.with(this).load(resIds[i]).config(Bitmap.Config.RGB_565).into(target);

        }

    }

    private void initView() {
        mPuzzleView = (PuzzleView) findViewById(R.id.puzzle_view);
        mPuzzleView.setPuzzleLayout(mPuzzleLayout);
        mPuzzleView.setMoveLineEnable(true);
        mPuzzleView.setNeedDrawBorder(false);
        mPuzzleView.setNeedDrawOuterBorder(false);
        mPuzzleView.setExtraSize(0);
        mPuzzleView.setBorderWidth(4);
        mPuzzleView.setBorderColor(Color.WHITE);
        mPuzzleView.setSelectedBorderColor(Color.parseColor("#ff0000"));
//        mPuzzleView.setDefaultPiecePadding(30);
//        mPuzzleView.setPadding(30, 30, 30, 30);

    }
    private void log(String s){
        Log.d("PuzzleActivity",s);
    }


    int REQUEST_PICK_IMAGE;
    public void replece(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_PICK_IMAGE);

    }

    public void rotate(View view) {
        mPuzzleView.rotate(90f);
    }

    public void filp_horizontal(View view) {
        mPuzzleView.flipHorizontally();
    }

    public void filp_vertical(View view) {
        mPuzzleView.flipVertically();
    }

    public void biankuang(View view) {
        mPuzzleView.setNeedDrawBorder(!mPuzzleView.isNeedDrawBorder());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            String imagePath = null;
            Uri imageUri = data.getData();

//            imagePath = getPath(imageUri);

            final Target target = new Target() {
                @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    mPuzzleView.replace(bitmap);
                }

                @Override public void onBitmapFailed(Drawable errorDrawable) {
                    Snackbar.make(mPuzzleView, "Replace Failed!", Snackbar.LENGTH_SHORT).show();
                }

                @Override public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            int mDeviceWidth = getResources().getDisplayMetrics().widthPixels;
            log("onActivityResult imagePath=="+imagePath);
            log("onActivityResult imageUri=="+imageUri);
//            try {
//                Bitmap photo = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//                log("onActivityResult photo=="+photo);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            Picasso.with(this)
                    .load(imageUri)
                    .resize(mDeviceWidth, mDeviceWidth)
                    .centerInside()
                    .config(Bitmap.Config.RGB_565)
                    .into(target);
        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getPath(Uri uri) {
//        if (TextUtils.isEmpty(uri.getAuthority())) {
//            return null;
//        }
//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String path = cursor.getString(column_index);
//        return path;

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(this, uri)) {
            // ExternalStorageProvider
            if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(this, contentUri, null, null);
            }
            // MediaProvider
            else if ("com.android.providers.media.documents".equals(uri
                    .getAuthority())) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(this, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if ("com.google.android.apps.photos.content".equals(uri.getAuthority()))
                return uri.getLastPathSegment();
            return getDataColumn(this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


}
