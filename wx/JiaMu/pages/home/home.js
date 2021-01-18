// pages/home/home.js
var app=getApp()
var util=require('../../utils/util.js')

Page({

  /**
   * 页面的初始数据
   */
  data: {

    scrennWidth:0,
    scrennHeight:0,
    cardCur: 0,
    swipperList: []

  },

  onShareAppMessage: function (res) {
    if (res.from === 'button') {
      // 来自页面内转发按钮
      //console.log(res.target)
    }
    return {
      //title: '自定义转发标题',
      //path: '/page/user?id=123'
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //设置屏幕信息
    var that = this
    that.setData({
      scrennWidth: app.globalData.windowWidth,
      scrennHeight: app.globalData.windowHeight
    })
    
    wx.request({
      url: util.getHostUrl()+'swipper/getSwipper',
      success(res) {
        //console.log(res.data)
        that.setData({
          swipperList:res.data
        })
        //console.log(that.data.sliderPhoto)
      }
    })
    

  },

  /**
   * 根据选定的dot
   */
  DotStyle(e) {
    this.setData({
      DotStyle: e.detail.value
    })
  },

  /**
   * 变化时改变当前card
   */
  cardSwiper(e) {
    this.setData({
      cardCur: e.detail.current
    })
  },

  /**
   * 导航到各个页面
   */
  goTotea:function(){
    wx.navigateTo({
      url: '../teaMenu/teaMenu',
    })
  },
  goToteapot: function () {
    wx.navigateTo({
      url: '../teapotMenu/teapotMenu',
    })
  },
  goToteaplace: function () {
    wx.navigateTo({
      url: '../teaplace/teaplace',
    })
  }
  
})