const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

function getHostUrl(){

  return "https://supermancantfly.top/"
  //return "http://localhost:8080/"
}

function getPhone(){
  return "18180041191";
}


function getCopyright(){
  return "Copyright © 2020 zoushiyu . All rights reserved.";
}



module.exports = {
  formatTime: formatTime,
  getHostUrl: getHostUrl,
  getPhone:getPhone,
  getCopyright: getCopyright
}
