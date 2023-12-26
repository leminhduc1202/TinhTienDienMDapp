package mdideas.devapp.tinhtiendienmdapp.extention

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import mdideas.devapp.tinhtiendienmdapp.R
import mdideas.devapp.tinhtiendienmdapp.databinding.LayoutBottomTabBarBinding

class BottomTabBar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {
    init {
        initView(attributeSet)
    }

    companion object {
        const val TAB_HOME = 0
        const val TAB_EVN = 1
        const val TAB_ABOUT = 2
    }

    private lateinit var binding: LayoutBottomTabBarBinding
    var imageViewList: Array<ImageView>? = null
    private var textViewList: Array<TextView>? = null
    var position = 0

    private fun initView(attributeSet: AttributeSet?) {
        binding = LayoutBottomTabBarBinding.inflate(LayoutInflater.from(context), this, true)
        imageViewList = arrayOf<ImageView>(
            binding.imgHome, binding.imgEvn, binding.imgAbout
        )
        textViewList = arrayOf<TextView>(
            binding.tvHome, binding.tvEvn, binding.tvAbout
        )
        setTabSelected(binding.imgHome)

    }

    fun onTabClick(viewPager: ViewPager2) {
        imageViewList?.forEach {
            binding.apply {
                when (it) {
                    imgHome -> {
                        llHome.setOnClickListener {
                            setTabSelected(imgHome)
                            position = TAB_HOME
                            viewPager.currentItem = TAB_HOME
                        }
                    }
                    imgEvn -> {
                        llEvn.setOnClickListener{
                            setTabSelected(imgEvn)
                            position = TAB_EVN
                            viewPager.currentItem = TAB_EVN
                        }
                    }
                    imgAbout -> {
                        llAbout.setOnClickListener{
                            setTabSelected(imgAbout)
                            position = TAB_ABOUT
                            viewPager.currentItem = TAB_ABOUT
                        }
                    }
                    else -> {
                        Log.d("Bottom TabBar", "Do not know tab")
                    }
                }
            }
        }
    }

    private fun setTabSelected(view: View) {
        for (i in imageViewList?.indices!!) {
            val imageView: ImageView = imageViewList!![i]
            if (view.id == imageView.id) {
                imageView.isSelected = true
                textViewList!![i]
                    .setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                imageView.isSelected = false
                textViewList!![i]
                    .setTextColor(ContextCompat.getColor(context, R.color.black))
            }
        }
    }
}