package com.grappus.custombubblepickerexample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.igalata.bubblepicker.BubblePickerListener
import com.igalata.bubblepicker.OnReadyForInitializationListener
import com.igalata.bubblepicker.adapter.BubblePickerAdapter
import com.igalata.bubblepicker.model.BubbleGradient
import com.igalata.bubblepicker.model.PickerItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName
    private var selectedFoodItemList: ArrayList<PickerItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUiComponents()
    }

    override fun onResume() {
        super.onResume()
        bubbleFoodInterestPicker.onResume()
    }

    override fun onPause() {
        super.onPause()
        bubbleFoodInterestPicker.onPause()
    }

    private fun initUiComponents() {
        val foodItemList = arrayOf("Soup", "Noodles", "Sandwiches", "Salad")
        val drawableList = arrayOf(
            R.drawable.soup, R.drawable.noodles,
            R.drawable.sandwiches, R.drawable.salad
        )

        bubbleFoodInterestPicker.adapter = object : BubblePickerAdapter {

            override val totalCount = foodItemList.size

            override fun getItem(position: Int): PickerItem {
                return PickerItem().apply {

                    title = foodItemList[position]
                    gradient = BubbleGradient(
                        ContextCompat.getColor(this@MainActivity, R.color.start_color),
                        ContextCompat.getColor(this@MainActivity, R.color.end_color), BubbleGradient.VERTICAL
                    )
                    color = ContextCompat.getColor(this@MainActivity, R.color.white_50)
//                    typeface = mediumTypeface
                    textColor = ContextCompat.getColor(this@MainActivity, android.R.color.white)
                    backgroundImage = ContextCompat.getDrawable(this@MainActivity, drawableList[position])
                }
            }
        }

        bubbleFoodInterestPicker.listener = object : BubblePickerListener {
            override fun onBubbleSelected(item: PickerItem) {
                Log.d(TAG, "Item selected")
                selectedFoodItemList.add(item)
                updateFoodPreferences()
            }

            override fun onBubbleDeselected(item: PickerItem) {
                Log.d(TAG, "Item deselected")
                selectedFoodItemList.remove(item)
                updateFoodPreferences()
            }
        }
        bubbleFoodInterestPicker.centerImmediately = true

        bubbleFoodInterestPicker.setInitializationListener(object : OnReadyForInitializationListener {
            override fun onReady(`object`: Any?) {
                Log.d(TAG, "inside onReady on bubble picker")
                tvSelectedInterests.visibility = View.INVISIBLE
                selectedFoodItemList.clear()
                bubbleFoodInterestPicker.initializeView()
            }
        })
    }

    private fun updateFoodPreferences() {
        if (selectedFoodItemList.size > 0) {
            var foodString = ""
            for (i in 0 until selectedFoodItemList.size) {
                if (i == 0)
                    foodString = selectedFoodItemList[i].title!!
                else foodString += ", ${selectedFoodItemList[i].title}"
            }
            tvSelectedInterests.visibility = View.VISIBLE
            tvSelectedInterests.text = "Your food preferences are: $foodString"
        } else tvSelectedInterests.visibility = View.INVISIBLE
    }
}
