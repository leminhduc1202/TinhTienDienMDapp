package mdideas.devapp.tinhtiendienmdapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mdideas.devapp.tinhtiendienmdapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ketQuaTinhDien.setOnClickListener {
            var tongDienNang : Int = (binding.tongSoDienNang.text.toString()).toInt()


            var bac01 : Int =  if (tongDienNang >= 100){
                100
            }else{
                tongDienNang
            }
            binding.dienTieuThuBac01.text = bac01.toString()
            var thanhTien01 : Int = bac01 * 1678

            binding.thanhTienBac01.text = thanhTien01.toString()

            var bac02 : Int = if (tongDienNang >= 200) {
                100
            }else{
                if (tongDienNang < 200){
                    tongDienNang - bac01
                }else{
                    0
                }
            }
            binding.dienTieuThuBac02.text = bac02.toString()
            var thanhTien02 : Int = bac02 * 1734
            binding.thanhTienBac02.text = thanhTien02.toString()
            var bac03 : Int = if (tongDienNang >= 400) {
                200
            }else{
                if (tongDienNang < 400){
                    tongDienNang - bac01 - bac02
                }else{
                    0
                }
            }
            binding.dienTieuThuBac03.text = bac03.toString()
            var thanhTien03 : Int = bac03 * 2014
            binding.thanhTienBac03.text = thanhTien03.toString()
            var bac04 : Int = if (tongDienNang >= 600) {
                200
            }else{
                if (tongDienNang < 600){
                    tongDienNang - bac01 - bac02 - bac03
                }else{
                    0
                }
            }
            binding.dienTieuThuBac04.text = bac04.toString()
            var thanhTien04 : Int = bac04 * 2536
            binding.thanhTienBac04.text = thanhTien04.toString()
            var bac05 : Int = if (tongDienNang >= 800) {
                200
            }else{
                if (tongDienNang < 800){
                    tongDienNang - bac01 - bac02 - bac03 - bac04
                }else{
                    0
                }
            }
            binding.dienTieuThuBac05.text = bac05.toString()
            var thanhTien05 : Int = bac05 * 2834
            binding.thanhTienBac05.text = thanhTien05.toString()
            var bac06 : Int = if (tongDienNang > 800) {
                tongDienNang - bac01 - bac02 - bac03 - bac04 - bac05
            }else{
                0
            }
            binding.dienTieuThuBac06.text = bac06.toString()
            var thanhTien06 : Int = bac06 * 2927
            binding.thanhTienBac06.text = thanhTien06.toString()

            var tongTienChuaThue : Int = (thanhTien01 + thanhTien02 + thanhTien03 + thanhTien04 + thanhTien05 + thanhTien06)
            binding.tongTienChuaThue.text = tongTienChuaThue.toString()
            var tienThueGTGT : Int = tongTienChuaThue / 10
            binding.tienThue.text = tienThueGTGT.toString()
            binding.tongTienThanhToan.text = (tongTienChuaThue + tienThueGTGT).toString()
        }
    }
}