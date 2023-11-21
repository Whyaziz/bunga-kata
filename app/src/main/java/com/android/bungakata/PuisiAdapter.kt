package com.android.bungakata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.bungakata.databinding.ListPuisiBinding

typealias OnClickPuisi = (Puisi) -> Unit
typealias PassDataToEdit = (Puisi) -> Unit
typealias DeleteData = (Puisi) -> Unit

class PuisiAdapter(private val listPuisi : List<Puisi>,
                   private val onClickPuisi: OnClickPuisi,
                   private val passDataToEdit: PassDataToEdit,
                   private val deleteData: DeleteData

) : RecyclerView.Adapter<PuisiAdapter.PuisiViewHolder>() {

    inner class PuisiViewHolder(private val binding: ListPuisiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(puisi: Puisi) {
            with(binding) {

                txtTitle.text = puisi.title
                txtWriter.text = puisi.writer

                btnEdit.setOnClickListener {

                }

            }
        }

        init {
            itemView.setOnLongClickListener {

                val background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_list_focused)
                val txtWhite = ContextCompat.getColor(itemView.context, R.color.white)

                itemView.background = background
                with(binding){
                    layoutHold.visibility = View.VISIBLE
                    txtTitle.setTextColor(txtWhite)
                    txtWriter.setTextColor(txtWhite)

                    btnEdit.setOnClickListener {
                        passDataToEdit(listPuisi[adapterPosition])
                    }

                    btnDelete.setOnClickListener {
                        deleteData(listPuisi[adapterPosition])
                    }
                }

                true
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PuisiViewHolder {
        val binding = ListPuisiBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PuisiViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listPuisi.size
    }

    override fun onBindViewHolder(holder: PuisiViewHolder, position: Int) {
        val puisi = listPuisi[position]
        holder.bind(listPuisi[position])

        holder.itemView.setOnClickListener {
            onClickPuisi(puisi)
        }


    }
}