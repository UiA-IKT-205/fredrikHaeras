package com.example.piano

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import com.example.piano.data.Note
import com.example.piano.databinding.FragmentPianoBinding
import kotlinx.android.synthetic.main.fragment_piano.view.*
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class   PianoLayout : Fragment() {

    var onSave:((file: Uri) -> Unit)? = null

    private var _binding:FragmentPianoBinding? = null
    private val binding get() = _binding!!

    private val whiteTones = listOf("C","D","E","F","G","A","B","C2","D2","E2","F2","G2","A2","B2")
    private val blackTones = listOf("C#","D#","F#","G#","A#","C2#","D2#","F2#","G2#","A2#")

    private var score:MutableList<Note> = mutableListOf<Note>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPianoBinding.inflate(layoutInflater)

        val view = binding.root
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        whiteTones.forEach { originalNoteValue ->
            val whiteTangentKey = WhiteTangentFragment.newInstance(originalNoteValue)
            var startPlay:Long = 0
            var whiteTangentStartTime = ""

            whiteTangentKey.onKeyDown = { note->
                startPlay = System.currentTimeMillis()

                val currentTime = LocalDateTime.now()
                whiteTangentStartTime = currentTime.format(DateTimeFormatter.ISO_TIME)
                println("Piano key down $note")
            }

            whiteTangentKey.onKeyUp = {
                val endPlay = System.currentTimeMillis()
                var whiteTangentTotalTime = 0.0
                var whiteTangentTime:Long = 0

                whiteTangentTime = endPlay - startPlay
                whiteTangentTotalTime = whiteTangentTime.toDouble()

                val note = Note(it, whiteTangentStartTime, whiteTangentTotalTime)
                score.add(note)
                println("Piano key up $note. Started at $whiteTangentStartTime. Duration $whiteTangentTotalTime ms")
            }

            fragmentTransaction.add(view.pianoKeys.id, whiteTangentKey, "note_$originalNoteValue")
        }

        blackTones.forEach { originalNoteValue ->
            val blackTangentKey = BlackTangentFragment.newInstance(originalNoteValue)
            var startPlay:Long = 0
            var blackTangentStartTime = ""

            blackTangentKey.onKeyDown = { note ->
                startPlay = System.currentTimeMillis()
                val currentTime = LocalDateTime.now()
                blackTangentStartTime = currentTime.format(DateTimeFormatter.ISO_TIME)
                println("Piano key down $note")
            }

            blackTangentKey.onKeyUp = { 
                val endPlay = System.currentTimeMillis()
                var blackTangentTotalTime = 0.0
                var blackTangentTime:Long = 0

                blackTangentTime = endPlay - startPlay
                blackTangentTotalTime = blackTangentTime.toDouble()

                val note = Note(it, blackTangentStartTime, blackTangentTotalTime)
                score.add(note)
                println("Piano key up $note. Started at $blackTangentStartTime. Duration $blackTangentTotalTime ms")
            }

            fragmentTransaction.add(view.pianoKeys.id, blackTangentKey, "note_$originalNoteValue")
        }

        fragmentTransaction.commit()

        view.saveScoreBtn.setOnClickListener{
            var fileName = view.fileNameTextEdit.text.toString()
            if (score.count() > 0 && fileName.isNotEmpty()){
                fileName = "$fileName.music"
                val content:String = score.map {
                    it.toString()
                }.reduce{
                    acc, s -> acc + s + "\n"
                }
                saveFile(fileName, content)
                Toast.makeText(activity, "Saved the file with name $fileName", Toast.LENGTH_SHORT).show()
            }else{
                if (score.count() == 0){
                    Toast.makeText(activity, "You have to press some notes for it to be saved", Toast.LENGTH_SHORT).show()
                }
                if (fileName.isNotEmpty()){
                    Toast.makeText(activity, "Write a name to your file", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }

    private fun saveFile(fileName:String, content:String){
        val path = this.activity?.getExternalFilesDir(null)
        if (path != null){
            val file = File(path, fileName)
            FileOutputStream(file, true).bufferedWriter().use { writer ->
                writer.write(content)
            }
            this.onSave?.invoke(file.toUri())
        }else{
            Toast.makeText(activity, "The path does not exist", Toast.LENGTH_SHORT).show()
        }
    }
}

/*view.saveScoreBtn.setOnClickListener{
            var fileName = view.fileNameTextEdit.text.toString()
            val path = this.activity?.getExternalFilesDir(null)
            val newNoteFile = (File(path,fileName))

            when{
                score.count() == 0 -> Toast.makeText(activity, "You have to press some notes for it to be saved", Toast.LENGTH_SHORT).show()
                fileName.isEmpty() -> Toast.makeText(activity, "Write a name to your file", Toast.LENGTH_SHORT).show()
                path == null -> Toast.makeText(activity, "The path does not exist", Toast.LENGTH_SHORT).show()
                newNoteFile.exists() -> Toast.makeText(activity, "The file already exists, use another name", Toast.LENGTH_SHORT).show()

                else -> {
                    fileName = "$fileName.music"
                    FileOutputStream(newNoteFile, true).bufferedWriter().use { writer ->
                    score.forEach{
                        writer.write("$it\n")
                    }
                        FileOutputStream(newNoteFile).close()
                        score.clear()
                    }
                    Toast.makeText(activity, "Saved the file with name $fileName", Toast.LENGTH_SHORT).show()
                    print("Saved as $fileName at $path/$fileName")
                }
            }
        }*/