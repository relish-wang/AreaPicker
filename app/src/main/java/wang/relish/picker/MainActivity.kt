package wang.relish.picker

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import wang.relish.citypicker.SCAreaPicker

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private var code: String = ""

    fun show(view: View) {
        SCAreaPicker(this)
            .withPickedArea(code)
            .withAreaPickedListener(object : SCAreaPicker.OnAreaPickedListener {
                @SuppressLint("SetTextI18n")
                override fun onAreaPicked(p: String, c: String, a: String, code: String) {
                    tv.text = "$p $c $a"
                    this@MainActivity.apply {
                        this.code = code
                    }
                }

                override fun onAreaPickFailed() {
                }
            })
            .show()
    }
}
