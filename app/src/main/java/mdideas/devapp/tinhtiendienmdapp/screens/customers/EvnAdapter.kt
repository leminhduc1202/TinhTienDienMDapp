package mdideas.devapp.tinhtiendienmdapp.screens.customers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import mdideas.devapp.tinhtiendienmdapp.databinding.ItemEvenDataBinding
import mdideas.devapp.tinhtiendienmdapp.model.EvnData
import java.text.NumberFormat
import java.util.*

class EvnAdapter() : RecyclerView.Adapter<EvnAdapter.EvnAdapterViewHolder>() {

    private val listEvnData = ArrayList<EvnData>()

    fun setListEvent(listEvn: ArrayList<EvnData>) {
        val diffUtilCallback = EvnDiffUtilCallback(listEvnData, listEvn)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        this.listEvnData.apply {
            clear()
            addAll(listEvn)
        }
        diffResult.dispatchUpdatesTo(this)
    }

    inner class EvnAdapterViewHolder(private val binding: ItemEvenDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(evnData: EvnData) {
            val nf: NumberFormat = NumberFormat.getInstance(Locale.US)
            binding.apply {
                evnData.apply {
                    tvTypedPrice.text = this.typedPrice
                    tvElectricPrice.text = nf.format(this.electricPrice)
                    tvElectricOutput.text = this.electricOutput.toString()
                    tvElectricAmount.text = nf.format(this.electricAmount)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvnAdapterViewHolder {
        return EvnAdapterViewHolder(
            ItemEvenDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EvnAdapterViewHolder, position: Int) {
        holder.bindView(listEvnData[position])
    }

    override fun getItemCount(): Int = listEvnData.size


}