package com.studyrhythmo.feature.schedule

import android.os.Bundle
import androidx.navigation.NavDirections
import com.studyrhythmo.R
import kotlin.Int
import kotlin.Long

public class ScheduleFragmentDirections private constructor() {
  private data class ActionScheduleToAddEditCourse(
    public val courseId: Long = -1L,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_schedule_to_addEditCourse

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putLong("courseId", this.courseId)
        return result
      }
  }

  public companion object {
    public fun actionScheduleToAddEditCourse(courseId: Long = -1L): NavDirections =
        ActionScheduleToAddEditCourse(courseId)
  }
}
