package mdideas.devapp.tinhtiendienmdapp.extention

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity, var lisFragment: ArrayList<Fragment>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = lisFragment.size

    override fun createFragment(position: Int): Fragment {
        return lisFragment[position]
    }

}