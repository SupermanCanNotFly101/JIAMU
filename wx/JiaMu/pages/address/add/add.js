// pages/address/add/add.js
var util = require('../../../utils/util.js')
var app=getApp()

Page({

  data: {
    region: [],
    modalName: null  
  },

  /**
   * 提交表单
   */
  submit(e) {
    var that = this
    //console.log('Default Form Submit \n', e.detail.value)
    //console.log(that.data.region)
    if (e.detail.value.phone == null ||
      e.detail.value.phone == "" ||
      e.detail.value.name == null ||
      e.detail.value.name == "" ||
      e.detail.value.address2 == null ||
      e.detail.value.address2 == "" ||
      that.data.region.length == 0) {
        console.log(e.detail.value)
        wx.showToast({
          title: '请填写完整信息',
          icon: 'none',
          duration: 1500
      })
    } else if (e.detail.value.phone.length!=11||
              !e.detail.value.phone.startsWith("1")){
      wx.showToast({
        title: '请检查手机号',
        icon: 'none',
        duration: 1500
      })
    } else if (e.detail.value.name.length>=20){
      wx.showToast({
        title: '收货人姓名请小于20字',
        icon: 'none',
        duration: 1500
      })
    } else if (e.detail.value.address2.length >= 200) {
      wx.showToast({
        title: '详细地址请小于200字',
        icon: 'none',
        duration: 1500
      })
    } else {
      //console.log(that.data.region)
      wx.request({
        url: util.getHostUrl() + 'userAddress/addAddress',
        data: {
          name: e.detail.value.name,
          phone: e.detail.value.phone,
          address10: that.data.region[0],
          address11: that.data.region[1],
          address12: that.data.region[2],
          address2: e.detail.value.address2,
          id: app.globalData.userId
        },
        success: function (res) {
          console.log(res);
          if(res.data.result==1){
            wx.showToast({
              title: '保存成功',
              icon: 'success',
              duration: 1500
            })
            setTimeout(function () {
              wx.navigateBack({
              })
            }, 2000)
          }
        }
      })
      
      
    }
  },

  /**
   * 选择省市区时显示
   */
  RegionChange: function (e) {
    this.setData({
      region: e.detail.value
    })
  },

  /**
   * 当详细地址有时变色
   */
  textareaBInput(e) {
    this.setData({
      textareaBValue: e.detail.value
    })
  }
  
})