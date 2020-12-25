package ru.kahn.meetinggogoriki.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.kahn.meetinggogoriki.R
import ru.kahn.meetinggogoriki.adapter.AdapterMain
import ru.kahn.meetinggogoriki.application.AppGoGoRiRiki
import ru.kahn.meetinggogoriki.model.ModelListMembers
import ru.kahn.meetinggogoriki.model.ModelPartyMain
import java.io.IOException
import java.io.InputStream


class ActivityMain : AppCompatActivity() {
    private var listMembers: MutableList<ModelListMembers> =  mutableListOf()
    var adapter: AdapterMain = AdapterMain(listMembers)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = getSupportActionBar();
        actionBar?.setHomeButtonEnabled(true);
        actionBar?.setDisplayHomeAsUpEnabled(true);
        actionBar?.setTitle(resources.getString(R.string.text_toolbar))

        val layoutManager = LinearLayoutManager(this)
        rv_list_member.setLayoutManager(layoutManager)
        rv_list_member.setAdapter(adapter)

        jsonFileConverter()
        //responseParty() - метод для работы с json из сети
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun jsonFileConverter() {
        val json: String = try {
            val stream: InputStream = resources.openRawResource(R.raw.party)
            val size: Int = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return
        }
        val jsonFile = JSONObject(json)

        val arr: JSONArray = jsonFile.getJSONArray("list_members")
        var i = 0
        while(i < arr.length()){
            val objectItem: JSONObject = arr.get(i) as JSONObject
            val model = ModelListMembers(objectItem.getString("image_member"), objectItem.getString("name_member"))
            listMembers.add(i, model)
            i++;
        }

        jsonAddView(jsonFile)
    }

    @SuppressLint("SetTextI18n")
    private fun jsonAddView(jsonFile: JSONObject) {
        Picasso.with(this@ActivityMain)
            .load(jsonFile.getString("image_party"))
            .fit()
            .into(iv_meeting)
        Picasso.with(this@ActivityMain)
            .load(jsonFile.getString("icon_organizer"))
            .fit()
            .into(iv_organizer)
        tv_name_party.text = jsonFile.getString("name_party")
        tv_name_organizer.text = resources.getString(R.string.text) +" "+ jsonFile.getString("name_organizer")

        adapter.notifyDataSetChanged()
    }

    private fun responseParty() {
        val jsonPath: String = "https://losyash-library.fandom.com/ru/wiki/" // сюда подставить нужный путь
        val call: Call<ModelPartyMain> = AppGoGoRiRiki().retrofitSettings(jsonPath).getResponce()
        call.enqueue(object: Callback<ModelPartyMain> {
            override fun onResponse(call: Call<ModelPartyMain>, response: Response<ModelPartyMain>) {
                if(response.body()?.cod.equals("200")) {
                    Picasso.with(this@ActivityMain)
                        .load(response.body()!!.image_party)
                        .fit()
                        .into(iv_meeting)
                    Picasso.with(this@ActivityMain)
                        .load(response.body()!!.icon_organizer)
                        .fit()
                        .into(iv_organizer)
                    tv_name_party.text = response.body()!!.name_party
                    tv_name_organizer.text = response.body()!!.name_organizer

                    adapter.listMembers = response.body()!!.list_members
                    adapter.notifyDataSetChanged()
                    rl_no_data.visibility = View.GONE
                    rv_list_member.visibility = View.VISIBLE
                } else {
                    rl_no_data.visibility = View.VISIBLE
                    rv_list_member.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<ModelPartyMain>, t: Throwable) {
                Log.e("ОШИБКА", t.toString())
            }
        })
    }
}

