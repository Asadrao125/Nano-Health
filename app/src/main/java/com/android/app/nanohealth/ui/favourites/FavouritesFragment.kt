package com.android.app.nanohealth.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.app.nanohealth.databinding.FragmentFavouritesBinding

class FavouritesFragment : Fragment() {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.toolbarTitle.setText("Favourites")

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}