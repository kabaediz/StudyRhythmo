package com.studyrhythmo.feature.notes

import android.os.Bundle
import androidx.navigation.NavDirections
import com.studyrhythmo.R
import kotlin.Int
import kotlin.Long

public class NotesFragmentDirections private constructor() {
  private data class ActionNotesToAddEditNote(
    public val noteId: Long = -1L,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_notes_to_addEditNote

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putLong("noteId", this.noteId)
        return result
      }
  }

  public companion object {
    public fun actionNotesToAddEditNote(noteId: Long = -1L): NavDirections =
        ActionNotesToAddEditNote(noteId)
  }
}
