// pages/teaMenu/teaMenu.js
var util = require('../../utils/util.js')
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    tea:[]
  },

  /**
    * 获取商品 type=1
    */
  getProduct() {
    var that = this
    wx.request({
      url: util.getHostUrl() + 'tea/getTea',
      data: {
        teaType: 1
      },
      success(res) {
        console.log(res)
        that.setData({
          tea: res.data
        })
      }
    })

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getProduct()
  },
  onShow: function () {
    this.getProduct()
  },

  /**
   * 商品详情页
   */
  gotoDetail:function(e){
    var index = e.currentTarget.dataset.index;
    //console.log(teaId)
    var that = this
    let str = JSON.stringify(that.data.tea[index]);
    console.log(str)
    wx.navigateTo({
      url: '../detail/detail' + "?str=" + str,
    })

    
  }
})