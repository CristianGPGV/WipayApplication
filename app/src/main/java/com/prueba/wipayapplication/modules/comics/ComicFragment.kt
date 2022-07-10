package com.prueba.wipayapplication.modules.comics

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.prueba.wipayapplication.R
import com.prueba.wipayapplication.databinding.FragmentComicsBinding
import com.prueba.wipayapplication.marvelapi.io.MainRepository
import com.prueba.wipayapplication.marvelapi.io.RetrofitService
import com.prueba.wipayapplication.marvelapi.model.Result
import com.prueba.wipayapplication.modules.characters.CharactersFragment
import com.prueba.wipayapplication.modules.detailComics.DetailComicsActivity


class ComicFragment : Fragment(),ComicsAdapterListener {

    companion object {
        const val COMIC_DATA_TO_DETAIL_COMIC = "COMIC_DATA_TO_DETAIL_COMIC"
    }

    private var _binding: FragmentComicsBinding? = null
    private val binding get() = _binding!!

    private lateinit var comicsAdapter: ComicsAdapter

    private lateinit var comicsViewModel: ComicsViewModel

    private lateinit var  formData: Result

    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = activity?.intent?.extras
        formData = bundle?.getSerializable(CharactersFragment.CHARACTER_DATA_TO_COMIC) as Result

        comicsViewModel = ViewModelProvider(this, ComicsModelFactory(MainRepository(retrofitService)))[ComicsViewModel::class.java]

        comicsViewModel.listComics.observe(this, getComicsObserver())

        comicsViewModel.errorMessage.observe(this, Observer {
            _binding?.progressBar?.visibility = View.INVISIBLE
            showNoData(true)
        })
        comicsViewModel.getComics(formData.id.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentComicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.toolbar?.setNavigationOnClickListener { this.requireActivity().onBackPressed() }
        _binding?.progressBar?.visibility  = View.VISIBLE
        _binding?.titleTextComic?.text  = "${context?.getString(R.string.text_comics)} ${formData.name}"

        config()
    }

    private fun config() {
        _binding?.formHolderRecycler?.layoutManager = LinearLayoutManager(context)
        comicsAdapter = ComicsAdapter(context)
        comicsAdapter.listener = this
        _binding?.formHolderRecycler?.adapter = comicsAdapter
    }

    private fun getComicsObserver(): Observer<List<Result>> {
        return Observer {
            comicsAdapter.comics = it
            _binding?.progressBar?.visibility = View.INVISIBLE
            showNoData(it.isEmpty())
        }
    }

    override fun itemSelected(position: Int) {
        val comic: Result =  comicsAdapter.comics[position]
        val bundle = Bundle()
        bundle.putSerializable(COMIC_DATA_TO_DETAIL_COMIC, comic)
        this.openDetailComic(bundle)
    }

    private fun openDetailComic(bundle : Bundle){
        val intent = Intent(context, DetailComicsActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun showNoData(show: Boolean){
        _binding?.emptyView?.visibility = if(show) View.VISIBLE else View.GONE
    }
}