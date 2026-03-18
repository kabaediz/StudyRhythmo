package com.studyrhythmo.feature.tasks

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Long
import kotlin.jvm.JvmStatic

public data class AddEditTaskFragmentArgs(
  public val taskId: Long = -1L,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putLong("taskId", this.taskId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("taskId", this.taskId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): AddEditTaskFragmentArgs {
      bundle.setClassLoader(AddEditTaskFragmentArgs::class.java.classLoader)
      val __taskId : Long
      if (bundle.containsKey("taskId")) {
        __taskId = bundle.getLong("taskId")
      } else {
        __taskId = -1L
      }
      return AddEditTaskFragmentArgs(__taskId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): AddEditTaskFragmentArgs {
      val __taskId : Long?
      if (savedStateHandle.contains("taskId")) {
        __taskId = savedStateHandle["taskId"]
        if (__taskId == null) {
          throw IllegalArgumentException("Argument \"taskId\" of type long does not support null values")
        }
      } else {
        __taskId = -1L
      }
      return AddEditTaskFragmentArgs(__taskId)
    }
  }
}
