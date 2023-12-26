package mdideas.devapp.tinhtiendienmdapp.screens

import android.os.Bundle
import android.view.*
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import mdideas.devapp.tinhtiendienmdapp.databinding.FragmentEvnPageBinding


class EvnDataFragment : Fragment() {

    private lateinit var binding: FragmentEvnPageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEvnPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpWebView()
    }

    private fun setUpWebView() {
        binding.webViewEvn.apply {
            loadUrl("https://www.evn.com.vn/c3/calc/Cong-cu-tinh-hoa-don-tien-dien-9-172.aspx")
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            clearCache(true)
            clearHistory()
            canGoBack()
            setOnKeyListener(View.OnKeyListener { v, key, event ->
                if (key == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP
                    && this.canGoBack()
                ) {
                    this.goBack()
                    return@OnKeyListener true
                }
                false
            })
        }
    }
}