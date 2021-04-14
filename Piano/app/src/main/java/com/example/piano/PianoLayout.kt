package com.example.piano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.piano.databinding.FragmentPianoBinding
import kotlinx.android.synthetic.main.fragment_piano.view.*

class PianoLayout : Fragment() {

    private var _binding: FragmentPianoBinding? = null
    private val binding get() = _binding!!

    private val whiteTones = listOf("C","D","E","F","G","A","B","C2","D2","E2","F2","G2","A2","B2")
    private val blackTones = listOf("C#","D#","F#","G#","A#","C2#","D2#","F2#","G2#","A2#")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentPianoBinding.inflate(layoutInflater)
        val view = binding.root

        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        whiteTones.forEach { originalNoteValue ->
            val whiteTangentKey = WhiteTangentFragment.newInstance(originalNoteValue)

            whiteTangentKey.onKeyDown = { note->
                println("Piano key down $note")
            }

            whiteTangentKey.onKeyUp = { note ->
                println("Piano key up $note")
            }

            fragmentTransaction.add(view.pianoKeys.id, whiteTangentKey, "note_$originalNoteValue")
        }

        blackTones.forEach { originalNoteValue ->
            val blackTangentKey = BlackTangentFragment.newInstance(originalNoteValue)

            blackTangentKey.onKeyDown = { note ->
                println("Piano key down $note")
            }

            blackTangentKey.onKeyUp = { note ->
                println("Piano key up $note")
            }

            fragmentTransaction.add(view.pianoKeys.id, blackTangentKey, "note_$originalNoteValue")
        }

        fragmentTransaction.commit()

        return view
    }
}