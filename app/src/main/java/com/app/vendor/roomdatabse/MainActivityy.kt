package com.app.vendor.roomdatabse

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.vendor.R
import com.app.vendor.base.BaseActivity
import kotlinx.android.synthetic.main.roomdb_first.*

class MainActivityy : BaseActivity(), RecyclerViewAdapter.ClickListener {

    private var rvAdapter: RecyclerViewAdapter? = null
    lateinit var viewModel: MainActivityVM

    override fun layoutRes(): Int {
        return R.layout.roomdb_first
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setAdapter()



        viewModel = ViewModelProviders.of(this).get(MainActivityVM::class.java)

        viewModel.getAllUsersObservers().observe(this, Observer {
            rvAdapter?.setListData(ArrayList(it))
            rvAdapter?.notifyDataSetChanged()
        })

        setListener()

    }

    private fun setListener() {
        btnInsert.setOnClickListener({
            val First = etFN.text.toString()
            val Second = etLN.text.toString()
            val user = User(First, Second)
            viewModel.insertUserInfo(user)
        })
    }

    private fun setAdapter() {
        rvAdapter = RecyclerViewAdapter(this)
        rvRoom.setHasFixedSize(true) //every item of the RecyclerView has a fix size
        rvRoom.adapter = rvAdapter

    }

    override fun onDeleteUserClickListener(user: User) {
        viewModel.deleteUserInfo(user)

    }

    override fun onItemClickListener(user: User) {

    }

}