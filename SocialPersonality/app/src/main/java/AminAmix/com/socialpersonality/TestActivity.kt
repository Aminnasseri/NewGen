package AminAmix.com.socialpersonality

import AminAmix.com.socialpersonality.TestActivity.FileUtil.getPath
import AminAmix.com.socialpersonality.WebService.APIClient
import AminAmix.com.socialpersonality.WebService.APIInterface
import AminAmix.com.socialpersonality.model.ImagePath
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_first_page.*
import kotlinx.android.synthetic.main.activity_personal_information.*
import kotlinx.android.synthetic.main.activity_test.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class TestActivity : AppCompatActivity() {
    val REQUEST_CODE = 100
    val token =
        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjo4NiwiaWF0IjoxNjM2NTUwODAxfQ.xBZhFpgpT0-2q6awKpPvAWP6kwspqGR2Ync3b0m2t9Q"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        imageView19.setOnClickListener {

            Dexter.withActivity(this)
                .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                        openGalleryForImage()

                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) { /* ... */
                    }
                }).check()

        }
    }

    private fun openGalleryForImage() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
//            imageView19.setImageURI(data?.data) // handle chosen image]

            Log.i("imageAmin", data?.data!!.path.toString())
            val imgFile = File(data.data?.getPath());
            val imageUri: Uri = data?.data!!
            // val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
            val fileOrg = bitmapToFile(this, bitmap, "image")
            val file = File(data?.data!!.getPath())
            val newfile = File(getPath(imageUri, this))
            Log.i("imageAmin", newfile.toString())
            // val smallSizeFile = saveBitmapToFile(newfile)
            imageView19.setImageBitmap(bitmap)

            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), newfile)

            val image = MultipartBody.Part.createFormData("image", newfile.name, requestFile)

// add another part within the multipart request

// add another part within the multipart request
            val usert = "user"

            val type = RequestBody.create(MediaType.parse("multipart/form-data"), usert)

            upladPhoto(token, type, image)


            // uploadPhoto(token, "user", imgFile)


            // val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, data.data)

        }

    }


    fun upladPhoto(token: String, type: RequestBody, image: MultipartBody.Part) {


        val apilnterface: APIInterface = APIClient().getClient()!!.create(APIInterface::class.java)

        var call: Call<ImagePath> = apilnterface.updateProfile(token, type, image)
        call.enqueue(object : Callback<ImagePath> {
            override fun onResponse(
                call: Call<ImagePath>,
                response: Response<ImagePath>
            ) {
                if (response.code() == 200) {
//                    val preferences: SharedPreferences =
//                        applicationContext.getSharedPreferences("SOCIAL_PERSONALITY", Context.MODE_PRIVATE)
//                    preferences.edit().putString("TOKEN", tokenOrg).apply()
//                    Log.i("TokenHome1",tokenOrg)
//                    progressbarPersonalInfo.visibility = View.INVISIBLE

                    Log.w("RES", "${response.body().toString()}")

                    val dataFromResponse = response.body()

                    Toast.makeText(
                        applicationContext,
                        "oKdaadaaa",
                        Toast.LENGTH_LONG
                    ).show()


                }
                if (response.code() == 400) {
//                    progressbarPersonalInfo.visibility = View.INVISIBLE

                    try {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        Toast.makeText(
                            applicationContext,
                            jObjError.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ImagePath>, t: Throwable) {

                Toast.makeText(
                    this@TestActivity,
                    t.message,
                    Toast.LENGTH_LONG
                )
                    .show()
            }

        })
    }

    fun bitmapToFile(
        context: Context?,
        bitmap: Bitmap,
        fileNameToSave: String
    ): File? { // File name like "image.png"
        //create a file to write bitmap data
        var file: File? = null
        return try {
            file = File(
                Environment.getExternalStorageDirectory()
                    .toString() + File.separator + fileNameToSave
            )
            file!!.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitmapdata = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    object FileUtil {
        /*
     * Gets the file path of the given Uri.
     */
        @SuppressLint("NewApi")
        fun getPath(uri: Uri, context: Context): String? {
            var uri = uri
            val needToCheckUri = Build.VERSION.SDK_INT >= 19
            var selection: String? = null
            var selectionArgs: Array<String>? = null
            // Uri is different in versions after KITKAT (Android 4.4), we need to
            // deal with different Uris.
            if (needToCheckUri && DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":").toTypedArray()
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                } else if (isDownloadsDocument(uri)) {
                    val id = DocumentsContract.getDocumentId(uri)
                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:".toRegex(), "")
                    }
                    uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(id)
                    )
                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":").toTypedArray()
                    val type = split[0]
                    when (type) {
                        "image" -> uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        "video" -> uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        "audio" -> uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    selection = "_id=?"
                    selectionArgs = arrayOf(
                        split[1]
                    )
                }
            }
            if ("content".equals(uri.scheme, ignoreCase = true)) {
                val projection = arrayOf(
                    MediaStore.Images.Media.DATA
                )
                try {
                    context.contentResolver.query(uri, projection, selection, selectionArgs, null)
                        .use { cursor ->
                            if (cursor != null && cursor.moveToFirst()) {
                                val columnIndex =
                                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                                return cursor.getString(columnIndex)
                            }
                        }
                } catch (e: java.lang.Exception) {
                    Log.e("on getPath", "Exception", e)
                }
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                return uri.path
            }
            return null
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         */
        private fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        private fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        private fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }
    }

    fun saveBitmapToFile(file: File): File? {
        return try {

            // BitmapFactory options to downsize the image
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            o.inSampleSize = 6
            // factor of downsizing the image
            var inputStream = FileInputStream(file)
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o)
            inputStream.close()

            // The new size we want to scale to
            val REQUIRED_SIZE = 75

            // Find the correct scale value. It should be the power of 2.
            var scale = 1
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE
            ) {
                scale *= 2
            }
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            inputStream = FileInputStream(file)
            val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
            inputStream.close()

            // here i override the original image file
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            file
        } catch (e: java.lang.Exception) {
            null
        }
    }
}