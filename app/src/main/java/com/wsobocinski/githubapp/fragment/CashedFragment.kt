package com.wsobocinski.githubapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wsobocinski.githubapp.CommitsAdapter
import com.wsobocinski.githubapp.R
import com.wsobocinski.githubapp.database.CommitsDatabase
import com.wsobocinski.githubapp.viewmodel.CashedViewModel
import kotlinx.android.synthetic.main.fragment_cashed.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class CashedFragment : Fragment() {

    private lateinit var cashedViewModel: CashedViewModel
    private lateinit var commitsAdapter: CommitsAdapter
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


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        commitsAdapter = CommitsAdapter()
        cashedCommitsRecyclerView.adapter = commitsAdapter

        cashedSearchButton.setOnClickListener {
            val search: String = cashedSearchEditText.text.toString()
            try {
                val (owner, repository) = search.split("/")
                val employeeDatabase = CommitsDatabase.getInstance(requireContext())
                val commitsDao = employeeDatabase.commitsDatabaseDao
                uiScope.launch {
                    val allCommits = commitsDao.getCommitsFromOwnerAndRepoNames(owner, repository)
                    if (allCommits != null) {
                        cashedRepositoryId.text = allCommits.repositoryId
                        if (allCommits.commits != null)
                            commitsAdapter.submitList(allCommits.commits)
                    }

                }
            } catch (e: Exception){}

        }
    }
}