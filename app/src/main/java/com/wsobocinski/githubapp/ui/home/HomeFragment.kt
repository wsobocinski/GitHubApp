package com.wsobocinski.githubapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wsobocinski.githubapp.CommitsAdapter
import com.wsobocinski.githubapp.R
import com.wsobocinski.githubapp.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var commitsAdapter: CommitsAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.homeViewModel = homeViewModel

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            repositoryId.text = it
        })

        commitsAdapter = CommitsAdapter()
        binding.commitsRecyclerView.adapter = commitsAdapter

        homeViewModel.listOfCommits.observe(viewLifecycleOwner, {
            commitsAdapter.submitList(it)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchButton.setOnClickListener {
            val search: String = searchEditText.text.toString()
            val (owner, repository) = search.split("/")
            homeViewModel.getRepositoryFromOwner(owner, repository)
            homeViewModel.getCommitsFromOwnersRepository(owner, repository)
        }
    }
}