package mdideas.devapp.tinhtiendienmdapp.extention

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import mdideas.devapp.tinhtiendienmdapp.extention.BottomTabBar.Companion.TAB_ABOUT
import mdideas.devapp.tinhtiendienmdapp.extention.BottomTabBar.Companion.TAB_EVN
import mdideas.devapp.tinhtiendienmdapp.extention.BottomTabBar.Companion.TAB_HOME
import mdideas.devapp.tinhtiendienmdapp.extention.BottomTabBar.Companion.TAB_SETTING
import mdideas.devapp.tinhtiendienmdapp.screens.AboutFragment
import mdideas.devapp.tinhtiendienmdapp.screens.EvnDataFragment
import mdideas.devapp.tinhtiendienmdapp.screens.HomePageFragment
import mdideas.devapp.tinhtiendienmdapp.screens.SettingFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity, var lisFragment: ArrayList<Fragment>) :
    FragmentStateAdapter(fragmentActivity,) {

    override fun getItemCount(): Int = lisFragment.size

    override fun createFragment(position: Int): Fragment {
        when (position) {
            TAB_HOME -> {
                return HomePageFragment()
            }
            TAB_EVN -> {
                return EvnDataFragment()
            }
            TAB_ABOUT -> {
                return AboutFragment()
            }
            TAB_SETTING -> {
                return SettingFragment()
            }
            else -> {
                return HomePageFragment()
            }
        }
    }


}