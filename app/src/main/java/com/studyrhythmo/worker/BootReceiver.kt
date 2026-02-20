package com.studyrhythmo.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // WorkManager persists its work across reboots automatically via JobScheduler/AlarmManager
    }
}
