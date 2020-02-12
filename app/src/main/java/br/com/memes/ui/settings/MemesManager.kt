package br.com.memes.ui.settings

import android.content.Context
import android.content.SharedPreferences

class MemesManager(private var _context: Context) {
    private var pref: SharedPreferences
    private var editor: SharedPreferences.Editor
    private var PRIVATE_MODE = 0

    var color: String?
        get() = pref.getString(KEY_COLOR, "#42a3b2")
        set(color) {
            editor.putString(KEY_COLOR, color)
            editor.commit()
        }


    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }


    companion object {
        private val PREF_NAME = "Memes"
        // Color
        private val KEY_COLOR = "color"
    }
}