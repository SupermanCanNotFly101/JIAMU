// pages/order/detail/detail.js
var util = require('../../../utils/util.js')
var app = getApp()
import { $wuxActionSheet } from '../../../dist/index'
import { $wuxDialog } from '../../../dist/index'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: null,
    orderId: null,
    sumMoney: null,
    payStatus: null,
    addressStatus: null,
    userName: null,
    userPhone: null,
    userAddress: null,
    cart: [],
    //0 未付款 1 已经付款  2 接单   3 已发货  4 交易完成
    payStatusInfo: null,
    expressId: "无"
  },

  getOrderById: function (id) {

    var that = this
    wx.request({
      url: util.getHostUrl() + 'order/getOrderById',
      data: {
        id: id
      },
      success(res) {
        console.log(res)
        var a = res.data
        that.setData({
          orderId: a.orderId,
          sumMoney: a.sumMoney,
          payStatus: a.payStatus,
          addressStatus: a.addressStatus,
          userName: a.userName,
          userPhone: a.userPhone,
          userAddress: a.userAddress,
          cart: a.cart,
          expressId:a.expressId
        })
        
        var info = null

        if (a.payStatus == 0) {
          info = "未付款"
        } else if (a.payStatus == 1) {
          info = "已付款"
        } else if (a.payStatus == 2) {
          info = "已接单 "
        } else if (a.payStatus == 3) {
          info = "已发货 "
        } else if (a.payStatus == 4) {
          info = "交易完成 "
        }
        that.setData({
          payStatusInfo: info
        })



      }
    })

  },





  //////////////////////////////////////

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //console.log(options.id)
    var that = this
    var id = options.id
    that.setData({
      id: id
    })
    that.getOrderById(id)
  },

  deleteOrder() {
    var that = this
    var id = that.data.id
    wx.request({
      url: util.getHostUrl() + 'order/deleteOrder',
      data: {
        id: id
      }, success(res) {
        if (res.data == "ok") {
          wx.showToast({
            title: '删除成功',
          })
          setTimeout(function () {
            wx.navigateBack({
              delta: 1
            })
          }, 2000)
        } else {
          wx.showToast({
            title: '删除失败',
            icon: 'none'
          })
          setTimeout(function () {
            wx.navigateBack({
              delta: 1
            }, 2000)
          })
        }
      }
    })
  },

  showActionSheet2() {
    var that = this
    $wuxActionSheet().showSheet({
      titleText: '处理订单',
      buttons: [{
        text: '接单'
      },
      {
        text: '发货'
      },
      {
        text: '交易完成'
      }
      ],
      buttonClicked(index, item) {
        var payStatus
        if(index==0){
          payStatus = 2
        } else if (index == 1) {

          payStatus = 3
          
        } else if (index == 2) {
          payStatus = 4
        }
        wx.request({
          url: util.getHostUrl() + 'order/updateOrderState',
          data:{
            payStatus:payStatus,
            code:app.globalData.code,
            id:that.data.id
          },
          header: {
            'Authorization': app.globalData.Authorization
          },
          success(res){
            if(res.data=="ok"){
              wx.showToast({
                title: '修改成功',
              })
              that.getOrderById(that.data.id)
            }else{
              wx.showToast({
                title: '修改失败',
                icon:'none'
              })
            }
          }
        })

        return true
      },
      cancelText: '取消',
      cancel() { },
      destructiveButtonClicked() { },
    })
  },

  express(){
    var that = this
     $wuxDialog().prompt({
      resetOnClose: true,
       title: '输入快递编号',
       content: '输入快递编号',
      fieldtype: 'text',
      password: 0,
      defaultText: '',
       placeholder: '请输入快递编号',
      maxlength: -1,
      onConfirm(e, response) {
        wx.request({
          url:util.getHostUrl() + 'order/updateexpress',
          data:{
            code:app.globalData.code,
            id:that.data.id,
            expressId:response
          },
          header: {
            'Authorization': app.globalData.Authorization
          },
          success(res) {
            console.log(res)
            if (res.data == "ok") {
              wx.showToast({
                title: '修改成功',
              })
              that.getOrderById(that.data.id)
            } else {
              wx.showToast({
                title: '修改失败',
                icon: 'none'
              })
            }
          }
        })
        
      },
    })



    // $wuxDialog().prompt({
    //   resetOnClose: true,
    //   title: '输入快递编号',
    //   placeholder: '请输入',
    //   fieldtype: 'number',
    //   //resetOnClose: true,
    //   //title: '提示',
    //   //content: '输入快递编号',
    //   //fieldtype: 'number',
    //   //password: !0,
    //   //defaultText: '',
    //   //placeholder: '请输入Wi-Fi密码',
    //   //maxlength: 8,
    //   onConfirm(e, response) {
    //     wx.request({
    //       url:util.getHostUrl() + 'order/updateexpress',
    //       data:{
    //         code:app.globalData.code,
    //         id:that.data.id,
    //         expressId:response
    //       },
    //       success(res) {
    //         if (res.data == "ok") {
    //           wx.showToast({
    //             title: '修改成功',
    //           })
    //           that.getOrderById(that.data.id)
    //         } else {
    //           wx.showToast({
    //             title: '修改失败',
    //             icon: 'none'
    //           })
    //         }
    //       }
    //     })
        
    //   },
    // })
  }


})