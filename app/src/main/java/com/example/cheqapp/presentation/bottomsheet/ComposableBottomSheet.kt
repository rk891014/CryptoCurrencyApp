package com.example.cheqapp.presentation.bottomsheet

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import com.example.cheqapp.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class ComposableBottomSheet : BottomSheetDialogFragment() {

    private var binding: BottomSheetLayoutBinding? = null
    private var name: String? = null
    private var id: Int? = null
    companion object {
        fun newInstance(name: String, id: Int): ComposableBottomSheet {
            return ComposableBottomSheet().apply {
                arguments = Bundle().apply {
                    putString("user_name", name)
                    putInt("user_id", id)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = arguments?.getString("user_name")
        id = arguments?.getInt("user_id")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetLayoutBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.composeView?.setContent {
            BottomSheetContent(name, id)
        }

        dialog?.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                // Apply rounded corners from the top only
                val shapeAppearanceModel = ShapeAppearanceModel.Builder()
                    .setTopLeftCorner(CornerFamily.ROUNDED, 24.dpToPx()) // Adjust as per your design
                    .setTopRightCorner(CornerFamily.ROUNDED, 24.dpToPx()) // Adjust as per your design
                    .build()

                ViewCompat.setBackground(it, MaterialShapeDrawable(shapeAppearanceModel).apply {
                    fillColor = ColorStateList.valueOf(Color.White.toArgb())
                })

                val behavior = BottomSheetBehavior.from(it)
                val screenHeight = resources.displayMetrics.heightPixels
                behavior.peekHeight = (2 * screenHeight) / 3  // Set the peek height to half of the screen height
                behavior.isDraggable = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    @Composable
    private fun BottomSheetContent(name: String?, id: Int?) {
        LazyColumn(
            modifier = Modifier.wrapContentSize(),
            contentPadding = PaddingValues(20.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = name ?: "",
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 20.dp)
                    )
                }
            }
        }
    }
    private fun Int.dpToPx(): Float {
        return this * resources.displayMetrics.density
    }
}