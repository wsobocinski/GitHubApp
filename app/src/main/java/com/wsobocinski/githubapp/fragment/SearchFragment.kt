package com.wsobocinski.githubapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wsobocinski.githubapp.CommitsAdapter
import com.wsobocinski.githubapp.R
import com.wsobocinski.githubapp.database.CommitsDao
import com.wsobocinski.githubapp.database.CommitsDatabase
import com.wsobocinski.githubapp.database.model.CommitsModel
import com.wsobocinski.githubapp.databinding.FragmentSearchBinding
import com.wsobocinski.githubapp.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var commitsAdapter: CommitsAdapter
    private val employeeDatabase :CommitsDatabase by lazy {
        CommitsDatabase.getInstance(requireContext()) }
    private val commitsDao: CommitsDao by lazy {
        employeeDatabase.commitsDatabaseDao}


    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        binding.homeViewModel = searchViewModel

        searchViewModel.text.observe(viewLifecycleOwner, Observer {
            repositoryId.text = it
        })

        commitsAdapter = CommitsAdapter()
        binding.commitsRecyclerView.adapter = commitsAdapter

        searchViewModel.listOfCommits.observe(viewLifecycleOwner, {
            commitsAdapter.submitList(it)
        })

        searchViewModel.text.observe(viewLifecycleOwner, {
            uiScope.launch {
                commitsDao.addCommits(CommitsModel(searchViewModel.text.value!!,
                    searchViewModel.listOfCommits.value))
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchButton.setOnClickListener {
            val search: String = searchEditText.text.toString()
            val (owner, repository) = search.split("/")
            searchViewModel.getRepositoryFromOwner(owner, repository)
            searchViewModel.getCommitsFromOwnersRepository(owner, repository)
        }
    }
}