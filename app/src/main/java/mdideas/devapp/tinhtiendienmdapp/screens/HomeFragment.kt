package mdideas.devapp.tinhtiendienmdapp.screens

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.coroutines.flow.Flow
import mdideas.devapp.tinhtiendienmdapp.R
import mdideas.devapp.tinhtiendienmdapp.ResultActivity
import mdideas.devapp.tinhtiendienmdapp.ResultActivity.Companion.TYPED_CITIZEN
import mdideas.devapp.tinhtiendienmdapp.databinding.FragmentHomeBinding
import mdideas.devapp.tinhtiendienmdapp.extention.PrimaryButtonView
import mdideas.devapp.tinhtiendienmdapp.extention.gone
import mdideas.devapp.tinhtiendienmdapp.extention.visible
import mdideas.devapp.tinhtiendienmdapp.model.EvnData
import mdideas.devapp.tinhtiendienmdapp.model.EvnResponse
import mdideas.devapp.tinhtiendienmdapp.screens.customers.EvnAdapter
import java.text.NumberFormat
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val database = FirebaseDatabase.getInstance(ResultActivity.URL_REALTIME_DATABASE)
    private var reference: DatabaseReference? = null
    val listEvnData = ArrayList<EvnData>()
    private var evnAdapter = EvnAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reference = database.getReference(ResultActivity.URL_EVN_DATA)
        reference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val evnResponse = snapshot.getValue(EvnResponse::class.java)
                evnResponse?.listEvn?.forEach {
                    if (it.typedCustomer == TYPED_CITIZEN) {
                        listEvnData.add(it)
                    }
                }
                evnAdapter.setListEvent(listEvnData)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", error.message)
            }
        })
        setEvnDataAdapter()
        handleClickCalculate(listEvnData)
    }

    private fun setEvnDataAdapter() {
        binding.apply {
            pbvCalculate.handleEnable(false)
            lnTotalAmount.gone()
            rcvData.apply {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = evnAdapter
            }
        }
    }

    private fun handleClickCalculate(listEvn: ArrayList<EvnData>) {
        binding.inputText.addTextChangedListener {
            binding.pbvCalculate.apply {
                handleEnable(it.toString().isNotEmpty())
                buttonViewClickListener = object : PrimaryButtonView.OnPrimaryButtonView {
                    override fun onClickPrimaryButtonView(view: View?) {
                        calculateElectricOutput(it.toString().toInt(), listEvn)
                        it?.clear()
                    }
                }
            }
        }
    }

    private fun calculateElectricOutput(outPut: Int, listEvn: ArrayList<EvnData>) {
        val nf: NumberFormat = NumberFormat.getInstance(Locale.US)
        val listPrice = ArrayList<Int>()
        val listThreshold = ArrayList<Int>()
        val listCitizen = ArrayList<EvnData>()
        listEvn.forEach {
            it.electricPrice?.let { it1 -> listPrice.add(it1) }
            it.electricOutput?.let { it1 -> listThreshold.add(it1) }
        }
        val billResult = calculateElectricityBillDetail(outPut, listPrice, listThreshold)
        for (i in 0 until billResult.first.size) {
            val citizenBill = billResult.first[i]
            val evnData = listEvn[i]
            listCitizen.add(
                EvnData(
                    TYPED_CITIZEN,
                    evnData.typedPrice,
                    evnData.electricPrice,
                    citizenBill.consumedUnits,
                    citizenBill.billAmount,
                    ""
                )
            )
        }
        evnAdapter.setListEvent(listCitizen)
        binding.apply {
            lnTotalAmount.visible()
            tvTotalAmountNoVat.text = getString(R.string.total_amount_without_vat, nf.format(billResult.second))
            tvTotalAmountVat.text = getString(R.string.total_amount_vat, nf.format(billResult.second / 10))
            tvTotalAmount.text = Html.fromHtml(getString(R.string.html_text,nf.format(billResult.second + (billResult.second / 10))),0)
        }
        val flow : Flow<EvnData>

    }

    data class BillItem(val consumedUnits: Int, val billAmount: Int)

    private fun calculateElectricityBillDetail(
        units: Int, priceList: ArrayList<Int>, thresholdList: ArrayList<Int>
    ): Pair<ArrayList<BillItem>, Int> {
        val billList = ArrayList<BillItem>()
        val totalBill: Pair<ArrayList<BillItem>, Int>
        var totalElectricBill = 0
        var remainingUnits = units

        for (i in 0 until priceList.size) {
            val currentPrice = priceList[i]
            val threshold = thresholdList[i]

            if (i < 5) {
                if (remainingUnits > threshold) {
                    val consumedUnits = threshold
                    val currentBill = consumedUnits * currentPrice
                    billList.add(BillItem(consumedUnits, currentBill))
                    totalElectricBill += consumedUnits * currentPrice
                    remainingUnits -= consumedUnits
                } else {
                    val currentBill = remainingUnits * currentPrice
                    billList.add(BillItem(remainingUnits, currentBill))
                    totalElectricBill += remainingUnits * currentPrice
                    break
                }
            } else {
                val consumedUnits = remainingUnits
                val currentBill = consumedUnits * currentPrice
                billList.add(BillItem(consumedUnits, currentBill))
                totalElectricBill += remainingUnits * currentPrice
                break
            }
        }
        totalBill = Pair(billList, totalElectricBill)
        return totalBill
    }

}