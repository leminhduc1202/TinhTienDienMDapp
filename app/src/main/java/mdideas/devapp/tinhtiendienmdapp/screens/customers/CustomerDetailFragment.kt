package mdideas.devapp.tinhtiendienmdapp.screens.customers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mdideas.devapp.tinhtiendienmdapp.databinding.FragmentDetailCustomersBinding
import mdideas.devapp.tinhtiendienmdapp.model.EvnData

class CustomerDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailCustomersBinding
    var positionType = 0
    private var evnAdapter = EvnAdapter()
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
                showViewCustomerCitizen()
            }
            1 -> {


            }
            2 -> {


            }
            3 -> {

            }
            4 -> {


            }
            5 -> {


            }
            6 -> {


            }
        }
    }

    private fun showViewCustomerCitizen(){
        binding.apply {
            rcvData.apply {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = evnAdapter
            }
        }
        val listEvnData = ArrayList<EvnData>()
        listEvnData.apply {
            add(EvnData(0, "Bac thang 1", 1728, 50, 0))
            add(EvnData(1, "Bac thang 2", 1786, 50, 0))
            add(EvnData(2, "Bac thang 3", 2074,100, 0))
            add(EvnData(3, "Bac thang 4", 2612, 100, 0))
            add(EvnData(4, "Bac thang 5", 2919, 100, 0))
            add(EvnData(5, "Bac thang 6", 3015, 0, 0))
        }
        evnAdapter.setLisEvent(listEvnData)
    }
}