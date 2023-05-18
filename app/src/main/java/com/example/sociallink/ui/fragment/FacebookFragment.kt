package com.example.sociallink.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sociallink.R
import com.example.sociallink.databinding.FragmentFacebookBinding
import com.example.sociallink.databinding.FragmentGithubBinding


class FacebookFragment : Fragment() {
    private  var _binding: FragmentFacebookBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle?
    ) : View? {
        // Inflate the layout for this fragment
        _binding=FragmentFacebookBinding.inflate(inflater,container,false)

        binding.tvName.setOnClickListener {


            val faceUserName="mohammad.hadi.18007"
            val facebookUrl = "https://facebook.com/$faceUserName"
            val Intent = Intent(Intent.ACTION_VIEW , Uri.parse(facebookUrl))
            startActivity(Intent)
        }
        return binding.root
    }


}