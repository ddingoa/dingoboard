module.exports = {
  lintOnSave : true,
  css : {
    sourceMap : true,
  },
  devServer : {
    proxy:{
      "/api":{
        target:"http://localhost:8085",
        changerOrigin: true,
        secure:false,
        pathRewrite:{
          '^/api/':''
        }
      }
    },
    historyApiFallback: true,
  },
  assetsDir:"./assets/",
  outputDir:"",
  // outputDir:"../../main/resources/static/",
  transpileDependencies : ['vuetify'],
}
