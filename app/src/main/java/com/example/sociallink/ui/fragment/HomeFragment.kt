package com.example.sociallink.ui.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.sociallink.R
import com.example.sociallink.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
   private var _binding:FragmentHomeBinding?=null
    private  val binding get()=_binding!!
    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle?
    ) : View {
       _binding=FragmentHomeBinding.inflate(inflater,container,false)

//        val BankName = resources.getStringArray(R.array.Skills)
//        val arrayAdapter = ArrayAdapter(context as Activity , android.R.layout.simple_expandable_list_item_1, BankName)
//        binding.listview.setAdapter(arrayAdapter)
        return binding.root
    }


}