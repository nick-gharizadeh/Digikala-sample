package com.example.digikalasample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digikalasample.data.model.coupon.Coupon
import com.example.digikalasample.data.model.coupon.CouponLine
import com.example.digikalasample.data.model.customer.Customer
import com.example.digikalasample.data.model.order.Order
import com.example.digikalasample.data.model.product.Category
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.data.model.statusLiveData
import com.example.digikalasample.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductsRepository) :
    ViewModel() {
    var mProduct: Product? = null
    var mOrder = MutableLiveData<Order?>()
    var popularProductList = MutableStateFlow<List<Product>?>(emptyList())
    var ratingProductList = MutableStateFlow<List<Product>?>(emptyList())
    var newestProductList = MutableStateFlow<List<Product>?>(emptyList())
    var productsList = MutableStateFlow<List<Product>?>(emptyList())
    val categoriesList = MutableStateFlow<List<Category>?>(emptyList())
    val relatedProductList = MutableLiveData<List<Product>?>()
    val productByCategoriesList = MutableLiveData<List<Product>?>()
    val specialOffers = MutableLiveData<Product?>()
    var shoppingCardList: List<Product?> = emptyList()
    private var couponsList: List<Coupon>? = emptyList()
    val finalAmount = MutableLiveData<Int>()
    var couponAmount = 0
    var usedCouponList: List<CouponLine> = emptyList()
    var flagOnceUseCoupon = false

    init {
        callServices()
    }

    private fun callServices() {
        getAllCategories()
        getProducts("popularity", popularProductList)
        getProducts("rating", ratingProductList)
        getProducts("date", newestProductList)
        getSliderPhotos()
    }

    fun getProducts(
        orderBy: String,
        relatedStateFlow: MutableStateFlow<List<Product>?>,
        perPage: Int = 20
    ) {

        viewModelScope.launch {
            val response = productRepository.getProducts(
                orderBy = orderBy,
                perPage = perPage
            )
            if (response.message == null)
                relatedStateFlow.emit(
                    response.data
                )
            else
                statusLiveData.postValue(response.message)
        }
    }

    fun getRelatedProducts(includeList: List<Int>) {
        viewModelScope.launch {
            val response = productRepository.getRelatedProducts(includeList)
            if (response.message == null)
                relatedProductList.postValue(response.data)
            else
                statusLiveData.postValue(response.message)
        }
    }


    fun getProductsByCategory(category: Int) {
        viewModelScope.launch {
            val response = productRepository.getProductsByCategory(category = category)
            if (response.message == null)
                productByCategoriesList.postValue(response.data)
            else
                statusLiveData.postValue(response.message)
        }
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            val response = productRepository.getCategories()
            if (response.message == null)
                categoriesList.emit(response.data)
            else
                statusLiveData.postValue(response.message)
        }
    }

    fun getProductById(id: Int?, count: Int) {
        viewModelScope.launch {
            val product = id?.let { productRepository.getProductById(it) }
            addToShoppingCard(product?.data, count)
        }
    }

    private fun getSliderPhotos(id: Int = 608) {
        viewModelScope.launch {
            val response = productRepository.getProductById(id)
            if (response.message == null)
                specialOffers.postValue(response.data)
            else
                statusLiveData.postValue(response.message)
        }
    }


    fun getCoupons() {
        viewModelScope.launch {
            val response = productRepository.getAllCoupons()
            if (response.message == null)
                couponsList = response.data
            else
                statusLiveData.postValue(response.message)
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
        if (flagOnceUseCoupon && couponAmount > 0)
            minusFromFinalAmount(couponAmount)

    }


    fun createOrder(order: Order) {
        viewModelScope.launch {
            val orderResponse = productRepository.createOrder(order)
            if (orderResponse.message == null)
                mOrder.postValue(orderResponse.data)
            else
                statusLiveData.postValue(orderResponse.message)
        }
    }

    private fun isItAlreadyInShoppingCart(): Boolean {
        for (product in shoppingCardList) {
            if (mProduct?.id == product?.id)
                return true
        }
        return false
    }


    fun isItExistsInTheCoupons(code: String): Boolean {
        if (couponsList != null) {
            for (coupon in couponsList!!) {
                if (code == coupon.code && !flagOnceUseCoupon) {
                    minusFromFinalAmount(coupon.amount.toDouble().toInt())
                    usedCouponList = usedCouponList.plus(CouponLine(coupon.code))
                    couponAmount = coupon.amount.toDouble().toInt()
                    return true

                }
            }
        }
        return false
    }


    private fun minusFromFinalAmount(amount: Int) {
        finalAmount.value = finalAmount.value?.minus(amount)
    }

}