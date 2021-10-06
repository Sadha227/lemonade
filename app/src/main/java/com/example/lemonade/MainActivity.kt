package com.example.lemonade


import android.app.SharedElementCallback
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    /**
     * НЕ ИЗМЕНЯТЬ ЗНАЧЕНИЕ ПЕРЕМЕННЫХ И КОНСТАНТ, А ТАКЖЕ ИХ ИМЕНА
     *
     * Все, что отмечено var вместо val, должно быть изменено в функции, НО НЕ ИЗМЕНЯЙТЕ
     * их начальные значение, инициализированные ниже, это может повлять на работоспособность приложения согласно заданию
     */
    private val LEMONADE_STATE = "LEMONADE_STATE"
    private val LEMON_SIZE = "LEMON_SIZE"
    private val SQUEEZE_COUNT = "SQUEEZE_COUNT"
    // SELECT представляет состояние "сорвать лимон"
    private val SELECT = "select"
    // SQUEEZE представляет состояние "выжать лимон"
    private val SQUEEZE = "squeeze"
    // DRINK представляет состояние "выпить лимонад"
    private val DRINK = "drink"
    // RESTART представляет состояние, когда лимонад выпили и стакан стал пустым
    private val RESTART = "restart"
    // Состояние поумолчанию - это состояние "сорвать лимон"
    private var lemonadeState = "select"
    // По умолчанию количество лимонов -1
    private var lemonSize = -1
    // По умолчанию выжато -1 лимонов
    private var squeezeCount = -1

    private var lemonTree = LemonTree()
    private var lemonImage: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // === НЕ РЕДАКТИРОВАТЬ КОД В ЭТОМ IF-ВЫРАЖЕНИИ, ОН НЕ ОТНОСИТСЯ К ВАШЕМУ ЗАДАНИЮ ===
        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(LEMONADE_STATE, "select")
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }
        // === END IF STATEMENT ===

        lemonImage = findViewById(R.id.image_lemon_state)
        setViewElements()
        lemonImage!!.setOnClickListener {
            // TODO: вызвать функцию, которая обрабатывает нажатие на картинку на экране
            clickLemonImage()
        }
        lemonImage!!.setOnLongClickListener {
            // TODO: заменить 'false' на вызов функции, которая количество раз,
            //  которое Вы выжимали из лимонов сок
            false
        }
    }

    /**
     * === НЕ РЕДАКТИРОВАТЬ ЭТУ ФУНКЦИЮ ===
     *
     * Эта функция сохраняет состояние приложения в том случае, есл приложение "преходит
     * на задний план"
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEMONADE_STATE, lemonadeState)
        outState.putInt(LEMON_SIZE, lemonSize)
        outState.putInt(SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }

    /**
     * Нажатие на кнопку повлечет разные дествие, в зависимости от состояния приложения
     * Эта функция определяет состояние и обрабатывает его выполняя необходимое действие
     */
    private fun clickLemonImage() {
        // TODO: Используйте условное выражение 'if' или 'when' для отслеживания lemonadeState (состояния приложения)
        //  когда на картинку нажали нам, возможно, необходимо изменить состояние для перехода
        //  на следующий шаг в процессе (алгоритме) создания лимонада (ну или хотя бы сделать некоторые изменения
        //  относительно текущего состояния приложения. Это все необходимо делать в выбранном условном выражении
        when(lemonadeState){
            SELECT-> {
                Log.d(TAG, "You are in select branch")
                lemonadeState = SQUEEZE
                lemonSize = lemonTree.pick()
                squeezeCount = 0
            }
            SQUEEZE ->{
                Log.d(TAG, "You are squeeze branch")
                lemonSize--
                squeezeCount++
                if(lemonSize == 0){
                    lemonadeState = DRINK
                }
            }
            DRINK ->{
                Log.d(TAG, "You are drink branch")
                lemonadeState = RESTART
                lemonSize = -1
            }
            RESTART ->{
                Log.d(TAG, "You are restart branch")
                lemonadeState = SELECT
            }
        }
        setViewElements()
        // TODO: Когда на картинку нажали в состоянии SELECT (сорвать лимоны), состояние должно изменить на SQUEEZE (выжимать)
        //  - Переменная lemonSize должна быть установленна в значение, которое возвратит функция  'pick()' класса LemonTree
        //  - Переменная squeezeCount должна быть устновленна в значение 0, так как мы ещё не выжали ни одного лимона для лимонада
        // TODO: Когда на картинку нажали в состоянии SQUEEZE (выжимать лимоны), значение переменной squeezeCount должно быть
        //  УВЕЛИЧЕНО на 1 и значение переменной lemonSize должно быть УМЕНЬШЕНО на 1.
        //  - Если значение переменной lemonSize достигло 0, сок из лимонов был выжат и необходимо изменить состояние на DRINK (пить)
        //  - Также переменная lemonSize (которое означает количество лимонов, которые были собраны) более не несет смысла,
        //  потому необходимо установить значение переменной в -1
        // TODO: Когда на картинку нажали в состоянии DRINK (пить) состояние должно изменить на RESTART (начать заново)

        // TODO: Когда на картинку нажали в состоянии RESTART состояние должно изменить на SELECT (сорвать лимоны)

        // TODO: Перед окончание работы функции необходимо изменить содержимое элементов графического
        //  интерфейса (картинки и текста) чтобы UI корректно отображал состояние приложения
    }
    /**
     * Функция устанавливает элементы графического интерфейса согласно состоянию
     */
    private fun setViewElements() {
        val textAction: TextView = findViewById(R.id.text_action)

        // TODO: используйте условные операторы чтобы обрабатывать состояния переменной
        when(lemonadeState){
            SELECT ->{
                textAction.text= getString(R.string.lemon_select)
                lemonImage?.setImageResource(R.drawable.lemon_tree)
            }
            SQUEEZE ->{
                textAction.text = getString(R.string.lemon_squeeze)
                lemonImage?.setImageResource(R.drawable.lemon_squeeze)
            }
            DRINK ->{
               textAction.text = getString(R.string.lemon_drink)
                lemonImage?.setImageResource(R.drawable.lemon_drink)
            }
            RESTART ->{
                textAction.text = getString(R.string.lemon_empty_glass)
                lemonImage?.setImageResource(R.drawable.lemon_restart)
            }
        }

        // TODO: для каждого состояния элемент TextView с id textAction должен получить соответствующую строку (string) из
        //  файла со строковыми ресурсами

        // TODO: Дополнительно для каждого состояния lemonImage (переменная для управления картинкой ImageView) должна
        //  устанавливать необходимую картинку из папки drawable resources. Картинки имеют те же имена, что и строковые ресурсы
    }

    /**
     * === НЕ РЕДАКТИРОВАТЬ ЭТУ ФУНКЦИЮ, ОНА НЕ ИМЕЕТ ОТНОШЕНИЯ К ЗАДАНИЮ ===
     *
     * Долгое нажатие на лимон отобразит число лимонов, из которых был выжат сок
     */
    private fun showSnackbar(): Boolean {
        if (lemonadeState != SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar.make(
            findViewById(R.id.constraint_Layout),
            squeezeText,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}

/**
 * Класс Lemon tree (лимонное дерево) с функцией "pick" (собрать\сорвать) лимон. Количество ("size") определяем при помощи
 * and determines how many times a lemon needs to be squeezed before you get lemonade.
 */
class LemonTree {
    fun pick(): Int {
        return (2..4).random()
    }
}