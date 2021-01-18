// pages/order/detail/detail.js
var util = require('../../../utils/util.js')
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id:null,
    orderId: null,
    sumMoney: null,
    payStatus: null,
    addressStatus:null,
    userName:null,
    userPhone:null,
    userAddress:null,
    cart:[],
    //0 未付款 1 已经付款  2 接单   3 已发货  4 交易完成
    payStatusInfo:null,
    expressId:null
  },

  getOrderById:function(id){

    var that = this
    wx.request({
      url: util.getHostUrl() + 'order/getOrderById',
      data:{
        id:id
      },
      success(res){
        console.log(res)
        var a = res.data
        that.setData({
          orderId: a.orderId,
          sumMoney:a.sumMoney,
          payStatus: a.payStatus,
          addressStatus: a.addressStatus,
          userName: a.userName,
          userPhone: a.userPhone,
          userAddress: a.userAddress,
          cart: a.cart
        })
        if(a.expressId!=0){
          that.setData({
            expressId:a.expressId
          })
        }
        var info = null
        
        if(a.payStatus==0){
          info ="未付款"
        } else if (a.payStatus == 1){
          info = "已付款"
        } else if (a.payStatus == 2) {
          info = "已接单 "
        } else if (a.payStatus == 3) {
          info = "已发货 "
        } else if (a.payStatus == 4){
          info = "交易完成 "
        }
        that.setData({
          payStatusInfo:info
        })



      }
    })

  },

  
  
  
  
  //////////////////////////////////////
  
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //console.log(options.id)
    var that = this
    var id = options.id
    that.setData({
      id:id
    })
    that.getOrderById(id)
  },

  deleteOrder(){
    var that = this
    var id =that.data.id
    wx.request({
      url: util.getHostUrl() + 'order/deleteOrder',
      data:{
        id:id
      },success(res){
        if(res.data=="ok"){
          wx.showToast({
            title: '删除成功',
          })
          setTimeout(function(){
            wx.navigateBack({
              delta: 1
            })
          },2000)
        }else{
          wx.showToast({
            title: '删除失败',
            icon:'none'
          })
          setTimeout(function () {
            wx.navigateBack({
              delta: 1
            }, 2000)
          })
        }
      }
    })
  },

  goToPay(){
    var that = this;
    wx.showLoading({
      title: '正在前往支付',
    })
    wx.request({
      url: util.getHostUrl() + 'wxPay/prepay',

      data: {
        orderId: that.data.orderId,
        sumMoney: that.data.sumMoney * 100,
        openId: app.globalData.openId

      },
      success: function (res) {
        console.log(res);
        var c = res.data.data;
        wx.requestPayment({
          timeStamp: c.timestamp,
          nonceStr: c.nonce_str,
          package: c.package,
          signType: 'MD5',
          paySign: c.sign,
          success(res) {
            console.log(res)
            console.log("统一下单接口成功");
            wx.hideLoading()
            wx.showToast({
              title: '支付成功',
            })
            that.getOrderById(that.data.id)
            

          },
          fail(res) {
            console.log(res)
            console.log("统一下单接口失败");
            wx.hideLoading()
            wx.showToast({
              title: '支付失败',
              icon: 'none'
            })

          }
        });
      },
      fail: function (res) { },
      complete: function (res) { },
    });

  
  }

  
})