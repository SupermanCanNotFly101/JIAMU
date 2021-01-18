// pages/swipper/swipper.js
var util = require('../../utils/util.js')
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    swipperList:[]
  },


  /**
   * 查询轮播图
   */
  getSwipper:function(){

    var that = this
    wx.request({
      url: util.getHostUrl() + 'swipper/getSwipper',
      data:{
      },
      success: function (res) {
        console.log(res)
        that.setData({
          swipperList : res.data
        })
      }
    })
  },




  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    that.getSwipper()
  },


  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this
    that.getSwipper()
  },


  add:function(){
    wx.navigateTo({
      url: './add/add',
    })
  },


  delete: function (e) {
    var that = this
    var url = e.currentTarget.dataset.url
    wx.request({
      url: util.getHostUrl() + 'swipper/deleteSwipper',
      header: {
        'Authorization': app.globalData.Authorization
      },
      data: {
        url: url,
      },
      success: function (res) {
        console.log(res);
        if (res.data.code == 200) {
          wx.showToast({
            title: res.data.msg,
          })
          that.getSwipper()
        } else if (res.data.code == 500) {
          wx.showToast({
            title: res.data.msg,
            image: 'none'
          })
        } else {
          wx.showToast({
            title: '服务器错误',
            image: 'none'
          })
        }

      },
      fail(res) {
        wx.showToast({
          title: '网络错误',
          image: 'none'
        })
      }
    
    })
  },


  


  
})