package com.example.sociallink.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sociallink.databinding.FragmentGithubBinding


class GithubFragment : Fragment() {
 private  var _binding:FragmentGithubBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle? ,
    ) : View {
        _binding=FragmentGithubBinding.inflate(inflater,container,false)

        binding.tvName.setOnClickListener {
            val githubUserName="MuhammadiHadi"
            val githubUrl = "https://github.com/$githubUserName"
            val githubIntent = Intent(Intent.ACTION_VIEW , Uri.parse(githubUrl))
            startActivity(githubIntent)
        }

        return binding.root

    }


}