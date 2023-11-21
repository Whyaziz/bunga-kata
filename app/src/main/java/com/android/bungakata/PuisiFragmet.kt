package com.android.bungakata

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.bungakata.databinding.FragmentPuisiFragmetBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private lateinit var binding : FragmentPuisiFragmetBinding
private lateinit var executorService: ExecutorService
private var updateId: Int = 0
private lateinit var puisis: PuisiDao

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PuisiFragmet.newInstance] factory method to
 * create an instance of this fragment.
 */
class PuisiFragmet : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPuisiFragmetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        executorService = Executors.newSingleThreadExecutor()
        val db = PuisiDatabase.getDatabase(requireContext())
        puisis = db!!.puisiDao()!!


        with(binding){

            reycPuisi.apply {
                layoutManager = LinearLayoutManager(requireContext())
            }

        }
        getAllNotes()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PuisiFragmet.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PuisiFragmet().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getAllNotes() {

        puisis.allPuisi.observe(viewLifecycleOwner){ puisi ->
            val adapter = PuisiAdapter(puisi,
                { selectedPuisi ->

                updateId = selectedPuisi.id
                val title = selectedPuisi.title
                val writer = selectedPuisi.writer
                val content = selectedPuisi.content

                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra("UPDATE_ID", updateId)
                intent.putExtra("TITLE", title)
                intent.putExtra("WRITER", writer)
                intent.putExtra("CONTENT", content)
                startActivity(intent)

            }, { selectedPuisi ->
                updateId = selectedPuisi.id
                val title = selectedPuisi.title
                val writer = selectedPuisi.writer
                val content = selectedPuisi.content

                val intent = Intent(requireContext(), EditActivity::class.java)
                intent.putExtra("UPDATE_ID", updateId)
                intent.putExtra("TITLE", title)
                intent.putExtra("WRITER", writer)
                intent.putExtra("CONTENT", content)
                startActivity(intent)
            },{selectedPuisi ->
                    delete(selectedPuisi)
                }
            )

            binding.reycPuisi.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        getAllNotes()
    }

    private fun delete(puisi: Puisi){
        executorService.execute{
            puisis.delete(puisi)
        }
    }
}