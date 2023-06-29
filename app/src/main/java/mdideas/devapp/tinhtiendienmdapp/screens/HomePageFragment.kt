package mdideas.devapp.tinhtiendienmdapp.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import mdideas.devapp.tinhtiendienmdapp.R
import mdideas.devapp.tinhtiendienmdapp.ResultActivity
import mdideas.devapp.tinhtiendienmdapp.ResultActivity.Companion.TYPED_CITIZEN
import mdideas.devapp.tinhtiendienmdapp.databinding.FragmentHomePageBinding
import mdideas.devapp.tinhtiendienmdapp.extention.PrimaryButtonView
import mdideas.devapp.tinhtiendienmdapp.extention.gone
import mdideas.devapp.tinhtiendienmdapp.extention.visible
import mdideas.devapp.tinhtiendienmdapp.model.CustomerData
import mdideas.devapp.tinhtiendienmdapp.model.EvnData
import mdideas.devapp.tinhtiendienmdapp.model.EvnResponse
import mdideas.devapp.tinhtiendienmdapp.screens.customers.CustomerAdapter
import mdideas.devapp.tinhtiendienmdapp.screens.customers.EvnAdapter
import java.text.NumberFormat
import java.util.*

class HomePageFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    private lateinit var customerAdapter: CustomerAdapter
    private var evnAdapter = EvnAdapter()
    private val database = FirebaseDatabase.getInstance(ResultActivity.URL_REALTIME_DATABASE)
    private var reference: DatabaseReference? = null
    val listEvnData = ArrayList<EvnData>()
    val listEvnCitizen = ArrayList<EvnData>()
    val listEvnCompany = ArrayList<EvnData>()
    val listEvnIndustry = ArrayList<EvnData>()
    val listEvnAdmin = ArrayList<EvnData>()
    val listEvnHospital = ArrayList<EvnData>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomePageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reference = database.getReference(ResultActivity.URL_EVN_DATA)
        binding.apply {
            tinhToan.handleEnable(false)
            tongDienNang.doAfterTextChanged {
                tinhToan.apply {
                    handleEnable(it.toString().isNotEmpty())
                    buttonViewClickListener =
                        object : PrimaryButtonView.OnPrimaryButtonView {
                            override fun onClickPrimaryButtonView(view: View?) {
                                calculateElectric(it.toString().toInt())
                            }
                        }
                }
            }
            tvSelectCustomer.visible()
        }
        reference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val evnResponse = snapshot.getValue(EvnResponse::class.java)
                listEvnData.addAll(evnResponse?.listEvn ?: arrayListOf())
                listEvnData.forEach {
                    when (it.typedCustomer) {
                        ResultActivity.TYPED_CITIZEN -> {
                            listEvnCitizen.add(it)
                        }
                        ResultActivity.TYPED_COMPANY -> {
                            listEvnCompany.add(it)
                        }
                        ResultActivity.TYPED_INDUSTRY -> {
                            listEvnIndustry.add(it)
                        }
                        ResultActivity.TYPED_ADMIN -> {
                            listEvnAdmin.add(it)
                        }
                        else -> {
                            listEvnHospital.add(it)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", error.message)
            }
        })
        setEvnDataAdapter()
        setCustomersAdapter()

        binding.inputText.addTextChangedListener {
            binding.pbvCalculate.apply {
                handleEnable(it.toString().isNotEmpty())
                buttonViewClickListener =
                    object : PrimaryButtonView.OnPrimaryButtonView {
                        override fun onClickPrimaryButtonView(view: View?) {
                            calculateElectricOutput(
                                TYPED_CITIZEN,
                                it.toString().toInt(),
                                listEvnCitizen
                            )
                        }
                    }
            }
        }
    }

    private fun setCustomersAdapter() {
        val customerList = ArrayList<CustomerData>()
        customerList.apply {
            add(CustomerData(0, "Sinh hoạt", false))
            add(CustomerData(1, "Kinh doanh", false))
            add(CustomerData(2, "Sản xuất", false))
            add(CustomerData(3, "Hành chính sự nghiệp", false))
            add(CustomerData(4, "Cơ quan bệnh viện", false))
        }

        customerAdapter = CustomerAdapter(customerList)
        binding.rcvCustomer.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = customerAdapter
        }
        customerAdapter.setListenerItem(object : CustomerAdapter.OnItemClickCustomer {
            override fun onClickItemCustomer(customerData: CustomerData) {
                binding.tvSelectCustomer.gone()
                when (customerData.id) {
                    0 -> {
                        evnAdapter.setListEvent(listEvnCitizen)
                    }
                    1 -> {
                        evnAdapter.setListEvent(listEvnCompany)
                    }
                    2 -> {
                        evnAdapter.setListEvent(listEvnIndustry)
                    }
                    3 -> {
                        evnAdapter.setListEvent(listEvnAdmin)
                    }
                    4 -> {
                        evnAdapter.setListEvent(listEvnHospital)
                    }
                }
            }
        })
    }

    private fun setEvnDataAdapter() {
        binding.apply {
            rcvData.apply {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = evnAdapter
            }
        }
    }

    private fun calculateElectricOutput(
        typeCustomer: String,
        outPut: Int,
        listEvn: ArrayList<EvnData>
    ) {
        val nf: NumberFormat = NumberFormat.getInstance(Locale.US)
        val listEvnOutput = ArrayList<EvnData>()
        when (typeCustomer) {
            ResultActivity.TYPED_CITIZEN -> {
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
                binding.tvTotalAmount.apply {
                    visible()
                    text = getString(R.string.total_amount, nf.format(billResult.second))
                }
                println("Bậc thang $listCitizen")
                println("Bậc thang ${billResult.second}")
            }
            ResultActivity.TYPED_COMPANY -> {

            }
            ResultActivity.TYPED_INDUSTRY -> {

            }
            ResultActivity.TYPED_ADMIN -> {

            }
            else -> {

            }
        }
        listEvnOutput.addAll(listEvn)
    }

    data class BillItem(val consumedUnits: Int, val billAmount: Int)

    private fun calculateElectricityBillDetail(
        units: Int,
        priceList: ArrayList<Int>,
        thresholdList: ArrayList<Int>
    ): Pair<ArrayList<BillItem>, Int> {
        val billList = ArrayList<BillItem>()
        val totalBill: Pair<ArrayList<BillItem>, Int>
        var totalElectricBill = 0
        var remainingUnits = units

        for (i in 0 until priceList.size) {
            val currentPrice = priceList[i]
            val threshold = thresholdList[i] // Ngưỡng của bậc thang

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

    private fun calculateElectric(electricNumber: Int) {
        val nf: NumberFormat = NumberFormat.getInstance(Locale.US)
        //Khai don gia cac bac dien
        val donGiaBac1: Int = 1678
        val donGiaBac2: Int = 1734
        val donGiaBac3: Int = 2014
        val donGiaBac4: Int = 2536
        val donGiaBac5: Int = 2834
        val donGiaBac6: Int = 2927
        val thueGTGT: Int = 10

        //So dien tieu thu bac 1
        val bac01: Int = if (electricNumber >= 100) {
            100
        } else {
            electricNumber
        }
        binding.dienTieuThuBac01.text = bac01.toString()
        val thanhTien01: Int = bac01 * donGiaBac1
        binding.thanhTienBac01.text = nf.format(thanhTien01).toString()

        //So dien tieu thu bac 2
        val bac02: Int = if (electricNumber >= 200) {
            100
        } else {
            if (electricNumber < 200) {
                electricNumber - bac01
            } else {
                0
            }
        }
        binding.dienTieuThuBac02.text = bac02.toString()
        val thanhTien02: Int = bac02 * donGiaBac2
        binding.thanhTienBac02.text = nf.format(thanhTien02).toString()

        //So dien tieu thu bac 3
        val bac03: Int = if (electricNumber >= 400) {
            200
        } else {
            if (electricNumber < 400) {
                electricNumber - bac01 - bac02
            } else {
                0
            }
        }
        binding.dienTieuThuBac03.text = bac03.toString()
        val thanhTien03: Int = bac03 * donGiaBac3
        binding.thanhTienBac03.text = nf.format(thanhTien03).toString()

        //So dien tieu thu bac 4
        val bac04: Int = if (electricNumber >= 600) {
            200
        } else {
            if (electricNumber < 600) {
                electricNumber - bac01 - bac02 - bac03
            } else {
                0
            }
        }
        binding.dienTieuThuBac04.text = bac04.toString()
        val thanhTien04: Int = bac04 * donGiaBac4
        binding.thanhTienBac04.text = nf.format(thanhTien04).toString()

        //So dien tieu thu bac 5
        val bac05: Int = if (electricNumber >= 800) {
            200
        } else {
            if (electricNumber < 800) {
                electricNumber - bac01 - bac02 - bac03 - bac04
            } else {
                0
            }
        }
        binding.dienTieuThuBac05.text = bac05.toString()
        val thanhTien05: Int = bac05 * donGiaBac5
        binding.thanhTienBac05.text = nf.format(thanhTien05).toString()

        //So dien tieu thu bac 6
        val bac06: Int = if (electricNumber > 800) {
            electricNumber - bac01 - bac02 - bac03 - bac04 - bac05
        } else {
            0
        }
        binding.dienTieuThuBac06.text = bac06.toString()
        val thanhTien06: Int = bac06 * donGiaBac6
        binding.thanhTienBac06.text = nf.format(thanhTien06).toString()

        // Tong tien dien
        val tongTienChuaThue: Int =
            (thanhTien01 + thanhTien02 + thanhTien03 + thanhTien04 + thanhTien05 + thanhTien06)
        binding.tongTienChuaThue.text = nf.format(tongTienChuaThue).toString()
        val tienThueGTGT: Int = tongTienChuaThue / thueGTGT
        binding.tienThue.text = nf.format(tienThueGTGT).toString()
        binding.tongTienThanhToan.text =
            nf.format(tongTienChuaThue + tienThueGTGT).toString()
// Alert Dialog
        val mAlertDialog = AlertDialog.Builder(requireContext())
        mAlertDialog.setTitle("Tổng tiền điện của bạn: ")
        mAlertDialog.setIcon(R.drawable.ic_app)
        mAlertDialog
            .setMessage(
                "Tiền điện chưa thuế: ${nf.format(tongTienChuaThue)} VNĐ \n"
                        + "Tiền thuế: ${nf.format(tienThueGTGT)} VNĐ \n"
                        + "Tổng tiền bao gồm thuế: ${nf.format(tongTienChuaThue + tienThueGTGT)} VNĐ"
            )
        mAlertDialog.setCancelable(false)
        mAlertDialog.setPositiveButton("OK") { _, _ ->

        }
        mAlertDialog.setNegativeButton("Chi tiết") { _, _ ->
            Toast.makeText(requireContext(), "Chi tiết tiền điện", Toast.LENGTH_LONG)
                .show()
        }
        mAlertDialog.create().show()
//        Toast.makeText(applicationContext,"Tổng tiền điện của bạn bao gồm thuế là: ${nf.format(tongTienChuaThue + tienThueGTGT)} VNĐ",Toast.LENGTH_LONG).show()

    }
}