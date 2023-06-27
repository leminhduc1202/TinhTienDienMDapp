package mdideas.devapp.tinhtiendienmdapp.screens.customers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mdideas.devapp.tinhtiendienmdapp.databinding.ItemCustomerBinding
import mdideas.devapp.tinhtiendienmdapp.extention.PrimaryButtonView
import mdideas.devapp.tinhtiendienmdapp.model.CustomerData

class CustomerAdapter(
    private val customerList: ArrayList<CustomerData>,

    ) :
    RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    private var onItemClick: OnItemClickCustomer? = null

    fun setListenerItem(listener: OnItemClickCustomer) {
        this.onItemClick = listener
    }

    inner class CustomerViewHolder(private val binding: ItemCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(customer: CustomerData) {
            binding.pbvCustomerName.apply {
                buttontext = customer.name
                primaryButtonViewClickListener = object : PrimaryButtonView.OnPrimaryButtonView {
                    override fun onClickPrimaryButtonView(view: View?) {
                        onItemClick?.onClickItemCustomer(customer)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        return CustomerViewHolder(
            ItemCustomerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.bindView(customerList[position])
    }

    override fun getItemCount(): Int = customerList.size

    interface OnItemClickCustomer {
        fun onClickItemCustomer(customerData: CustomerData)
    }
}