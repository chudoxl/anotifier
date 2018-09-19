package tech.chudoxl.anotifier

import android.content.Context
import android.os.Environment
import android.util.Log
import timber.log.Timber
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "FileLoggingTree"

class FileLoggingTree(private val context: Context) : Timber.DebugTree() {

    override fun log(priority: Int, tag: String, message: String, t: Throwable?) {

        try {

            val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).resolve("YoScholarDeliveryLogs")

            if (!dir.exists()) {
                dir.mkdir()
            }

            val fileNameTimeStamp = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            val logTimeStamp = SimpleDateFormat("E MMM dd yyyy 'at' hh:mm:ss:SSS aaa", Locale.getDefault()).format(Date())

            val fileName = "$fileNameTimeStamp.txt"

            val file = dir.resolve(fileName)

            file.createNewFile()

            if (file.exists()) {

                val fileOutputStream = FileOutputStream(file, true)

                fileOutputStream.write("$logTimeStamp\t$message\r\n".toByteArray())
                fileOutputStream.close()

            }

        } catch (e: Exception) {
            Log.e(TAG, "Error while logging into file : $e")
        }
    }

}