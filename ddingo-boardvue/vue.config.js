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
  outputDir:"/Users/ddingo/Desktop/Project/0.bbodekboardServer/meBoard/src/main/resources/static/",
  //outputDir: "C:/Users/mwpar/Desktop/Project_/0.Project_Source/1.bbodekServer/meBoard/src/main/resources/static",
  // outputDir:"../../main/resources/static/",
  transpileDependencies : ['vuetify'],
}
