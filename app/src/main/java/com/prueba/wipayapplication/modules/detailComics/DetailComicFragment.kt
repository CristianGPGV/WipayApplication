package com.prueba.wipayapplication.modules.detailComics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.prueba.wipayapplication.R
import com.prueba.wipayapplication.databinding.FragmentDetailComicBinding
import com.prueba.wipayapplication.marvelapi.model.Result
import com.prueba.wipayapplication.modules.comics.ComicFragment

class DetailComicFragment : Fragment() {

    private var _binding: FragmentDetailComicBinding? = null
    private val binding get() = _binding!!

    private lateinit var comicData: Result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = activity?.intent?.extras
        comicData = bundle?.getSerializable(ComicFragment.COMIC_DATA_TO_DETAIL_COMIC) as Result
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailComicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.toolbar?.setNavigationOnClickListener { this.requireActivity().onBackPressed() }
        _binding?.progressBar?.visibility = View.VISIBLE
        config()

    }

    private fun config() {
        if (isEmpty(comicData)) {
            showNoData(isEmpty(comicData))
        } else {
            val urlImage =
                "${this.comicData.thumbnail?.path}.${this.comicData.thumbnail?.extension}"
            _binding?.let {
                Glide.with(context!!).load(urlImage).into(it.imageComicDetail)
                _binding?.titleTextComic?.text =
                    if (comicData.title.isNullOrEmpty()) requireContext().getString(R.string.text_name_comic_error) else comicData.title
                _binding?.descriptionMessageText?.text =
                    if (comicData.description.isNullOrEmpty()) requireContext().getString(R.string.text_description_comic_error) else comicData.description
            }
            _binding?.progressBar?.visibility = View.INVISIBLE
            showNoData(isEmpty(comicData))
        }
    }

    private fun showNoData(show: Boolean) {
        _binding?.imageComicDetail?.visibility = if (show) View.GONE else View.VISIBLE
        _binding?.emptyView?.visibility = if (show) View.VISIBLE else View.GONE
    }


    private fun isEmpty(result: Result): Boolean {
        return (result.id == (0L) ||
                result.id == null) &&
                result.name.isNullOrEmpty() &&
                result.title.isNullOrEmpty() &&
                result.description.isNullOrEmpty() &&
                result.modified.isNullOrEmpty() &&
                result.resourceURI.isNullOrEmpty() &&
                result.thumbnail?.path.isNullOrEmpty() &&
                result.thumbnail?.extension.isNullOrEmpty()
    }

}