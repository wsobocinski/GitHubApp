package com.wsobocinski.githubapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.wsobocinski.githubapp.CommitsAdapter
import com.wsobocinski.githubapp.R
import com.wsobocinski.githubapp.databinding.FragmentCashedBinding
import com.wsobocinski.githubapp.viewmodel.CashedViewModel
import kotlinx.android.synthetic.main.fragment_cashed.*
import java.lang.Exception

class CashedFragment : Fragment() {

    private val cashedViewModel by viewModels<CashedViewModel>()
    private lateinit var commitsAdapter: CommitsAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val binding: FragmentCashedBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cashed, container, false)
        binding.lifecycleOwner = this


        commitsAdapter = CommitsAdapter()
        binding.cashedCommitsRecyclerView.adapter = commitsAdapter


        cashedViewModel.repositoryResponse.observe(viewLifecycleOwner, {
            cashedRepositoryId.text = "REPOSITORY ID: ${it.repositoryId}"
            commitsAdapter.submitList(it.commits!!)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cashedSearchButton.setOnClickListener {
            val search: String = cashedSearchEditText.text.toString()
            try {
                val (owner, repository) = search.split("/")
                cashedViewModel.getCommitsForOwnerAndRepoNames(owner, repository)

                } catch (e: Exception){}
        }

        fab.setOnClickListener {
            sendSelectedInformation()
        }
    }

    fun sendSelectedInformation() {
        val commitList = commitsAdapter.getCurrentList()
        var stringToSend = ""

        for (commit in commitList) {
            if (commit.selected) {
                stringToSend += "author:  ${commit.author}  message:  ${commit.message} " +
                        " sha value:  ${commit.shaValue} \n"
            }
        }
        if (stringToSend != "") {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, stringToSend)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
}