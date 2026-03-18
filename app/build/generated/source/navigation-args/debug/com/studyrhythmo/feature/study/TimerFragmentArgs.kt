package com.studyrhythmo.feature.study

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.Long
import kotlin.jvm.JvmStatic

public data class TimerFragmentArgs(
  public val sessionId: Long = -1L,
  public val durationMinutes: Int = 25,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putLong("sessionId", this.sessionId)
    result.putInt("durationMinutes", this.durationMinutes)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("sessionId", this.sessionId)
    result.set("durationMinutes", this.durationMinutes)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): TimerFragmentArgs {
      bundle.setClassLoader(TimerFragmentArgs::class.java.classLoader)
      val __sessionId : Long
      if (bundle.containsKey("sessionId")) {
        __sessionId = bundle.getLong("sessionId")
      } else {
        __sessionId = -1L
      }
      val __durationMinutes : Int
      if (bundle.containsKey("durationMinutes")) {
        __durationMinutes = bundle.getInt("durationMinutes")
      } else {
        __durationMinutes = 25
      }
      return TimerFragmentArgs(__sessionId, __durationMinutes)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): TimerFragmentArgs {
      val __sessionId : Long?
      if (savedStateHandle.contains("sessionId")) {
        __sessionId = savedStateHandle["sessionId"]
        if (__sessionId == null) {
          throw IllegalArgumentException("Argument \"sessionId\" of type long does not support null values")
        }
      } else {
        __sessionId = -1L
      }
      val __durationMinutes : Int?
      if (savedStateHandle.contains("durationMinutes")) {
        __durationMinutes = savedStateHandle["durationMinutes"]
        if (__durationMinutes == null) {
          throw IllegalArgumentException("Argument \"durationMinutes\" of type integer does not support null values")
        }
      } else {
        __durationMinutes = 25
      }
      return TimerFragmentArgs(__sessionId, __durationMinutes)
    }
  }
}
