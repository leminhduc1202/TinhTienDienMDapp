package mdideas.devapp.tinhtiendienmdapp.screens

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import mdideas.devapp.tinhtiendienmdapp.R
import mdideas.devapp.tinhtiendienmdapp.ResultActivity
import mdideas.devapp.tinhtiendienmdapp.ResultActivity.Companion.TYPED_CITIZEN
import mdideas.devapp.tinhtiendienmdapp.ResultActivity.Companion.URL_EVN_TAX
import mdideas.devapp.tinhtiendienmdapp.databinding.FragmentHomeBinding
import mdideas.devapp.tinhtiendienmdapp.extention.PrimaryButtonView
import mdideas.devapp.tinhtiendienmdapp.extention.gone
import mdideas.devapp.tinhtiendienmdapp.extention.hideSoftKeyboard
import mdideas.devapp.tinhtiendienmdapp.extention.visible
import mdideas.devapp.tinhtiendienmdapp.model.EvnData
import mdideas.devapp.tinhtiendienmdapp.model.EvnResponse
import mdideas.devapp.tinhtiendienmdapp.model.EvnTaxResponse
import mdideas.devapp.tinhtiendienmdapp.screens.customers.EvnAdapter
import java.text.NumberFormat
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val database = FirebaseDatabase.getInstance(ResultActivity.URL_REALTIME_DATABASE)
    private var reference: DatabaseReference? = null
    private var referenceTax: DatabaseReference? = null
    val listEvnData = ArrayList<EvnData>()
    private var evnAdapter = EvnAdapter()
    private var evnTax : EvnTaxResponse ?= null

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
        referenceTax = database.getReference(URL_EVN_TAX)
        referenceTax?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                evnTax = snapshot.getValue(EvnTaxResponse::class.java)
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
        binding.apply {
            inputText.addTextChangedListener { text ->
                binding.pbvCalculate.apply {
                    handleEnable(text.toString().isNotEmpty())
                    buttonViewClickListener = object : PrimaryButtonView.OnPrimaryButtonView {
                        override fun onClickPrimaryButtonView(view: View?) {
                            calculateElectricOutput(text.toString().toInt(), listEvn)
                            text?.clear()
                        }
                    }
                }
                inputText.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        calculateElectricOutput(text.toString().toInt(), listEvn)
                        text?.clear()
                        hideSoftKeyboard(requireActivity())
                        return@setOnEditorActionListener true
                    }
                    false
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
            if (evnTax?.evnTaxUpdate != null){
                tvTotalAmountVat.text = getString(
                    R.string.total_amount_vat,
                    evnTax?.evnTaxUpdate.toString(),
                    nf.format((billResult.second / 100) * evnTax!!.evnTaxUpdate!!)
                )
                tvTotalAmount.text = Html.fromHtml(getString(R.string.html_text,nf.format(billResult.second + (billResult.second / 100) * evnTax!!.evnTaxUpdate!!)),0)
            }
        }

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
                    val consumedUnitsBelowFive = threshold
                    val currentBill = consumedUnitsBelowFive * currentPrice
                    billList.add(BillItem(consumedUnitsBelowFive, currentBill))
                    totalElectricBill += consumedUnitsBelowFive * currentPrice
                    remainingUnits -= consumedUnitsBelowFive
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