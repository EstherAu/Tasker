/**
 * ホーム画面のメインコンポーネント
 * ユーザー情報・ナビゲーションメニューを管理
 */
 
<template>
  <el-container class="home_container">
    <el-header>
      <div class="home_title">TASKER タスク管理システム</div>
      <div class="home_userinfoContainer">
        <el-dropdown @command="handleCommand">
  <span class="el-dropdown-link home_userinfo">
    {{currentUserName}}<i class="el-icon-arrow-down el-icon--right home_userinfo"></i>
  </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="sysMsg">システム通知</el-dropdown-item>
            <el-dropdown-item command="logout" divided>ログアウト</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu
          default-active="0"
          class="el-menu-vertical-demo" style="background-color: #ECECEC" router>
          <!-- Dynamically render the routing menu to show only non-hidden routes -->
          <template v-for="(item,index) in this.$router.options.routes" v-if="!item.hidden">
            <el-submenu :index="index+''" v-if="item.children.length>1" :key="index">
              <template slot="title">
                <i :class="item.iconCls"></i>
                <span>{{item.name}}</span>
              </template>
              <el-menu-item v-for="child in item.children" v-if="!child.hidden" :index="child.path" :key="child.path">
                {{child.name}}
              </el-menu-item>
            </el-submenu>
            <template v-else>
              <el-menu-item :index="item.children[0].path">
                <i :class="item.children[0].iconCls"></i>
                <span slot="title">{{item.children[0].name}}</span>
              </el-menu-item>
            </template>
          </template>
        </el-menu>
      </el-aside>
      <el-container>
        <el-main>
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">ホーム</el-breadcrumb-item>
            <el-breadcrumb-item v-text="this.$router.currentRoute.name"></el-breadcrumb-item>
          </el-breadcrumb>
          <keep-alive>
            <router-view v-if="this.$route.meta.keepAlive"></router-view>
          </keep-alive>
          <router-view v-if="!this.$route.meta.keepAlive"></router-view>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
</template>
<script>
  import {getRequest} from '../utils/api'
  export default{
    methods: {
      // ドロップダウンメニューのコマンド処理
      handleCommand(command){
        var _this = this;
        if (command == 'logout') {
          // ログアウト処理
          this.$confirm('ログアウトしますか？', '確認', {
            confirmButtonText: 'はい',
            cancelButtonText: 'いいえ',
            type: 'warning'
          }).then(function () {
            getRequest("/logout")
            _this.currentUserName = 'ゲスト';
            _this.$router.replace({path: '/'});
          }, function () {
            // キャンセル時の処理
          })
        }
      }
    },
    mounted: function () {
      // ページ初期表示時のアラート（本番環境では不要）
      this.$alert('演示用のデータベースですので、閲覧・一部編集のみ可能です。全権限での操作はローカル環境でご確認ください。', 'お知らせ', {
        confirmButtonText: 'OK',
        callback: action => {
        }
      });
      var _this = this;
      // ユーザー名取得API呼び出し
      getRequest("/currentUserName").then(function (msg) {
        _this.currentUserName = msg.data || 'ゲスト';
      }, function (msg) {
        _this.currentUserName = 'ゲスト';
      });
    },
    data(){
      return {
        currentUserName: '' // ログイン中のユーザー名
      }
    }
  }
</script>
<style>
  .home_container {
    height: 100%;
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
  }

  .el-header {
    background-color: #20a0ff;
    color: #333;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .el-aside {
    background-color: #ECECEC;
  }

  .el-main {
    background-color: #fff;
    color: #000;
    text-align: center;
  }

  .home_title {
    color: #fff;
    font-size: 22px;
    display: inline;
  }

  .home_userinfo {
    color: #fff;
    cursor: pointer;
  }

  .home_userinfoContainer {
    display: inline;
    margin-right: 20px;
  }
</style>
