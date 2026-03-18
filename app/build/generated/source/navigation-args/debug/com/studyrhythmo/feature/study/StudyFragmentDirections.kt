package com.studyrhythmo.feature.study

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.studyrhythmo.R
import kotlin.Int
import kotlin.Long

public class StudyFragmentDirections private constructor() {
  private data class ActionStudyToTimer(
    public val sessionId: Long = -1L,
    public val durationMinutes: Int = 25,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_study_to_timer

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putLong("sessionId", this.sessionId)
        result.putInt("durationMinutes", this.durationMinutes)
        return result
      }
  }

  public companion object {
    public fun actionStudyToTimer(sessionId: Long = -1L, durationMinutes: Int = 25): NavDirections =
        ActionStudyToTimer(sessionId, durationMinutes)

    public fun actionStudyToAddEditSession(): NavDirections =
        ActionOnlyNavDirections(R.id.action_study_to_addEditSession)
  }
}
