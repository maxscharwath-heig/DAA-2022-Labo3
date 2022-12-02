package ch.heigvd.daa_labo3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.daa_labo3.MyApp
import ch.heigvd.daa_labo3.R
import ch.heigvd.daa_labo3.viewmodels.NotesViewModel
import ch.heigvd.daa_labo3.viewmodels.NotesViewModelFactory

class NotesFragment : Fragment() {

    private val viewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory((requireActivity().application as MyApp).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        val adapter = NotesRecyclerAdapter()
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)

        viewModel.sortedNotes.observe(viewLifecycleOwner) { notes ->
            adapter.items = notes
        }
    }
}