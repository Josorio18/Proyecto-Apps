package com.example.proyecto.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.proyecto.MainActivity
import com.example.proyecto.R

class NotificationManager(private val context: Context) {

    companion object {
        private const val CHANNEL_ID = "urban_steps_channel"
        private const val CHANNEL_NAME = "UrbanSteps Notifications"
        private const val CHANNEL_DESCRIPTION = "Notificaciones de UrbanSteps"
        
        private const val NOTIFICATION_ID_FAVORITE = 1001
        private const val NOTIFICATION_ID_CART = 1002
        private const val NOTIFICATION_ID_PURCHASE = 1003
        private const val NOTIFICATION_ID_WELCOME = 1004
        private const val NOTIFICATION_ID_OFFER = 1005
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
                enableVibration(true)
                enableLights(true)
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showFavoriteNotification(productName: String, isFavorite: Boolean) {
        val title = if (isFavorite) "¡Agregado a favoritos!" else "Eliminado de favoritos"
        val message = if (isFavorite) {
            "$productName ahora está en tu lista de favoritos"
        } else {
            "$productName ya no está en tus favoritos"
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID_FAVORITE, notification)
    }

    fun showAddToCartNotification(productName: String) {
        val title = "¡Agregado al carrito!"
        val message = "$productName se ha agregado a tu carrito de compras"

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID_CART, notification)
    }

    fun showPurchaseSuccessNotification(totalAmount: Double, itemCount: Int) {
        val title = "¡Compra exitosa! 🎉"
        val message = "Tu pedido de $itemCount productos por $${String.format("%.2f", totalAmount)} ha sido confirmado"

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID_PURCHASE, notification)
    }

    fun showWelcomeNotification() {
        val title = "¡Bienvenido a UrbanSteps! 👟"
        val message = "Descubre la mejor selección de zapatos y encuentra tu estilo perfecto"

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID_WELCOME, notification)
    }

    fun showSpecialOfferNotification(discount: Int, productName: String) {
        val title = "¡Oferta especial! 🔥"
        val message = "$discount% de descuento en $productName. ¡No te lo pierdas!"

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID_OFFER, notification)
    }

    fun showNewProductNotification(productName: String, category: String) {
        val title = "¡Nuevo producto disponible! ✨"
        val message = "$productName de la categoría $category ya está disponible"

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .build()

        NotificationManagerCompat.from(context).notify(System.currentTimeMillis().toInt(), notification)
    }

    fun showLowStockNotification(productName: String, remainingStock: Int) {
        val title = "¡Últimas unidades! ⚠️"
        val message = "Solo quedan $remainingStock unidades de $productName. ¡Compra ahora!"

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .build()

        NotificationManagerCompat.from(context).notify(System.currentTimeMillis().toInt(), notification)
    }
}
