package mdideas.devapp.tinhtiendienmdapp.screens.customers

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import mdideas.devapp.tinhtiendienmdapp.ResultActivity.Companion.TYPED_ADMIN
import mdideas.devapp.tinhtiendienmdapp.ResultActivity.Companion.TYPED_CITIZEN
import mdideas.devapp.tinhtiendienmdapp.ResultActivity.Companion.TYPED_COMPANY
import mdideas.devapp.tinhtiendienmdapp.ResultActivity.Companion.TYPED_INDUSTRY
import mdideas.devapp.tinhtiendienmdapp.ResultActivity.Companion.URL_EVN_DATA
import mdideas.devapp.tinhtiendienmdapp.ResultActivity.Companion.URL_REALTIME_DATABASE
import mdideas.devapp.tinhtiendienmdapp.databinding.FragmentDetailCustomersBinding
import mdideas.devapp.tinhtiendienmdapp.model.EvnData
import mdideas.devapp.tinhtiendienmdapp.model.EvnResponse

class CustomerDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailCustomersBinding
    var positionType = 0
    private var evnAdapter = EvnAdapter()
    var listEvnData = ArrayList<EvnData>()

    companion object {
        const val TYPE_CUSTOMER = "TYPE_CUSTOMER"
        fun newInstance(position: Int, evnData: ArrayList<EvnData>): CustomerDetailFragment {
            val bundle = Bundle()
            bundle.putInt(TYPE_CUSTOMER, position)
            bundle.putParcelableArrayList(URL_EVN_DATA, evnData)
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

            if (it.containsKey(URL_EVN_DATA)){
                listEvnData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    it.getParcelableArrayList(URL_EVN_DATA, EvnData::class.java)!!
                } else {
                    it.getParcelableArrayList(URL_EVN_DATA)!!
                }
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

        binding.apply {
            rcvData.apply {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = evnAdapter
            }
            evnAdapter.setListEvent(listEvnData)
        }
    }

}