package com.jvarela.pokdex.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {

    var selected: MutableLiveData<FilterList>? = null
    val originalList = MutableLiveData<List<Pokemon>>()
    val filteredList = MutableLiveData<List<Pokemon>>()

    fun setOriginalList(list: List<Pokemon>){
        originalList.value = list
    }

    fun select(filter: FilterList?) {
        if (selected == null){
            selected = MutableLiveData<FilterList>()
        }
        selected?.value = filter
        filterList()
    }

    fun filterList(){
        if(selected?.value == FilterList.ALL){
            filteredList.value = originalList.value
        }
        else if(selected?.value == FilterList.FAVORITES) {
            filteredList.value = originalList.value?.filter { it.favorite }
        }
        else if(selected?.value == FilterList.VIEWED) {
            filteredList.value = originalList.value?.filter { it.viewed }
        }
    }
}

enum class FilterList {
    FAVORITES, ALL, VIEWED
}