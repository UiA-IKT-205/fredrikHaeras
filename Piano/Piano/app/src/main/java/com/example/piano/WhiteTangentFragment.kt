package com.example.piano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.piano.databinding.FragmentWhiteTangentBinding
import kotlinx.android.synthetic.main.fragment_white_tangent.view.*

class WhiteTangentFragment : Fragment() {

    private var _binding:FragmentWhiteTangentBinding? = null
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

        _binding = FragmentWhiteTangentBinding.inflate(inflater)
        val view = binding.root

        view.whiteTangent.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when(event?.action){
                    MotionEvent.ACTION_DOWN -> this@WhiteTangentFragment.onKeyDown?.invoke(note)
                    MotionEvent.ACTION_UP -> this@WhiteTangentFragment.onKeyUp?.invoke(note)
                }
                return true
            }
        })

        return view
        }

        companion object {
            @JvmStatic
            fun newInstance(note: String) =
                WhiteTangentFragment().apply {
                    arguments = Bundle().apply {
                        putString("NOTE", note)
                    }
                }
        }
    }