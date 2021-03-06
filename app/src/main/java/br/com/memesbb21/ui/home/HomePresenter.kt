package br.com.memesbb21.ui.home

import android.content.Context
import android.util.Log
import br.com.memesbb21.MyApplication
import br.com.memesbb21.model.MemeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.nio.charset.Charset

class HomePresenter(private val mContext : Context) : HomeContract.Presenter {

    private var mView : HomeContract.View? = null
    var memes: List<MemeModel> = ArrayList()


    override fun getList() {
        memes = MyApplication.database?.memeDao()?.getAllMemes()!!

        if(memes.isEmpty()){
            val typeToken = object : TypeToken<ArrayList<MemeModel>>() {}.type
            val listMemes = Gson().fromJson<ArrayList<MemeModel>>(loadJSONFromAsset(), typeToken)

            listMemes.forEach {
                MyApplication.database?.memeDao()?.insertMeme(it)
            }

            memes = MyApplication.database?.memeDao()?.getAllMemes()!!

        }

        memes.let { mView?.setList(it) }
    }

    override fun setError(error: Throwable) {

    }


    override fun sharedMeme(memeModel: MemeModel) {
        mView?.notifyDataChanged()
        mView?.displayShare()
    }


    override fun favoriteMeme(memeModel: MemeModel) {
        memeModel.isFavorite = !memeModel.isFavorite
        MyApplication.database?.memeDao()?.updateMeme(memeModel)
        mView?.notifyDataChanged()

        if(memeModel.isFavorite){
            mView?.displayMessageFavorite()
        } else {
            mView?.displayMessageNotFavorite()
        }
    }

    override fun searchMeme(text: String) {
        Log.i("searchMeme", text)
        val listSearch = arrayListOf<MemeModel>()
        repeat(memes.size){
            if(memes[it].name.contains(text, true)){
                listSearch.add(memes[it])
            }
        }
        mView?.setList(listSearch)
        mView?.notifyDataChanged()
    }

    override fun attach(view: HomeContract.View) {
        mView = view
    }

    override fun detach() {
        mView = null
    }

    private fun loadJSONFromAsset() : String {
        var json  = ""
        try {
            val inputStream : InputStream = mContext.assets.open("meme.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch ( e : Exception ){
            e.printStackTrace()
            return ""
        }
        return json
    }
}