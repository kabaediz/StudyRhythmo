package com.studyrhythmo.feature.study

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.studyrhythmo.data.db.AppDatabase
import com.studyrhythmo.data.entity.StudySessionEntity
import com.studyrhythmo.data.repository.StudySessionRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class TimerState(
    val isRunning: Boolean = false,
    val remainingSeconds: Long = 0,
    val totalSeconds: Long = 0
)

class StudyViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = StudySessionRepository(AppDatabase.getInstance(app).studySessionDao())

    val sessions: StateFlow<List<StudySessionEntity>> =
        repo.getAllSessions().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _timerState = MutableStateFlow(TimerState())
    val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    private var timer: CountDownTimer? = null
    private var activeSessionId: Long = 0
    private var elapsedSeconds: Long = 0

    fun insertSession(session: StudySessionEntity) = viewModelScope.launch { repo.insertSession(session) }
    fun updateSession(session: StudySessionEntity) = viewModelScope.launch { repo.updateSession(session) }
    fun deleteSession(session: StudySessionEntity) = viewModelScope.launch { repo.deleteSession(session) }

    fun startTimer(durationMinutes: Int, sessionId: Long) {
        timer?.cancel()
        activeSessionId = sessionId
        elapsedSeconds = 0
        val totalMs = durationMinutes * 60L * 1000L
        _timerState.value = TimerState(true, durationMinutes * 60L, durationMinutes * 60L)
        timer = object : CountDownTimer(totalMs, 1000) {
            override fun onTick(ms: Long) {
                val remaining = ms / 1000
                elapsedSeconds = durationMinutes * 60L - remaining
                _timerState.value = _timerState.value.copy(remainingSeconds = remaining)
            }
            override fun onFinish() {
                _timerState.value = _timerState.value.copy(isRunning = false, remainingSeconds = 0)
                saveElapsedTime()
            }
        }.start()
    }

    fun stopTimer() {
        timer?.cancel()
        _timerState.value = _timerState.value.copy(isRunning = false)
        saveElapsedTime()
    }

    private fun saveElapsedTime() {
        if (activeSessionId <= 0) return
        viewModelScope.launch {
            val session = repo.getSessionById(activeSessionId) ?: return@launch
            repo.updateSession(session.copy(
                actualDurationMinutes = (elapsedSeconds / 60).toInt(),
                isCompleted = true
            ))
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}
