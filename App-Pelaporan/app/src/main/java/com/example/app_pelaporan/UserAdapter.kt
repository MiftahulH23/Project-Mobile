package com.example.app_pelaporan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class UserAdapter(
    val userContext: Context,
    val layoutResId: Int,
    userList: List<User>
) : ArrayAdapter<User>(userContext, layoutResId, userList.filter { it.role == "admin" }) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(userContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_nama: TextView = view.findViewById(R.id.username)
        val o_email: TextView = view.findViewById(R.id.email)
        val o_alamat: TextView = view.findViewById(R.id.alamat)

        val user = getItem(position)
        o_nama.text = user?.nama
        o_email.text = user?.email
        o_alamat.text = user?.alamat



        return view
    }

}