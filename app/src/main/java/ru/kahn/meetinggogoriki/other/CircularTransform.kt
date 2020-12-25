package ru.kahn.meetinggogoriki.other

import android.graphics.*
import com.squareup.picasso.Transformation

class CircularTransform(val radius: Int) : Transformation {

    override fun transform(source: Bitmap): Bitmap? {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        canvas.drawCircle(
            source.width / 2.toFloat(),
            source.height / 2.toFloat(),
            source.width / 2.toFloat(),
            paint
        )
        if (source != output) source.recycle()
        return output
    }

    override fun key(): String? {
        return "circle"
    }

}