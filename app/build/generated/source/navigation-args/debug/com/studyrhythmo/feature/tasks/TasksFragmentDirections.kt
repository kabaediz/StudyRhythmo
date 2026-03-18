package com.studyrhythmo.feature.tasks

import android.os.Bundle
import androidx.navigation.NavDirections
import com.studyrhythmo.R
import kotlin.Int
import kotlin.Long

public class TasksFragmentDirections private constructor() {
  private data class ActionTasksToAddEditTask(
    public val taskId: Long = -1L,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_tasks_to_addEditTask

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putLong("taskId", this.taskId)
        return result
      }
  }

  public companion object {
    public fun actionTasksToAddEditTask(taskId: Long = -1L): NavDirections =
        ActionTasksToAddEditTask(taskId)
  }
}
