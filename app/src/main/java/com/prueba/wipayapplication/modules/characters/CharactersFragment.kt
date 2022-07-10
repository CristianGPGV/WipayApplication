package com.prueba.wipayapplication.modules.characters

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.prueba.wipayapplication.R
import com.prueba.wipayapplication.databinding.FragmentCharactersBinding
import com.prueba.wipayapplication.marvelapi.io.MainRepository
import com.prueba.wipayapplication.marvelapi.io.RetrofitService
import com.prueba.wipayapplication.marvelapi.model.Result
import com.prueba.wipayapplication.modules.comics.ComicsActivity

class CharactersFragment : Fragment() {

    companion object {
        private const val TAG = "CharactersFragment"
        const val CHARACTER_DATA_TO_COMIC = "CHARACTER_DATA_TO_COMIC"
    }

    private lateinit var charactersViewModel: CharactersViewModel
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        charactersViewModel = ViewModelProvider(this, CharactersModelFactory(MainRepository(retrofitService)))[CharactersViewModel::class.java]

        charactersViewModel.listCharacter.observe(this, Observer {
            loadImageCharacters(it)
            Log.d(TAG, "onCreate: $it")
        })

        charactersViewModel.errorMessage.observe(this, Observer {
            _binding?.progressBar?.visibility = View.GONE
            showNoData(true,it)

        })
        charactersViewModel.getCharacters()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.toolbar?.setNavigationOnClickListener {
            this.requireActivity().onBackPressed()
        }
        _binding?.progressBar?.visibility = View.VISIBLE
        _binding?.imageCharacter1?.setOnClickListener {
            getDataCharacter(0)?.let { it1 -> gotoComics(it1) }
        }

        _binding?.imageCharacter2?.setOnClickListener {
            getDataCharacter(1)?.let { it1 -> gotoComics(it1) }
        }
        _binding?.imageCharacter3?.setOnClickListener {
            getDataCharacter(2)?.let { it1 -> gotoComics(it1) }
        }
        _binding?.imageCharacter4?.setOnClickListener {
            getDataCharacter(3)?.let { it1 -> gotoComics(it1) }
        }
    }

    private fun loadImageCharacters(characters: List<Result>) {

        if (!characters.isNullOrEmpty()) {
            characters.forEachIndexed { index, character ->
                val position = index + 1
                val nameImage = "imageCharacter$position"
                val imageId = resources.getIdentifier(nameImage, "id", this.context?.packageName)
                val imageView = view?.findViewById<ImageView>(imageId)
                imageView?.let { Glide.with(context!!).load(formUrlImage(character)).into(it) }
            }
        } else {
            showNoData(characters.isEmpty(), "")
        }
        _binding?.progressBar?.visibility = View.INVISIBLE
    }

    private fun formUrlImage(character: Result): String {
        return "${character.thumbnail?.path}.${character.thumbnail?.extension}"
    }

    private fun gotoComics(dataCharacter: Result) {

        val bundle = Bundle()
        val intent = Intent(requireContext(), ComicsActivity::class.java)
        bundle.putSerializable(CHARACTER_DATA_TO_COMIC, dataCharacter)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun getDataCharacter(numberImage: Int): Result? {
        return charactersViewModel.listCharacter.value?.get(numberImage)
    }

    private fun showNoData(show: Boolean, message: String?) {
        _binding?.emptyView?.visibility = if (show) View.VISIBLE else View.GONE
        _binding?.constraintCharacters?.visibility = if (show) View.GONE else View.VISIBLE
        _binding?.emptyTitle?.text =
            if (message.isNullOrEmpty()) requireContext().getString(R.string.text_description_comic_error) else message


    }

}