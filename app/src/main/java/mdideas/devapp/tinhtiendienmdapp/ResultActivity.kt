package mdideas.devapp.tinhtiendienmdapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import mdideas.devapp.tinhtiendienmdapp.databinding.ActivityResultBinding
import mdideas.devapp.tinhtiendienmdapp.extention.ViewPagerAdapter
import mdideas.devapp.tinhtiendienmdapp.screens.*

class ResultActivity : AppCompatActivity() {

    companion object {
        const val URL_REALTIME_DATABASE =
            "https://tinhtiendienmdapp-default-rtdb.asia-southeast1.firebasedatabase.app"
        const val URL_EVN_DATA = "evn_data"
        const val TYPED_CITIZEN = "EVN CITIZEN"
    }

    lateinit var binding: ActivityResultBinding
    private val lisFragment = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)

        lisFragment.apply {
            add(HomeFragment())
            add(EvnDataFragment())
            add(AboutFragment())
            add(SettingFragment())
        }
        innitViewPager(lisFragment)
    }

    private fun innitViewPager(lisFragment: ArrayList<Fragment>) {
        binding.apply {
            bTabBar.onTabClick(viewPagerMain)
            viewPagerMain.apply {
                isUserInputEnabled = false
                adapter = ViewPagerAdapter(this@ResultActivity, lisFragment)
            }
        }
    }
}


