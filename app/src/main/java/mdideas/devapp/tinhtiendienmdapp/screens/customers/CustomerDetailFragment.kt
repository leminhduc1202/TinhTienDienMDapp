package mdideas.devapp.tinhtiendienmdapp.screens.customers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import mdideas.devapp.tinhtiendienmdapp.databinding.FragmentDetailCustomersBinding

class CustomerDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailCustomersBinding
    var positionType = 0

    companion object {
        const val TYPE_CUSTOMER = "TYPE_CUSTOMER"
        fun newInstance(position: Int): CustomerDetailFragment {
            val bundle = Bundle()
            bundle.putInt(TYPE_CUSTOMER, position)
            val fragment = CustomerDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(TYPE_CUSTOMER)) {
                positionType = it.getInt(TYPE_CUSTOMER)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailCustomersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (positionType) {
            0 -> {
                binding.tvCustomer.text = "0"
            }
            1 -> {
                binding.tvCustomer.text = "1"

            }
            2 -> {
                binding.tvCustomer.text = "2"

            }
            3 -> {
                binding.tvCustomer.text = "3"

            }
            4 -> {
                binding.tvCustomer.text = "4"

            }
            5 -> {
                binding.tvCustomer.text = "5"

            }
            6 -> {
                binding.tvCustomer.text = "6"

            }
        }
    }
}