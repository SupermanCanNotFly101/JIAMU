// pages/pay/pay.js
var util =  require('../../utils/util.js')
var app = getApp()
import { $wuxDialog } from '../../dist/index'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    payCart:[], //购物车
    sumMoney:0, //总金额
    orderId:"", //订单Id
    time:"",    //创建时间
    scrennHeight: 0,
    choose:true,
    myself:false,
    myselfPhone:null,
    address:false,
    addressId:null, 
    addressInfo:null
  },

  //////////////////////////////////////////////////////////////////////////
  /**
   * 支付创建订单
   */
  gotoPay:function(){
    var that = this
    var timestamp = Date.parse(new Date());
    timestamp = timestamp / 1000;
    var TIME = util.formatTime(new Date());
    //console.log(TIME)
    var orderId = timestamp + app.globalData.openId
    that.setData({
      orderId: orderId.substring(0, 20),
      time: TIME
    })
    //当选择了地址时
    if(!that.data.myself&&!that.data.address){
      wx.showToast({
        title: '请选择地址',
        icon:'none'
      })
    }else{
      
      wx.showLoading({
        title: '生成订单中',
      })
      //如果自取
      if(that.data.myself){
        wx.request({
          url: util.getHostUrl() + 'order/createOrder',
          data: {
            orderId: that.data.orderId,
            sumMoney: that.data.sumMoney,
            userId: app.globalData.userId,
            addressStatus: 0,
            userPhone:that.data.myselfPhone,
            payCart: that.data.payCart,
            id:0,
            payStatus:0
          },
          success(res) {
            //生成订单成功
            if (res.data.result == 1) {
              setTimeout(function(){
                wx.hideLoading()
                wx.showLoading({
                  title: '正在前往支付',
                })
              },2000)
              
              /////////////////////////////////////

              that.pay()
              /////////////////////////////////////

            } else {
              wx.hideLoading()
              wx.showToast({
                title: '生成订单失败，稍后尝试',
                icon: 'none',
                duration: 2000
              })
              setTimeout(function () {
                wx.navigateBack({
                  delta: 1
                })
              }, 2000)
            }
          }
        })
        //当有地址时
      }else{
        var addressInfo = that.data.addressInfo
        var address = addressInfo.userAddress1a + addressInfo.userAddress1b + addressInfo.userAddress1c + addressInfo.userAddress2
        wx.request({
          url: util.getHostUrl() + 'order/createOrder',
          data: {
            orderId: that.data.orderId,
            sumMoney: that.data.sumMoney,
            userId: app.globalData.userId,
            addressStatus: 1,
            userName: that.data.addressInfo.userName,
            userPhone: that.data.addressInfo.userPhone,
            userAddress:address,
            payCart:that.data.payCart,
            id:0,
            payStatus:0
          },
          success(res) {
            
            if(res.data.result==1){
              setTimeout(function () {
                wx.hideLoading()
                wx.showLoading({
                  title: '正在前往支付',
                })
              }, 2000)
              /////////////////////////////////////
              that.pay()

              /////////////////////////////////////
              
            }else{
              wx.hideLoading()
              wx.showToast({
                title: '生成订单失败，稍后尝试',
                icon: 'none',
                duration: 2000
              })
              setTimeout(function(){
                wx.navigateBack({
                  delta: 1
                })
              },2000)  
            }
          }
        })
      }           
    }  
  },

 
 


 
 ///////////////////////////////////////////////////////////////////////////
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    var payCart = JSON.parse(options.payCart)
    var sumMoney = 0
    for(let i in payCart){
      var temp = payCart[i].teaPhotoUrl
      //照片转码
      payCart[i].teaPhotoUrl = decodeURIComponent(temp)
      //验证sumMoney
      sumMoney = sumMoney + (payCart[i].productNum * payCart[i].teaPrice)
    }
    //sumMoney = options.sumMoney
    //console.log(options)
    //console.log(payCart,sumMoney)
    that.setData({
      payCart:payCart,
      sumMoney :sumMoney
    })
    var timestamp = Date.parse(new Date());
    timestamp = timestamp / 1000;
    var TIME = util.formatTime(new Date());
    //console.log(TIME)
    var orderId = timestamp + app.globalData.openId
    that.setData({
      orderId: orderId.substring(0, 20),
      time : TIME
    })
    that.setData({
      scrennHeight: app.globalData.windowHeight,
    })
  },



  /////////////////////////////////////////////////////////////////////////
  /**
   * 选地址
   */
  chooseAddress:function(){
    wx.navigateTo({
      url: '../address/choose/choose',
    })
  },


  pay(){
    var that = this;
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
            setTimeout(function () {
              wx.redirectTo({
                url:'../order/order'
              })
            }, 2000)  

          },
          fail(res) {
            console.log(res)
            console.log("统一下单接口失败");
            wx.hideLoading()
            wx.showToast({
              title: '支付失败',
              icon:'none'
            })

          }
        });
      },
      fail: function (res) { },
      complete: function (res) { },
    });
  
  }

  
})