<template>
  <el-form :model="loginForm" :rules="rules" class="login-container" label-position="left"
           label-width="0px" v-loading="loading">
    <h3 class="login_title">ログイン</h3>
    <el-form-item prop="username">
      <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="ユーザー名"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="パスワード"></el-input>
    </el-form-item>
    <el-checkbox class="login_remember" v-model="checked" label-position="left">パスワードを記憶する</el-checkbox>
    <el-form-item style="width: 100%">
      <el-button type="primary" @click.native.prevent="submitClick" style="width: 100%">ログイン</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
  import {postRequest} from '../utils/api'
  export default{
    data(){
      return {
        rules: {
          username: [{required: true, message: 'ユーザー名を入力してください', trigger: 'blur'}],
          password: [{required: true, message: 'パスワードを入力してください', trigger: 'blur'}]
        },
        checked: true,
        loginForm: {
          username: 'admin',
          password: '123'
        },
        loading: false
      }
    },
    methods: {
      submitClick: function () {
        var _this = this;
        this.loading = true;
        postRequest('/login', {
          username: this.loginForm.username,
          password: this.loginForm.password
        }).then(resp=> {
          _this.loading = false;
          if (resp.status == 200) {
            // ログイン成功
            var json = resp.data;
            if (json.status == 'success') {
              _this.$router.replace({path: '/home'});
            } else {
              _this.$alert('ログイン失敗', 'エラー');
            }
          } else {
            // 失敗
            _this.$alert('ログイン失敗', 'エラー');
          }
        }, resp=> {
          _this.loading = false;
          _this.$alert('サーバーが見つかりません ⊙﹏⊙∥', 'エラー');
        });
      }
    }
  }
</script>
<style>
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }

  .login_remember {
    margin: 0px 0px 35px 0px;
    text-align: left;
  }
</style>