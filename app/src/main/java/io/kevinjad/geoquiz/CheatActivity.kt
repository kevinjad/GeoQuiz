package io.kevinjad.geoquiz


import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders


private const val EXTRA_ANSWER_IS_TRUE = "io.kevinjad.geoquiz.answer_is_true"
private const val EXTRA_ANSWER_IS_SHOWN = "io.kevinjad.geoquiz.answer_is_shown"

class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private var answerIsTrue = false

    private val cheatViewModel: CheatViewModel by lazy {
        ViewModelProviders.of(this).get(CheatViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false)
        showAnswerButton.setOnClickListener {
            view: View ->
            answerTextView.setText(if(answerIsTrue) R.string.true_button else R.string.false_button)
            cheatViewModel.hasCheated = true
            setAnswerShownResult()
        }

        if(cheatViewModel.hasCheated){
            answerTextView.setText(if(answerIsTrue) R.string.true_button else R.string.false_button)
            setAnswerShownResult()
        }
    }

    private fun setAnswerShownResult(){
        val data = Intent()
        data.putExtra(EXTRA_ANSWER_IS_SHOWN,cheatViewModel.hasCheated)
        setResult(Activity.RESULT_OK,data)
    }

    companion object{
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent{
            return Intent(packageContext,CheatActivity::class.java).putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue)
        }
    }
}