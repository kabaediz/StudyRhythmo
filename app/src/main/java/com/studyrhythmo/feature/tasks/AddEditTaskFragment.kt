package com.studyrhythmo.feature.tasks

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.studyrhythmo.data.db.AppDatabase
import com.studyrhythmo.data.entity.TaskEntity
import com.studyrhythmo.data.entity.TaskPriority
import com.studyrhythmo.data.entity.TaskType
import com.studyrhythmo.data.repository.TaskRepository
import com.studyrhythmo.databinding.FragmentAddEditTaskBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddEditTaskFragment : Fragment() {

    private var _binding: FragmentAddEditTaskBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TasksViewModel by viewModels()
    private val args: AddEditTaskFragmentArgs by navArgs()
    private var editingTask: TaskEntity? = null
    private var dueDate: Long = System.currentTimeMillis() + 24 * 60 * 60 * 1000
    private val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddEditTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val priorities = TaskPriority.values().map { it.name }
        binding.spinnerPriority.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorities).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        val types = TaskType.values().map { it.name }
        binding.spinnerType.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, types).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.btnDueDate.text = sdf.format(Date(dueDate))

        if (args.taskId > 0) {
            viewLifecycleOwner.lifecycleScope.launch {
                val repo = TaskRepository(AppDatabase.getInstance(requireContext()).taskDao())
                editingTask = repo.getTaskById(args.taskId)
                editingTask?.let { populateFields(it) }
            }
        }

        binding.btnDueDate.setOnClickListener { showDatePicker() }
        binding.btnSave.setOnClickListener { saveTask() }
        binding.btnCancel.setOnClickListener { findNavController().navigateUp() }
    }

    private fun populateFields(task: TaskEntity) {
        binding.editTaskTitle.setText(task.title)
        binding.editTaskDescription.setText(task.description)
        binding.spinnerPriority.setSelection(task.priority.ordinal)
        binding.spinnerType.setSelection(task.type.ordinal)
        dueDate = task.dueDate
        binding.btnDueDate.text = sdf.format(Date(dueDate))
    }

    private fun showDatePicker() {
        val cal = Calendar.getInstance().apply { timeInMillis = dueDate }
        DatePickerDialog(requireContext(), { _, y, m, d ->
            cal.set(y, m, d)
            dueDate = cal.timeInMillis
            binding.btnDueDate.text = sdf.format(Date(dueDate))
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun saveTask() {
        val title = binding.editTaskTitle.text.toString().trim()
        if (title.isEmpty()) { binding.editTaskTitle.error = "Required"; return }
        val task = TaskEntity(
            id = editingTask?.id ?: 0,
            title = title,
            description = binding.editTaskDescription.text.toString().trim(),
            dueDate = dueDate,
            priority = TaskPriority.values()[binding.spinnerPriority.selectedItemPosition],
            type = TaskType.values()[binding.spinnerType.selectedItemPosition]
        )
        if (editingTask != null) viewModel.updateTask(task) else viewModel.insertTask(task)
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
