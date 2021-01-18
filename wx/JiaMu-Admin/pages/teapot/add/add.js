// pages/tea/add/add.js
var util = require('../../../utils/util.js')
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    mainFile: [],//商品的主图{"url" : " "}
    fileList: [],//商品的详细图，数组里面多个{"url" : " "}对象
  },

  /**
   * 上传主图
   */
  afterReadMainFile(event) {
    var that = this
    const file = event.detail;// 当设置 mutiple 为 true 时, file 为数组格式，否则为对象格式
    //console.log(file)
    var f = file.file //f.path为临时图片路径   
    wx.uploadFile({
      url: util.getHostUrl() + 'teaPhoto/addFile',
      filePath: f.path,
      name: 'file',
      header: {
        'Authorization': app.globalData.Authorization
      },
      success(res) {
        console.log(res);
        if (res.statusCode == 200) {
          var data = JSON.parse(res.data)
          if (data.code == 200) {
            var temp = []
            var temp1 = {}
            temp1.url = JSON.parse(res.data).data
            temp.push(temp1)
            that.setData({
              mainFile: temp
            })
          } else if (data.code == 500) {
            wx.showToast({
              title: data.msg,
              icon: 'none'
            })
          } else {
            wx.showToast({
              title: '上传失败',
              icon: 'none'
            })
          }

        } else {
          wx.showToast({
            title: '上传失败',
            icon: 'none'
          })
        }
      },
      fail(res) {
        wx.showToast({
          title: '网络出错',
        })
      }
    })
  },



  /**
   * 上传详细图
   */
  afterRead(event) {
    var that = this
    const files = event.detail;
    // 当设置 mutiple 为 true 时, file 为数组格式，否则为对象格式
    //console.log(files.file)
    var f = files.file
    for (var i = 0; i < f.length; i++) {
      //console.log(file)
      wx.uploadFile({
        url: util.getHostUrl() + 'teaPhoto/addFile',
        filePath: f[i].path,
        name: 'file',
        header: {
          'Authorization': app.globalData.Authorization
        },
        success(res) {
          console.log(res);
          if (res.statusCode == 200) {
            var data = JSON.parse(res.data)
            if (data.code == 200) {
              var temp = that.data.fileList
              var temp1 = {}
              temp1.url = JSON.parse(res.data).data
              temp.push(temp1)
              that.setData({
                fileList: temp
              })
            } else if (data.code == 500) {
              wx.showToast({
                title: data.msg,
                icon: 'none'
              })
            } else {
              wx.showToast({
                title: '上传失败',
                icon: 'none'
              })
            }

          } else {
            wx.showToast({
              title: '上传失败',
              icon: 'none'
            })
          }
        },
        fail(res) {
          wx.showToast({
            title: '网络出错',
          })
        }
      })
    }
  },




  /**
   * 删除主图
   */
  deleteMainFile: function () {
    var that = this
    var t = []
    that.setData({
      mainFile: t
    })
  },


  /**
   * 删除详细图
   */
  delete: function (event) {
    var index = event.detail.index //详细图的index
    var that = this
    var f = that.data.fileList
    var temp = f.splice(index, 1);
    that.setData({
      fileList: f
    })
  },


  /**
   * 提交表单数据
   */
  submit(e) {
    var that = this
    //console.log('Default Form Submit \n', e.detail.value)
    //console.log(that.data.region)
    wx.request({
      url: util.getHostUrl() + 'tea/addTea',
      data: {
        teaName: e.detail.value.teaName,
        teaSize: e.detail.value.teaSize,
        teaTaste: e.detail.value.teaTaste,
        teaPrice: e.detail.value.teaPrice,
        teaInfo: e.detail.value.teaInfo,
        teaPhotoUrl: that.data.mainFile[0].url,
        teaPhotoUrlList: that.data.fileList,
        teaType: 2,
      },
      header: {
        'Authorization': app.globalData.Authorization
      },
      success(res) {
        console.log(res)
        if (res.data.code == 200) {
          wx.showToast({
            title: '上传成功'
          })
          setTimeout(function () {
            wx.navigateBack({
              delta: 1
            })
          }, 2000)
        } else {
          wx.showToast({
            title: '上传失败,请检查输入是否合法',
            icon: "none"
          })
        }

      }
    })
  }












})