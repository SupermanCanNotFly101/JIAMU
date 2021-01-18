// pages/address/choose/choose.js
var util = require('../../../utils/util.js')
var app = getApp()
import { $wuxDialog } from '../../../dist/index'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    address: []   
  },

  /**
   * 完善
   * input：userid
   * output:更新address
   */
  getAddress: function () {
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
          address: res.data
        })
      }
    })

    //console.log('aaaa')
  },

  /**
   * input:addressId
   * output：删除结果
   */
  deleteById: function (id) {
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
        if (res.data.result) {
          wx.showToast({
            title: '删除成功',
            icon: 'success',
            duration: 1000
          })
          //冲新请求数据
          that.getAddress()
        } else {
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
////////////////////////////////////////////////////





  




  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
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
  add: function () {
    wx.navigateTo({
      url: '../add/add',
    })
  },

  update: function (e) {
    var id = e.currentTarget.dataset.id;
    var name = e.currentTarget.dataset.name;
    var phone = e.currentTarget.dataset.phone;
    var address1a = e.currentTarget.dataset.address1a;
    var address1b = e.currentTarget.dataset.address1b;
    var address1c = e.currentTarget.dataset.address1c;
    var address2 = e.currentTarget.dataset.address2;
    wx.navigateTo({
      url: '../update/update' + '?id=' + id + '&name=' + name + '&phone=' + phone + '&address1a=' + address1a + '&address1b=' + address1b + '&address1c=' + address1c + '&address2=' + address2,
    })
  },


  /**
   * 删除
   */
  delete: function (e) {
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
  },

  /**
   * 到店自取
   */
  myself:function(){
    let pages = getCurrentPages();
    let prevPage = pages[pages.length - 2];
    $wuxDialog().prompt({
      resetOnClose: true,
      title: '输入手机号',
      content: '通过手机号自提',
      fieldtype: 'number',
      defaultText: '',
      placeholder: '请输入手机号',
      maxlength: 11,
      onConfirm(e, response) {
        
        
        prevPage.setData({
          myself: true,
          choose: false,
          address: false,
          myselfPhone:response
        })
        wx.navigateBack({
          delta: 1
        })
      },
    })
    
  },
  /**
   * 选择地址
   * 
   */
  chooseAddress:function(e){
    let pages = getCurrentPages();
    let prevPage = pages[pages.length - 2];
    var address = e.currentTarget.dataset.address
    console.log(address)
    prevPage.setData({
      address: true,
      addressId: address.id,
      addressInfo:address,
      myself: false,
      choose: false,
    })
    wx.navigateBack({
      delta: 1
    })
  },

})