// pages/swipper/add/add.js
var util = require('../../../utils/util.js')
var app=getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    fileList: [],
    
  },

  

  /**
   * 上传图片
   */
  afterRead(event) {
    var that = this
    const  files  = event.detail;
    // 当设置 mutiple 为 true 时, file 为数组格式，否则为对象格式
    console.log(files.file)
    var f = files.file
    for(var i=0;i<f.length;i++){
      //console.log(file)
      wx.uploadFile({
        header:{
          'Authorization':app.globalData.Authorization
        },
        url: util.getHostUrl() + 'swipper/addSwipper',
        filePath: f[i].path,
        name: 'file',
        success(res) {
          console.log(res)
          var relativeList = that.data.fileList
          //console.log(JSON.parse(res.data))
          var data = JSON.parse(res.data)
          if((res.statusCode==200)&&(data.code==200)){
            var temp = {}
            temp.url = data.data
            relativeList.push(temp)
            var temp1= data.realPath
            that.setData({
              fileList:relativeList,   
            })
            wx.showToast({
              title: data.msg  
            })
          } else if ((res.statusCode == 200) && (data.code == 500)){
            wx.showToast({
              title:data.msg,
              icon:'none'
            })
          }else{
            wx.showToast({
              title: '服务器错误',
              icon: 'none'
            })
          }         
        },
        fail(res){
          wx.showToast({
            title: '网络错误',
            icon: 'none'
          })
        }
      })
    }   
  },




  /**
   * 删除
   */
  delete:function(event){
    var index  = event.detail.index
    var that = this
    var f = that.data.fileList
    var temp = f.splice(index,1);
    console.log(temp[0].url)
    wx.request({
      url: util.getHostUrl() + 'swipper/deleteSwipper',
      data: {
        url: temp[0].url,
      },
      header: {
        'Authorization': app.globalData.Authorization
      },
      success: function (res) {
        console.log(res);
        if(res.data.code==200){
          wx.showToast({
            title: res.data.msg,
          })
          that.setData({
            fileList: f
          })
        } else if (res.data.code == 500){
          wx.showToast({
            title: res.data.msg,
            image:'none'
          })
        }else{
          wx.showToast({
            title: '服务器错误',
            image: 'none'
          })
        }
        
      },
      fail(res){
        wx.showToast({
          title: '网络错误',
          image: 'none'
        })
      }
    })


  },


  submit:function(e){
    wx.showToast({
      title: '保存成功',
    })
    setTimeout(function(){
      wx.navigateBack({
        delta: 1
      })
    },2000
    )
    

  }



})