package com.example.theanimeapp.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.theanimeapp.Adapters.AnimeAdapter
import com.example.theanimeapp.Model.Data
import com.example.theanimeapp.R
import com.example.theanimeapp.Utils.Resource
import com.example.theanimeapp.viewmodel.AnimeViewModel

class SearchFragment : Fragment() {

    private val animeViewModel: AnimeViewModel by viewModels()
    private lateinit var animeAdapter: AnimeAdapter
    private val animeList = mutableListOf<Data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        animeAdapter = AnimeAdapter(animeList) { anime ->

            val action = SearchFragmentDirections.actionSearchFragmentToDescriptionFragment(anime._id)
            findNavController().navigate(action)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = animeAdapter

        // Set up ProgressBar
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)

        // Set up Search Button and EditText
        val searchButton: Button = view.findViewById(R.id.searchButton)
        val searchEditText: EditText = view.findViewById(R.id.searchEditText)

        // Set up search button click listener
        searchButton.setOnClickListener {
            val query = searchEditText.text.toString()
            if (query.isNotEmpty()) {
                // Fetch anime data based on search query
                animeViewModel.fetchAnimeData(
                    page = 1,
                    size = 40,
                    search = query,
                    genres = null,
                    sortBy = "ranking",
                    sortOrder = "asc"
                )
            }
        }

        animeViewModel.animeData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    resource.data?.let { animeResponse ->
                        animeList.clear()
                        animeList.addAll(animeResponse.data)
                        animeAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    // Handle error state here
                }
            }
        }
    }
}
