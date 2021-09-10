package mdideas.devapp.tinhtiendienmdapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mdideas.devapp.tinhtiendienmdapp.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    var hasPackage: Boolean = false
    var uriString: String = "Chia sẻ ứng dung Tính tiền điện "+" https://play.google.com/store/apps/details?id=mdideas.devapp.tinhtiendienmdapp"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tinhKetQua.setOnClickListener {
            val intent = Intent(this,ResultActivity::class.java)
            startActivity(intent)
        }
//       Share Link
        binding.shareLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,uriString)
            startActivity(Intent.createChooser(intent,"Chia sẻ cho nhau"))
        }
    }
}

    //Nhap tong so dien nang tieu thu
//    private fun tinhTongDienNang() {
//        // Khai tong dien muon tinh
//        var tongDienNang: Int = (binding1.nhapSoDienNang.text.toString()).toInt()
//        //dinh dang so them "," vao so tien
//        val nf: NumberFormat = NumberFormat.getInstance(Locale.US)
//        //Khai don gia cac bac dien
//        var donGiaBac1: Int = 1678
//        var donGiaBac2: Int = 1734
//        var donGiaBac3: Int = 2014
//        var donGiaBac4: Int = 2536
//        var donGiaBac5: Int = 2834
//        var donGiaBac6: Int = 2927
//        var thueGTGT: Int = 10
//
//        //So dien tieu thu bac 1
//        var bac01: Int = if (tongDienNang >= 100) {
//            100
//        } else {
//            tongDienNang
//        }
//        binding!!.dienTieuThuBac01.text = bac01.toString()
//        var thanhTien01: Int = bac01 * donGiaBac1
//        binding!!.thanhTienBac01.text = nf.format(thanhTien01).toString()
//
//        //So dien tieu thu bac 2
//        var bac02: Int = if (tongDienNang >= 200) {
//            100
//        } else {
//            if (tongDienNang < 200) {
//                tongDienNang - bac01
//            } else {
//                0
//            }
//        }
//        binding!!.dienTieuThuBac02.text = bac02.toString()
//        var thanhTien02: Int = bac02 * donGiaBac2
//        binding!!.thanhTienBac02.text = nf.format(thanhTien02).toString()
//
//        //So dien tieu thu bac 3
//        var bac03: Int = if (tongDienNang >= 400) {
//            200
//        } else {
//            if (tongDienNang < 400) {
//                tongDienNang - bac01 - bac02
//            } else {
//                0
//            }
//        }
//        binding!!.dienTieuThuBac03.text = bac03.toString()
//        var thanhTien03: Int = bac03 * donGiaBac3
//        binding!!.thanhTienBac03.text = nf.format(thanhTien03).toString()
//
//        //So dien tieu thu bac 4
//        var bac04: Int = if (tongDienNang >= 600) {
//            200
//        } else {
//            if (tongDienNang < 600) {
//                tongDienNang - bac01 - bac02 - bac03
//            } else {
//                0
//            }
//        }
//        binding!!.dienTieuThuBac04.text = bac04.toString()
//        var thanhTien04: Int = bac04 * donGiaBac4
//        binding!!.thanhTienBac04.text = nf.format(thanhTien04).toString()
//
//        //So dien tieu thu bac 5
//        var bac05: Int = if (tongDienNang >= 800) {
//            200
//        } else {
//            if (tongDienNang < 800) {
//                tongDienNang - bac01 - bac02 - bac03 - bac04
//            } else {
//                0
//            }
//        }
//        binding!!.dienTieuThuBac05.text = bac05.toString()
//        var thanhTien05: Int = bac05 * donGiaBac5
//        binding!!.thanhTienBac05.text = nf.format(thanhTien05).toString()
//
//        //So dien tieu thu bac 6
//        var bac06: Int = if (tongDienNang > 800) {
//            tongDienNang - bac01 - bac02 - bac03 - bac04 - bac05
//        } else {
//            0
//        }
//        binding!!.dienTieuThuBac06.text = bac06.toString()
//        var thanhTien06: Int = bac06 * donGiaBac6
//        binding!!.thanhTienBac06.text = nf.format(thanhTien06).toString()
//
//        // Tong tien dien
//        var tongTienChuaThue: Int =
//            (thanhTien01 + thanhTien02 + thanhTien03 + thanhTien04 + thanhTien05 + thanhTien06)
//        binding!!.tongTienChuaThue.text = nf.format(tongTienChuaThue).toString()
//        var tienThueGTGT: Int = tongTienChuaThue / thueGTGT
//        binding!!.tienThue.text = nf.format(tienThueGTGT).toString()
//        binding!!.tongTienThanhToan.text = nf.format(tongTienChuaThue + tienThueGTGT).toString()
//        //Dialog tổng tiền điện
//        val addDialog = AlertDialog.Builder(this)
//            .setTitle("Kết quả tính tiền điện")
//            .setMessage("Tổng số tiền điện: ${nf.format(tongTienChuaThue + tienThueGTGT)} VNĐ ")
//            .create()
//        addDialog.show()
//        // Toast sau khi tinh toan
//        //Toast.makeText(applicationContext,"Tổng tiền điện của bạn bao gồm thuế là: ${nf.format(tongTienChuaThue + tienThueGTGT)} VNĐ",Toast.LENGTH_LONG).show()
//        //hide keyboard
//        closeKeyBoard((binding1.nhapSoDienNang))
//    }


