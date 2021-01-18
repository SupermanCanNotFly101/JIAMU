// pages/detail/detail.js
var util = require('../../utils/util.js')
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    showModalStatus: false,//显示弹窗与否
    number:1,//购买的数量
    category:1,
    tea: {},
    teaPhotoUrlList: [],
    cart:[],
    flag:false,
    str:""
    
  },


  onShareAppMessage: function (res) {
    var that = this
    let str = JSON.stringify(that.data.tea);
    console.log(that.data.tea)
    console.log(str)
    if (res.from === 'button') {
      // 来自页面内转发按钮
      //console.log(res.target)
    }
    return {
      
      title: that.data.tea.teaName,
      path: 'pages/detail/detail'+ "?str=" + str,
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    that.setData({
      str: options.str
    })
    let item = JSON.parse(that.data.str);
    //console.log(item);
    this.setData({ tea: item });
    let a = JSON.parse(item.teaPhotoUrlList)
    var b = []
    for (var i = 0; i < a.length; i++) {
      b.push(a[i].url)
    }
    this.setData({
      teaPhotoUrlList: b,
      cart: app.globalData.cart
    })
    
  },




  /**
   * 加入购物车
   */
  addToCart: function () {
    var that = this
    var cart = app.globalData.cart
    wx.request({
        url: util.getHostUrl() + 'cart/addCart',
        data: {
          userId: app.globalData.userId,
          productId:that.data.tea.id,
          productNum:that.data.number
        },
        success: function (res) {
          console.log(res);
          if(res.data.result==1){
            wx.showToast({
              title: '成功加入购物车',
            })
            var tea = that.data.tea
            tea.select = 0
            tea.productNum = that.data.number
            tea.cartId = res.data.cartId
            console.log(tea)

            cart.push(tea)
            //console.log(cart)
            that.setData({
              cart: cart
            })
            app.globalData.cart = cart
            that.setData({
              showModalStatus: !that.data.showModalStatus
            })
            
          }else{
            wx.showToast({
              title: '加入购物车失败',
              icon:'none'
            })
            setTimeout(function () {
              wx.navigateBack({
                delta: 1
              })
            }, 2000)
          }
        }
      })
  },







  
  
  /////////////////////////////////////////////
  


  /**
   * 加入购物车
   */
  buy:function(){
    var that = this
    var cart = app.globalData.cart
    
    for(var i=0;i<cart.length;i++){
      if(cart[i].id==that.data.tea.id){
        that.setData({
          flag : true
        })
        break
      }
    }
    if(that.data.flag){
      wx.showToast({
        title: '去购物车看看吧',
      })
    }else{
      this.setData({
        showModalStatus: !this.data.showModalStatus //显示弹窗
      })
    }
    
  },

  /**
   * 点击遮罩层消除弹窗
   */
  closeDrawer:function(){
    var that = this
    that.setData({
      number: 1,
      showModalStatus: !that.data.showModalStatus
    })
  },

  /**
   * 增加减少数量
   */
  addNum:function(){
    var that = this
    var number = that.data.number+1
    that.setData({
      number:number
    })
  },
  reduceNum: function () {
    var that = this
    var number = that.data.number - 1
    if(number>=1){
      that.setData({
        number: number
      })
    }
  },


  
  

})