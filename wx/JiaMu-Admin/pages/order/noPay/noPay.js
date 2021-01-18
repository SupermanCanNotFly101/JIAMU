// pages/order/noPay/noPay.js
var util = require('../../../utils/util.js')
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    order: []
  },


  /**
   * 获取订单
   */
  getOrder: function () {

    var that = this
    wx.request({
      url: util.getHostUrl() + 'order/getCompleteOrder',
      data: {
        userId: app.globalData.userId
      },
      success(res) {
        console.log(res)
        that.setData({
          order: res.data
        })
        var order = that.data.order
        for (var i = 0; i < that.data.order.length; i++) {
          var info = " "
          if (order[i].status == 0) {
            info = "未付款"
          } else if (order[i].status == 1) {
            info = "已付款"
          } else if (order[i].status == 2) {
            info = "已接单 "
          } else if (order[i].status == 3) {
            info = "已发货 "
          } else if (order[i].status == 4) {
            info = "交易完成 "
          }
          var currentData = "order[" + i + "].payStatusInfo"
          that.setData({
            [currentData]: info
          })

        }

      }
    })
  },





  /////////////////////////////////////////////////////////////////////
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    that.getOrder()
  },

  onShow() {
    var that = this
    that.getOrder()
  },

  /**
   * 订单详情
   */
  gotoOrder: function (e) {
    var orderId = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '../detail/detail' + '?id=' + orderId,
    })
  },

  


})