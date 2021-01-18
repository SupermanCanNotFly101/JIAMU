// pages/login/login.js
var util = require('../../utils/util.js')
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    disabled: false
  },

  /**
   * 登录
   */
  formSubmit: function (e) {
    var that =this
    wx.showLoading({
      title: '登录中...',
    })
    //console.log(e);
    that.setData({ disabled: true });
    wx.request({
      url: util.getHostUrl()+'admin/login', 
      data: {
        no: e.detail.value.no,
        pwd: e.detail.value.pwd
      },
      success: function (res) {
        console.log(res);
        if(res.statusCode==200){
          if(res.data.code == 200){
            wx.showToast({
              title: res.data.msg,
              icon: 'success',
              duration: 2000
            })
          app.globalData.Authorization = res.data.data
          that.setData({ disabled: false });
          setTimeout(function () {
            wx.switchTab({
              url: '../product/product'
            })
          }, 2000)
          } else if (res.data.code == 403){
            wx.showToast({
              title: res.data.msg,
              icon: 'none',
              duration: 2000
            })
            that.setData({ disabled: false });
          } else if (res.data.code == 401){
            wx.showToast({
              title: res.data.msg,
              icon: 'none',
              duration: 2000
            })
            that.setData({ disabled: false });
          }
        }else{
          wx.showToast({
            title: "未知错误",
            icon: 'none',
            duration: 2000
          })
          that.setData({ disabled: false });
        }
      },
      fail(res){
        wx.showToast({
          title: "网络错误",
          icon: 'none',
          duration: 2000
        })
        that.setData({ disabled: false });
      }
    })
  },

})