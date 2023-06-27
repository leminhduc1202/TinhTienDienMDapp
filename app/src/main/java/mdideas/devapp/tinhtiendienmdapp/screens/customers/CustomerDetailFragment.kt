package mdideas.devapp.tinhtiendienmdapp.screens.customers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import mdideas.devapp.tinhtiendienmdapp.ResultActivity.Companion.URL_EVN_DATA
import mdideas.devapp.tinhtiendienmdapp.ResultActivity.Companion.URL_REALTIME_DATABASE
import mdideas.devapp.tinhtiendienmdapp.databinding.FragmentDetailCustomersBinding
import mdideas.devapp.tinhtiendienmdapp.model.EvnData
import mdideas.devapp.tinhtiendienmdapp.model.EvnResponse

class CustomerDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailCustomersBinding
    var positionType = 0
    private var evnAdapter = EvnAdapter()
    private val database = FirebaseDatabase.getInstance(URL_REALTIME_DATABASE)
    private val reference = database.getReference(URL_EVN_DATA)

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
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val evnResponse = snapshot.getValue(EvnResponse::class.java)
                evnResponse?.listEvn?.let { listEvnData.addAll(it) }
                evnAdapter.setLisEvent(listEvnData)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", error.message)
            }
        })
    }
}