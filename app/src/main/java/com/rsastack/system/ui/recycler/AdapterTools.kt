package com.rsastack.system.ui.recycler

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate

/**
 * Created by Roman Savelev (aka @rsa) on 12/26/18.
 */

interface ListItem
{
    fun areItemsTheSame(o: ListItem):Boolean
    fun areContentsTheSame(o: ListItem):Boolean
}

abstract class ListItemAdapterDelegate<I : ListItem, VH : RecyclerView.ViewHolder>
    : AbsListItemAdapterDelegate<I, ListItem, VH>()

class DiffCallbackForListItem(
    private val newData: List<ListItem>,
    private val oldData: List<ListItem>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldData.size
    override fun getNewListSize() = newData.size
    override fun areItemsTheSame(oldPos: Int, newPos: Int):Boolean = oldData[oldPos].areItemsTheSame(newData[newPos])
    override fun areContentsTheSame(oldPos: Int, newPos: Int):Boolean = oldData[oldPos].areContentsTheSame(newData[newPos])
}
