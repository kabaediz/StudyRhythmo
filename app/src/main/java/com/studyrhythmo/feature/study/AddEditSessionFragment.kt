package com.studyrhythmo.feature.study

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.studyrhythmo.data.entity.StudySessionEntity
import com.studyrhythmo.databinding.FragmentAddEditSessionBinding
import java.text.SimpleDateFormat
import java.util.*

class AddEditSessionFragment : Fragment() {

    private var _binding: FragmentAddEditSessionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StudyViewModel by viewModels()
    private var plannedStart: Long = System.currentTimeMillis()
    private val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddEditSessionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnStartTime.text = sdf.format(Date(plannedStart))

        binding.btnStartTime.setOnClickListener { showDateTimePicker() }
        binding.btnSave.setOnClickListener { saveSession() }
        binding.btnCancel.setOnClickListener { findNavController().navigateUp() }
    }

    private fun showDateTimePicker() {
        val cal = Calendar.getInstance().apply { timeInMillis = plannedStart }
        DatePickerDialog(requireContext(), { _, y, m, d ->
            cal.set(y, m, d)
            TimePickerDialog(requireContext(), { _, h, min ->
                cal.set(Calendar.HOUR_OF_DAY, h)
                cal.set(Calendar.MINUTE, min)
                plannedStart = cal.timeInMillis
                binding.btnStartTime.text = sdf.format(Date(plannedStart))
            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun saveSession() {
        val topic = binding.editTopic.text.toString().trim()
        val duration = binding.editDuration.text.toString().toIntOrNull()
        if (duration == null || duration <= 0) { binding.editDuration.error = "Enter valid duration"; return }
        val session = StudySessionEntity(
            topic = topic,
            plannedStart = plannedStart,
            plannedDurationMinutes = duration
        )
        viewModel.insertSession(session)
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
