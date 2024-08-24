package com.example.theanimeapp.UI

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.theanimeapp.R
import com.example.theanimeapp.Utils.Resource
import com.example.theanimeapp.viewmodel.AnimeViewModel

class DescriptionFragment : Fragment() {

    private val animeViewModel: AnimeViewModel by viewModels()
    private val args: DescriptionFragmentArgs by navArgs()  // Get arguments using Safe Args

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get UI components
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val animeImageView: ImageView = view.findViewById(R.id.animeImageView)
        val genreTextView: TextView = view.findViewById(R.id.genreTextView)
        val rankingTextView: TextView = view.findViewById(R.id.rankingTextView)
        val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
        val episodeTextView: TextView = view.findViewById(R.id.episodeTextView)
        val alternativeNameTextView: TextView = view.findViewById(R.id.alternativeNameTextView)
        val linkForMoreTextView: TextView = view.findViewById(R.id.linkForMoreTextView)

        // Get the anime ID from the arguments
        val animeId = args.animeId

        // Fetch anime details
        animeViewModel.fetchAnimeById(animeId)

        // Observe data from ViewModel
        animeViewModel.animeDetail.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Show loading state if needed
                }
                is Resource.Success -> {
                    resource.data?.let { anime ->
                        // Update UI with anime details
                        titleTextView.text = anime.title
                        genreTextView.text = "Genres: ${anime.genres.joinToString(", ")}"
                        rankingTextView.text = "Rank: ${anime.ranking}"
                        descriptionTextView.text = anime.synopsis
                        episodeTextView.text = "Episodes: ${anime.episodes}"
                        alternativeNameTextView.text = "Alternative Titles: ${anime.alternativeTitles.joinToString(", ")}"

                        val linkText = "For more details, visit ${anime.link}"
                        linkForMoreTextView.text = createBoldClickableSpannable("For more details, visit ", anime.link)
                        linkForMoreTextView.movementMethod = LinkMovementMethod.getInstance()

                        Glide.with(this)
                            .load(anime.image)
                            .into(animeImageView)
                    }
                }
                is Resource.Error -> {
                    // Handle error state
                }
            }
        }
    }

    // Helper function to create a SpannableString with bold and clickable text
    private fun createBoldClickableSpannable(boldText: String, link: String): SpannableString {
        val fullText = "$boldText$link"
        val spannable = SpannableString(fullText)

        // Bold span for the label
        val boldSpan = StyleSpan(android.graphics.Typeface.BOLD)
        spannable.setSpan(boldSpan, 0, boldText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Clickable span for the link
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                widget.context.startActivity(browserIntent)
            }
        }
        spannable.setSpan(clickableSpan, boldText.length, fullText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        return spannable
    }
}
