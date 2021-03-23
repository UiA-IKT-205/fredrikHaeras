package com.example.piano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.piano.databinding.FragmentBlackTangentBinding
import kotlinx.android.synthetic.main.fragment_black_tangent.view.*
import kotlinx.android.synthetic.main.fragment_white_tangent.view.*

class BlackTangentFragment : Fragment() {

    private var _binding:FragmentBlackTangentBinding? = null
    private val binding get() = _binding!!
    private lateinit var note:String

    var onKeyDown:((note:String) -> Unit)? = null
    var onKeyUp:((note:String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val let = arguments?.let {
            note = it.getString("NOTE") ?: "?"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBlackTangentBinding.inflate(inflater)
        val view = binding.root

        view.blackTangentKey.setOnTouchListener(object: View.OnTouchListener{

            var startTime = 0

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when(event?.action){
                    MotionEvent.ACTION_DOWN -> this@BlackTangentFragment.onKeyDown?.invoke(note)
                    MotionEvent.ACTION_UP -> this@BlackTangentFragment.onKeyUp?.invoke(note)
                }
                return true
            }
        })

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(note: String) =
            BlackTangentFragment().apply {
                arguments = Bundle().apply {
                    putString("NOTE", note)
                }
            }
    }
}