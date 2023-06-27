package mdideas.devapp.tinhtiendienmdapp.screens.customers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mdideas.devapp.tinhtiendienmdapp.databinding.ItemEvenDataBinding
import mdideas.devapp.tinhtiendienmdapp.model.EvnData

class EvnAdapter() : RecyclerView.Adapter<EvnAdapter.EvnAdapterViewHolder>() {

    private val listEvnData = ArrayList<EvnData>()

    fun setLisEvent(listEvn: ArrayList<EvnData>) {
        this.listEvnData.apply {
            clear()
            addAll(listEvn)
        }
        notifyDataSetChanged()
    }

    inner class EvnAdapterViewHolder(private val binding: ItemEvenDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(evnData: EvnData) {
            binding.apply {
                evnData.apply {
                    tvTypedPrice.text = this.typedPrice
                    tvElectricPrice.text = this.electricPrice.toString()
                    tvElectricOutput.text = this.electricOutput.toString()
                    tvElectricAmount.text = this.electricAmount.toString()
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