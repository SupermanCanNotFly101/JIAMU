//app.js
var util = require('./utils/util.js')


App({
  
  onLaunch: function () {
    var that=this;
    ///////////////////////////////////
    //获取屏幕信息
    //////////////////////////////////
    wx.getSystemInfo({
      success: function (res) {
        //console.log(res);
        // 屏幕宽度、高度
        //console.log('height=' + res.windowHeight);
        //console.log('width=' + res.windowWidth);
        // 高度,宽度 单位为px
        that.globalData.windowHeight = res.windowHeight
        that.globalData.windowWidth=res.windowWidth
        //console.log(that.globalData.windowHeight) 
        //console.log(that.globalData.windowWidth) 
      }
    })

    ///////////////////////////////////
    //用户登录
    //////////////////////////////////
    wx.login({
      success(res) {
        if (res.code) {
          //console.log(res)
          //发起网络请求
          wx.request({
            url: util.getHostUrl() +'userLogin/login',
            data: {
              code: res.code
            },
            success:function(res){
              console.log(res)
              that.globalData.openId=res.data.openId
              that.globalData.userId = res.data.id
              console.log(res) 
              //获取购物车
              wx.request({
                url: util.getHostUrl() + 'cart/getCart',
                data: {
                  userId: res.data.id
                },
                success(res) {
                  console.log(res);
                  that.globalData.cart=res.data
                }
              })
            }
          })
        } else {
          console.log('登录失败！' + res.errMsg)
        }
      }
    })

  },


  onShareAppMessage: function (res) {
    if (res.from === 'button') {
      // 来自页面内转发按钮
      //console.log(res.target)
    }
    return {
      //title: '自定义转发标题',
      //path: '/page/user?id=123'
    }
  },

  


  globalData: {
    windowHeight: null,
    windowWidth: null,
    userId:null,
    openId:null,
    cart:[]
  }
})