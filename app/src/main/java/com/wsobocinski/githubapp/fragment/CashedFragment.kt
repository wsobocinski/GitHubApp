package com.wsobocinski.githubapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wsobocinski.githubapp.R
import com.wsobocinski.githubapp.database.CommitsDatabase
import com.wsobocinski.githubapp.viewmodel.CashedViewModel
import kotlinx.android.synthetic.main.fragment_cashed.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CashedFragment : Fragment() {

    private lateinit var cashedViewModel: CashedViewModel
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cashedViewModel =
                ViewModelProviders.of(this).get(CashedViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_cashed, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        cashedViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })


        val employeeDatabase = CommitsDatabase.getInstance(requireContext())
        val commitsDao = employeeDatabase.commitsDatabaseDao
        uiScope.launch {
            val allCommits = commitsDao.getAllCommits()
            val size = allCommits.size
            databaseSize.text = size.toString()
            textView.text = allCommits[allCommits.size - 1].repositoryId
        }



        return root
    }
}