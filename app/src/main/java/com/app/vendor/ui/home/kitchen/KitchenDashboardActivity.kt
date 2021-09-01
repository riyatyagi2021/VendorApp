package com.app.vendor.ui.home.kitchen


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.vendor.R
import com.app.vendor.base.App
import com.app.vendor.base.BaseActivity
import com.app.vendor.callback.CallbackType
import com.app.vendor.callback.RootCallback
import com.app.vendor.model.food.Food
import com.app.vendor.model.user.FoodCreateRequest
import com.app.vendor.utils.AppConstant
import com.app.vendor.utils.AppUtil
import com.app.vendor.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.dialog_remove_food.*
import kotlinx.android.synthetic.main.side_menu.*


class KitchenDashboardActivity : BaseActivity(), RootCallback<Any>,
    SwipeRefreshLayout.OnRefreshListener {

    private var foodId: String? = null
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
        getFoodListAPI()

        btnAdd.setOnClickListener {
            val intent = Intent(this, AddFoodActivity::class.java)
            startActivity(intent)
            //  launchActivity(AddFoodItems::class.java)
        }
    }

    private fun getFoodListAPI() {
        if (AppUtil.isConnection()) {
            showProgressBar()
            viewModel.getFoodUser(vendorId)
        }else{
            AppUtil.showToast(getString(R.string.msg_network_connection))
        }
    }

    override fun onRootCallback(index: Int, data: Any, type: CallbackType, view: View) {
        when (type) {
            CallbackType.DASHBOARD_ADAPTER_MENU -> {
                val v = data as Food
                val bottomSheetFragmentAddFood = BottomSheetFragmentAddFood(v, this)
                bottomSheetFragmentAddFood.show(supportFragmentManager, "bottomsheetdialog")
            }

            CallbackType.UPDATE_FOOD -> {
                val foodData = data as Food
                if (index == 1) {
                    editFood(foodData)
                } else {

                }
            }
            CallbackType.DELETE_FOOD -> {
                val foodData = data as Food
                if (index == 2 ){
                    deleteFood( foodData)
                } else {

                }
            }
        }
    }

    /* override fun click(foodId: String?, v: Int) {
         if (v == 1) {
             editFood(foodId)
         } else {
             deleteFood(foodId)
         }
     }*/

    private fun deleteFood(food: Food?) {
        var dialog: Dialog? = null
        dialog = Dialog(this)

        val foodReq = FoodCreateRequest()
        foodReq.foodId = foodId

        dialog.setContentView(R.layout.dialog_remove_food)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()

        dialog.btYes.setOnClickListener {
            if (AppUtil.isConnection()) {
                if(food!=null) {
                    viewModel.deleteFoodAPI(food.foodId)
                    dialog.dismiss()
                    launchActivity(KitchenDashboardActivity::class.java)
                    finish()
                }
            }
        }

        dialog.ivCross.setOnClickListener {
            dialog.dismiss()

        }
    }

    private fun editFood(food: Food?) {
        /* if (!foodId.isNullOrEmpty()){
             val bundle = Bundle()
             bundle.putString(AppConstant.BK.FOOD_ID, foodId)
             launchActivity(EditFoodActivity::class.java, bundle)
         }*/

        if (food != null) {
            val bundle = Bundle()
            bundle.putSerializable(AppConstant.BK.FOOD_DATA, food)
            launchActivity(EditFoodActivity::class.java, bundle)
        }
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
            // swpKt.isRefreshing = false
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


        viewModel.foodDeleteSuccess.observe(this) { data ->
            hideProgressBar()
            AppUtil.showToast(data?.message)
        }


        viewModel.error.observe(this) { errors ->
            // swpKt.isRefreshing = false
            hideProgressBar()
            AppUtil.showToast(errors.errorMessage)
        }
    }

    override fun onRefresh() {
        getUserData()
    }

    override fun onResume() {
        super.onResume()
        getUserData()
        getFoodListAPI()
    }

    fun getUserData(){
        if (AppUtil.isConnection()) {
                    viewModel.getMyProfile()
        }else{
            AppUtil.showToast(getString(R.string.msg_network_connection))
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
