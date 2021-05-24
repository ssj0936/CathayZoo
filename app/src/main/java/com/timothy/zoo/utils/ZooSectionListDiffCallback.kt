package com.timothy.zoo.utils

import androidx.recyclerview.widget.DiffUtil
import com.timothy.zoo.data.model.ZooSectionResultsItem

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