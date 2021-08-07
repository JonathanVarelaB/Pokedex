package com.jvarela.pokdex.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.jvarela.pokdex.R
import com.jvarela.pokdex.databinding.EmptyListWidgetBinding

class EmptyListWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding: EmptyListWidgetBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = EmptyListWidgetBinding.inflate(LayoutInflater.from(context), this, true)
        attrs?.let {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.EmptyListWidget,
                defStyleAttr,
                defStyleRes
            )
            binding.emptyText.text = typedArray.getString(R.styleable.EmptyListWidget_description)
        }
    }
}