package com.timothy.zoo.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.timothy.zoo.data.model.ZooSectionResultsItem
import com.timothy.zoo.databinding.RecyclerZooSectionItemLayoutBinding

class ZooSectionListAdapter(
    private val listener:OnClickListener
):RecyclerView.Adapter<ZooSectionListAdapter.ViewHolder>() {

    private var list:List<ZooSectionResultsItem?> = emptyList()

    class ViewHolder(val binding:RecyclerZooSectionItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(zooSectionResultsItem: ZooSectionResultsItem?){
            binding.section = zooSectionResultsItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerZooSectionItemLayoutBinding.inflate(layoutInflater, parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)

        holder.binding.root.setOnClickListener {
            item?.let {
                listener.itemClick(it)
            }
        }
    }

    fun swap(newZooSectionList: List<ZooSectionResultsItem?>){
        val diffCallback = ZooSectionListDiffCallback(newZooSectionList,this.list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.list = newZooSectionList
        diffResult.dispatchUpdatesTo(this)
    }

    fun getList():List<ZooSectionResultsItem?> = list

    interface OnClickListener{
        fun itemClick(zooSectionResultsItem:ZooSectionResultsItem)
    }
}

class ZooSectionListDiffCallback(
    private val newData:List<ZooSectionResultsItem?>,
    private val oldData:List<ZooSectionResultsItem?>
): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true

    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldData[oldItemPosition]?.id == newData[newItemPosition]?.id
}