package com.farshad.topmovies_compose.ui.screnns.submitMovie

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.farshad.topmovies_compose.MainActivity
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.util.Convertors
import kotlin.random.Random


class NotificationManager constructor(val context: Context) {


    fun showSimpleNotification(movieId: Int) {

        val flag = PendingIntent.FLAG_IMMUTABLE

        val clickIntent = Intent(
            Intent.ACTION_VIEW,
            "https://moviesapi.ir/api/v1/movies/$movieId".toUri(),
            context,
            MainActivity::class.java
        )

        val clickPendingIntent: PendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(clickIntent)
            getPendingIntent(1, flag)
        }

        val notification = NotificationCompat.Builder(context, "Main Channel ID")
            .setContentTitle("Movies app")
            .setContentText("movie has uploaded successfully !")
            .setSmallIcon(R.drawable.splash_icon)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(clickPendingIntent)
            .build()


        val notificationManager = context.getSystemService(NotificationManager::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "Main Channel ID",
                "Movie Upload",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }


        notificationManager.notify(Random.nextInt(), notification)
    }






    fun showImageNotification(movieId: Int, uri: Uri?) {


        if (uri ==null){
            showSimpleNotification(movieId = movieId)
        }else{
            val imageBitmap= Convertors().convertUriToBitmap(context = context, uri = uri)

            val flag = PendingIntent.FLAG_IMMUTABLE

            val clickIntent = Intent(
                Intent.ACTION_VIEW,
                "https://moviesapi.ir/api/v1/movies/$movieId".toUri(),
                context,
                MainActivity::class.java
            )

            val clickPendingIntent: PendingIntent = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(clickIntent)
                getPendingIntent(1, flag)
            }

            val notification = NotificationCompat.Builder(context, "Main Channel ID")
                .setContentTitle("Movies app")
                .setContentText("movie has uploaded successfully !")
                .setSmallIcon(R.drawable.splash_icon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setLargeIcon(imageBitmap)
                .setStyle(
                    NotificationCompat
                        .BigPictureStyle()
                        .bigPicture(imageBitmap)
                        // Smaller icon disappears after expanding
                        .bigLargeIcon(null as Bitmap?)
                )
                .setStyle(
                    NotificationCompat
                        .BigTextStyle()
                        .bigText("Movies app")
                )
                .setContentIntent(clickPendingIntent)
                .build()


            val notificationManager = context.getSystemService(NotificationManager::class.java)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "Main Channel ID",
                    "Movie Upload",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }


            notificationManager.notify(Random.nextInt(), notification)
        }


    }


}

