package com.timothy.zoo.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.timothy.zoo.data.model.PlantResultsItem
import com.timothy.zoo.databinding.RecyclerPlantListItemLayoutBinding

class PlantListAdapter(
    private val listener:OnClickListener
):RecyclerView.Adapter<PlantListAdapter.ViewHolder>() {

    private var list:List<PlantResultsItem?> = emptyList()

    class ViewHolder(val binding:RecyclerPlantListItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(plantResultsItem: PlantResultsItem?){
            binding.plant = plantResultsItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerPlantListItemLayoutBinding.inflate(layoutInflater, parent,false)
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

    fun swap(newList: List<PlantResultsItem?>){
        val diffCallback = PlantListDiffCallback(newList,this.list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.list = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun getList():List<PlantResultsItem?> = list

    interface OnClickListener{
        fun itemClick(plantResultsItem:PlantResultsItem)
    }
}

class PlantListDiffCallback(
    private val newData:List<PlantResultsItem?>,
    private val oldData:List<PlantResultsItem?>
): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true

    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean{
        return oldData[oldItemPosition]?.fNameCh == newData[newItemPosition]?.fNameCh &&
            oldData[oldItemPosition]?.fNameLatin == newData[newItemPosition]?.fNameLatin &&
            oldData[oldItemPosition]?.fFeature == newData[newItemPosition]?.fFeature
    }

}