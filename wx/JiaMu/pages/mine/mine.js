// pages/mine/mine.js


var app=getApp()

var util = require('../../utils/util.js')

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    scrennHeight:null,
    scrennWidth:null,
    copyright:null,
    phone:null

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that=this
    that.setData({
      scrennWidth:app.globalData.windowWidth,
      scrennHeight: app.globalData.windowHeight,
      phone: util.getPhone(),
      copyright: util.getCopyright()
    })
   // console.log(app.globalData)
    //console.log(that.data.scrennHeight)
   // console.log(that.data.phone)
    
  },

  navigateToOrder: function () {
    wx.navigateTo({
      url: '../order/order',
    })
  },

  navigateToAddress:function(){
    wx.navigateTo({
      url: '../address/address',
    })
  },

  navigateToMap: function (e) {
    wx.openLocation({
      latitude: 30.055930086,
      longitude: 103.8325149574,
      name: "嘉木",
      address: "眉山市东坡区杭州北路192号附45号"
    })
  },

  phone:function(){
    var that=this
    wx.makePhoneCall({
      phoneNumber:that.data.phone,
    })
  }
})