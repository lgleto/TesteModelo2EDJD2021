package ipca.pong.axxxxx

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.RequiresApi

//
// Created by lourencogomes on 1/6/21.
//
class GameView : SurfaceView, Runnable {

    var playing = false
    var gameThread : Thread? = null

    var surfaceHolder : SurfaceHolder? = null
    var canvas : Canvas? = null

    var paint : Paint = Paint()

    var squares = arrayListOf<Square>()

    private fun init(context: Context?, width:Int, height: Int){
        surfaceHolder = holder

        for (i in 1..3) {
            squares.add(Square(width, height))
        }
    }

    constructor(context: Context?, width:Int, height: Int) : super(context){
        init(context, width, height)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init(context,0,0)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init(context,0,0)
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes){
        init(context,0,0)
    }

    override fun run() {
        while (playing){
            update()
            draw()
            control()
        }
    }

    private fun update(){
        for(s in squares){
            s.update()
        }
    }

    private fun draw(){
        surfaceHolder?.let {
            if (it.surface.isValid){
                canvas = surfaceHolder?.lockCanvas()
                canvas?.drawColor(Color.BLACK)

                paint.color = Color.WHITE
                for(s in squares){
                    canvas?.drawRect(
                        s.x.toFloat(),
                        s.y.toFloat(),
                        s.x.toFloat() + 100F,
                        s.y.toFloat() + 100F, paint)
                }

                surfaceHolder?.unlockCanvasAndPost(canvas)
            }
        }
    }

    private fun control(){
        Thread.sleep(17L)
    }

    fun pause(){
        playing = false
        gameThread?.join()
    }

    fun resume(){
        playing = true
        gameThread = Thread(this)
        gameThread!!.start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action){
            MotionEvent.ACTION_UP ->{

            }
            MotionEvent.ACTION_DOWN ->{
                for(s in squares){
                    if (event.x  >  s.x &&  event.x  <  s.x+100) {
                        if (s.y < event.y && s.y+100 >  event.y) {
                            s.isGoingDown = true
                        }
                    }
                }
            }
        }
        return true
    }

}