package ch.heigvd.daa_labo3.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.daa_labo3.R
import ch.heigvd.daa_labo3.models.Note.Companion.generateRandomNote
import ch.heigvd.daa_labo3.viewmodels.NotesViewModel

class NotesFragment : Fragment() {

    companion object {
        fun newInstance() = NotesFragment()
    }

    private lateinit var viewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        // TODO: Use the ViewModel

        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        val adapter = ControlsRecyclerAdapter()
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)
        adapter.items = listOf(generateRandomNote(), generateRandomNote())
    }
}