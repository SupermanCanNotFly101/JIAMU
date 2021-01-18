// pages/address/address.js
var util = require('../../utils/util.js')
var app=getApp()
Page({

  data: {
    scrennHeight: null,
    scrennWidth: null,
    address:[]    
  },

  /**
   * 完善
   * input：userid
   * output:更新address
   */
  getAddress:function(){
    /**
     * 获取地址信息更新address
     */
    var that = this
    wx.request({
      url: util.getHostUrl() + 'userAddress/getAddress',
      data: {
        id: app.globalData.userId
      },
      success: function (res) {
        console.log(res);
        that.setData({
          address:res.data
        })
      }
    })
      
      //console.log('aaaa')
  },

  /**
   * input:addressId
   * output：删除结果
   */
  deleteById:function(id){
    var that = this
    //console.log(e)
    //console.log('用户点击确定')
    
    wx.request({
      url: util.getHostUrl() + 'userAddress/deleteAddress',
      data: {
        id: id
      },
      success: function (res) {
        //console.log(res);
        if(res.data.result){
          wx.showToast({
            title: '删除成功',
            icon: 'success',
            duration: 1000
          })
          //冲新请求数据
          that.getAddress()
        }else{
          wx.showToast({
            title: '删除失败',
            icon: 'fail',
            duration: 1000
          })
          //冲新请求数据
          that.getAddress()
        }
        
      }
    })
  },






/////////////////////////////////////////////////////////////

 
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that=this
    that.setData({
      scrennWidth: app.globalData.windowWidth,
      scrennHeight: app.globalData.windowHeight,
    })
    that.getAddress()
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function (options) {
    var that = this
    that.getAddress()
  },

  /**
   * 增加地址
   */
  add:function(){
    wx.navigateTo({
      url: './add/add',
    })
  },

  /**
   * 更新信息
   */
  update:function(e){
    var id = e.currentTarget.dataset.id;
    var name = e.currentTarget.dataset.name;
    var phone = e.currentTarget.dataset.phone;
    var address1a = e.currentTarget.dataset.address1a;
    var address1b = e.currentTarget.dataset.address1b;
    var address1c = e.currentTarget.dataset.address1c;
    var address2 = e.currentTarget.dataset.address2;
    wx.navigateTo({
      url: './update/update' + '?id=' + id + '&name=' + name + '&phone=' + phone + '&address1a=' + address1a + '&address1b=' + address1b + '&address1c=' + address1c+'&address2='+address2,
    })
  },

  /**
   * 删除
   */
  delete:function(e){
    var that = this
    var id = e.currentTarget.dataset.id;
    wx.showModal({
      title: '确认删除',
      success(res) {
        if (res.confirm) {
          that.deleteById(id)
        } else if (res.cancel) {
          //console.log('用户点击取消')
        }
      }
    })
  }

})