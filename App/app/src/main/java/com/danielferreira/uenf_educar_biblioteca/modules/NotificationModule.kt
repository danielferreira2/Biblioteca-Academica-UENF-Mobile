package com.danielferreira.uenf_educar_biblioteca.modules

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManagerCompat {
        return NotificationManagerCompat.from(context)
    }
}
