// pages/teapot/detail/detail.js
var util = require('../../../utils/util.js')
import { $wuxDialog } from '../../../dist/index'
var app=getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    tea: {},
    teaPhotoUrlList: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let item = JSON.parse(options.str);
    this.setData({ tea: item });
    let a = JSON.parse(item.teaPhotoUrlList)
    var b = []
    for (var i = 0; i < a.length; i++) {
      b.push(a[i].url)
    }
    this.setData({
      teaPhotoUrlList: b
    })
  },


  delete() {
    var that = this
    $wuxDialog().confirm({
      resetOnClose: true,
      closable: true,
      title: '确认下架',
      
      onConfirm(e) {
        wx.request({
          url: util.getHostUrl() + 'tea/deleteTea',
          data: {
            tea: that.data.tea,
            code: app.globalData.code
          },
          header: {
            'Authorization': app.globalData.Authorization
          },
          success(res) {
            wx.showToast({
              title: '删除成功',
            })
            setTimeout(function () {
              wx.navigateBack({
                delta: 1
              })
            }, 2000
            )
          }
        })
      },
      onCancel(e) {
        
      },
    })
    
  }


})