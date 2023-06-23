package mdideas.devapp.tinhtiendienmdapp.extention

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import mdideas.devapp.tinhtiendienmdapp.R
import mdideas.devapp.tinhtiendienmdapp.databinding.LayoutPrimaryButtonViewBinding

class PrimaryButtonView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    init {
        initView(attributeSet)
    }

    private lateinit var binding: LayoutPrimaryButtonViewBinding
    var primaryButtonViewClickListener: OnPrimaryButtonView? = null
    var buttontext: String? = null
        set(value) {
            binding.nameBtn.text = value
            field = value
        }

    private var isLoading: Boolean? = null
        set(value) {
            binding.nameBtn.handleGoneVisibility(value != true)
            field = value
        }

    private var buttonStyle: String? = null
        set(value) {
            updateStyle(value)
            field = value
        }

    private fun updateStyle(value: String?) {
        if (value == "normal") {
            binding.ctPrimaryButtonView.setBackgroundResource(R.drawable.primary_button_background_selector)
        }
    }

    private fun isLoading(): Boolean {
        return isLoading ?: false
    }

    private fun hideLoading() {
        isLoading = false
    }

    fun handleEnable(isEnable: Boolean) {
        binding.ctPrimaryButtonView.isEnabled = isEnable
        if (!isEnable) {
            hideLoading()
        }
        binding.nameBtn.setTextColor(
            ContextCompat.getColor(
                context,
                if (isEnable) android.R.color.white else android.R.color.black
            )
        )
    }

    var marginHorizontal: Int = 0
        set(value) {
            binding.nameBtn.setMargins(start = value, end = value)
            field = value
        }

    var marginVertical: Int = 0
        set(value) {
            binding.nameBtn.setMargins(top = value, bottom = value)
            field = value
        }


    private fun initView(attributeSet: AttributeSet?) {
        binding = LayoutPrimaryButtonViewBinding.inflate(LayoutInflater.from(context), this, true)
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.PrimaryButtonView)
        try {
            buttontext = typedArray.getString(R.styleable.PrimaryButtonView_pb_text)
            buttonStyle = typedArray.getString(R.styleable.PrimaryButtonView_pb_style)
            marginHorizontal = typedArray.getDimensionPixelOffset(R.styleable.PrimaryButtonView_pb_margin_horizontal, 0)
            marginVertical = typedArray.getDimensionPixelOffset(R.styleable.PrimaryButtonView_pb_margin_vertical, 0)
        } finally {
            typedArray.recycle()
        }
        binding.ctPrimaryButtonView.safeClick({
            if (!isLoading()) {
                hideSoftKeyBoard()
                primaryButtonViewClickListener?.onClickPrimaryButtonView(it)
            }
        })
    }

    interface OnPrimaryButtonView {
        fun onClickPrimaryButtonView(view: View?)
    }

}