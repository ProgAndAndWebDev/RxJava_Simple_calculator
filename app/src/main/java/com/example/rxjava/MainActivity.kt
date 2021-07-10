package com.example.rxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.rxjava.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        foo()
    }

    private fun foo() {
        Observable.create<String>{emitter ->
            binding.edit.doOnTextChanged { text, _, _, _ ->
                emitter.onNext(text.toString())
            }
        }.debounce(1500,TimeUnit.MILLISECONDS).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe { equation->
          try {
              binding.textView.text= ExpressionBuilder(equation).build().evaluate().toString()
//              Log.i(TAG,ExpressionBuilder(equation).build().evaluate().toString())
          }catch (e:Exception){
              Log.i(TAG,e.message.toString())
          }
        }
        }

    companion object{
        const val TAG="TAG"
    }
}