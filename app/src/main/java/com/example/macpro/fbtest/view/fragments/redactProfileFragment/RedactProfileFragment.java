package com.example.macpro.fbtest.view.fragments.redactProfileFragment;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.macpro.fbtest.R;
import com.example.macpro.fbtest.tools.CameraUtility;
import com.example.macpro.fbtest.tools.Config;
import com.example.macpro.fbtest.tools.ReadStorageUtility;
import com.example.macpro.fbtest.tools.SharedPreferencesHelper;
import com.example.macpro.fbtest.tools.WriteStorageUtility;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class RedactProfileFragment extends DialogFragment {

    private static final int RESULT_GALLERY = 1;
    private static final int RESULT_CAMERA = 2;

    private String imageUri;

    private Button mBtnSave;
    private TextInputEditText mEtEmail, mEtName, mEtYearOfBirth;
    private ImageView mIvUserLogo;
    private LinearLayout mLlRedactAvatar;

    public static RedactProfileFragment newInstance() {
        return new RedactProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageUri = SharedPreferencesHelper.getInstance().getStringValue(Config.USER_PHOTO_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_redact_profile, container, false);
        WriteStorageUtility.checkPermission(getContext());
        init(view);
        mBtnSave.setOnClickListener(view1 -> saveData());
        mEtName.setText(SharedPreferencesHelper.getInstance().getStringValue(Config.USER_NAME));
        mEtEmail.setText(SharedPreferencesHelper.getInstance().getStringValue(Config.USER_EMAIL));
        mEtYearOfBirth.setText(SharedPreferencesHelper.getInstance().getStringValue(Config.USER_YEAR_OF_BIRTH));
        mLlRedactAvatar.setOnClickListener(v -> showDialog());
        if(!imageUri.equals("")){
            setImage(imageUri);
        }
        WriteStorageUtility.checkPermission(getContext());
        CameraUtility.checkPermission(getContext());
        ReadStorageUtility.checkPermission(getContext());
        return view;
    }

    public void init(View view) {
        mEtName = view.findViewById(R.id.et_name_input_redact);
        mEtEmail = view.findViewById(R.id.et_email_input_redact);
        mEtYearOfBirth = view.findViewById(R.id.et_date_input_redact);
        mBtnSave = view.findViewById(R.id.btn_redact_save);
        mIvUserLogo = view.findViewById(R.id.iv_logo_person_redact);
        mLlRedactAvatar = view.findViewById(R.id.ll_frag_redact_photo);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            if (ReadStorageUtility.checkPermission(getContext()) && WriteStorageUtility.checkPermission(getContext())
                    && CameraUtility.checkPermission(getContext())) {
                if (requestCode == RESULT_GALLERY) {
                    imageUri = "file://" + getRealPathFromURI(data.getData());
                    setImage(imageUri);
                    SharedPreferencesHelper.getInstance().putStringValue(Config.USER_PHOTO_URL, imageUri);
                } else if (requestCode == RESULT_CAMERA) {
                    SharedPreferencesHelper.getInstance().putStringValue(Config.USER_PHOTO_URL, imageUri);
                    setImage(imageUri);
                    Log.d("PathC", imageUri.toString());
                } else {
                    Toast.makeText(getContext(), getContext().getString(R.string.no_image_selected),
                            Toast.LENGTH_SHORT).show();
                }
            }
    }


    private File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "loyality_profile_photo");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date().getTime());
        File imageFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
        values.put(MediaStore.MediaColumns.DATA, imageFile.getAbsolutePath());

        getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        return imageFile;
    }

    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getContext().getString(R.string.change_the_image_question))
                .setPositiveButton(getContext().getString(R.string.gallery), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (WriteStorageUtility.checkPermission(getContext())) {
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, getContext()
                                    .getString(R.string.choose_photo)), RESULT_GALLERY);
                        }
                        dialog.dismiss();
                    }
                })

                .setNegativeButton(getContext().getString(R.string.camera), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (CameraUtility.checkPermission(getContext())) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            Uri uri = null;
                            if (Build.VERSION.SDK_INT >= 24){
                                uri = FileProvider.getUriForFile(getContext(),
                                        getActivity().getApplicationContext().getPackageName()
                                                + ".my.package.name.provider", getOutputMediaFile());
                            }else {
                                uri = Uri.fromFile(getOutputMediaFile());
                            }
                            imageUri = uri.toString();
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, RESULT_CAMERA);
                        }
                        dialog.dismiss();
                    }
                })
                .setNeutralButton(getContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert = builder.create();
        alert.show();
    }

    private void setImage(String uri) {
        if (WriteStorageUtility.checkPermission(getContext())
                && ReadStorageUtility.checkPermission(getContext())) {


            Picasso.with(getContext())
                    .load(uri)
                    .placeholder(R.drawable.com_facebook_favicon_blue)
                    .error(R.drawable.com_facebook_favicon_blue)
                    .into(mIvUserLogo);

        }
    }

    public String getRealPathFromURI(Uri uri) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(uri, null,
                null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void saveData() {
        SharedPreferencesHelper.getInstance().putStringValue(Config.USER_NAME,
                mEtName.getText().toString());
        SharedPreferencesHelper.getInstance().putStringValue(Config.USER_EMAIL,
                mEtEmail.getText().toString());
        SharedPreferencesHelper.getInstance().putStringValue(Config.USER_YEAR_OF_BIRTH,
                mEtYearOfBirth.getText().toString());
        Toast.makeText(getContext(), getContext().getString(R.string.data_save_successful),
                Toast.LENGTH_SHORT).show();
        dismiss();
    }
}
