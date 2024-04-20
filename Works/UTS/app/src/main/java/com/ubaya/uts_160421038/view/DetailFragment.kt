package com.ubaya.uts_160421038.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ubaya.uts_160421038.R
import com.ubaya.uts_160421038.databinding.FragmentDetailBinding
import com.ubaya.uts_160421038.model.Page
import com.ubaya.uts_160421038.viewmodel.DetailViewModel

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        var id = 0
        if(arguments != null){
            id = DetailFragmentArgs.fromBundle(requireArguments()).idGame
        }
        binding.btnPrev.visibility = View.GONE

        viewModel.fetch(id)

        viewModel.beritaLD.observe(viewLifecycleOwner, Observer {
            binding.txtTitle2.setText(it.title)
            binding.txtAuthor2.setText("by " + it.author)
            binding.txtDate2.setText("created on: " + it.date)
            binding.txtDesc2.setText(it.description)


            val picasso = Picasso.Builder(requireContext())
            picasso.listener { picasso, uri, exception ->
                exception.printStackTrace()
            }

            picasso.build().load(it.images)
                .into(binding.imageView4, object: Callback {
                    override fun onSuccess() {
                        binding.progressBar4.visibility = View.INVISIBLE
                        binding.imageView4.visibility = View.VISIBLE
                    }
                    override fun onError(e: Exception?) {
                        Log.e("picasso_error", e.toString())
                    }
                })
        })
        var currPagee = 1
        fun each(it:List<Page>, idx:Int){
            it.forEach {
                if (it.id == idx){
                    currPagee = idx
                    binding.txtContent.setText(it.newsContent)
                }
            }
        }
        viewModel.listPage.observe(viewLifecycleOwner, Observer{
            var page = it
            each(it, 1)
            binding.btnNext.setOnClickListener {
                if (currPagee == 1){
                    each(page,currPagee+1)
                    binding.btnPrev.visibility = View.VISIBLE
                    binding.btnNext.visibility = View.VISIBLE
                }
                else if (currPagee == 2){
                    each(page,currPagee+1)
                    binding.btnNext.visibility = View.GONE
                    binding.btnPrev.visibility = View.VISIBLE
                }
            }
            binding.btnPrev.setOnClickListener {
                if (currPagee == 3) {
                    each(page, currPagee-1)
                    binding.btnNext.visibility = View.VISIBLE
                    binding.btnPrev.visibility = View.VISIBLE
                }
                else if (currPagee == 2) {
                    each(page, currPagee-1)
                    binding.btnPrev.visibility = View.GONE
                    binding.btnNext.visibility = View.VISIBLE
                }
            }
        })




    }
}