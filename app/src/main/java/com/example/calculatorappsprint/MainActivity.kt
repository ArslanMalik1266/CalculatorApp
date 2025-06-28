package com.example.calculatorappsprint

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.View.SCALE_Y
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.ViewStub
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.GridLayout
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.button.MaterialButton
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception
import java.lang.System.load

class MainActivity : AppCompatActivity() {
    private var store = ""
    private lateinit var operationTxtView: TextView
    private lateinit var resultTextView: TextView
    private lateinit var horizontalScrollView: HorizontalScrollView
    private lateinit var detailsView: View
    private var isViewStubInflated = false

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("store_key", store)
        outState.putString("result_key", resultTextView.text.toString())
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        store = savedInstanceState.getString("store_key", "")
        operationTxtView.text = store
        val result = savedInstanceState.getString("result_key", "")
        resultTextView.text = result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        horizontalScrollView = findViewById(R.id.horizontalScrollView)
        val btn0 = findViewById<MaterialButton>(R.id.BTN0)
        val btn1 = findViewById<MaterialButton>(R.id.BTN1)
        val btn2 = findViewById<MaterialButton>(R.id.BTN2)
        val btn3 = findViewById<MaterialButton>(R.id.BTN3)
        val btn4 = findViewById<MaterialButton>(R.id.BTN4)
        val btn5 = findViewById<MaterialButton>(R.id.BTN5)
        val btn6 = findViewById<MaterialButton>(R.id.BTN6)
        val btn7 = findViewById<MaterialButton>(R.id.BTN7)
        val btn8 = findViewById<MaterialButton>(R.id.BTN8)
        val btn9 = findViewById<MaterialButton>(R.id.BTN9)
        val btnDot = findViewById<MaterialButton>(R.id.dotBTN)
        val btnAC = findViewById<MaterialButton>(R.id.AC_BTN)
        val btnBrackets = findViewById<MaterialButton>(R.id.bracketsBTN)
        val btnRemainder = findViewById<MaterialButton>(R.id.remainderBTN)
        val btnDivide = findViewById<MaterialButton>(R.id.divideBTN)
        val btnMultiply = findViewById<MaterialButton>(R.id.multiplyBTN)
        val btnMinus = findViewById<MaterialButton>(R.id.minusBTN)
        val btnPlus = findViewById<MaterialButton>(R.id.plusBTN)
        val btnResult = findViewById<MaterialButton>(R.id.resultBTN)
        val btnSquareRoot = findViewById<TextView>(R.id.squareRootBTN)
        val btnPi = findViewById<TextView>(R.id.piBTN)
        val btnPower = findViewById<TextView>(R.id.powerBTN)
        val btnFactorial = findViewById<TextView>(R.id.factorialBTN)
        val btnBackSpace = findViewById<MaterialButton>(R.id.backspaceBTN)



        resultTextView = findViewById(R.id.result_txt)
        operationTxtView = findViewById(R.id.operation_txtView)
        val viewStub = findViewById<ViewStub>(R.id.viewStub)
        val viewStubButton = findViewById<ImageView>(R.id.viewStubButton)

        var openBracketCount = 0
        var closeBracketCount = 0


        btn0.setOnClickListener {
            store += "0"
            operationTxtView.text = store
            scrollToEnd()
        }
        btn1.setOnClickListener {
            store += "1"
            operationTxtView.text = store
            scrollToEnd()
        }
        btn2.setOnClickListener {
            store += "2"
            operationTxtView.text = store
            scrollToEnd()
        }
        btn3.setOnClickListener {
            store += "3"
            operationTxtView.text = store
            scrollToEnd()
        }
        btn4.setOnClickListener {
            store += "4"
            operationTxtView.text = store
            scrollToEnd()
        }
        btn5.setOnClickListener {
            store += "5"
            operationTxtView.text = store
            scrollToEnd()
        }
        btn6.setOnClickListener {
            store += "6"
            operationTxtView.text = store
            scrollToEnd()
        }
        btn7.setOnClickListener {
            store += "7"
            operationTxtView.text = store
            scrollToEnd()
        }
        btn8.setOnClickListener {
            store += "8"
            operationTxtView.text = store
            scrollToEnd()
        }
        btn9.setOnClickListener {
            store += "9"
            operationTxtView.text = store
            scrollToEnd()
        }
        btnDivide.setOnClickListener {
            store += "÷"
            operationTxtView.text = store
            scrollToEnd()
        }
        btnMultiply.setOnClickListener {
            store += "x"
            operationTxtView.text = store
            scrollToEnd()
        }
        btnMinus.setOnClickListener {
            store += "-"
            operationTxtView.text = store
            scrollToEnd()
        }
        btnPlus.setOnClickListener {
            store += "+"
            operationTxtView.text = store
            scrollToEnd()
        }
        btnBrackets.setOnClickListener {
            if (openBracketCount <= closeBracketCount) {
                store += "("
                openBracketCount++
            } else {
                store += ")"
                closeBracketCount++
            }
            operationTxtView.text = store
            scrollToEnd()
        }
        btnAC.setOnClickListener {
            store = ""
            operationTxtView.text = store
            resultTextView.text = ""
            resultTextView.setTextColor(Color.BLACK)
            operationTxtView.setTextColor(Color.BLACK)
            scrollToEnd()
        }
        btnDot.setOnClickListener {

            val parts = store.split("+", "-", "x", "÷", "(", ")")
            val lastNumber = parts.lastOrNull() ?: ""

            if (!lastNumber.contains(".")) {

                store += "."
            }
            // Update the TextView with the current value of 'store'
            operationTxtView.text = store
            scrollToEnd()
        }
        btnRemainder.setOnClickListener {
            store += "%"
            operationTxtView.text = store
            scrollToEnd()
        }
        btnBackSpace.setOnClickListener {
            if (store.isNotEmpty()) {
                val lastChar = store.last()
                when (lastChar) {
                    '(' -> openBracketCount--
                    ')' -> closeBracketCount--
                }
                store = store.substring(0, store.length - 1)
            }
            operationTxtView.text = store
            scrollToEnd()
        }
        btnSquareRoot.setOnClickListener {
            store += "√"
            operationTxtView.text = store
            scrollToEnd()
        }
        btnPi.setOnClickListener {
            store += "π"
            operationTxtView.text = store
            scrollToEnd()
        }
        btnPower.setOnClickListener {
            if (store.isNotEmpty()) {
                store += "^"
                operationTxtView.text = store
                scrollToEnd()

            } else

                store += ""

        }
        btnFactorial.setOnClickListener {
            store += "!"
            operationTxtView.text = store
            scrollToEnd()
        }


        btnResult.setOnClickListener {
            try {

                val expressionToEvaluate = store.replace("x", "*")
                    .replace("÷", "/")
                    .replace("√", "sqrt")
                    .replace("π", "pi")
                    .replace("sin(", "sin")
                    .replace("cos(", "cos")
                    .replace("tan(", "tan")

                val result = if (store.contains("!")) {
                    val numberString = store.substringBefore("!")
                    if (numberString.isNotEmpty()) {

                        val number = ExpressionBuilder(numberString).build().evaluate().toInt()
                        factorial(number)
                    } else {
                        Double.NaN
                    }
                } else {

                    val expression = ExpressionBuilder(expressionToEvaluate).build()
                    expression.evaluate()
                }
                val resultFormatted = if (result == result.toInt().toDouble()) {
                    result.toInt().toString()
                } else {
                    result.toString()
                }
                resultTextView.text = resultFormatted


            } catch (e: Exception) {
                e.printStackTrace()
                resultTextView.text = "Error accured"
                resultTextView.setTextColor(Color.RED)
                operationTxtView.setTextColor(Color.RED)
            }


        }



        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            viewStubButton.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)

            viewStubButton.setOnClickListener {

                val gridLayout = findViewById<GridLayout>(R.id.grid_Layout)
                if (!isViewStubInflated) {
                   detailsView = viewStub.inflate()
                    isViewStubInflated = true
                    val animation = AnimationUtils.loadAnimation(this,R.anim.fade_in)
                    detailsView.startAnimation(animation)
//                    val slide_in = AnimationUtils.loadAnimation(this,R.anim.slide_in)
//                    detailsView.startAnimation(slide_in)



                    val RAD = findViewById<TextView>(R.id.RAD)
                    val sin = findViewById<TextView>(R.id.sin)
                    val cos = findViewById<TextView>(R.id.cos)
                    val tan = findViewById<TextView>(R.id.tan)
                    val INV = findViewById<TextView>(R.id.INV)
                    val e = findViewById<TextView>(R.id.e)
                    val ln = findViewById<TextView>(R.id.ln)
                    val log = findViewById<TextView>(R.id.log)


                    sin.setOnClickListener {
                        store += "sin("

                        operationTxtView.text = store
                        scrollToEnd()
                    }

                    cos.setOnClickListener {
                        store += "cos("
                        operationTxtView.text = store
                        scrollToEnd()
                    }

                    tan.setOnClickListener {
                        store += "tan("
                        operationTxtView.text = store
                        scrollToEnd()
                    }
                    INV.setOnClickListener {
                        store += "asin("
                        operationTxtView.text = store
                        scrollToEnd()
                    }
                    e.setOnClickListener {
                        store += "e"
                        operationTxtView.text = store
                        scrollToEnd()
                    }
                    ln.setOnClickListener {
                        store += "ln("
                        operationTxtView.text = store
                        scrollToEnd()
                    }
                    log.setOnClickListener {
                        store += "log("
                        operationTxtView.text = store
                        scrollToEnd()
                    }

                    viewStubButton.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)

                } else {
                    if (detailsView?.visibility == View.VISIBLE) {
                        hideViewStub()

                        viewStubButton.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)

                    } else {

                        viewStubButton.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                        showViewStub()
                        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
                        detailsView?.startAnimation(fadeIn)
//                        val slide_in = AnimationUtils.loadAnimation(this,R.anim.slide_in)
//                        gridLayout.startAnimation(slide_in)
                    }
                }
            }
        }

    }


    private fun hideViewStub() {

        detailsView?.visibility = View.GONE
    }

    private fun showViewStub() {
        detailsView?.visibility = View.VISIBLE
    }

    private fun scrollToEnd() {

        horizontalScrollView.post {
            horizontalScrollView.fullScroll(View.FOCUS_RIGHT)
        }
    }

    private fun factorial(n: Int): Double {
        return if (n < 0) {
            Double.NaN
        } else {
            (1..n).fold(1.0) { acc, i -> acc * i }
        }
    }

}



