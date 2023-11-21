package com.android.bungakata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.bungakata.databinding.FragmentAddPuisiBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var executorService: ExecutorService
private var updateId: Int = 0
private lateinit var puisis: PuisiDao

/**
 * A simple [Fragment] subclass.
 * Use the [AddPuisiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPuisiFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding : FragmentAddPuisiBinding

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
        binding = FragmentAddPuisiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        executorService = Executors.newSingleThreadExecutor()
        val db = PuisiDatabase.getDatabase(requireContext())
        puisis = db!!.puisiDao()!!

        with(binding){
            btnSimpan.setOnClickListener {
                val judul = txtTitle.text.toString()
                val penulis = txtWriter.text.toString()
                val puisi = txtPuisi.text.toString()

                insert(
                    Puisi(
                        title = judul,
                        writer = penulis,
                        content = puisi
                    )
                )

                txtTitle.setText("")
                txtWriter.setText("")
                txtPuisi.setText("")

                Toast.makeText(requireContext(), "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                findNavController().navigate(AddPuisiFragmentDirections.actionAddPuisiFragmentToPuisiFragmet())
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddPuisiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddPuisiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        private fun insert(puisi:Puisi){
            executorService.execute{
                puisis.insert(puisi)
            }
        }
    }
}