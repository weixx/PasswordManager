package com.xinxin.passwordmanager.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.xinxin.passwordmanager.R
import com.xinxin.passwordmanager.repository.db.DataEntity
import java.util.*

/**
 * Created by 魏欣欣 on 2018/1/11  0011.
 * WeChat : xin10050903
 * Email  : obstinate.coder@foxmail.com
 * Role   : 首页列表的适配器
 */

class MainListAdapter : RecyclerView.Adapter<MainListAdapter.MyViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null
    private var onItemLongClickListener: OnItemLongClickListener? = null

    private var list = ArrayList<DataEntity>()
    private lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_main, parent, false)
        return MyViewHolder(view, onItemClickListener, onItemLongClickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvNameItem_MainList.text = list[position].softName
        Glide.with(context)
                .load(list[position].icon)
                .fitCenter()
                .error(R.drawable.ic_photo_library_black_24dp)
                .into(holder.imgItem_MainList)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View, private val onItemClickListener: OnItemClickListener?, private val onItemLongClickListener: OnItemLongClickListener?) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
        internal val imgItem_MainList: ImageView
        internal val tvNameItem_MainList: TextView

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
            imgItem_MainList = itemView.findViewById(R.id.imgItem_MainList) as ImageView
            tvNameItem_MainList = itemView.findViewById(R.id.tvNameItem_MainList) as TextView
        }

        override fun onClick(view: View) {
            onItemClickListener?.onItemClick(view, layoutPosition)
        }

        override fun onLongClick(view: View): Boolean {
            onItemLongClickListener?.onItemLongClick(view, layoutPosition)
            return true
        }
    }


    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener
    }

    fun setData(list: List<DataEntity>) {
        this.list = list as ArrayList<DataEntity>
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        this.list.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemId(position: Int): Long {
        return list[position].id
    }
    fun getItem(position:Int):DataEntity{
        return list[position]
    }
}
