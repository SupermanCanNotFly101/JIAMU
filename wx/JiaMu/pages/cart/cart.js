// pages/cart/cart.js
var app = getApp()
var util = require('../../utils/util.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    scrennHeight:0,
    isNull:true, //购物车是否有数据,开始为true
    sumMoney:0,
    cart:[]
  },


  /**
   * 去详情
   */
  gotoDetail: function (e) {
    var index = e.currentTarget.dataset.index;
    //console.log(teaId)
    var that = this
    let str = JSON.stringify(that.data.cart[index]);
    wx.navigateTo({
      url: '../detail/detail' + "?str=" + str,
    })
  },



  /**
   * 计算金额
   */
  makeSumMoney(){
    var that = this
    var cart = that.data.cart
    var sumMoney = 0
    for(var i = 0;i<cart.length;i++){
      if(cart[i].select==1){
        sumMoney = sumMoney + (cart[i].productNum * cart[i].teaPrice)
      }
    }
    that.setData({
      sumMoney : sumMoney
    })
  },

  
 
  
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getCart()
    
  },
  onShow: function (options) {
    this.getCart()
    
  },
  getCart(){
    var that = this
    //获取购物车数据
    //获取购物车
    that.setData({
      sumMoney: 0
    })
    wx.request({
      url: util.getHostUrl() + 'cart/getCart',
      data: {
        userId: app.globalData.userId
      },
      success: function (res) {
        //console.log(res)
        app.globalData.cart = res.data
        //console.log(app.globalData.cart)
        var cart = res.data
        if (cart.length > 0) {
          for (var i = 0; i < cart.length; i++) {
            cart[i].change = 0
            cart[i].select = 0
          }
          that.setData({
            scrennHeight: app.globalData.windowHeight,
            cart: cart,
            isNull: false
          })
        }else{
          that.setData({
            isNull: true
          })
        }
      }
    })
  },


  /**
   * 删除商品
   */
  delete: function (e) {
    var cartId = e.currentTarget.dataset.id
    var index = e.currentTarget.dataset.index
    //console.log(index)
    var that = this;
    //找到需要删除的数组，
    wx.showModal({
      title: '确认删除',
      success(res) {
        if (res.confirm) {
          wx.request({
            url: util.getHostUrl() + 'cart/deleteCart',
            data: {
              cartId: cartId
            },
            success(res) {
              if (res.data == "ok") {
                that.makeSumMoney()
                that.getCart()
              } else {
                wx.showToast({
                  title: '删除失败',
                  icon: "none"
                })
              }
            }
          })
        } else if (res.cancel) {
          //console.log('用户点击取消')
        }
      }
    })

  },




  /**
   * 选择商品
   */
  select:function(e){
    var that = this
    var index = e.currentTarget.dataset.index
    var currentData = "cart["+index+"].select"
    var currentState = that.data.cart[index].select
    //console.log(currentState)
    var temp
    if (currentState==1){
      temp=0
    }else{
      temp=1
    }
    that.setData({
      [currentData]:temp
    })
    app.globalData.cart=that.data.cart

    that.makeSumMoney()
  },


  /**
   * 改变数量
   */
  addNum: function (e) {
    var that = this
    var index = e.currentTarget.dataset.index
    var currentData = "cart[" + index + "].productNum"
    
    var currentState = that.data.cart[index].productNum
    var number = currentState + 1
    that.setData({
      [currentData]: number
    })
    app.globalData.cart = that.data.cart


    var currentDataState = "cart[" + index + "].change"
    //that.changeNum(productId, number)
    that.setData({
      [currentDataState]: 1
    })

    that.makeSumMoney()
  },


  reduceNum: function (e) {
    var that = this
    
    var index = e.currentTarget.dataset.index
    var money = that.data.cart[index].teaPrice
    var currentData = "cart[" + index + "].productNum"
    var currentState = that.data.cart[index].productNum
    var number = currentState - 1
    if(number>=1){
      that.setData({
        [currentData]: number
      })
      app.globalData.cart = that.data.cart
      var currentDataState = "cart[" + index + "].change"
      //that.changeNum(productId, number)
      that.setData({
        [currentDataState]: 1
      })
      
      that.makeSumMoney()
    }
  },

  updateNum(){
    var that = this
    var cart = that.data.cart
    for (var i = 0; i < cart.length; i++) {
      if (cart[i].change == 1) {
        wx.request({
          url: util.getHostUrl() + 'cart/updateCart',
          data: {
            id: cart[i].cartId,
            productNum: cart[i].productNum
          },
          success(res) {
            console.log(res)
          }
        })
      }
    }
  },
  
  onHide(){
    this.updateNum()
  },
  
  
  /**
   * 去付款
   */
  gotoPay:function(){
    var payCart = [];
    var that = this;
    var j = 0;
    var array = that.data.cart; //目标数组
    

    //查找符合条件值并存入新数组
    for (let i in array) {
      var temp = {}
      if (array[i].select) {
        temp.productId = array[i].id
        temp.teaName = array[i].teaName
        temp.teaPrice = array[i].teaPrice
        temp.teaSize = array[i].teaSize
        temp.productNum = array[i].productNum
        temp.teaPhotoUrl = encodeURIComponent(array[i].teaPhotoUrl)
        temp.cartId = array[i].cartId
        payCart[j++] = temp
      }
    }
    var payCart1 = JSON.stringify(payCart);
    if(j>0){
      wx.navigateTo({
        url: '../pay/pay' + '?payCart=' + payCart1 + '&sumMoney=' + that.data.sumMoney,
      }) 
    }
     
  }

})