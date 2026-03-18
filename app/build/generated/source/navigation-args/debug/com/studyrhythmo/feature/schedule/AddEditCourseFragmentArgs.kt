package com.studyrhythmo.feature.schedule

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Long
import kotlin.jvm.JvmStatic

public data class AddEditCourseFragmentArgs(
  public val courseId: Long = -1L,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putLong("courseId", this.courseId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("courseId", this.courseId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): AddEditCourseFragmentArgs {
      bundle.setClassLoader(AddEditCourseFragmentArgs::class.java.classLoader)
      val __courseId : Long
      if (bundle.containsKey("courseId")) {
        __courseId = bundle.getLong("courseId")
      } else {
        __courseId = -1L
      }
      return AddEditCourseFragmentArgs(__courseId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): AddEditCourseFragmentArgs {
      val __courseId : Long?
      if (savedStateHandle.contains("courseId")) {
        __courseId = savedStateHandle["courseId"]
        if (__courseId == null) {
          throw IllegalArgumentException("Argument \"courseId\" of type long does not support null values")
        }
      } else {
        __courseId = -1L
      }
      return AddEditCourseFragmentArgs(__courseId)
    }
  }
}
