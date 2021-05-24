package com.timothy.zoo.view.widgit

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.timothy.zoo.R
import timber.log.Timber
import kotlin.math.roundToInt

class VerticalRecyclerviewDecoration(
    mContext: Context,
    orientation:Int
): RecyclerView.ItemDecoration() {

    private val HORIZONTAL_LIST: Int = LinearLayoutManager.HORIZONTAL
    private val VERTICAL_LIST: Int = LinearLayoutManager.VERTICAL

    private var mOrientation:Int
    private var mDivider: Drawable
    private var isDrawFirstDivider: Boolean = true
    private var isDrawLastDivider: Boolean = true
    private val mBounds = Rect()

    constructor(mContext: Context,
                orientation:Int,
                isDrawFirstDivider:Boolean,
                isDrawLastDivider:Boolean):this(mContext,orientation){
        this.isDrawFirstDivider = isDrawFirstDivider
        this.isDrawLastDivider = isDrawLastDivider
    }

    init {
        mOrientation =
            if(orientation == VERTICAL_LIST || orientation == HORIZONTAL_LIST) orientation
            else throw IllegalArgumentException("invalid orientation")

        mDivider = ContextCompat.getDrawable(mContext, R.drawable.divider)
            ?: throw IllegalArgumentException("error divider resource")
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        when(mOrientation){
            //basically unreachable state
            HORIZONTAL_LIST ->{}

            VERTICAL_LIST -> drawHorizontal(c,parent)

        }
    }

    private fun drawHorizontal(c: Canvas, parent: RecyclerView){
        val left:Int
        val right:Int

        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            c.clipRect(left, parent.paddingTop, right,
                parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }

        val childCount = parent.childCount
        for(index in 0 until if(isDrawLastDivider) childCount else childCount-1){
            val child = parent.getChildAt(index)
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            val bottom = mBounds.bottom + child.translationY.roundToInt()
            val top = bottom - mDivider.intrinsicHeight
            mDivider.setBounds(left,top, right, bottom)
            mDivider.draw(c)
        }

    }

    //畫完分隔線的偏移量
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when(mOrientation){
            //basically unreachable state
            HORIZONTAL_LIST->{}

            VERTICAL_LIST->{
                outRect.set(0,0,0,mDivider.intrinsicHeight)
            }
        }
    }
}