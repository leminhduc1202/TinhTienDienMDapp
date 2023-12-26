package mdideas.devapp.tinhtiendienmdapp.screens

import android.os.Bundle
import android.view.*
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import mdideas.devapp.tinhtiendienmdapp.BuildConfig
import mdideas.devapp.tinhtiendienmdapp.R
import mdideas.devapp.tinhtiendienmdapp.databinding.FragmentAboutPageBinding

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutPageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpWebView()
    }

    private fun setUpWebView() {
        binding.apply {
            tvVersion.text = getString(R.string.version, BuildConfig.VERSION_NAME)
            webViewAbout.apply {
                loadUrl("https://sites.google.com/view/mdideas/home-page?authuser=0")
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
}