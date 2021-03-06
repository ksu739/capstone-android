package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.API.signupmodel
import com.example.myapplication.API.signupreturnmodel
import com.example.myapplication.databinding.ActivitySignupBinding
import com.example.myapplication.utility.APP
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Signupactivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    val TAG: String = "Signupactivity"
    var isExistBlank = false
    var isPWSame = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        signup()

    }

    private fun signup() {
        binding.signupcheck.setOnClickListener {
            // "name" : "1" , "major" : "1", "id" : "1", "phone" : "1", "address" : "1", "secretnum" : "1", "pwd" : "1"

            val name: String = binding.signupname.text.toString()
            val major: String = binding.signupmajor.text.toString()
            val id: String = binding.signupid.text.toString()
            val phone: String = binding.signupphone.text.toString()
            val address: String = binding.signupaddress.text.toString()
            val secretnum: String = binding.signupsecretnum.text.toString()
            val pwd: String = binding.signuppwd.text.toString()
            val check = binding.pwdcheck.text.toString()


            val call = APP.service.signup(signupmodel(name, major, id, phone, address, secretnum, pwd));

            if (registercheck(phone, id, pwd, check)) {
                call.enqueue(object : Callback<signupreturnmodel> {
                    override fun onResponse(
                        call: Call<signupreturnmodel>,
                        response: Response<signupreturnmodel>
                    ) {
                        when (response.code()) {
                            201 -> {
                                Toast.makeText(this@Signupactivity, "???????????? ??????", Toast.LENGTH_LONG).show()
                                val intent = Intent(this@Signupactivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            400 -> {
                                Toast.makeText(this@Signupactivity, "???????????? ??????!", Toast.LENGTH_SHORT).show()
                            }
                        }


                    }

                    override fun onFailure(call: Call<signupreturnmodel>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                }
                )
            }
        }
    }

    private fun registercheck(phone: String, id: String, pwd: String, check: String): Boolean {
        if (!(phone.isEmpty() || id.isEmpty() || pwd.isEmpty() || check.isEmpty())) {
            if (check == pwd) {
                return true
            }
            Toast.makeText(this@Signupactivity, "??????????????? ????????????.", Toast.LENGTH_SHORT).show()
            Log.i("abcdefg", "??????????????????")
            return false
        }
        Toast.makeText(this@Signupactivity, "????????? ???????????????.", Toast.LENGTH_SHORT).show()
        Log.i("abcdefg", "??????")
        return false
    }
}


/*
    // ???????????? ????????? ?????????????????? ???????????? ?????????
    fun dialog(type: String){
        val dialog = AlertDialog.Builder(this)

        // ?????? ?????? ????????? ?????? ??????
        if(type.equals("blank")){
            dialog.setTitle("???????????? ??????")
            dialog.setMessage("???????????? ?????? ??????????????????")
        }
        // ????????? ??????????????? ?????? ??????
        else if(type.equals("not same")){
            dialog.setTitle("???????????? ??????")
            dialog.setMessage("??????????????? ???????????? ????????????")
        }

        val dialog_listener = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which){
                    DialogInterface.BUTTON_POSITIVE ->
                        Log.d(TAG, "???????????????")
                }
            }
        }

        dialog.setPositiveButton("??????",dialog_listener)
        dialog.show()

    }
   */