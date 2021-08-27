package com.app.vendor.ui.home.kitchen

import android.Manifest
import android.R.attr
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.vendor.R
import com.app.vendor.base.BaseActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.add_food_item.*
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.viewModels

import androidx.core.app.ActivityCompat.startActivityForResult
import com.app.vendor.base.App
import com.app.vendor.model.Media
import com.app.vendor.utils.AppConstant
import com.app.vendor.utils.AppUtil
import com.app.vendor.utils.DialogUtils
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.mobcoder.kitchen.viewModel.AuthViewModel
import android.graphics.BitmapFactory

import android.R.attr.data
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.view.View
import java.io.ByteArrayOutputStream


class FoodActivity :BaseActivity(), BottomSheetFragment.cameraGallery {

    val bottomSheetFragment = BottomSheetFragment(this@FoodActivity)
    private var selectedMediaFiles: MutableList<Media>? = ArrayList()
    private val viewModel: AuthViewModel by viewModels()
    var count = 3

    override fun layoutRes(): Int {
        return R.layout.add_food_item
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arrow.setOnClickListener({
            finish()
        })

        addImage.setOnClickListener {
            validatePermission()
        }


        setObservables()

        btnAddFood.setOnClickListener{
           if(AppUtil.isConnection()) {
               viewModel.addFoodImageUploadAPI(selectedMediaFiles)

        }
        }
    }

    private fun validatePermission() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    Toast.makeText(this@FoodActivity, "Permission Granted", Toast.LENGTH_SHORT)
                        .show()

                    bottomSheetFragment.show(supportFragmentManager, "bottomsheetdialog")
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    AlertDialog.Builder(this@FoodActivity)
                        .setTitle(R.string.storage_permission_rationale_title)
                        .setMessage(R.string.storage_permission_rationale_message)
                        .setNegativeButton(
                            android.R.string.cancel,
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                                token?.cancelPermissionRequest()
                            })
                        .setPositiveButton(
                            android.R.string.ok,
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                                token?.continuePermissionRequest()
                            })
                        .show()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(
                        this@FoodActivity,
                        R.string.storage_permission_denied_message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            ).check()


    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            AppConstant.PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                  cameraImage()
                } else {

                    AppUtil.showToast("Permission denied")
                }
            }
        }
    }

    override fun onclick(v: Int) {
        if (v == 1) {
            cameraImage()
        } else {
            galleryImage()
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)
    }

    fun galleryImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2)
    }

    fun cameraImage() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, 1)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        var tempUri:Uri?=null
        if (resultCode == Activity.RESULT_OK && requestCode == 2) {
            //   photo1.setImageURI(data?.data)
            if (count == 3) {
                photo1.visibility = View.VISIBLE
               photo1.setImageURI(data?.data)
                tempUri=data?.data
            } else if (count == 2) {
                photo2.visibility = View.VISIBLE
               photo2.setImageURI(data?.data)
                tempUri=data?.data
            } else if (count == 1) {
                photo3.visibility = View.VISIBLE
                photo3.setImageURI(data?.data)
                tempUri=data?.data
            } else {
                AppUtil.showToast("Maximum 3 Images can be captured!")
            }
        }


        if (resultCode == Activity.RESULT_OK && requestCode == 1 && data != null) {
            //photo2.setImageBitmap(data.extras?.get("data") as Bitmap)
            if (count == 3) {
                photo1.visibility = View.VISIBLE
               photo1.setImageBitmap(data.extras?.get("data") as Bitmap)
                tempUri=getImageUri(applicationContext,data.extras?.get("data") as Bitmap)
            } else if (count == 2) {
                photo2.visibility = View.VISIBLE
               photo2.setImageBitmap(data.extras?.get("data") as Bitmap)
                tempUri=getImageUri(applicationContext,data.extras?.get("data") as Bitmap)

            } else if (count == 1) {
                photo3.visibility = View.VISIBLE
                photo3.setImageBitmap(data.extras?.get("data") as Bitmap)
                tempUri=getImageUri(applicationContext,data.extras?.get("data") as Bitmap)

            } else {
                AppUtil.showToast("Maximum 3 Images can be captured!")
            }

        }

        count--


        val media = Media()
        media.image = tempUri?.let { getPath(it) }
        selectedMediaFiles?.add(media)

        bottomSheetFragment.dismiss()
    }

    //get file path from storage
    fun getPath(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor? =
            App.INSTANCE.getContentResolver().query(uri, projection, null, null, null)
        return if (cursor != null) {
            // HERE YOU WILL GET A NULL POINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            val filePath = cursor.getString(column_index)
            cursor.close()
            filePath
        } else null
    }
    private fun setObservables() {
        viewModel.imageUploadResponse.observe(this) { data ->
            hideProgressBar()
            AppUtil.showToast(data?.message)

        }

        viewModel.error.observe(this) { errors ->
            // swpKt.isRefreshing = false
            hideProgressBar()
            AppUtil.showToast(errors.errorMessage)
        }


    }


    override fun onStoragePickUp(data: MutableList<Media>?) {
        data?.let {
            if (selectedMediaFiles?.size == 3) {
                AppUtil.showToast("Maximum 3 images can be captured")
                return
            }
            if (selectedMediaFiles?.size ?: 0 > 0) {
                selectedMediaFiles?.addAll(it)
            } else {
                selectedMediaFiles = it
            }
        }
    }

}