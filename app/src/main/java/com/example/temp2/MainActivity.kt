package com.example.temp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.example.temp2.data.CalcAns
import com.example.temp2.data.CalcResponse
import com.example.temp2.data.DeliveryRepository
import com.example.temp2.data.PackageResponse
import com.example.temp2.data.PostResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var spinner1 : Spinner
    private lateinit var spinner2 : Spinner
    private lateinit var button : Button

    private val deliveryRepository = DeliveryRepository()

    private fun getPoints() : Array<String> {
        val ans = mutableListOf<String>()
        val job = CoroutineScope(Dispatchers.IO).launch{
            val points = deliveryRepository.getPoints().points

            for(i in points) ans.add(i.name)
        }

        runBlocking {
            job.join()
        }
        return ans.toTypedArray()
    }

    private fun getTypes() : Array<String> {
        val ans = mutableListOf<String>()
        val job = CoroutineScope(Dispatchers.IO).launch{
            val types = deliveryRepository.getTypes().packages

            for(i in types) ans.add(i.name)
        }

        runBlocking {
            job.join()
        }
        return ans.toTypedArray()
    }

    private fun getCalc(query : CalcResponse) : CalcAns{
        lateinit var ans : CalcAns
        val job = CoroutineScope(Dispatchers.IO).launch {
            ans = deliveryRepository.getCalc(query)
        }
        runBlocking { job.join() }
        return ans
    }

    private fun onButtonClick(){
        val send = spinner.selectedItem.toString()
        val reciver = spinner1.selectedItem.toString()
        val type = spinner2.selectedItem.toString()

        var senderResponse : PostResponse? = null
        var reciverResponse : PostResponse? = null
        var typeResponse : PackageResponse? = null
        val scope = CoroutineScope(Dispatchers.IO).launch {
            val job1 = CoroutineScope(Dispatchers.IO).async {
                val points = deliveryRepository.getPoints().points

                for (i in points) {
                    if (i.name == send) {
                        senderResponse = PostResponse(
                            i.latitude,
                            i.longitude
                        )
                    }
                    if (i.name == reciver) {
                        reciverResponse = PostResponse(
                            i.latitude,
                            i.longitude
                        )
                    }
                }
            }

            val job2 = CoroutineScope(Dispatchers.IO).async {
                val types = deliveryRepository.getTypes().packages

                for (i in types) {
                    if (i.name == type) {
                        typeResponse = PackageResponse(i.length, i.width, i.weight, i.height)
                    }
                }
            }
            job1.join();job2.join()
        }
        runBlocking { scope.join() }
        val calcResponse = CalcResponse(typeResponse!!,senderResponse!!, reciverResponse!!)

        val ans = getCalc(calcResponse)
        val data = mutableListOf<String>()

        for(i in ans.options){
            data.add(i.name)
            data.add(i.price.toString())
        }

        val intent = Intent(this@MainActivity, Price::class.java)

        intent.putExtra("texts", data.toTypedArray())
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val array =  getPoints()
        spinner = findViewById(R.id.textView2)
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            array
        )
        spinner.adapter = adapter

        spinner1 = findViewById(R.id.textView3)
        val adapter1 = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            array
        )
        spinner1.adapter = adapter1

        val array1 = getTypes()

        spinner2 = findViewById(R.id.textView5)
        val adapter2 = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            array1
        )
        spinner2.adapter = adapter2

        button = findViewById(R.id.button)
        button.setOnClickListener{
            onButtonClick()
        }
    }
}
