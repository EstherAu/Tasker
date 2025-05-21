'use strict'
// Template version: 1.2.7
// see http://vuejs-templates.github.io/webpack for documentation.

const path = require('path')

module.exports = {
  dev: {

    // Paths
    assetsSubDirectory: 'static', // 静的ファイルのサブディレクトリ
    assetsPublicPath: '/',         // 静的ファイルの公開パス
    proxyTable: {
      '/': {
        target: 'http://localhost:8081',   // バックエンドサーバーのURL
        changeOrigin: true,
        pathRewrite: {
          '^/': ''
        }
      }
    },

    // Various Dev Server settings（ローカル開発サーバーの設定）
    host: 'localhost', // can be overwritten by process.env.HOST
    port: 8080, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
    autoOpenBrowser: false,
    errorOverlay: true,
    notifyOnErrors: true,
    poll: false, // https://webpack.js.org/configuration/dev-server/#devserver-watchoptions-


    /**
     * Source Maps（ソースマップ関連設定）
     */

    // https://webpack.js.org/configuration/devtool/#development
    // 開発用のソースマップ形式
    devtool: 'eval-source-map',

    // If you have problems debugging vue-files in devtools,
    // set this to false - it *may* help
    // https://vue-loader.vuejs.org/en/options.html#cachebusting
    // キャッシュバスター設定
    cacheBusting: true,

    // CSS Sourcemaps off by default because relative paths are "buggy"
    // with this option, according to the CSS-Loader README
    // (https://github.com/webpack/css-loader#sourcemaps)
    // In our experience, they generally work as expected,
    // just be aware of this issue when enabling this option.
    // CSSソースマップを有効化するかどうか
    cssSourceMap: false,
  },

  build: {
    // Template for index.html（index.html の出力先）
    index: path.resolve(__dirname, '../dist/index.html'),

    // Paths（出力ディレクトリ）
    assetsRoot: path.resolve(__dirname, '../dist'),
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',

    /**
     * Source Maps（本番用ソースマップ）
     */

    productionSourceMap: true,
    // https://webpack.js.org/configuration/devtool/#production
    devtool: '#source-map',

    // Gzip off by default as many popular static hosts such as
    // Surge or Netlify already gzip all static assets for you.
    // Before setting to `true`, make sure to:
    // npm install --save-dev compression-webpack-plugin
    // Gzip 圧縮の有無（通常は不要）
    productionGzip: false,
    productionGzipExtensions: ['js', 'css'],

    // Run the build command with an extra argument to
    // View the bundle analyzer report after build finishes:
    // `npm run build --report`
    // Set to `true` or `false` to always turn it on or off
    // ビルド後、バンドル解析レポートを出力するか
    bundleAnalyzerReport: process.env.npm_config_report
  }
}
