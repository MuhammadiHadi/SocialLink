package com.example.sociallink

import android.content.Context
import android.widget.Toast

public  fun toastMessage(context: Context ,message:String){
     Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
 }