package com.example.virnect_jeon

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.virnect_jeon.network.NaverBookItem
import kotlinx.android.synthetic.main.fragment_book_search_list.view.*


class BooksAdpater : RecyclerView.Adapter<BooksAdpater.ViewHolder> {
    private lateinit var items: ArrayList<NaverBookItem>
    private lateinit var context: Context

    constructor(context: Context, items: ArrayList<NaverBookItem>) : super() {
        this.context = context
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksAdpater.ViewHolder {
        var itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BooksAdpater.ViewHolder, position: Int) {
        var item: NaverBookItem = items.get(position)
        holder.setItem(item, context)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, BookDetailActivity::class.java)
            intent.putExtra("book", item)
            ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    public fun addItems(add: List<NaverBookItem>) {
        this.items.addAll(add)
        this.notifyDataSetChanged()
    }

    class ViewHolder : RecyclerView.ViewHolder {
        private lateinit var tvTitle: TextView
        private lateinit var tvAuthor: TextView
        private lateinit var tvPrice: TextView
        private lateinit var tvDiscount: TextView
        private lateinit var tvPublisher: TextView
        private lateinit var ivImage: ImageView
//        private lateinit var tvPubDate: TextView
//        private lateinit var tvISBN: TextView
//        private lateinit var tvDesc: TextView

        constructor(itemView: View) : super(itemView) {
            tvTitle = itemView.findViewById(R.id.title)
            tvAuthor = itemView.findViewById(R.id.author)
            tvPrice = itemView.findViewById(R.id.price)
            tvDiscount = itemView.findViewById(R.id.discount)
            tvPublisher = itemView.findViewById(R.id.publisher)
//            tvPubDate = itemView.findViewById(R.id.pubdate)
//            tvISBN = itemView.findViewById(R.id.isbn)
//            tvDesc = itemView.findViewById(R.id.description)
            ivImage = itemView.findViewById(R.id.image)
        }

        fun setItem(item: NaverBookItem, context : Context) {
            tvTitle.setText(Html.fromHtml(item.title))
            tvAuthor.setText(context.resources.getString(R.string.author, Html.fromHtml(item.author)))
            tvPrice.setText(context.getString(R.string.price, item.price))
            tvDiscount.setText(context.getString(R.string.discount, item.discount))
            if (item.discount.length == 0) {
                tvDiscount.visibility = View.INVISIBLE
            }
            tvPublisher.setText(context.getString(R.string.publishier, item.publisher))
//            tvPubDate.setText(res.getString(R.string.pubdate, item.pubdate))
//            tvISBN.setText(res.getString(R.string.isbn, item.isbn))
//            tvDesc.setText(res.getString(R.string.desc, Html.fromHtml(item.description)))
            Glide.with(context).load(item.image).into(ivImage)
        }
    }
}