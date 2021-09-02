package com.example.virnect_jeon

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virnect_jeon.network.NaverAPI
import com.example.virnect_jeon.network.NaverBookItem
import com.example.virnect_jeon.network.ResultGetSearchBooks
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookSearchListFragment : Fragment(), View.OnClickListener {

    val naverAPI = NaverAPI.create()

    var searchKeyword: String = "안드로이드"
    var start: Int = 1
    var display: Int = 10
    var total: Int = 0

    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: RecyclerView.Adapter<BooksAdpater.ViewHolder>

    private lateinit var searchButton: Button
    private lateinit var searchEdit: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_book_search_list, container, false)

        recyclerView = view.findViewById(R.id.fragmentRecyclerView)
        var layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // 스크롤이 끝에 도달했는지 확인
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount-1
                if (!recyclerView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    if (mAdapter.itemCount < total) {
                        searchBooks(searchKeyword, ++start, display)
                    }
                    else {
                        // end
                    }
                }
            }
        })

        searchButton = view.findViewById(R.id.search_button)
        searchButton.setOnClickListener(this);
        searchEdit = view.findViewById(R.id.search)
        searchEdit.setText(searchKeyword)
        searchEdit.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(tv: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchKeyword = searchEdit.text.toString()
                    querySearchBooks()
                    return true
                }
                return false
            }
        })

        searchBooks(searchKeyword, start, display)

        return view
    }

    fun searchBooks(query: String, start: Int, display: Int) {
        naverAPI.getSearchBooks(query, start, display)
            .enqueue(object : Callback<ResultGetSearchBooks> {
                override fun onResponse(
                    call: Call<ResultGetSearchBooks>,
                    response: Response<ResultGetSearchBooks>
                ) {
                    var result = response.body()
                    if (context != null) {
                        if (start == 1) {
                            var itemResult = ArrayList<NaverBookItem>()
                            itemResult.addAll(result!!.items)

                            mAdapter = BooksAdpater(context!!, itemResult)
                            recyclerView.adapter = mAdapter
                            total = result!!.total
                        }
                        else if (start > 1) {
                            (mAdapter as BooksAdpater).addItems(result!!.items)
                        }
                    }
                }

                override fun onFailure(call: Call<ResultGetSearchBooks>, t: Throwable) {
                    Log.d("Fail!", "onFailure: ")
                }
            })
    }

    override fun onClick(p0: View?) {
        querySearchBooks()
    }

    fun querySearchBooks() {
        searchKeyword = searchEdit.text.toString()
        start = 1
        total = 0
        searchBooks(searchKeyword, start, display)
    }

}
