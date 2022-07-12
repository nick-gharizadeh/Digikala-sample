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
    var mCustomer = MutableLiveData<Customer?>()
    var mOrder = MutableLiveData<Order?>()
    var mCustomerId: Int? = null
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
            val list = productRepository.getProducts(
                orderBy = orderBy,
                perPage = perPage
            )
            if (list.message == null)
                relatedStateFlow.emit(
                    list.data
                )
            else
                statusLiveData.postValue(list.message)
        }
    }

    fun getRelatedProducts(includeList: List<Int>) {
        viewModelScope.launch {
            val list = productRepository.getRelatedProducts(includeList)
            if (list.message == null)
                relatedProductList.postValue(list.data)
            else
                statusLiveData.postValue(list.message)
        }
    }


    fun getProductsByCategory(category: Int) {
        viewModelScope.launch {
            val list = productRepository.getProductsByCategory(category = category)
            if (list.message == null)
                productByCategoriesList.postValue(list.data)
            else
                statusLiveData.postValue(list.message)
        }
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            val list = productRepository.getCategories()
            if (list.message == null)
                categoriesList.emit(list.data)
            else
                statusLiveData.postValue(list.message)
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
            val list = productRepository.getProductById(id)
            if (list.message == null)
                specialOffers.postValue(list.data)
            else
                statusLiveData.postValue(list.message)
        }
    }


    fun getCoupons() {
        viewModelScope.launch {
            val list = productRepository.getAllCoupons()
            if (list.message == null)
                couponsList = list.data
            else
                statusLiveData.postValue(list.message)
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


    fun createCustomer(firstName: String, lastName: String, email: String) {
        viewModelScope.launch {
            val customer = productRepository.createCustomer(
                firstName = firstName,
                lastName = lastName,
                email = email
            )
            if (customer.message == null) {
                mCustomer.postValue(customer.data)
                mCustomerId = customer.data?.id
            } else
                statusLiveData.postValue(customer.message)
        }
    }

    fun getCustomer(id: Int) {
        viewModelScope.launch {
            val customer = productRepository.getCustomer(id)
            if (customer.message == null)
                mCustomer.postValue(customer.data)
            else
                statusLiveData.postValue(customer.message)
        }
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