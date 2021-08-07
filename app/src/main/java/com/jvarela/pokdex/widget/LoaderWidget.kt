package com.jvarela.pokdex.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.jvarela.pokdex.R
import com.jvarela.pokdex.databinding.LoaderWidgetBinding

class LoaderWidget  @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
    ) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding: LoaderWidgetBinding? = null

    init {
        _binding = LoaderWidgetBinding.inflate(LayoutInflater.from(context), this, true)
        attrs?.let {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.LoaderWidget,
                defStyleAttr,
                defStyleRes
            )
        }
    }

}