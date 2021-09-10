package mdideas.devapp.tinhtiendienmdapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import mdideas.devapp.tinhtiendienmdapp.databinding.ActivityResultBinding
import java.text.NumberFormat
import java.util.*

class ResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

//    Nhap tong so dien nang tieu thu

        binding.tinhToan.setOnClickListener {
        // Khai tong dien muon tinh
        val tongDienNang: Int = binding.tongDienNang.text.toString().toInt()
        //dinh dang so them "," vao so tien
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
        val bac01: Int = if (tongDienNang >= 100) {
            100
        } else {
            tongDienNang
        }
        binding.dienTieuThuBac01.text = bac01.toString()
        val thanhTien01: Int = bac01 * donGiaBac1
        binding.thanhTienBac01.text = nf.format(thanhTien01).toString()

        //So dien tieu thu bac 2
        val bac02: Int = if (tongDienNang >= 200) {
            100
        } else {
            if (tongDienNang < 200) {
                tongDienNang - bac01
            } else {
                0
            }
        }
        binding.dienTieuThuBac02.text = bac02.toString()
        val thanhTien02: Int = bac02 * donGiaBac2
        binding.thanhTienBac02.text = nf.format(thanhTien02).toString()

        //So dien tieu thu bac 3
        val bac03: Int = if (tongDienNang >= 400) {
            200
        } else {
            if (tongDienNang < 400) {
                tongDienNang - bac01 - bac02
            } else {
                0
            }
        }
        binding.dienTieuThuBac03.text = bac03.toString()
        val thanhTien03: Int = bac03 * donGiaBac3
        binding.thanhTienBac03.text = nf.format(thanhTien03).toString()

        //So dien tieu thu bac 4
        val bac04: Int = if (tongDienNang >= 600) {
            200
        } else {
            if (tongDienNang < 600) {
                tongDienNang - bac01 - bac02 - bac03
            } else {
                0
            }
        }
        binding.dienTieuThuBac04.text = bac04.toString()
        val thanhTien04: Int = bac04 * donGiaBac4
        binding.thanhTienBac04.text = nf.format(thanhTien04).toString()

        //So dien tieu thu bac 5
        val bac05: Int = if (tongDienNang >= 800) {
            200
        } else {
            if (tongDienNang < 800) {
                tongDienNang - bac01 - bac02 - bac03 - bac04
            } else {
                0
            }
        }
        binding.dienTieuThuBac05.text = bac05.toString()
        val thanhTien05: Int = bac05 * donGiaBac5
        binding.thanhTienBac05.text = nf.format(thanhTien05).toString()

        //So dien tieu thu bac 6
        val bac06: Int = if (tongDienNang > 800) {
            tongDienNang - bac01 - bac02 - bac03 - bac04 - bac05
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
        binding.tongTienThanhToan.text = nf.format(tongTienChuaThue + tienThueGTGT).toString()
// Alert Dialog
        val mAlertDialog = AlertDialog.Builder(this@ResultActivity)
        mAlertDialog.setTitle("Tổng tiền điện của bạn: ")
        mAlertDialog.setIcon(R.drawable.ic_app)
        mAlertDialog
            .setMessage("Tiền điện chưa thuế: ${nf.format(tongTienChuaThue)} VNĐ \n"
                        +"Tiền thuế: ${nf.format(tienThueGTGT)} VNĐ \n"
                        +"Tổng tiền bao gồm thuế: ${nf.format(tongTienChuaThue + tienThueGTGT)} VNĐ")
        mAlertDialog.setCancelable(false)
        mAlertDialog.setPositiveButton("OK"){_,_ ->
            finish()
        }
        mAlertDialog.setNegativeButton("Chi tiết"){_,_->
            Toast.makeText(this@ResultActivity, "Chi tiết tiền điện", Toast.LENGTH_LONG).show()
        }
        mAlertDialog.create().show()
//        Toast.makeText(applicationContext,"Tổng tiền điện của bạn bao gồm thuế là: ${nf.format(tongTienChuaThue + tienThueGTGT)} VNĐ",Toast.LENGTH_LONG).show()
        hideKeyboard(binding.tinhToan)
        }
    }
    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }
}


