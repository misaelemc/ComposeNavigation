package com.rappi.common.paging

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class Pageable<Value : Any>(default: State<Value> = State.Init) {

    private val _data = MutableStateFlow(default)

    protected var page: Int = 1
    protected var hasMorePages: Boolean = true

    val data: StateFlow<State<Value>> = _data

    init {
        MainScope().launch {
            load()
        }
    }

    abstract suspend fun load()

    protected fun resetPagination() {
        page = 1
        hasMorePages = true
    }

    protected suspend fun emit(value: State.Page<Value>) =
        withContext(Dispatchers.Default) {
            this@Pageable.hasMorePages = value.nextPage != null
            if (data.value is State.Page) {
                addPage(data.value as State.Page, value)
            } else {
                _data.emit(value)
            }
        }

    private suspend fun addPage(current: State.Page<Value>, value: State.Page<Value>) {
        val response = current.copy(current.data + value.data, value.prevPage, value.nextPage)
        _data.emit(response)
    }

    sealed class State<out Value : Any> {
        object Init : State<Nothing>()
        data class Page<Value : Any> constructor(
            val data: List<Value>,
            val prevPage: Int?,
            val nextPage: Int? = null,
        ) : State<Value>()
    }
}