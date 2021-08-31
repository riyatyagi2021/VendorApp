package com.app.vendor.ui.home.kitchen


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.DialogFragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.vendor.R
import com.app.vendor.base.BaseActivity
import com.app.vendor.callback.CallbackType
import com.app.vendor.model.food.Food
import com.app.vendor.utils.AppUtil
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.side_menu.*
import com.app.vendor.callback.RootCallback
import com.app.vendor.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.bottomsheet_addfood.*


class KitchenDashboardActivity:BaseActivity(), RootCallback<Any>,SwipeRefreshLayout.OnRefreshListener, BottomSheetFragmentAddFood.editDelete{

 var dialog:Dialog?=null

    val bottomSheetFragmentAddFood = BottomSheetFragmentAddFood(this)


    private var foodAdapter: FoodAdapter? = null
    private val viewModel: AuthViewModel by viewModels()
    var vendorId: String? = null
    private var foods: MutableList<Food>? = null


    override fun layoutRes(): Int {
        return R.layout.activity_dashboard
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setAdapter()
        setObservables()
        setNav()
        setListener()

        if (AppUtil.isConnection()) {
            showProgressBar()
            viewModel.getFoodUser(vendorId)
        }

        btnAdd.setOnClickListener{
            val intent=Intent(this,AddFoodActivity::class.java)
            startActivity(intent)
          //  launchActivity(AddFoodItems::class.java)
        }

    }


    override fun onRootCallback(index: Int, data: Any, type: CallbackType, view: View) {
        when (type) {
            CallbackType.DASHBOARD_ADAPTER_MENU -> {
                bottomSheetFragmentAddFood.show(supportFragmentManager, "bottomsheetdialog")

            }

        }
    }

    override fun click(v: Int) {
        if (v == 1) {
            editFood()
        } else {
           deleteFood()
        }    }

    private fun deleteFood() {
       // val intent=Intent(this,DeleteFoodActivity::class.java)
       // startActivity(intent)

        dialog= Dialog(this)
        //   dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.dialog_remove_food)
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.show()
    }

    private fun editFood() {
        val intent=Intent(this,EditFoodActivity::class.java)
        startActivity(intent)
    }

    private fun setListener() {
        ivMenu.setOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }

    }

    private fun setNav() {
        val drawerToggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()


    }


    private fun setAdapter() {
        foodAdapter = FoodAdapter()
        rv1.setHasFixedSize(true) //every item of the RecyclerView has a fix size
        rv1.adapter = foodAdapter
    foodAdapter?.setRootCallback(this as RootCallback<Any>)
    }

    private fun setObservables() {

        viewModel.foodUserSuccess.observe(this) { data ->
            hideProgressBar()
            //  swpKt.isRefreshing = false
            foods = data.foodItemList


            viewModel.myProfileSuccess.observe(this) { data ->
                tvName?.text = "Hello ${data.profileData?.firstName}"

                tvMenu?.text =
                    AppUtil.getFullName(data.profileData?.firstName, data.profileData?.lastName)
                tvGmail?.text = data.profileData?.email
                val c = data.profileData?.couponWalletBalance
                val w = data.profileData?.walletBalance
              //  tvMenuCW?.text = c?.let { AppUtil.getRupee(c) }
                tvMenuWallet?.text = w?.let { AppUtil.getRupee(w) }
                tvPhone?.text = data.profileData?.phone
            }

            /*  if (foods == null || foods?.size == 0) {
                tvNoDataKt.visible()
                tvNoDataKt.text = "No food available for users"
            } else {
                tvNoDataKt.gone()
            }
            tvItemCountKt.gone()
            linearCartKt.gone()
            */

            foodAdapter?.setData(foods)
        }




                    //Vendor List                                     //Have to make vendor adapter- not done

        viewModel.vendorListSuccess.observe(this) { data ->
           // pbVendor?.gone()
           // swpK.isRefreshing = false
            val vendors = data.vendorList
            /*if (vendors == null || vendors?.size == 0) {
                tvNoData.visible()
                tvNoData.text = "No vendor available yet!"
            } else {
                tvNoData.gone()
            }*/
           //vendorAdapter?.setData(vendors)
        }


        viewModel.error.observe(this) { errors ->
            // swpKt.isRefreshing = false
            hideProgressBar()
            AppUtil.showToast(errors.errorMessage)
        }


        }

    override fun onRefresh() {
        if (AppUtil.isConnection()) {
           // viewModel.getAllVendorAPI()
            viewModel.getMyProfile()
        }
    }

    override fun onResume() {
        super.onResume()
        if (AppUtil.isConnection()) {
           // viewModel.getAllVendorAPI()
            viewModel.getMyProfile()
        }
    }

    }


 /*   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstant.REQUEST_CODES.REQ_BUY_NOW_CODE) {
            val b = data?.extras
            val index = b?.getInt(AppConstant.BK.PAYMENT)
            if (resultCode == Activity.RESULT_OK) {
                if (index == 1 || index == 2) { // 1 for payment done, 2 for cancel, null back pressed
                    if (AppUtil.isConnection()) {
                        showProgressBar()
                        viewModel.getFoodUser(vendorId)
                    }
                }
            }
        }
    }*/
