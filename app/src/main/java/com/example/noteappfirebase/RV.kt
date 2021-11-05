package com.example.noteappfirebase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappfirebase.databinding.NotesRowBinding


class RV(val active :MainActivity,  var list:ArrayList<List<Any>>): RecyclerView.Adapter<RV.ItemBinding>() {

    class ItemBinding (val bin : NotesRowBinding):RecyclerView.ViewHolder(bin.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemBinding {
        return ItemBinding(NotesRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: ItemBinding, position: Int) {
      val id = list [position][0]
        val notes = list[position][1]
        holder.bin.apply{
            mSubTitle.text ="Note $position"
                mSubTitle.text = notes.toString()

            updatebt.setOnClickListener {
                active.dilogfun(id.toString())
            }
            deletebt.setOnClickListener {
              active.delete(id.toString())
            } } }

    override fun getItemCount()=list.size

}