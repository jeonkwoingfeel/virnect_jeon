package com.example.virnect_jeon

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.MenuItem

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.virnect_jeon.network.NaverBookItem

class BookDetailActivity : AppCompatActivity() {

    private var item: NaverBookItem? = null

    private lateinit var tvTitle: AppCompatTextView
    private lateinit var ivImage: AppCompatImageView
    private lateinit var tvAuthor: AppCompatTextView
    private lateinit var tvPrice: AppCompatTextView
    private lateinit var tvDiscount: AppCompatTextView
    private lateinit var tvPublisher: AppCompatTextView
    private lateinit var tvPubDate: AppCompatTextView
    private lateinit var tvISBN: AppCompatTextView
    private lateinit var tvDesc: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)
        if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        tvTitle = findViewById<View>(R.id.detail_title) as AppCompatTextView
        ivImage = findViewById<View>(R.id.detail_image) as AppCompatImageView
        tvAuthor = findViewById<View>(R.id.detail_author) as AppCompatTextView
        tvPrice = findViewById<View>(R.id.detail_price) as AppCompatTextView
        tvDiscount = findViewById<View>(R.id.detail_discount) as AppCompatTextView
        tvPublisher = findViewById<View>(R.id.detail_publisher) as AppCompatTextView
        tvPubDate = findViewById<View>(R.id.detail_pubdate) as AppCompatTextView
        tvISBN = findViewById<View>(R.id.detail_isbn) as AppCompatTextView
        tvDesc = findViewById<View>(R.id.detail_desc) as AppCompatTextView

        if (intent != null) {
            item = intent.getParcelableExtra("book")
            if (item != null) {
                tvTitle.setText(item!!.title)
                tvTitle.setText(Html.fromHtml(item!!.title))
                tvAuthor.setText(resources.getString(R.string.author, Html.fromHtml(item!!.author)))
                tvPrice.setText(resources.getString(R.string.price, item!!.price))

                tvDiscount.setText(resources.getString(R.string.discount, item!!.discount))
                if (item!!.discount.length == 0) {
                    tvDiscount.visibility = View.GONE
                }

                tvPublisher.setText(resources.getString(R.string.publishier, item!!.publisher))
                tvPubDate.setText(resources.getString(R.string.pubdate, item!!.pubdate))
                tvISBN.setText(resources.getString(R.string.isbn, item!!.isbn))
                tvDesc.setText(resources.getString(R.string.desc, Html.fromHtml(item!!.description)))

                Log.d("ImageURL", item!!.image)
                Glide.with(this).load(item!!.image).centerInside().into(ivImage)
            }
        }
        else {
            //title.setText("Intent is Null")
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}