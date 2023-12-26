package mdideas.devapp.tinhtiendienmdapp.screens.customers

import androidx.recyclerview.widget.DiffUtil
import mdideas.devapp.tinhtiendienmdapp.model.EvnData

class EvnDiffUtilCallback(
    private val oldList: ArrayList<EvnData>,
    private val newList: ArrayList<EvnData>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[newItemPosition].electricPrice == newList[newItemPosition].electricPrice
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}