package com.wsobocinski.githubapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.wsobocinski.githubapp.CommitsAdapter
import com.wsobocinski.githubapp.R
import com.wsobocinski.githubapp.database.CommitsDao
import com.wsobocinski.githubapp.database.CommitsDatabase
import com.wsobocinski.githubapp.databinding.FragmentSearchBinding
import com.wsobocinski.githubapp.network.GithubApi
import com.wsobocinski.githubapp.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private val searchViewModel by viewModels<SearchViewModel>()
    private lateinit var commitsAdapter: CommitsAdapter
    private val employeeDatabase :CommitsDatabase by lazy {
        CommitsDatabase.getInstance(requireContext()) }
    private val commitsDao: CommitsDao by lazy {
        employeeDatabase.commitsDatabaseDao}

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        binding.lifecycleOwner = this
        binding.homeViewModel = searchViewModel




        commitsAdapter = CommitsAdapter()
        binding.commitsRecyclerView.adapter = commitsAdapter

        searchViewModel.commitsModel.observe(viewLifecycleOwner, {
            repositoryId.text = "REPOSITORY ID: ${it.repositoryId}"
            commitsAdapter.submitList(it.commits!!)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchButton.setOnClickListener {
            val search: String = searchEditText.text.toString()
            try {
                val (owner, repository) = search.split("/")
                searchViewModel.getListOfCommits(owner, repository)
            } catch (e: Exception) {
            }
        }
    }
}