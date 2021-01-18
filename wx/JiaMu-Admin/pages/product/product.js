// pages/product/product.js
var app=getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  gotoSwipper:function(){
    wx.navigateTo({
      url: '../swipper/swipper',
    })
  },
  gotoTea: function () {
    wx.navigateTo({
      url: '../tea/tea',
    })
  },
  gotoTeapot: function () {
    wx.navigateTo({
      url: '../teapot/teapot',
    })
  },

 
})