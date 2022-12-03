package ch.heigvd.daa_labo3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import ch.heigvd.daa_labo3.MyApp
import ch.heigvd.daa_labo3.R
import ch.heigvd.daa_labo3.viewmodels.NotesViewModel
import ch.heigvd.daa_labo3.viewmodels.NotesViewModelFactory

/**
 * Fragment for operations on notes
 *
 * @author Nicolas Crausaz
 * @author Lazar Pavicevic
 * @author Maxime Scharwath
 */
class ControlsFragment : Fragment() {

    private val viewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory((requireActivity().application as MyApp).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_controls, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notesCounter = view.findViewById<TextView>(R.id.controls_fragment_notes_counter)
        val buttonGenerate = view.findViewById<TextView>(R.id.controls_fragment_button_generate)
        val buttonDelete = view.findViewById<TextView>(R.id.controls_fragment_button_delete)

        viewModel.countNotes.observe(viewLifecycleOwner) { count ->
            notesCounter.text = count.toString()
        }

        buttonGenerate.setOnClickListener {
            viewModel.generateANote()
        }

        buttonDelete.setOnClickListener {
            viewModel.deleteAllNote()
        }
    }
}