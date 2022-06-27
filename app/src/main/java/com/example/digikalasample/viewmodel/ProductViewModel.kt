package com.example.digikalasample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digikalasample.data.model.FilterItem
import com.example.digikalasample.data.model.coupon.Coupon
import com.example.digikalasample.data.model.customer.Customer
import com.example.digikalasample.data.model.order.Order
import com.example.digikalasample.data.model.product.Category
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.data.model.review.Review
import com.example.digikalasample.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductsRepository) :
    ViewModel() {
    var mProduct: Product? = null
    var mCustomer = MutableLiveData<Customer?>()
    var mOrder = MutableLiveData<Order?>()
    var mCustomerId: Int? = null
    val popularProductList = MutableStateFlow<List<Product?>>(emptyList())
    val ratingProductList = MutableStateFlow<List<Product?>>(emptyList())
    val newestProductList = MutableStateFlow<List<Product?>>(emptyList())
    val categoriesList = MutableStateFlow<List<Category?>>(emptyList())
    val productByCategoriesList = MutableLiveData<List<Product?>>()
    val specialOffers = MutableLiveData<Product?>()
    var shoppingCardList: List<Product?> = emptyList()
    var couponsList: List<Coupon?> = emptyList()
    var reviewsList = MutableLiveData<List<Review?>>()
    val searchedProductsList = MutableLiveData<List<Product?>>()
    var orderCriterion: String? = "popularity"
    var orderSortType: String? = "asc"
    var lastSearch: String = ""
    val finalAmount = MutableLiveData<Int>()

    init {
        callServices()
    }

    private fun callServices() {
        getAllCategories()
        getProducts("popularity", popularProductList)
        getProducts("rating", ratingProductList)
        getProducts("date", newestProductList)
        getSpecialOffers()
    }

    private fun getProducts(orderBy: String, relatedLiveData: MutableStateFlow<List<Product?>>) {
        viewModelScope.launch {
            relatedLiveData.emit(productRepository.getProducts(orderBy = orderBy))
        }
    }

    fun getProductsBySearch(
        searchQuery: String,
        orderBy: String = "popularity",
        order: String = "asc"
    ) {
        viewModelScope.launch {
            val list = productRepository.getProductsBySearch(searchQuery, orderBy, order)
            searchedProductsList.value = list
        }
    }

    private fun getProductsBySearch(
        searchQuery: String,
        orderBy: String = "popularity",
        order: String = "asc", attribute: String, attributeTerm: String
    ) {
        viewModelScope.launch {
            val list = productRepository.getProductsBySearch(
                searchQuery,
                orderBy,
                order,
                attribute,
                attributeTerm
            )
            searchedProductsList.value = list
        }
    }


    fun getProductsByCategory(category: Int) {
        viewModelScope.launch {
            val list = productRepository.getProductsByCategory(category = category)
            productByCategoriesList.value = list
        }
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            categoriesList.emit(productRepository.getCategories())
        }
    }

    suspend fun getProductById(id: Int?): Product? {
        return id?.let { productRepository.getProductById(it) }
    }

    // 📌 get slider photos
    private fun getSpecialOffers(id: Int = 608) {
        viewModelScope.launch {
            val list = productRepository.getProductById(id)
            specialOffers.value = list
        }
    }

    fun getReviews(id: String) {
        viewModelScope.launch {
            val list = productRepository.getReviews(id)
            reviewsList.value = list
        }
    }

    fun getCoupons() {
        viewModelScope.launch {
            couponsList = productRepository.getAllCoupons()
        }
    }

    fun addToShoppingCard(product: Product? = mProduct, count: Int = 1) {
        if (isItAlreadyInShoppingCart()) {
            for (shoppingCartProduct in shoppingCardList)
                if (shoppingCartProduct?.id == product?.id) {
                    shoppingCartProduct?.count = shoppingCartProduct?.count?.plus(1)
                    shoppingCardList = shoppingCardList.minus(shoppingCartProduct)
                    shoppingCardList = shoppingCardList.plus(shoppingCartProduct)
                }
        } else {
            product?.count = count
            shoppingCardList = shoppingCardList.plus(product)
        }
    }

    fun removeFromShoppingCard(product: Product?) {
        shoppingCardList = shoppingCardList.minus(product)
    }

    fun calculatePrice() {
        var sum = 0
        for (product in shoppingCardList) {
            sum += product?.count?.times(product.price.toInt()) ?: 0
        }
        finalAmount.value = sum
    }


    fun createCustomer(firstName: String, lastName: String, email: String) {
        viewModelScope.launch {
            val customer = productRepository.createCustomer(
                firstName = firstName,
                lastName = lastName,
                email = email
            )
            mCustomer.value = customer
            mCustomerId = customer?.id
        }
    }


    fun createOrder(order: Order) {
        viewModelScope.launch {
            val orderResponse = productRepository.createOrder(order)
            mOrder.value = orderResponse
        }
    }

    private fun isItAlreadyInShoppingCart(): Boolean {
        for (product in shoppingCardList) {
            if (mProduct?.id == product?.id)
                return true
        }
        return false
    }

    fun doFilterByColor(filterItem: FilterItem) {
        orderSortType?.let {
            orderCriterion?.let { it1 ->
                getProductsBySearch(
                    lastSearch, it1,
                    it, "pa_color", filterItem.id.toString()
                )
            }
        }
    }


    fun doFilterBySize(filterItem: FilterItem) {
        orderSortType?.let {
            orderCriterion?.let { it1 ->
                getProductsBySearch(
                    lastSearch, it1,
                    it, "pa_size", filterItem.id.toString()
                )
            }
        }
    }


    fun isItExistsInTheCoupons(code: String): Boolean {
        for (coupon in couponsList) {
            if (code == coupon?.code) {
                minusFromFinalAmount(coupon.amount.toDouble().toInt())
                return true
            }
        }
        return false
    }


    private fun minusFromFinalAmount(amount: Int) {
        finalAmount.value = finalAmount.value?.minus(amount)
    }

}