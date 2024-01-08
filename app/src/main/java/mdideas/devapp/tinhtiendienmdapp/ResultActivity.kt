package mdideas.devapp.tinhtiendienmdapp

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import mdideas.devapp.tinhtiendienmdapp.databinding.ActivityResultBinding
import mdideas.devapp.tinhtiendienmdapp.extention.BroadCastReceiverHolder
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
    private val airPlaneMode = BroadCastReceiverHolder()
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        if (firebaseAuth.currentUser == null){
            firebaseAuth.signInWithEmailAndPassword(
                BuildConfig.FIREBASE_EMAIL,
                BuildConfig.FIREBASE_PASSWORD
            )
        }

        registerReceiver(airPlaneMode, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        lisFragment.apply {
            add(HomeFragment())
            add(EvnDataFragment())
            add(AboutFragment())
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

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airPlaneMode)
    }
}


