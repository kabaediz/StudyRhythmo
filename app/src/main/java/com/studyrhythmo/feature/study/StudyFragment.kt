package com.studyrhythmo.feature.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.studyrhythmo.R
import com.studyrhythmo.databinding.FragmentStudyBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StudyFragment : Fragment() {

    private var _binding: FragmentStudyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StudyViewModel by viewModels()
    private lateinit var adapter: StudySessionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStudyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = StudySessionAdapter(
            onStart = { session ->
                val action = StudyFragmentDirections.actionStudyToTimer(session.id, session.plannedDurationMinutes)
                findNavController().navigate(action)
            },
            onDelete = { viewModel.deleteSession(it) }
        )
        binding.recyclerSessions.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sessions.collectLatest { sessions ->
                adapter.submitList(sessions)
                binding.textNoSessions.visibility = if (sessions.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        binding.fabAddSession.setOnClickListener {
            findNavController().navigate(R.id.action_study_to_addEditSession)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
