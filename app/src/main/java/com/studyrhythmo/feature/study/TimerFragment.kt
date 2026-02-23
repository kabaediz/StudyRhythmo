package com.studyrhythmo.feature.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.studyrhythmo.R
import com.studyrhythmo.databinding.FragmentTimerBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StudyViewModel by viewModels()
    private val args: TimerFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStart.setOnClickListener {
            viewModel.startTimer(args.durationMinutes, args.sessionId)
        }
        binding.btnStop.setOnClickListener {
            viewModel.stopTimer()
            findNavController().navigateUp()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.timerState.collectLatest { state ->
                val minutes = state.remainingSeconds / 60
                val seconds = state.remainingSeconds % 60
                binding.textTimer.text = "%02d:%02d".format(minutes, seconds)
                binding.btnStart.isEnabled = !state.isRunning
                binding.btnStop.isEnabled = state.isRunning
                val progress = if (state.totalSeconds > 0) (state.remainingSeconds * 100 / state.totalSeconds).toInt() else 100
                binding.progressTimer.setProgressCompat(progress, true)
                if (!state.isRunning && state.remainingSeconds == 0L && state.totalSeconds > 0) {
                    binding.textStatus.text = getString(R.string.session_complete)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
