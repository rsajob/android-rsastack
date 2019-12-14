package com.rsastack.system.ui.recycler

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

/**
 * Created by Roman Savelev (aka @rsa) on 12/26/18.
 */

interface ListItem
{
    fun areItemsTheSame(o: ListItem):Boolean
    fun areContentsTheSame(o: ListItem):Boolean
}

abstract class ListItemAdapterDelegate<LI : ListItem, VH : RecyclerView.ViewHolder>
    : AbsListItemAdapterDelegate<LI, ListItem, VH>()

abstract class AsyncListDifferDelegationAdapterForListItem : AsyncListDifferDelegationAdapter<ListItem>(object : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem) = newItem.areItemsTheSame(oldItem)
    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem) = newItem.areContentsTheSame(oldItem)
})

class DiffCallbackForListItem(
    private val newData: List<ListItem>,
    private val oldData: List<ListItem>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldData.size
    override fun getNewListSize() = newData.size
    override fun areItemsTheSame(oldPos: Int, newPos: Int):Boolean = oldData[oldPos].areItemsTheSame(newData[newPos])
    override fun areContentsTheSame(oldPos: Int, newPos: Int):Boolean = oldData[oldPos].areContentsTheSame(newData[newPos])
}
