package com.example.sociallink.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sociallink.databinding.FragmentLinkedinBinding


class LinkedinFragment :  Fragment() {
    private  var _binding: FragmentLinkedinBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle?
    ) : View{
        _binding= FragmentLinkedinBinding.inflate(inflater,container,false)
        binding.tvName.setOnClickListener {
            val linkedinUserName="muhammad-hadi-9411631a5/"
            val Url = "https://www.linkedin.com/in/$linkedinUserName"
            val Intent = Intent(Intent.ACTION_VIEW , Uri.parse( Url))
            startActivity(Intent)

        }

        return binding.root
    }


}